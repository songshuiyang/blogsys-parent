package com.songsy.admin.controller;

import com.songsy.admin.dto.BlogTagDTO;
import com.songsy.admin.entity.BlogTag;
import com.songsy.admin.service.BlogTagService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.CacheUtils;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringEscapeUtils;
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
 * 博客文章标签
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@RequestMapping("/blogTag")
public class BlogTagController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BlogTagService service;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "/admin/blog/tag/blogTagList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, BlogTagDTO dto) {
        Map<String, Object> map = new HashMap<>();
        BlogTag params = new BlogTag();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<BlogTag> record = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(record);
    }
    /**
     * 获取所有tag
     * @return
     */
    @RequestMapping(value = "/allList", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> allList() {
        Map<String, Object> map = new HashMap<>();
        List<BlogTag> result = service.findAll();
        map.put("data", result);
        map.put("code", 00);
        map.put("count", result.size());
        map.put("msg", "success");
        return map;
    }
    /**
     * 新增,修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(BlogTagDTO dto) {
        BlogTag params = new BlogTag();
        BeanUtils.copyProperties(dto, params);
        // Xss
        params.setName(StringEscapeUtils.escapeHtml4(params.getName()));
        // 新增记录
        if (dto.getId() == null) {
            service.insertSelective(params);
        } else {
            service.updateByPrimaryKeySelective(params);
        }
        CacheUtils.remove("tagList","tagList");
        return MessageUtils.success();
    }
    /**
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadDetailPage")
    public String loadDetailPage(Model model, Integer id) {
        model.addAttribute("id",id);
        return "/admin/blog/tag/blogTagDetail";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public BlogTag view(@PathVariable Integer id) {
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
        BlogTag user = new BlogTag();
        user.setId(id);
        user.setEnable(false);
        service.updateByPrimaryKeySelective(user);
        CacheUtils.remove("tagList","tagList");
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
            BlogTag deleteRecord = new BlogTag();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        CacheUtils.remove("tagList","tagList");
        return MessageUtils.success();
    }

    //************************** 前端接口start ***********************************

    /**
     * 获取所有tag
     * @return
     */
    @RequestMapping(value = "front/tagList", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> tagList() {
        Map<String, Object> cacheMap = (Map<String, Object>) CacheUtils.get("tagList","tagList");
        if (cacheMap == null) {
            List<BlogTag> result = service.findAll();
            cacheMap = covertListMap(result);
            CacheUtils.put("tagList","tagList",cacheMap);
        }
        return cacheMap;
    }


}
