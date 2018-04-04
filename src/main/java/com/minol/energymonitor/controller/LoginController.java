package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.LoginInfo;
import com.minol.energymonitor.domain.entity.SysPermission;
import com.minol.energymonitor.domain.entity.SysRole;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.service.PermissionService;
import com.minol.energymonitor.service.RoleService;
import com.minol.energymonitor.service.UserService;
import com.minol.energymonitor.utils.IPAddressUtils;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    HttpServletRequest request;
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
                SysRole role = roleService.selectRoleById(muser.getRole_id());
                if (role!=null){
                    muser.setRolename(role.getName());
                    muser.setRights(role.getRights());
                    String rights = role.getRights();
                    if(!rights.equals("*")){//拥有部分权限
                        String[] permissionIds = rights.split(",");
                        List<Integer> ids=new ArrayList<>();
                        for (String s:permissionIds){
                            ids.add(Integer.parseInt(s));
                        }
                        List<SysPermission> sysPermissions = permissionService.selectPermissionsByIds(ids);
                        String menus="";
                        String buttons="";
                        for (SysPermission p:sysPermissions){
                            if (p.getMenu_type()==1){
                                menus+=p.getCode()+",";
                            }
                            else if (p.getMenu_type()==2){
                                buttons+=p.getCode()+",";
                            }
                        }
                        muser.setMenus(menus);
                        muser.setButtons(buttons);
                    }
                }
                muser.setLoginInfo(userService.selectLastLoginByUserId(muser.getId()));
                List<SysUser> users=new ArrayList<>();
                users.add(muser);
                //插入一条登录记录
                LoginInfo loginInfo=new LoginInfo();
                loginInfo.setLastlogin_ip(user.getClientIP());
//                Timestamp timestamp=new Timestamp(System.currentTimeMillis());
                loginInfo.setLastlogin_time(new Timestamp(System.currentTimeMillis()));
                loginInfo.setUser_id(muser.getId());
                loginInfo.setLastlogin_city(IPAddressUtils.getAddressByIP(user.getClientIP()));
                userService.insertLoginInfo(loginInfo);
                //登录成功
                result= JsonUtils.fillResultString(200,"成功",users);
            }
            else {
                //密码错误
                result= JsonUtils.fillResultString(200,"密码错误","");
            }
        }
        else {
            //用户不存在
            result= JsonUtils.fillResultString(200,"用户不存在","");
        }
        return result;
    }

    @GetMapping("/getIPAddress/{ip:.+}")
    public String getIPAddress(@PathVariable String ip){
        return IPAddressUtils.getAddressByIP(ip);
    }

    @GetMapping("/getIP")
    public String getIP() {
        try {
            return IPAddressUtils.getIpAddress(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
