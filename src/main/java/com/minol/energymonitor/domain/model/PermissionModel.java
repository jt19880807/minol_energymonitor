package com.minol.energymonitor.domain.model;

import com.minol.energymonitor.domain.entity.SysPermission;

import java.util.ArrayList;
import java.util.List;

public class PermissionModel extends SysPermission {
    private List<SysPermission> sysPermissionChildren=new ArrayList<>();

    public List<SysPermission> getSysPermissionChildren() {
        return sysPermissionChildren;
    }

    public void setSysPermissionChildren(List<SysPermission> sysPermissionChildren) {
        this.sysPermissionChildren = sysPermissionChildren;
    }
}
