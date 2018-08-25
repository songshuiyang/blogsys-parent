package com.songsy.admin.controller;

import com.songsy.admin.dto.UserDTO;
import com.songsy.admin.entity.User;
import com.songsy.admin.service.UserService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.songsy.core.shiro.SecurityUtils.generateSalt;


/**
 * 用户管理
 * @author songshuiyang
 * @date 2017/11/29 21:46
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;


    /**
     * 加载用户列表查询页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/user/userList";
    }
    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, UserDTO user) {
        Map<String, Object> map = new HashMap<>();
        user.setEnable(true);
        Page<User> record = userService.findPageList(pageNum, pageSize, user);
        return covertPageMap(record);
    }

    /**
     * 检查用户名是否存在
     * @param username
     * @return
     */
    @RequestMapping(value = "/checkUser", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> checkUser(String username) {
        Boolean isExist = userService.checkUser(username.trim());
        if (isExist) {
            return MessageUtils.success("用户名可用");
        } else {
            return MessageUtils.fail("用户已存在");
        }
    }
    /**
     * 加载新增用户界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUserAddPage")
    public String userAdd(Model model) {
        return "admin/user/userAdd";
    }
    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        if (StringUtils.isNotBlank(user.getPassword())) {
            // 密码
            String salt = generateSalt();
            String encryptedPassword = com.songsy.core.shiro.SecurityUtils.entryptPassword(user.getUsername(), user.getPassword(), salt);
            user.setPassword(encryptedPassword);
            user.setSalt(salt);
        }
        if (userDTO.getId() == null) {

            userService.insertSelective(user);
        } else {
            userService.updateByPrimaryKeySelective(user);
        }

        return MessageUtils.success();
    }
    /**
     * 加载修改用户界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUserUpdatePage")
    public String loadUserUpdatePage(Model model, Integer id) {
        model.addAttribute("isUpdate", true);
        model.addAttribute("id",id);
        return "admin/user/userAdd";
    }
    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public User view(@PathVariable Integer id) {
        return userService.selectByPrimaryKey(id);
    }
    /**
     * 根据用户id删除用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        user.setEnable(false);
        userService.updateByPrimaryKeySelective(user);
        return MessageUtils.success();
    }
    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        List<String> idStrList = Arrays.asList(ids.split(","));
        for (String idStr: idStrList) {
            Integer id = Integer.parseInt(idStr);
            User deleteRecord = new User();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            userService.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }


    /**
     * 加载角色分配界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAssginRolePage")
    public String loadAssginRolePage(Model model, Integer id) {
        model.addAttribute("userId",id);
        return "admin/user/assignRole";
    }



}
