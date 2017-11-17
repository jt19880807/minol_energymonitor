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
     * @param ids 指定ID，默认为1,2,3... *为查找所有楼栋
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/buildings/{ids}")
    public PageInfo<Building> selectBuildings(@PathVariable String ids,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        if(ids.equals("*")){//加入ID
            map.put("ids",'*');
        }
        else {
            map.put("ids",ids.split(","));
        }
        List<Building> buildings=buildingService.selectBuildings(map);
        return new PageInfo<Building>(buildings);
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
