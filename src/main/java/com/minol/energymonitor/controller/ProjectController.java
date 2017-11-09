package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.service.ProjectService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
