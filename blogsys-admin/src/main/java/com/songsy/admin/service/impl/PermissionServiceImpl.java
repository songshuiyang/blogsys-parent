package com.songsy.admin.service.impl;

import com.songsy.admin.dao.PermissionMapper;
import com.songsy.admin.entity.PermisssionTreeGrid;
import com.songsy.admin.entity.Permission;
import com.songsy.admin.entity.PermisssionXTree;
import com.songsy.admin.service.PermissionService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限service
 * @author songshuiyang
 * @date 2018/3/29 21:10
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Integer> implements PermissionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PermissionMapper mapper;

    @Override
    public BaseMapper<Permission,Integer> getMappser(){
        return mapper;
    }

    /**
     * 获取权限tree数据
     * 列表数据
     * @return
     */
    @Override
    public List<PermisssionTreeGrid> getPermissionTreeGrid (){
        List<PermisssionTreeGrid> perlist = mapper.findAllPermission();
        // 查询所有
        List<PermisssionTreeGrid> parentList = new ArrayList<>();
        if(perlist.size()>0){
            for(PermisssionTreeGrid beanOne : perlist){
                if(beanOne.getParentId() ==null || beanOne.getParentId() == 0){
                    List<PermisssionTreeGrid> sonList = mapper.findPermissionChildren(beanOne.getId());
                    //判断是否有子菜单
                    if(sonList.size()>0){
                        for(PermisssionTreeGrid beanTwo : sonList){
                            List<PermisssionTreeGrid> sunlist = mapper.findPermissionChildren(beanTwo.getId());
                            //设置第三级
                            beanTwo.setChildren(sunlist);
                            for(PermisssionTreeGrid beanThree : sonList){
                                List<PermisssionTreeGrid> sanlist = mapper.findPermissionChildren(beanThree.getId());
                                beanThree.setChildren(sanlist);
                            }
                        }
                    }
                    beanOne.setChildren(sonList);
                    parentList.add(beanOne);
                }
            }
        }
        return parentList;
    }

    /**
     * 获取权限XTree数据 -使用layui社区插件
     * 分配权限使用
     * @return
     */
    @Override
    public List<PermisssionXTree> getPermissionXTree (Integer roleId){
        // 所有权限
        List<PermisssionTreeGrid> allPerlist = mapper.findAllPermission();
        // 查找该角色所拥有的权限
        List<PermisssionTreeGrid> rolePerlist = mapper.findPermissionByRoleId(roleId);
        // 返回的Xtree数据
        List<PermisssionXTree> resultList = new ArrayList<>();
        if (allPerlist.size() > 0) {
            for (PermisssionTreeGrid lever1Per:allPerlist) {
                // 先处理一级节点
                if (lever1Per.getParentId() == null || lever1Per.getParentId()==0) {
                    PermisssionXTree lever1xTree = new PermisssionXTree();
                    lever1xTree.setTitle(lever1Per.getName());
                    lever1xTree.setValue(lever1Per.getId());
                    lever1xTree.setDisabled(!lever1Per.getEnable());
                    lever1xTree.setChecked(false);
                    // 判断是否选中
                    long lever1Count = rolePerlist.stream().filter( p -> p.getId().equals(lever1Per.getId())).count();
                    if (lever1Count !=0) {
                        lever1xTree.setChecked(true);
                    }
                    // 处理二级节点
                    List<PermisssionXTree> lever2List=new  ArrayList<>();
                    for (PermisssionTreeGrid lever2Per: allPerlist) {
                        if (lever2Per.getParentId().equals(lever1Per.getId())) {
                            PermisssionXTree lever2xTree = new PermisssionXTree();
                            lever2xTree.setTitle(lever2Per.getName());
                            lever2xTree.setValue(lever2Per.getId());
                            lever2xTree.setDisabled(!lever2Per.getEnable());
                            lever2xTree.setChecked(false);
                            // 判断是lever2xTree否选中
                            long lever2Count = rolePerlist.stream().filter( p -> p.getId().equals(lever2Per.getId())).count();
                            if (lever2Count !=0) {
                                lever2xTree.setChecked(true);
                            }
                            // 处理三级级节点
                            List<PermisssionXTree> lever3List=new  ArrayList<>();
                            for (PermisssionTreeGrid lever3Per: allPerlist) {
                                if (lever3Per.getParentId().equals(lever2Per.getId())) {
                                    PermisssionXTree lever3xTree = new PermisssionXTree();
                                    lever3xTree.setTitle(lever3Per.getName());
                                    lever3xTree.setValue(lever3Per.getId());
                                    lever3xTree.setDisabled(!lever3Per.getEnable());
                                    lever3xTree.setChecked(false);
                                    // 判断是lever3xTree否选中
                                    long lever3Count = rolePerlist.stream().filter( p -> p.getId().equals(lever3Per.getId())).count();
                                    if (lever3Count !=0) {
                                        lever3xTree.setChecked(true);
                                    }
                                    // 三级下去没有子节点了
                                    List<PermisssionXTree> nullList = new ArrayList<>();
                                    lever3xTree.setData(nullList);
                                    lever3List.add(lever3xTree);
                                }
                            }
                            lever2xTree.setData(lever3List);
                            lever2List.add(lever2xTree);
                        }
                    }
                    lever1xTree.setData(lever2List);
                    resultList.add(lever1xTree);
                }
            }
        }
        return resultList;


    }

    public static void main(String[] args) {
        // 查找该角色所拥有的权限
        List<PermisssionTreeGrid> rolePerlist = new ArrayList<>();
        PermisssionTreeGrid permisssionTreeGrid = new PermisssionTreeGrid();
        permisssionTreeGrid.setId(1);
        PermisssionTreeGrid permisssionTreeGrid1 = new PermisssionTreeGrid();
        permisssionTreeGrid1.setId(1);
        PermisssionTreeGrid permisssionTreeGrid2 = new PermisssionTreeGrid();
        permisssionTreeGrid2.setId(2);

        rolePerlist.add(permisssionTreeGrid);
        rolePerlist.add(permisssionTreeGrid1);
        rolePerlist.add(permisssionTreeGrid2);

        long count = rolePerlist.stream().filter( p -> p.getId().equals(1)).count();
        System.out.println(count);

    }

}
