package com.songsy.admin.controller;

import com.songsy.admin.dto.LoginHistoryDTO;
import com.songsy.admin.entity.LoginHistory;
import com.songsy.admin.service.LoginHistoryService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
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

/**
 * 用户登录记录
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@RequestMapping("/loginHistory")
public class LoginHistoryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoginHistoryService service;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/login/loginHistoryList";
    }
    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, LoginHistoryDTO dto) {
        Map<String, Object> map = new HashMap<>();
        LoginHistory params = new LoginHistory();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<LoginHistory> records = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(records);
    }


    /**
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadDetailPage")
    public String loadDetailPage(Model model, Integer id) {
        model.addAttribute("id",id);
        return "admin/blog/tag/blogTagDetail";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public LoginHistory view(@PathVariable Integer id) {
        return service.selectByPrimaryKey(id);
    }
    /**
     * 根据用户id逻辑删除单条记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Integer id) {
        LoginHistory user = new LoginHistory();
        user.setId(id);
        user.setEnable(false);
        service.updateByPrimaryKeySelective(user);
        return MessageUtils.success();
    }
    /**
     * 根据用户id逻辑删除 多条记录
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        List<String> idStrList = Arrays.asList(ids.split(","));
        for (String idStr: idStrList) {
            Integer id = Integer.parseInt(idStr);
            LoginHistory deleteRecord = new LoginHistory();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }


}
