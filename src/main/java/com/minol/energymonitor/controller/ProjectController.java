package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.service.ProjectService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /**
     * 获取项目信息
     * @param ids
     * @return
     */
    @GetMapping("/projects/{ids}")
    public String selectProjects(@PathVariable String ids){
        List<Project> projects=projectService.selectProjects(ids);
        return JsonUtils.fillResultString(0,"成功",projects);
    }

    /**
     * 批量删除项目信息
     * @param projects
     * @return
     */
    @PostMapping("/projects-del")
    public String batchDeleteProjects(@RequestBody List<Project> projects){
        int result=projectService.batchDeleteProjects(projects);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条项目信息
     * @param project
     * @return
     */
    @PostMapping("/project")
    public String insertProject(@RequestBody Project project){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        project.setCreate_time(timestamp);
        project.setUpdate_time(timestamp);
        int result=projectService.insertProject(project);
        return JsonUtils.fillResultString(0,"成功",result);
    }

//    /**
//     * 修改一条数据
//     * @param id
//     * @param name
//     * @param principal
//     * @return
//     */
//    @PutMapping("/project/{id}")
//    public String updateProject(@PathVariable int id,
//                                @RequestParam(name = "name",required = false) String name,
//                                @RequestParam(name = "principal",required = false) String principal){
//        Project project=new Project();
//        project.setId(id);
//        project.setName(name);
//        project.setPrincipal(principal);
//        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//        project.setUpdate_time(timestamp);
//        int result=projectService.updateProject(project);
//        return JsonUtils.fillResultString(0,"成功",result);
//    }

    /**
     * 修改一条数据
     * @param id
     * @param project
     * @return
     */
    @PutMapping("/project/{id}")
    public String updateProject(@PathVariable int id, @RequestBody Project project){
        Project mproject=projectService.selectProjectById(id);
        if (mproject!=null){
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            mproject.setName(project.getName());
            mproject.setPrincipal(project.getPrincipal());
            mproject.setContact(project.getContact());
            mproject.setParty_a(project.getParty_a());
            mproject.setConstruction_unit(project.getConstruction_unit());
            mproject.setSupervisor_unit(project.getSupervisor_unit());
            mproject.setDesign_unit(project.getDesign_unit());
            mproject.setUse_unit(project.getUse_unit());
            mproject.setDistrict(project.getDistrict());
            mproject.setAddress(project.getAddress());
            mproject.setUpdate_by(project.getUpdate_by());
            mproject.setUpdate_time(timestamp);
        }
        int result=projectService.updateProject(mproject);
        return JsonUtils.fillResultString(0,"成功",result);
    }
}
