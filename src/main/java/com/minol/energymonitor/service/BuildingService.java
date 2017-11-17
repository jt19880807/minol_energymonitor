package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Building;
import com.minol.energymonitor.repository.BuildingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class BuildingService {
    @Autowired
    BuildingMapper buildingMapper;
    /**
     * 查询指定ID的楼栋信息
     * @param map
     * @return
     */
    public List<Building> selectBuildings(Map map){
        return buildingMapper.selectBuildings(map);
    }

    /**
     * 根据主键ID查找楼栋信息
     * @param id
     * @return
     */
    public Building selectBuildingById(int id){
        return buildingMapper.selectBuildingById(id);
    }

    /**
     * 批量删除
     * @param buildings
     * @return
     */
    public int batchDeleteBuildings(List<Building> buildings){
        return buildingMapper.batchDeleteBuildings(buildings);
    }

    /**
     * 插入一条楼栋信息
     * @param building
     * @return
     */
    public int insertBuilding(Building building){
        return buildingMapper.insertBuilding(building);
    }
    /**
     * 修改楼栋信息
     * @param building
     * @return
     */
    public int updateBuilding(Building building){
        return buildingMapper.updateBuilding(building);
    }


}
