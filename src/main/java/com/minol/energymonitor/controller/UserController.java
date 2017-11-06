package com.minol.energymonitor.controller;

import com.minol.energymonitor.repository.UserMapper;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String selectAllUsers(){
        return JsonUtils.fillResultString(0,"成功", userMapper.selectAllUsers());
    }

    @RequestMapping("user")
    public String selectUserByName(){
        List<SysUser> users=new ArrayList<SysUser>(){{
            add(userMapper.selectUserByName("admin"));
        }};
        return JsonUtils.fillResultString(0,"成功",users);
    }

    /**
     * 批量删除用户用户信息(假删除，将status字段改为1)
     * @return
     */
    @PostMapping("/users-del")
    public String batchDelete(@RequestBody List<SysUser> users){

        return null;
    }
}
