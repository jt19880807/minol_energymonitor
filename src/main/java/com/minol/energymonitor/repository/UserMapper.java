package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<SysUser> selectAllUsers();
    SysUser selectUserByName(String name);
}
