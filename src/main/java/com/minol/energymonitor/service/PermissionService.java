package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.SysPermission;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.repository.PermissionMapper;
import com.minol.energymonitor.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    public List<SysPermission> selectAllPermissions() {
        return permissionMapper.selectAllPermissions();
    }
    public List<SysPermission> selectPermissionsByIds(List<Integer> ids) {
        return permissionMapper.selectPermissionsByIds(ids);
    }
    public int insertPermission(SysPermission sysPermission){return  permissionMapper.insertPermission(sysPermission);}
    public int updatePermission(SysPermission sysPermission){return  permissionMapper.updatePermission(sysPermission);}
    public int batchDeletPermissions(SysPermission sysPermission){return  permissionMapper.batchDeletPermissions(sysPermission);}
}
