package com.minol.energymonitor.domain.model;

import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Project;

import java.util.List;

public class ProjectModel extends Project {
    private List<AreaModel> areaModels;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AreaModel> getAreaModels() {
        return areaModels;
    }

    public void setAreaModels(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }
}
