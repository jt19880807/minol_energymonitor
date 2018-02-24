package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.PowerConsumption;
import com.minol.energymonitor.repository.PowerConsumptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class PowerConsumptionService {

    @Autowired
    PowerConsumptionMapper powerConsumptionMapper;
    /**
     * 查询指定ID的耗电量信息
     * @param map
     * @return
     */
    public List<PowerConsumption> selectPowerConsumptions(Map map){
        return powerConsumptionMapper.selectPowerConsumptions(map);
    }

    /**
     * 获取指定项目的在某个时间段内的耗电量
     * @param map
     * @return
     */
    public Double selectPowerConsumptionByProjectId(Map map){
        return powerConsumptionMapper.selectPowerConsumptionByProjectId(map);
    }


}
