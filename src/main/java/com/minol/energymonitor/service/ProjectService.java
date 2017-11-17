package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.repository.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class ProjectService {
    @Autowired
    ProjectMapper projectMapper;
    /**
     * 查询指定ID的和带搜索关键字的项目信息
     * @param map
     * @return
     */
    public List<Project> selectProjects(Map map){
        return projectMapper.selectProjects(map);
    }

    /**
     * 查询指定ID的和带搜索关键字的项目信息
     * @param map
     * @return
     */
    public List<Project> selectAllProjects(Map map){
        return projectMapper.selectAllProjects(map);
    }

    /**
     * 根据主键ID查找项目信息
     * @param id
     * @return
     */
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
