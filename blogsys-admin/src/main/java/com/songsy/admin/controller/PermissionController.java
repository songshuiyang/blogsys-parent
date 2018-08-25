package com.songsy.admin.controller;

import com.songsy.admin.common.DictEnum;
import com.songsy.admin.dto.PermissionDTO;
import com.songsy.admin.entity.Permission;
import com.songsy.admin.entity.PermisssionTreeGrid;
import com.songsy.admin.entity.PermisssionXTree;
import com.songsy.admin.service.PermissionService;
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
 * 博客文章标签
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PermissionService service;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "/admin/permission/permissionList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, PermissionDTO dto) {
        Map<String, Object> map = new HashMap<>();
        Permission params = new Permission();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<Permission> record = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(record);
    }
    /**
     * 加载新增页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAddPage")
    public String loadAddPage(Model model) {
        model.addAttribute("isUpdatePage",false);
        return "/admin/permission/permissionEdit";
    }
    /**
     * 加载修改页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadUpdatePage")
    public String loadUpdatePage(Model model, Integer id) {
        model.addAttribute("isUpdatePage",true);
        model.addAttribute("permission",service.selectByPrimaryKey(id));
        return "/admin/permission/permissionEdit";
    }
    /**
     * 新增,修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> edit(PermissionDTO dto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        // 根据选择的父级id查找父级层
        Permission parent = service.selectByPrimaryKey(permission.getParentId());
        // 第一级
        if (null == parent) {
            permission.setParentId(0);
            permission.setLevel(1);
        } else {
            // 第二级
            if (permission.getPermissionType() == DictEnum.System.PermissionType.菜单.getIndex() &&
                    parent.getPermissionType() == DictEnum.System.PermissionType.菜单.getIndex()) {
                permission.setLevel(2);
            } else if (permission.getPermissionType() == DictEnum.System.PermissionType.按钮.getIndex() ||
                    permission.getPermissionType() == DictEnum.System.PermissionType.权限.getIndex()) {
                // 第三级
                permission.setLevel(3);
            }
        }
        // 新增记录
        if (dto.getId() == null) {
            service.insertSelective(permission);
        } else { // 修改记录
            service.updateByPrimaryKeySelective(permission);
        }
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
        return "/admin/permission/permissionDetail";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Permission view(@PathVariable Integer id) {
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
        Permission user = new Permission();
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
            Permission deleteRecord = new Permission();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }

    /**
     * 获取权限tree数据
     * @return
     */
    @RequestMapping(value = "getPermissionTreeGrid", method = {RequestMethod.GET})
    @ResponseBody
    public List<PermisssionTreeGrid> getPermissionTreeGrid() {
        return service.getPermissionTreeGrid();
    }

    /**
     * 获取权限XTree数据 -使用layui社区插件
     * @return
     */
    @RequestMapping(value = "getPermissionXTree/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public List<PermisssionXTree> getPermissionXTree(@PathVariable Integer id) {
        return service.getPermissionXTree(id);
    }
}
