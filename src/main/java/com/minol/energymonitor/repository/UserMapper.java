package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.LoginInfo;
import com.minol.energymonitor.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<SysUser> selectAllUsers();
    SysUser selectUserByName(String name);
    SysUser selectUserById(int id);
    int batchDeleteUsers(List<SysUser> users);
    int insertUser(SysUser sysUser);
    int updateUser(SysUser sysUser);
    int insertLoginInfo(LoginInfo loginInfo);
    LoginInfo selectLastLoginByUserId(int userId);
}
