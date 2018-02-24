package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.SysRole;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.repository.RoleMapper;
import com.minol.energymonitor.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    public List<SysRole> selectAllRoles() {
        return roleMapper.selectAllRoles();
    }
    public SysRole selectRoleByName(String name) {
        return roleMapper.selectRoleByName(name);
    }
    public int batchDeletRoles(List<SysRole> roles){
        return roleMapper.batchDeleteRoles(roles);
    }
    public int isnertUser(SysRole sysRole){return  roleMapper.insertRole(sysRole);}
    public int updateUser(SysRole sysRole){return  roleMapper.updateRole(sysRole);}
}
