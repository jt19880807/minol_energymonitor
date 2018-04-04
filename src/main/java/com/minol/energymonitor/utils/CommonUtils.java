package com.minol.energymonitor.utils;

import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Project;

import java.util.List;

public class CommonUtils {
    public static Integer findProject(List<Project> projects, String projectName){
        Integer id=0;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getName().equals(projectName)){
                id=projects.get(i).getId();
            }
        }
        return id;
    }
    public static Integer findArea(List<Area> areas, String areaName){
        Integer id=0;
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).getName().equals(areaName)){
                id=areas.get(i).getId();
            }
        }
        return id;
    }
}
