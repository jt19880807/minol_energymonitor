package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.SysRole;
import com.minol.energymonitor.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    List<SysRole> selectAllRoles();
    SysRole selectRoleById(int id);
    int batchDeleteRoles(SysRole role);
    int insertRole(SysRole sysRole);
    int updateRole(SysRole sysRole);
    int updateRolePermission(SysRole sysRole);
}
