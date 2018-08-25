package com.songsy.admin.controller;

import com.songsy.admin.dto.RoleDTO;
import com.songsy.admin.entity.*;
import com.songsy.admin.service.*;
import com.songsy.base.BaseController;
import com.songsy.core.utils.MessageUtils;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 角色
 * @author songshuiyang
 * @date 2018/3/3 21:46
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private RoleService service;

    @Autowired
    private UserService userService;

    /**
     * 加载列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "/admin/role/roleList";
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
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, RoleDTO dto) {
        Role params = new Role();
        BeanUtils.copyProperties(dto,params);
        params.setEnable(true);
        Page<Role> records = service.findPageList(pageNum, pageSize, params);
        return covertPageMap(records);
    }
    
    /**
     * 加载新增界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAddPage")
    public String loadAddPage(Model model) {
        return "/admin/role/roleEdit";
    }
    
    /**
     * 新增/修改记录
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add(RoleDTO dto) {
        Role params = new Role();
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
        model.addAttribute("role",service.selectByPrimaryKey(id));
        return "/admin/role/roleEdit";
    }
    /**
     * 根据id获取记录
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Role view(@PathVariable Integer id) {
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
        Role user = new Role();
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
            Role deleteRecord = new Role();
            deleteRecord.setId(id);
            deleteRecord.setEnable(false);
            service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }

    /**
     * 根据用户id获取所属角色(打tag标记)
     * 包括不属于的角色
     * @param userId
     * @return
     */
    @RequestMapping(value = "getRolesByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getRolesByUserId(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize,@RequestParam("userId") int userId) {
        List<Role> allRoleList = service.findAllRole();
        List<Role> ownRoleList = service.findRolesByUserId(userId);
        Set<Integer> ownRoleId = new HashSet<>();
        for (Role ownRole :ownRoleList) {
            ownRoleId.add(ownRole.getId());
        }

        List<Map<String, Object>> record = new ArrayList<>();
        for (Role allRole: allRoleList) {
            Map<String,Object> map = new HashMap<>();
            // 如果是所属角色, 则打tag标记, layui复选框默认选中
            if (ownRoleId.contains(allRole.getId())) {
                map.put("LAY_CHECKED",true);
            } else {
                map.put("LAY_CHECKED",false);
            }
            map.put("id",allRole.getId());
            map.put("roleCode",allRole.getRoleCode());
            map.put("roleName",allRole.getRoleName());
            map.put("roleDescribe",allRole.getRoleDescribe());
            record.add(map);
        }

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("data", record);
        resultMap.put("code", 00);
        resultMap.put("count", record.size());
        resultMap.put("msg", "success");
        return resultMap;
    }

    /**
     * 根据用户id更新用户角色
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "updateRoleUserRelationship", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> updateRoleUserRelationship(@RequestParam("userId") String userId, @RequestParam("roleIds") String roleIds) {
        List<String> roleIdStrList = Arrays.asList(roleIds.split(","));
        Integer userIdInt = Integer.parseInt(userId);
        // 先删除该用户所有的用户角色关系
        UserRole userRole = new UserRole();
        userRole.setUserId(userIdInt);
        service.deleteUserRoleRelation(userRole);
        // 插入新的关系
        for (String idStr: roleIdStrList) {
            Integer roleIdInt = Integer.parseInt(idStr);
            UserRole userRole1 = new UserRole();
            userRole1.setUserId(userIdInt);
            userRole1.setRoleId(roleIdInt);
            service.insertUserRoleRelation(userRole1);
        }
        return MessageUtils.success();
    }

    /**
     * 加载角色分配界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAssignUserPage")
    public String loadRoleDetailPage(Model model, Integer id) {
        model.addAttribute("roleId",id);
        return "admin/role/assignUser";
    }


    /**
     * 根据角色id获取对应的成员(打tag标记)
     * @param roleId
     * @return
     */
    @RequestMapping(value = "getUsersByRoleId", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getUsersByRoleId(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize,@RequestParam("roleId") int roleId) {
        List<User> allUserList = userService.findAll();
        List<User> ownUserList = service.findUsersByRoleId(roleId);

        Set<Integer> ownUserId = new HashSet<>();
        for (User ownUser :ownUserList) {
            ownUserId.add(ownUser.getId());
        }
        // 属于该角色的用户
        List<Map<String, Object>> ownRecord = new ArrayList<>();
        for (User user: ownUserList) {
            Map<String,Object> map = new HashMap<>();
            map.put("LAY_CHECKED",true);
            map.put("id",user.getId());
            map.put("userName",user.getUsername());
            map.put("nickName",user.getNickname());
            ownRecord.add(map);
        }
        // 不属于该角色的用户
        List<Map<String, Object>> notOwnRecord = new ArrayList<>();
        for (User user: allUserList) {
            if (!ownUserId.contains(user.getId())){
                Map<String,Object> map = new HashMap<>();
                map.put("LAY_CHECKED",false);
                map.put("id",user.getId());
                map.put("userName",user.getUsername());
                map.put("nickName",user.getNickname());
                notOwnRecord.add(map);
            }
        }
        // 取并集
        ownRecord.addAll(notOwnRecord);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("data", ownRecord);
        resultMap.put("code", 00);
        resultMap.put("count", ownRecord.size());
        resultMap.put("msg", "success");
        return resultMap;
    }

    /**
     * 根据角色id更新用户角色关系
     * @param userIds
     * @return
     */
    @RequestMapping(value = "updateUserRoleRelationship", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> updateUserRoleRelationship(@RequestParam("roleId") String roleId, @RequestParam("userIds") String userIds) {
        List<String> userIdStrList = Arrays.asList(userIds.split(","));
        Integer roleIdInt = Integer.parseInt(roleId);
        // 先删除该用户所有的用户角色关系
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleIdInt);
        service.deleteUserRoleRelation(userRole);
        // 插入新的关系
        for (String idStr: userIdStrList) {
            Integer userIdInt = Integer.parseInt(idStr);
            UserRole userRole1 = new UserRole();
            userRole1.setUserId(userIdInt);
            userRole1.setRoleId(roleIdInt);
            service.insertUserRoleRelation(userRole1);
        }
        return MessageUtils.success();
    }


    /**
     * 加载权限分配界面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadAssignPermissionPage")
    public String loadAssignPermissionPage(Model model, Integer id) {
        model.addAttribute("roleId",id);
        return "admin/role/assignPermission";
    }

    /**
     * 根据角色id更新角色权限关系
     * @param roleId
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "updateRolePermRelationship", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> updateRolePermRelationship(@RequestParam("roleId") String roleId, @RequestParam("permissionIds") String permissionIds) {
        List<String> permissionIdsStrList = Arrays.asList(permissionIds.split(","));
        Integer roleIdInt = Integer.parseInt(roleId);
        // 先删除该用户所有的角色权限关系
        RolePermisssion rolePermisssion = new RolePermisssion();
        rolePermisssion.setRoleId(roleIdInt);
        service.deleteRolePermisssionRelation(rolePermisssion);
        // 插入新的关系
        for (String idStr: permissionIdsStrList) {
            Integer permissionIdInt = Integer.parseInt(idStr);
            RolePermisssion rolePermisssion1 = new RolePermisssion();
            rolePermisssion1.setRoleId(roleIdInt);
            rolePermisssion1.setPermissionId(permissionIdInt);
            service.insertRolePermisssionRelation(rolePermisssion1);
        }
        return MessageUtils.success();
    }
}
