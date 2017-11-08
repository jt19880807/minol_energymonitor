package com.minol.energymonitor.controller;

import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.service.ProjectService;
import com.minol.energymonitor.service.UserService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;


}
