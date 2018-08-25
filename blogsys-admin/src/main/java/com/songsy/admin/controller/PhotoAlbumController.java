package com.songsy.admin.controller;

import com.songsy.admin.dto.PhotoAlbumDTO;
import com.songsy.admin.dto.PhotoAlbumTreeDTO;
import com.songsy.admin.entity.PhotoAlbum;
import com.songsy.admin.entity.SysResource;
import com.songsy.admin.service.PhotoAlbumService;
import com.songsy.admin.service.SysResourceService;
import com.songsy.base.BaseController;
import com.songsy.core.utils.DateUtils;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 相册功能
 * @author songshuiyang
 * @date 2018/4/5 20:15
 */
@Controller
@RequestMapping("/photoAlbum")
public class PhotoAlbumController extends BaseController {
    
    @Autowired
    private PhotoAlbumService service;
    
    @Resource
    private SysResourceService sysResourceService;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/photoAlbum/photoAlbumList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, PhotoAlbumDTO dto) {
        PhotoAlbum params = new PhotoAlbum();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<PhotoAlbum> records = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(records);
    }

    /**
     * 加载新增界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAddPage")
    public String loadAddPage(Model model) {
        model.addAttribute("categoryList",sysResourceService.getPhotoAlbumCategory());
        return "/admin/photoAlbum/photoAlbumEdit";
    }

    /**
     * 新增/修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(PhotoAlbumDTO dto) {
        PhotoAlbum params = new PhotoAlbum();
        BeanUtils.copyProperties(dto, params);
        // 新增记录
        if (dto.getId() == null) {
            service.insertSelective(params);
        } else {// 修改记录
            service.updateByPrimaryKeySelective(params);
        }
        return MessageUtils.success();
    }
    /**
     * 加载修改界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUpdatePage")
    public String loadUpdatePage(Model model, Integer id) {
        PhotoAlbum photoAlbum = service.selectByPrimaryKey(id);
        if (photoAlbum.getShootingTime() !=null) {
            photoAlbum.setShootingTimeStr(DateUtils.format(photoAlbum.getShootingTime()));
        }
        model.addAttribute("categoryList",sysResourceService.getPhotoAlbumCategory());
        model.addAttribute("record",photoAlbum);
        return "/admin/photoAlbum/photoAlbumEdit";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public PhotoAlbum view(@PathVariable Integer id) {
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
        PhotoAlbum user = new PhotoAlbum();
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
            PhotoAlbum deleteRecord = new PhotoAlbum();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }



    //************************************* 前台开放接口 **************************
    /**
     * 前台相册数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/front/loadListPage", method = RequestMethod.GET)
    public String loadFrontListPage(Model model) {
        List<SysResource> category = sysResourceService.getPhotoAlbumCategory();
        List<PhotoAlbumTreeDTO> photoAlbumTreeList = new ArrayList<>();
        for (SysResource sysResource: category) {
            PhotoAlbumTreeDTO photoAlbumTreeDTO = new PhotoAlbumTreeDTO();
            List<PhotoAlbum> photoAlbumList = service.findPhotoAlbuByCategory(sysResource.getValue());
            photoAlbumTreeDTO.setCategory(sysResource.getValue());
            photoAlbumTreeDTO.setPhotoAlbums(photoAlbumList);
            photoAlbumTreeList.add(photoAlbumTreeDTO);
        }
        model.addAttribute("photoAlbumTreeList",photoAlbumTreeList);
        return "front/photoAlbum";
    }



}
