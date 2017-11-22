package com.minol.energymonitor.domain.model;

import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Building;

import java.util.List;

public class AreaModel extends Area {
    private List<Building> buildings;
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
