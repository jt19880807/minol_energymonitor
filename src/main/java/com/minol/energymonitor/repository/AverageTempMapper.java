package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.AverageTemp;
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
public interface AverageTempMapper {
    List<AverageTemp> selectAverageTemps(Map map);
//    PowerConsumption selectPowerConsumptionById(int id);
//    int batchDeletePowerConsumptions(List<PowerConsumption> powerConsumptions);
//    int insertPowerConsumption(PowerConsumption powerConsumption);
//    int updatePowerConsumption(PowerConsumption powerConsumption);
}
