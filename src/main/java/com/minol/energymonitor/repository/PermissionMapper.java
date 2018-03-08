package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.SysPermission;
import com.minol.energymonitor.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    List<SysPermission> selectAllPermissions();
    int insertPermission(SysPermission sysPermission);
    int updatePermission(SysPermission sysPermission);
    int batchDeletPermissions(SysPermission sysPermission);
}
