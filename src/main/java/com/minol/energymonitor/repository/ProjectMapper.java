package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.domain.model.EnergyReport;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface ProjectMapper {
    List<Project> selectProjects(Map map);
    List<Project> selectProjectWithIDAndName(Map map);
    Project selectProjectById(int id);
    int batchDeleteProjects(List<Project> projects);
    int insertProject(Project project);
    int updateProject(Project project);
    EnergyReport selectProjectForReport(int projectId);

}
