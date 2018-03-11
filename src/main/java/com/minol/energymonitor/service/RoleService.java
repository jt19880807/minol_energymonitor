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
    public SysRole selectRoleById(int id) {
        return roleMapper.selectRoleById(id);
    }
    public int batchDeletRoles(SysRole role){
        return roleMapper.batchDeleteRoles(role);
    }
    public int insertRole(SysRole sysRole){return  roleMapper.insertRole(sysRole);}
    public int updateRole(SysRole sysRole){return  roleMapper.updateRole(sysRole);}
    public int updateRolePermission(SysRole sysRole){return roleMapper.updateRolePermission(sysRole);}
}
