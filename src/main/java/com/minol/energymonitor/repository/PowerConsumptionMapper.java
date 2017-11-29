package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.PowerConsumption;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface PowerConsumptionMapper {
    List<PowerConsumption> selectPowerConsumptions(Map map);
    Double selectPowerConsumptionByProjectId(Map map);
//    PowerConsumption selectPowerConsumptionById(int id);
//    int batchDeletePowerConsumptions(List<PowerConsumption> powerConsumptions);
//    int insertPowerConsumption(PowerConsumption powerConsumption);
//    int updatePowerConsumption(PowerConsumption powerConsumption);
}
