package com.songsy.admin.controller;

import com.songsy.admin.common.CommonConstant;
import com.songsy.admin.dto.SysResourceDTO;
import com.songsy.admin.entity.SysResource;
import com.songsy.admin.service.SysResourceService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.CacheUtils;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 随机头像管理
 * @author songshuiyang
 * @date 2018/3/25 16:24
 */
@Controller
@RequestMapping("/headPortrait")
public class HeadPortraitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysResourceService service;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/sysResource/headPortrait/headPortraitList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, SysResourceDTO dto) {
        SysResource params = new SysResource();
        BeanUtils.copyProperties(dto,params);
        params.setPkey(CommonConstant.SYS_RESOURCE_HEAD_PORTRAIT_KEY);
        params.setEnable(true);
        Page<SysResource> record = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(record);
    }
    /**
     * 加载新增界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAddPage")
    public String userAdd(Model model) {
        return "admin/sysResource/headPortrait/headPortraitEdit";
    }


    //************************** 前端接口start ***********************************
    /**
     * 获取随机头像
     * @return
     */
    @RequestMapping(value = "front/headPortrait", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> headPortrait() {
        Map<String, Object> cacheMap = (Map<String, Object>) CacheUtils.get("categoryList","categoryList");
        if (cacheMap == null) {
            List<SysResource> result = service.findAll();
            cacheMap = covertListMap(result);
            CacheUtils.put("categoryList","categoryList",cacheMap);
        }
        return cacheMap;
    }


}
