package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.HeatConsumption;
import com.minol.energymonitor.domain.entity.PowerConsumption;
import com.minol.energymonitor.repository.HeatConsumptionMapper;
import com.minol.energymonitor.repository.PowerConsumptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class HeatConsumptionService {

    @Autowired
    HeatConsumptionMapper heatConsumptionMapper;
    /**
     * 查询指定项目ID的耗热量信息
     * @param map
     * @return
     */
    public List<HeatConsumption> selectPowerConsumptions(Map map){
        return heatConsumptionMapper.selectHeatConsumptions(map);
    }



}
