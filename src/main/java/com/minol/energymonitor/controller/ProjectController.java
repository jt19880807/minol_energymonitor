package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Building;
import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.domain.model.AreaModel;
import com.minol.energymonitor.domain.model.ProjectModel;
import com.minol.energymonitor.domain.model.TreeModel;
import com.minol.energymonitor.service.AreaService;
import com.minol.energymonitor.service.BuildingService;
import com.minol.energymonitor.service.ProjectService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    AreaService areaService;
    @Autowired
    BuildingService buildingService;
    /**
     * 分页查找指定ID和带搜索关键字的项目信息
     * @param ids 指定ID，默认为1,2,3... *为查找所有项目
     * @param keywords 搜索关键字
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/projects/{ids}")
    public PageInfo<Project> selectProjects(@PathVariable String ids,
                                 @RequestParam String keywords,
                                 @RequestParam int num,
                                 @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        if(ids.equals("*")){//加入ID
            map.put("projectIds",'*');
        }
        else {
            map.put("projectIds",ids.split(","));
        }
        map.put("keywords",keywords);//加入关键字
        List<Project> projects=projectService.selectProjects(map);
        return new PageInfo<Project>(projects);
    }

    /**
     * 查找指定ID或者全部项目信息（用于下拉框，只返回ID和Name）
     * @param ids 指定ID，默认为1,2,3... *为查找所有项目
     * @return
     */
    @GetMapping("/projects-list/{ids}")
    public String selectProjects(@PathVariable String ids){
        Map<String, Object> map = new HashMap<String, Object>();
        if(ids.equals("*")){//加入ID
            map.put("projectIds",'*');
        }
        else {
            map.put("projectIds",ids.split(","));
        }
        List<Project> projects=projectService.selectProjects(map);
        return JsonUtils.fillResultString(0,"成功",projectService.selectProjectWithIDAndName(map));
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
        }
        int result=projectService.updateProject(mproject);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    @GetMapping("/projectree/{ids}")
    public String selectProjectTree(@PathVariable String ids){
        Map<String, Object> map = new HashMap<String, Object>();
        if(ids.equals("*")){//加入ID
            map.put("projectIds",'*');
        }
        else {
            map.put("projectIds",ids.split(","));
        }
        List<Project> projects=projectService.selectProjects(map);
        List<Area> areas=new ArrayList<>();
        areas= areaService.selectAreaWithIDAndName(map);
        List<TreeModel> projectModels=new ArrayList<>();
        TreeModel projectModel;
        List<TreeModel> areaModels=new ArrayList<>();
        TreeModel areaModel;
        List<Building> buildings=new ArrayList<>();
        List<TreeModel> buildingModels=new ArrayList<>();
        TreeModel buildingModel;
        if (projects.size()>0){
            for (int i = 0; i < projects.size(); i++) {
                projectModel=new TreeModel();
                projectModel.setTitle(projects.get(i).getName());
                projectModel.setId(projects.get(i).getId());
                projectModel.setType("project");
                projectModel.setExpand(true);
                //map = new HashMap<String, Object>();
                map.put("projectIds",new String[]{projects.get(i).getId()+""});
                areas= areaService.selectAreaWithIDAndName(map);
                if (areas.size()>0) {
                    areaModels=new ArrayList<>();
                    for (int j = 0; j < areas.size(); j++) {
                        areaModel = new TreeModel();
                        areaModel.setId(areas.get(j).getId());
                        areaModel.setTitle(areas.get(j).getName());
                        areaModel.setType("area");
                        map.put("areaId", areas.get(j).getId() + "");
                        buildings = buildingService.selectBuildingWithIDAndName(map);
                        buildingModels = new ArrayList<>();
                        if (buildings.size() > 0) {
                            for (int k = 0; k < buildings.size(); k++) {
                                buildingModel = new TreeModel();
                                buildingModel.setId(buildings.get(k).getId());
                                buildingModel.setTitle(buildings.get(k).getName());
                                buildingModel.setType("building");
                                buildingModels.add(buildingModel);
                            }
                            areaModel.setChildren(buildingModels);
                        }
                        areaModels.add(areaModel);
                    }
                    projectModel.setChildren(areaModels);
                }
                projectModels.add(projectModel);
            }
        }
        return JsonUtils.fillResultString(0,"成功",projectModels);
    }
}
