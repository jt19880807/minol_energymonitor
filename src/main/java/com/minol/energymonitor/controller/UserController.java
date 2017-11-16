package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.repository.UserMapper;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.service.UserService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String selectAllUsers(){
        return JsonUtils.fillResultString(0,"成功", userService.selectAllUsers());
    }


    /**
     * 批量删除用户用户信息(假删除，将status字段改为1)
     * @return
     */
    @PostMapping("/users-del")
    public String batchDelete(@RequestBody List<SysUser> users){
        int result = userService.batchDeletUsers(users);

        return JsonUtils.fillResultString(0,"成功", result);
    }

    @GetMapping("/getusers")
    public PageInfo<SysUser> selectAllUsers(@RequestParam int num,@RequestParam int size){
        PageHelper.startPage(num, size);
        List<SysUser> list = userService.selectAllUsers();
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return  pageInfo;
    }
}
