package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.SysRole;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.service.RoleService;
import com.minol.energymonitor.service.UserService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 获取所有的角色信息
     * @return
     */
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public String selectAllRoles(){
        return JsonUtils.fillResultString(0,"成功", roleService.selectAllRoles());
    }


    /**
     * 批量删除角色信息(假删除，将status字段改为1)
     * @param role
     * @return
     */
    @PostMapping("/roles-del")
    public String batchDelete(@RequestBody SysRole role){
        int result = roleService.batchDeletRoles(role);
        return JsonUtils.fillResultString(0,"成功", result);
    }

    /**
     * 插入一条角色信息
     * @param sysRole
     * @return
     */
    @PostMapping("/role")
    public String insertRole(@RequestBody SysRole sysRole){
        int result=roleService.isnertUser(sysRole);
        return JsonUtils.fillResultString(0,"成功",result);
    }
    /**
     * 修改一条角色信息
     * @param id
     * @param sysRole
     * @return
     */
    @PutMapping("/role/{id}")
    public String updateRole(@PathVariable int id, @RequestBody SysRole sysRole){
//        SysRole mUser=roleService.se(id);
//        if (mUser!=null){
//            mUser.setUsername(sysUser.getUsername());
//            mUser.setRole_id(sysUser.getRole_id());
//            mUser.setProjects(sysUser.getProjects());
//            mUser.setStatus(sysUser.getStatus());
//        }
        sysRole.setId(id);
        int result=roleService.updateUser(sysRole);
        return JsonUtils.fillResultString(0,"成功",result);
    }
    /**
     * 修改指定角色的权限信息
     * @param id
     * @param sysRole
     * @return
     */
    @PutMapping("/editRolePermission/{id}")
    public String updateRolePermission(@PathVariable int id, @RequestBody SysRole sysRole){

        sysRole.setId(id);
        int result=roleService.updateRolePermission(sysRole);
        return JsonUtils.fillResultString(0,"成功",result);
    }
}
