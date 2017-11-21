package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Building;
import com.minol.energymonitor.domain.model.TreeModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface BuildingMapper {
    List<Building> selectBuildings(Map map);
    List<Building> selectBuildingWithIDAndName(Map map);
    List<TreeModel> selectBuildingWithIDAndAreaName(int projectId);
    Building selectBuildingById(int id);
    int batchDeleteBuildings(List<Building> buildings);
    int insertBuilding(Building building);
    int updateBuilding(Building building);
}
