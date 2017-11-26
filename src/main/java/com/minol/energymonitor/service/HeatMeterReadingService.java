package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.HeatMeterReading;
import com.minol.energymonitor.repository.HeatMeterReadingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class HeatMeterReadingService {

    @Autowired
    HeatMeterReadingMapper heatMeterReadingMapper;
    /**
     * 查询指定ID的楼栋信息
     * @param map
     * @return
     */
    public List<HeatMeterReading> selectHeatMeterReadings(Map map){
        return heatMeterReadingMapper.selectHeatMeterReadings(map);
    }

    /**
     * 根据主键ID查找楼栋信息
     * @param id
     * @return
     */
    public HeatMeterReading selectHeatMeterReadingById(int id){
        return heatMeterReadingMapper.selectHeatMeterReadingById(id);
    }

    /**
     * 批量删除
     * @param heatMeterReadings
     * @return
     */
    public int batchDeleteHeatMeterReadings(List<HeatMeterReading> heatMeterReadings){
        return heatMeterReadingMapper.batchDeleteHeatMeterReadings(heatMeterReadings);
    }

    /**
     * 插入一条楼栋信息
     * @param heatMeterReading
     * @return
     */
    public int insertHeatMeterReading(HeatMeterReading heatMeterReading){
        return heatMeterReadingMapper.insertHeatMeterReading(heatMeterReading);
    }
    /**
     * 修改楼栋信息
     * @param heatMeterReading
     * @return
     */
    public int updateHeatMeterReading(HeatMeterReading heatMeterReading){
        return heatMeterReadingMapper.updateHeatMeterReading(heatMeterReading);
    }


}
