package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface ProjectMapper {
    List<Project> selectProjectsByIds(String[] ids);
    List<Project> selectProjects();
    int batchDeleteProjects(List<Project> projects);
    int insertProject(Project project);
}
