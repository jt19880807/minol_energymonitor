package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.HeatConsumption;
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
public interface HeatConsumptionMapper {
    List<HeatConsumption> selectHeatConsumptions(Map map);
}
