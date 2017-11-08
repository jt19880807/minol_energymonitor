package com.minol.energymonitor.controller;

import com.minol.energymonitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {

    @Autowired
    ProjectService projectService;


}
