package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.SysPermission;
import com.minol.energymonitor.domain.entity.SysRole;
import com.minol.energymonitor.service.PermissionService;
import com.minol.energymonitor.service.RoleService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    /**
     * 获取所有的权限信息
     * @return
     */
    @RequestMapping(value = "/permissions-tree",method = RequestMethod.GET)
    public String selectPermissionTree(){
        List<SysPermission> sysPermissions = permissionService.selectAllPermissions();
        List<SysPermission> rootPermission=new ArrayList<>();
        for (SysPermission sysPermission:sysPermissions){
            if (sysPermission.getParent_id()==0){
                rootPermission.add(sysPermission);
            }
        }
        for (SysPermission sysPermission:rootPermission){
            sysPermission.setChildren(getChild(sysPermission.getId(),sysPermissions));
        }
        return JsonUtils.fillResultString(0,"成功", rootPermission);
    }
    /**
     * 获取所有的权限信息
     * @return
     */
    @RequestMapping(value = "/permissions",method = RequestMethod.GET)
    public String selectPermissions(){
        List<SysPermission> sysPermissions = permissionService.selectAllPermissions();
        List<SysPermission> rootPermission=new ArrayList<>();
        List<SysPermission> resultPermission=new ArrayList<>();
        for (SysPermission sysPermission:sysPermissions){
            if (sysPermission.getParent_id()==0){
                rootPermission.add(sysPermission);
            }
        }
        for (SysPermission sysPermission:rootPermission){
            resultPermission.add(sysPermission);
            getChild(sysPermission.getId(),sysPermissions,resultPermission);
        }
        return JsonUtils.fillResultString(0,"成功", resultPermission);
    }
    private void getChild(int id, List<SysPermission> rootMenu,List<SysPermission> resultMenu) {
        // 子菜单
        for (SysPermission menu : rootMenu) {
            if (menu.getParent_id()==id) {
                resultMenu.add(menu);
                getChild(menu.getId(), rootMenu,resultMenu);
            }
        }
    }
    private List<SysPermission> getChild(int id, List<SysPermission> rootMenu) {
        // 子菜单
        List<SysPermission> childList = new ArrayList<>();
        for (SysPermission menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParent_id()==id) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysPermission menu : childList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
    /**
     * 插入一条角色信息
     * @param sysPermission
     * @return
     */
    @PostMapping("/permission")
    public String insertPermission(@RequestBody SysPermission sysPermission){
        int result=permissionService.insertPermission(sysPermission);
        return JsonUtils.fillResultString(0,"成功",result);
    }
    /**
     * 修改一条用户
     * @param id
     * @param sysPermission
     * @return
     */
    @PutMapping("/permission/{id}")
    public String updatePermission(@PathVariable int id, @RequestBody SysPermission sysPermission){
//        SysRole mUser=roleService.se(id);
//        if (mUser!=null){
//            mUser.setUsername(sysUser.getUsername());
//            mUser.setRole_id(sysUser.getRole_id());
//            mUser.setProjects(sysUser.getProjects());
//            mUser.setStatus(sysUser.getStatus());
//        }
        sysPermission.setId(id);
        int result=permissionService.updatePermission(sysPermission);
        return JsonUtils.fillResultString(0,"成功",result);
    }
}
