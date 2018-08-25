package com.songsy.admin.controller;

import com.songsy.admin.common.CommonConstant;
import com.songsy.admin.dto.SysResourceDTO;
import com.songsy.admin.entity.SysResource;
import com.songsy.admin.service.SysResourceService;
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
import java.util.List;
import java.util.Map;

/**
 * 相册管理
 * @author songshuiyang
 * @date 2018/04/06 16:24
 */
@Controller
@RequestMapping("/photoAlbumCategory")
public class PhotoAlbumCategoryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysResourceService service;

    /**
     * 加载相册列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadCategoryListPage(Model model) {
        return "admin/photoAlbumCategory/categoryList";
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
        params.setPkey(CommonConstant.SYS_RESOURCE_PHOTOALBUM_CATEGORY);
        params.setEnable(true);
        Page<SysResource> record = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(record);
    }

    /**
     * 新增,修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(SysResourceDTO dto) {
        SysResource params = new SysResource();
        BeanUtils.copyProperties(dto, params);
        // 新增记录
        if (dto.getId() == null) {
            params.setPkey(CommonConstant.SYS_RESOURCE_PHOTOALBUM_CATEGORY);
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
    public SysResource view(@PathVariable Integer id) {
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
        SysResource user = new SysResource();
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
            SysResource deleteRecord = new SysResource();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }

}
