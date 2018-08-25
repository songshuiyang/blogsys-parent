package com.songsy.admin.controller;

import com.songsy.admin.common.CommonConstant;
import com.songsy.admin.common.DictEnum;
import com.songsy.admin.dto.BlogArticlesDTO;
import com.songsy.admin.entity.*;
import com.songsy.admin.service.*;
import com.songsy.base.BaseController;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 博客文章
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@Api(value="博客文章",description="博客文章接口")
@RequestMapping("/blogArticles")
public class BlogArticlesController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogArticlesService service;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private BlogArticlesTagService blogArticlesTagService;

    @Autowired
    private CommentArticlesService commentArticlesService;

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "/admin/blog/articles/blogArticlesList";
    }
    /**
     * 分页查询
     *
     * @param pageNum   页码
     * @param pageSize  一页记录数
     * @param dto       查询参数
     * @return result
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", response = Map.class, notes = "博客列表分页查询")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, BlogArticlesDTO dto) {
        BlogArticles params = new BlogArticles();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<BlogArticles> records = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(records);
    }
    
    /**
     * 加载新增界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAddPage")
    public String loadAddPage(Model model) {
        model.addAttribute("categoryList",sysResourceService.findResourceByPkey(CommonConstant.SYS_RESOURCE_BLOG_CATEGORY_KEY));
        model.addAttribute("notExistTagList",blogTagService.findAll());
        return "/admin/blog/articles/blogArticlesEdit";
    }
    
    /**
     * 新增/修改记录
     * @param dto
     * @return
     */
    @RequiresPermissions(value={"blog:blogArticles:add", "blog:blogArrrticles:update"}, logical= Logical.OR)
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(BlogArticlesDTO dto) {
        BlogArticles params = new BlogArticles();
        BeanUtils.copyProperties(dto, params);
        // xss
        escapeHtml4(params);
        // 新增记录
        if (dto.getId() == null) {
            service.insertSelective(params);
            // 修改tag记录表
            int lastInsertRecordId = service.getLastInsertRecordId();
            if (StringUtils.isNotBlank(dto.getTag())) {
                List<String> tagIdList = Arrays.asList(dto.getTag().split(","));
                for (String tagId: tagIdList) {
                    Integer tagIdInt = Integer.parseInt(tagId);
                    BlogArticlesTag blogArticlesTag = new BlogArticlesTag();
                    blogArticlesTag.setBlogArticlesId(lastInsertRecordId);
                    blogArticlesTag.setBlogTagId(tagIdInt);
                    blogArticlesTagService.insertSelective(blogArticlesTag);
                }
            }
        } else {// 修改记录
            service.updateByPrimaryKeySelective(params);
            // 处理文章 标签中间表,先删除原先的关系记录
            blogArticlesTagService.deleteArticlesTagByArticlesId(params.getId());
            // 插入新的关系记录
            if (StringUtils.isNotBlank(dto.getTag())) {
                List<String> tagIdList = Arrays.asList(dto.getTag().split(","));
                for (String tagId: tagIdList) {
                    Integer tagIdInt = Integer.parseInt(tagId);
                    BlogArticlesTag blogArticlesTag = new BlogArticlesTag();
                    blogArticlesTag.setBlogArticlesId(params.getId());
                    blogArticlesTag.setBlogTagId(tagIdInt);
                    blogArticlesTagService.insertSelective(blogArticlesTag);
                }
            }

        }

        return MessageUtils.success();
    }

    /**
     * 防xss
     * @param articles
     */
    private void escapeHtml4(BlogArticles articles){
        // 防XSS攻击
        articles.setAuthor(StringEscapeUtils.escapeHtml4(articles.getAuthor()));
        articles.setOutline(StringEscapeUtils.escapeHtml4(articles.getOutline()));
        articles.setRemarks(StringEscapeUtils.escapeHtml4(articles.getRemarks()));
        articles.setCategory(StringEscapeUtils.escapeHtml4(articles.getCategory()));
        articles.setCoverImage(StringEscapeUtils.escapeHtml4(articles.getCoverImage()));
        articles.setTag(StringEscapeUtils.escapeHtml4(articles.getTag()));
    }
    /**
     * 加载修改界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUpdatePage")
    public String loadUpdatePage(Model model, Integer id) {
        model.addAttribute("isUpdate", true);
        model.addAttribute("id",id);
        List<BlogTag> allBlogTagsList = blogTagService.findAll();
        List<BlogTag> existTagList = blogArticlesTagService.findTagByArticlesId(id);
        List<SysResource> categoryList = sysResourceService.findResourceByPkey(CommonConstant.SYS_RESOURCE_BLOG_CATEGORY_KEY);
        List<BlogTag> notExistTagList = new ArrayList<>();
        if (existTagList.size() != 0) {
            for (BlogTag allBlogTags: allBlogTagsList) {
                boolean isExist = false;
                for (BlogTag existTag: existTagList) {
                    if (allBlogTags.getId().equals(existTag.getId())) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    notExistTagList.add(allBlogTags);
                }
            }
        } else {
            notExistTagList = allBlogTagsList;
        }
        // 博客分类
        model.addAttribute("categoryList",categoryList);
        // 已经选择的标签
        model.addAttribute("existTagList",existTagList);
        // 未选择的标签
        model.addAttribute("notExistTagList", notExistTagList);
        return "/admin/blog/articles/blogArticlesEdit";
    }
    /**
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadDetailPage")
    public String loadDetailPage(Model model, Integer id) {
        model.addAttribute("id",id);
        return "/admin/blog/articles/blogArticlesDetail";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public BlogArticles view(@PathVariable Integer id) {
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
        BlogArticles user = new BlogArticles();
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
            BlogArticles deleteRecord = new BlogArticles();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        
        return MessageUtils.success();
    }
    /**
     * 根据用户id 发布/卸载单条记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/{choose}", method = {RequestMethod.PUT})
    @ResponseBody
    public Map<String, Object> publish(@PathVariable Integer id, @PathVariable Integer choose) {
        BlogArticles record = new BlogArticles();
        record.setId(id);
        if (choose == 1) {
            record.setStatus(DictEnum.BlogArticles.Status.发布.getIndex());
        } else {
            record.setStatus(DictEnum.BlogArticles.Status.未发布.getIndex());
        }
        service.updateByPrimaryKeySelective(record);
        
        return MessageUtils.success();
    }

    /**
     * 根据用户id 批量发布多条记录
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> batchPublish(@RequestParam("ids") String ids) {
        List<String> idStrList = Arrays.asList(ids.split(","));
        for (String idStr: idStrList) {
            Integer id = Integer.parseInt(idStr);
            BlogArticles publishRecord = new BlogArticles();
            publishRecord.setId(id);
            publishRecord.setStatus(DictEnum.BlogArticles.Status.发布.getIndex());
            service.updateByPrimaryKeySelective(publishRecord);
        }
        
        return MessageUtils.success();
    }

    /********************************** 向前端开放的接口 ***************************

     /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param dto
     * @return
     */
    @RequestMapping(value = "front/list", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findFrontPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, BlogArticlesDTO dto) {
        BlogArticles params = new BlogArticles();
        BeanUtils.copyProperties(dto,params);
        if (params.getTag() == null){
            params.setTag("");
        }
        if (params.getCategory() == null) {
            params.setCategory("");
        }
        params.setEnable(true);
        Page<BlogArticles> records = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(records);
    }

    /**
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "front/{id}")
    public String loadFrontDetailPage(Model model, @PathVariable Integer id) {
        BlogArticles blogArticles = service.selectByPrimaryKey(id);
        List<CommentArticles> commentList = commentArticlesService.findCommentByArticlesId(id);
        model.addAttribute("randomHeadPortrait",sysResourceService.getRandomHeadPortrait().getValue());
        model.addAttribute("blog",blogArticles);
        model.addAttribute("commentList",commentList);
        model.addAttribute("id",id);
        if (null != blogArticles && null != blogArticles.getHitsNum() ) {
            // 阅读数加1
            BlogArticles blogArticlesUpdate = new BlogArticles();
            blogArticlesUpdate.setId(id);
            blogArticlesUpdate.setHitsNum(blogArticles.getHitsNum() + 1);
            service.updateByPrimaryKeySelective(blogArticlesUpdate);
        }
        return "/front/blog/articles/articlesDetail";
    }

    /**
     * 获取博客热文
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "front/hotArticles", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> hotArticles(@RequestParam("pageSize") int pageSize) {
        List<BlogArticles> records = service.getHotArticlesInCache(pageSize);
        return covertListMap(records);
    }
}
