package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.repository.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    public List<Project> selectProjects(String ids){

        if ("*".equals(ids)){
            return projectMapper.selectProjects();
        }

        return projectMapper.selectProjectsByIds(ids.split(","));
    }

    /**
     * 批量删除
     * @param projects
     * @return
     */
    public int batchDeleteProjects(List<Project> projects){
        return projectMapper.batchDeleteProjects(projects);
    }

}
