package com.minol.energymonitor.domain.model;

import com.minol.energymonitor.domain.entity.SysPermission;

import java.util.ArrayList;
import java.util.List;

public class PermissionModel{
    private int id;
    private String title;
    private String url;
    private int menu_type;
    private String code;
    private int level;
    private int status;
    private int parent_id;
    private boolean expand;
    private boolean selected;
    private List<PermissionModel> children=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<PermissionModel> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionModel> children) {
        this.children = children;
    }
}
