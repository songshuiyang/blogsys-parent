package com.songsy.admin.controller;

import com.songsy.admin.common.DictEnum;
import com.songsy.admin.dto.CommentArticlesDTO;
import com.songsy.admin.entity.Address;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.entity.CommentArticles;
import com.songsy.admin.service.BlogArticlesService;
import com.songsy.admin.service.CommentArticlesService;
import com.songsy.admin.service.SysResourceService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.IpUtils;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章评论
 * @author songshuiyang
 * @date 2018/3/24 21:46
 */
@Controller
@RequestMapping("/commentArticles")
public class CommentArticlesController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentArticlesService service;

    @Autowired
    private BlogArticlesService blogArticlesService;

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 加载文章列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadCommentArticlesListPage")
    public String loadCommentArticlesListPage(Model model) {
        return "/admin/commentArticles/commentArticlesList";
    }
    /**
     * 加载留言页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadLeavingMessageListPage")
    public String loadLeavingMessageListPage(Model model) {
        return "/admin/commentArticles/leavingMessageList";
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
        params.setType(DictEnum.CommentArticles.Type.评论.getIndex());
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
        model.addAttribute("comment",service.selectByPrimaryKey(id));
        model.addAttribute("id",id);
        return "/admin/commentArticles/commentArticlesEdit";
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

    //************************** 前端开放接口start ***********************************
    /**
     * 新增评论留言
     * @param dto
     * @return
     */
    @RequestMapping(value = "front/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(CommentArticlesDTO dto, HttpServletRequest request) {
        CommentArticles params = new CommentArticles();
        BeanUtils.copyProperties(dto, params);
        params.setEmail(StringEscapeUtils.escapeHtml4(params.getEmail()));
        params.setAuthor(StringEscapeUtils.escapeHtml4(params.getAuthor()));
        params.setContent(StringEscapeUtils.escapeHtml4(params.getContent()));
        // 新增记录
        if (dto.getId() == null) {
            String userAgentStr = request.getHeader("user-agent");
            Address address = IpUtils.getCityInfoByIp(IpUtils.getRemoteIp(request));
            params.setRemarks(userAgentStr);
            params.setAuthorLocationCity(address.getCity());
            params.setAuthorLocationProvince(address.getProvince());

            // 文章表评论数加一
            if (params.getBlogArticlesId()!=null) {
                BlogArticles blogArticles = new BlogArticles();
                blogArticles.setId(params.getBlogArticlesId());
                Integer commentNum = blogArticlesService.selectByPrimaryKey(params.getBlogArticlesId()).getCommentNum();
                commentNum ++ ;
                blogArticles.setCommentNum(commentNum);
                blogArticlesService.updateByPrimaryKeySelective(blogArticles);
            }
            service.insertSelective(params);
        } else {
            service.updateByPrimaryKeySelective(params);
        }
        return MessageUtils.success();
    }

    /**
     * 文章点赞功能
     * @param id
     * @return
     */
    @RequestMapping(value = "front/thumbs/{id}/{choose}", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> thumbs(@PathVariable Integer id, @PathVariable Integer choose ,HttpServletRequest request) {
        BlogArticles blogArticles = new BlogArticles();
        blogArticles.setId(id);
        if (choose == 0) {
            Integer dissatisfiedNum = blogArticlesService.selectByPrimaryKey(id).getDissatisfiedNum();
            dissatisfiedNum ++;
            blogArticles.setDissatisfiedNum(dissatisfiedNum);
        } else {
            Integer satisfactoryNum = blogArticlesService.selectByPrimaryKey(id).getSatisfactoryNum();
            satisfactoryNum ++;
            blogArticles.setSatisfactoryNum(satisfactoryNum);
        }
        blogArticlesService.updateByPrimaryKeySelective(blogArticles);
        return MessageUtils.success();
    }

    /**
     * 评论点赞功能
     * @param id
     * @return
     */
    @RequestMapping(value = "front/thumbs/commmet/{id}/{choose}", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> thumbsCommmet(@PathVariable Integer id, @PathVariable Integer choose ,HttpServletRequest request) {
        CommentArticles commentArticles = new CommentArticles();
        commentArticles.setId(id);
        if (choose == 0) {
            Integer dissatisfiedNum = service.selectByPrimaryKey(id).getDissatisfiedNum();
            dissatisfiedNum ++;
            commentArticles.setDissatisfiedNum(dissatisfiedNum);
        } else {
            Integer satisfactoryNum = service.selectByPrimaryKey(id).getSatisfactoryNum();
            satisfactoryNum ++;
            commentArticles.setSatisfactoryNum(satisfactoryNum);
        }
        service.updateByPrimaryKeySelective(commentArticles);
        return MessageUtils.success();
    }

    /**
     * 加载留言界面
     * @param model
     * @return
     */
    @RequestMapping(value = "front/leavingMessage")
    public String loadLeavingMessagePage(Model model) {
        List<CommentArticles> commentList = service.findLeavingMessage();
        model.addAttribute("commentList",commentList);
        model.addAttribute("randomHeadPortrait",sysResourceService.getRandomHeadPortrait().getValue());
        return "/front/comment/leavingMessage";
    }

    /**
     * 加载畅言界面
     * @param model
     * @return
     */
    @RequestMapping(value = "front/changyan")
    public String loadChangyanPage(Model model) {
        return "/front/comment/changyan";
    }


}
