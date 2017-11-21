package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Building;
import com.minol.energymonitor.service.BuildingService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    /**
     * 分页查找指定ID楼栋信息
     * @param projectIds 指定ID，默认为1,2,3... *为查找所有楼栋
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/buildings/{projectIds}")
    public PageInfo<Building> selectBuildings(@PathVariable String projectIds,
                                            @RequestParam int areaId,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        if(projectIds.equals("*")){//加入ID
            map.put("projectIds",'*');
        }
        else {
            map.put("projectIds",projectIds.split(","));
        }
        map.put("areaId",areaId);
        List<Building> buildings=buildingService.selectBuildings(map);
        return new PageInfo<Building>(buildings);
    }

    /**
     * 根据项目ID查找下面的楼栋，名字以xx小区xx号楼格式
     * @param projectId 项目ID
     * @return
     */
    @GetMapping("/buildingWithIDAndAreaName/{projectId}")
    public String selectBuildingWithIDAndAreaName(@PathVariable int projectId){
        return JsonUtils.fillResultString(0,"成功", buildingService.selectBuildingWithIDAndAreaName(projectId));
    }
    /**
     * 批量删除楼栋信息
     * @param buildings
     * @return
     */
    @PostMapping("/buildings-del")
    public String batchDeleteBuildings(@RequestBody List<Building> buildings){
        int result=buildingService.batchDeleteBuildings(buildings);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条楼栋信息
     * @param building
     * @return
     */
    @PostMapping("/building")
    public String insertBuilding(@RequestBody Building building){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        building.setCreate_time(timestamp);
        building.setUpdate_time(timestamp);
        int result=buildingService.insertBuilding(building);
        return JsonUtils.fillResultString(0,"成功",result);
    }


    /**
     * 修改一条数据
     * @param id
     * @param building
     * @return
     */
    @PutMapping("/building/{id}")
    public String updateBuilding(@PathVariable int id, @RequestBody Building building){
        Building mbuilding=buildingService.selectBuildingById(id);
        if (mbuilding!=null){
            mbuilding.setName(building.getName());
            mbuilding.setProject_id(building.getProject_id());
            mbuilding.setArea_id(building.getArea_id());
            mbuilding.setBuilding_years(building.getBuilding_years());
            mbuilding.setBuilding_height(building.getBuilding_height());
            mbuilding.setHouse_count(building.getHouse_count());
            mbuilding.setHeating_area(building.getHeating_area());
            mbuilding.setBuliding_type(building.getBuilding_type());
            mbuilding.setUpdate_by(building.getUpdate_by());
        }
        int result=buildingService.updateBuilding(mbuilding);
        return JsonUtils.fillResultString(0,"成功",result);
    }


}
