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

    /**
     * 查询指定ID的项目信息
     * @param ids
     * @return
     */
    public List<Project> selectProjects(String ids){
        if ("*".equals(ids)){
            return projectMapper.selectProjects();
        }
        return projectMapper.selectProjectsByIds(ids.split(","));
    }

    public Project selectProjectById(int id){
        return projectMapper.selectProjectById(id);
    }

    /**
     * 批量删除
     * @param projects
     * @return
     */
    public int batchDeleteProjects(List<Project> projects){
        return projectMapper.batchDeleteProjects(projects);
    }

    /**
     * 插入一条项目信息
     * @param project
     * @return
     */
    public int insertProject(Project project){
        return projectMapper.insertProject(project);
    }
    /**
     * 修改项目信息
     * @param project
     * @return
     */
    public int updateProject(Project project){
        return projectMapper.updateProject(project);
    }


}
