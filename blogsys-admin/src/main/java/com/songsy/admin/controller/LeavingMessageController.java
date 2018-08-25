package com.songsy.admin.controller;

import com.songsy.admin.common.DictEnum;
import com.songsy.admin.dto.CommentArticlesDTO;
import com.songsy.admin.entity.CommentArticles;
import com.songsy.admin.service.CommentArticlesService;
import com.songsy.admin.service.SysResourceService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 留言管理
 * @author songshuiyang
 * @date 2018/4/5 10:19
 */
@Controller
@RequestMapping("/leavingMessage")
public class LeavingMessageController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentArticlesService service;

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 加载留言页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadLeavingMessageListPage")
    public String loadLeavingMessageListPage(Model model) {
        return "/admin/leavingMessage/leavingMessageList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, CommentArticlesDTO dto) {
        Map<String, Object> map = new HashMap<>();
        CommentArticles params = new CommentArticles();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        params.setType(DictEnum.CommentArticles.Type.留言.getIndex());
        Page<CommentArticles> record = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(record);
    }

    /**
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadDetailPage")
    public String loadDetailPage(Model model, Integer id) {
        model.addAttribute("id",id);
        return "/admin/blog/tag/commentArticlesDetail";
    }

    /**
     * 加载编辑界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUpdatePage")
    public String loadUpdatePage(Model model, Integer id) {
        model.addAttribute("leaving",service.selectByPrimaryKey(id));
        model.addAttribute("id",id);
        return "/admin/leavingMessage/leavingMessageEdit";
    }
    /**
     * 新增,修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> edit(CommentArticlesDTO dto) {
        CommentArticles params = new CommentArticles();
        BeanUtils.copyProperties(dto, params);
        // 新增记录
        if (dto.getId() == null) {
            service.insertSelective(params);
        } else {
            service.updateByPrimaryKeySelective(params);
        }
        return MessageUtils.success();
    }

    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public CommentArticles view(@PathVariable Integer id) {
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
        CommentArticles user = new CommentArticles();
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
            CommentArticles deleteRecord = new CommentArticles();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }
}
