package com.songsy.admin.controller;

import com.songsy.admin.dto.UserDTO;
import com.songsy.admin.entity.LoginHistory;
import com.songsy.admin.entity.MenuBar;
import com.songsy.admin.entity.User;
import com.songsy.admin.entity.UserRole;
import com.songsy.admin.service.*;
import com.songsy.core.utils.MessageUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.songsy.core.shiro.SecurityUtils.generateSalt;

/**
 * @author songshuiyang
 * @title: 登录注册控制器
 * @description:
 * @date 2017/12/17 11:51
 */
@Controller
@RequestMapping("/system")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogArticlesService blogArticlesService;

    @Autowired
    private RoleService service;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginHistoryService historyService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private MenuService menuService;

    /**
     * 加载后台主页
     * @return
     */
    @RequestMapping(value = "/admin", method = {RequestMethod.GET})
    public String loadAdminPage() {
        return "admin";
    }

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(User user, HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        LoginHistory loginHistory = new LoginHistory();
        boolean loginStatus = true;
        try {
            subject.login(token);
            // 获取一些用户信息放到request的session中
            User userDB = userService.getByUsername(user.getUsername());
            user.setId(userDB.getId());
            user.setNickname(userDB.getNickname());
            user.setHeadPortrait(userDB.getHeadPortrait());
            request.getSession().setAttribute("user", user);
            List<MenuBar> menuBars = menuService.getMenuBars();
            request.getSession().setAttribute("menuBars",menuBars);

            loginHistory.setUserId(userDB.getId());
            loginHistory.setUsername(userDB.getUsername());
            loginHistory.setCreatedBy(userDB.getUsername());
            loginHistory.setLastModifiedBy(userDB.getUsername());
        } catch (LockedAccountException e) {
            // 用户被锁定
            loginStatus = false;
            HandlingLoginException(e,request,user,model,loginHistory,"用户被锁定!");

        } catch (ExcessiveAttemptsException e) {
            // 登录失败次数过多
            loginStatus = false;
            HandlingLoginException(e,request,user,model,loginHistory,"登录失败次数过多!");

        } catch (AuthenticationException e) {
            // 用户名密码错误
            loginStatus = false;
            HandlingLoginException(e,request,user,model,loginHistory,"用户名或密码错误!");
        }
        // 插入用户登入记录
        try {
            historyService.insertUserLoginHistory(loginHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!loginStatus) {
            return "redirect:/login";
        }
        return "admin";
    }

    /**
     * 登录异常处理
     */
    private void HandlingLoginException (Exception e,HttpServletRequest request, User user,Model model, LoginHistory loginHistory, String message) {
        e.printStackTrace();
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("message", message);
        loginHistory.setStatus(1);
        loginHistory.setUsername(user.getUsername());
        loginHistory.setRemarks("username: " + user.getUsername() + " password: " +user.getPassword() + " ," + message);
    }
    /**
     * 加载注册页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/front/loadRegisterPage", method = RequestMethod.GET)
    public String loadRegisterPage(Model model) {
        model.addAttribute("randomHeadPortrait",sysResourceService.getRandomHeadPortrait().getValue());
        return "front/register";
    }

    /**
     * 注册操作
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/front/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String salt = generateSalt();
        String encryptedPassword = com.songsy.core.shiro.SecurityUtils.entryptPassword(username, password, salt);
        user.setPassword(encryptedPassword);
        user.setSalt(salt);
        userService.insertSelective(user);
        // 暂时写法, 分配普通用户 TODO
        int lastInsertRecordId = blogArticlesService.getLastInsertRecordId();
        UserRole userRole = new UserRole();
        userRole.setUserId(lastInsertRecordId);
        userRole.setRoleId(2);
        service.insertUserRoleRelation(userRole);
        return MessageUtils.success();
}
    /**
     * 注销退出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //request.getSession().invalidate();
        return "redirect:/login";
    }
}
