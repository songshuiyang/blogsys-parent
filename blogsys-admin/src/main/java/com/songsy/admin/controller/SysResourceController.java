package com.songsy.admin.controller;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统资源
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@RequestMapping("/sysResouce")
public class SysResourceController extends BaseController {

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
        return "admin/sysResource/sysResourceList";
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
        Map<String, Object> map = new HashMap<>();
        SysResource params = new SysResource();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<SysResource> record = service.findPageList(pageNum, pageSize, params);
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
        List<SysResource> result = service.findAll();
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
    public Map<String, Object> add(SysResourceDTO dto) {
        SysResource params = new SysResource();
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
     * 加载详情界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadDetailPage")
    public String loadDetailPage(Model model, Integer id) {
        model.addAttribute("id",id);
        return "admin/sysResource/sysResourceDetail";
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
