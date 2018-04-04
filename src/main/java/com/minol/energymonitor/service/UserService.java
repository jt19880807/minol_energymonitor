package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.LoginInfo;
import com.minol.energymonitor.repository.UserMapper;
import com.minol.energymonitor.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public List<SysUser> selectAllUsers() {
        return userMapper.selectAllUsers();
    }
    public SysUser selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }
    public SysUser selectUserById(int id) {
        return userMapper.selectUserById(id);
    }
    public int batchDeletUsers(List<SysUser> users){
        return userMapper.batchDeleteUsers(users);
    }
    public int isnertUser(SysUser sysUser){return  userMapper.insertUser(sysUser);}
    public int updateUser(SysUser sysUser){return  userMapper.updateUser(sysUser);}
    public int insertLoginInfo(LoginInfo loginInfo){return  userMapper.insertLoginInfo(loginInfo);}
    public LoginInfo selectLastLoginByUserId(int userId){return  userMapper.selectLastLoginByUserId(userId);}
}
