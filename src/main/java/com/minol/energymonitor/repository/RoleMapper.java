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
    SysRole selectRoleByName(String name);
}
