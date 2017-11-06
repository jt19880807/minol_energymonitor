package com.minol.energymonitor;

import com.minol.energymonitor.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Console;

/**
 * Created by Administrator on 2017/10/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
    @Autowired
    UserService userService;
    //@Test
    public void selectAllUsers(){
        int count= userService.selectAllUsers().size();
        if (count>0){

        }
    }
    @Test
    public void selectUserByName(){
        String name=userService.selectUserByName("admin").getPassword();
    }
}
