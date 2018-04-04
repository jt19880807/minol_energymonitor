package com.minol.energymonitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class LoginInfo {
    private Integer id;
    private Integer user_id;
    private String lastlogin_ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp lastlogin_time;
    private String lastlogin_city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLastlogin_ip() {
        return lastlogin_ip;
    }

    public void setLastlogin_ip(String lastlogin_ip) {
        this.lastlogin_ip = lastlogin_ip;
    }

    public Timestamp getLastlogin_time() {
        return lastlogin_time;
    }

    public void setLastlogin_time(Timestamp lastlogin_time) {
        this.lastlogin_time = lastlogin_time;
    }

    public String getLastlogin_city() {
        return lastlogin_city;
    }

    public void setLastlogin_city(String lastlogin_city) {
        this.lastlogin_city = lastlogin_city;
    }
}
