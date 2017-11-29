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

//    /**
//     * 根据主键ID查找楼栋信息
//     * @param id
//     * @return
//     */
//    public PowerConsumption selectPowerConsumptionById(int id){
//        return powerConsumptionMapper.selectPowerConsumptionById(id);
//    }
//
//    /**
//     * 批量删除
//     * @param powerConsumptions
//     * @return
//     */
//    public int batchDeletePowerConsumptions(List<PowerConsumption> powerConsumptions){
//        return powerConsumptionMapper.batchDeletePowerConsumptions(powerConsumptions);
//    }
//
//    /**
//     * 插入一条楼栋信息
//     * @param powerConsumption
//     * @return
//     */
//    public int insertPowerConsumption(PowerConsumption powerConsumption){
//        return powerConsumptionMapper.insertPowerConsumption(powerConsumption);
//    }
//    /**
//     * 修改楼栋信息
//     * @param powerConsumption
//     * @return
//     */
//    public int updatePowerConsumption(PowerConsumption powerConsumption){
//        return powerConsumptionMapper.updatePowerConsumption(powerConsumption);
//    }


}
