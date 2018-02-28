package com.minol.energymonitor.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class SysPermission {
    private int id;
    private String name;
    private String url;
    private int menu_type;
    private String code;
    private int level;
    private int status;
    private int parent_id;
    private List<SysPermission> childPermission=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(int menu_type) {
        this.menu_type = menu_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<SysPermission> getChildPermission() {
        return childPermission;
    }

    public void setChildPermission(List<SysPermission> childPermission) {
        this.childPermission = childPermission;
    }
}
