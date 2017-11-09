package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.service.UserService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public String test(){
        return "Welcome to SpringBoot";
    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String login(@RequestBody SysUser user){
        //返回结果
        String result="";
        SysUser muser=userService.selectUserByName(user.getUsername());
        if (muser!=null){
            if (muser.getPassword().equals(user.getPassword())){
                List<SysUser> users=new ArrayList<>();
                users.add(muser);
//                登录成功
                result= JsonUtils.fillResultString(200,"成功",users);
            }
            else {
//                密码错误
                result= JsonUtils.fillResultString(200,"密码错误","");
            }
        }
        else {
            //用户不存在
            result= JsonUtils.fillResultString(200,"用户不存在","");
        }
        return result;
    }
}
