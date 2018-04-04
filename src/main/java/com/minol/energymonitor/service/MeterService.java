package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Meter;
import com.minol.energymonitor.repository.MeterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class MeterService {

    @Autowired
    MeterMapper meterMapper;
    /**
     * 查询指定ID的表计信息
     * @param map
     * @return
     */
    public List<Meter> selectMeters(Map map){
        return meterMapper.selectMeters(map);
    }

    /**
     * 根据主键ID查找表计信息
     * @param id
     * @return
     */
    public Meter selectMeterById(int id){
        return meterMapper.selectMeterById(id);
    }

    /**
     *根据采集器ID和表号查询表计信息
     * @param meter
     * @return
     */
    public Meter getMeterByCollectorIdAndMeterNumber(Meter meter){return meterMapper.getMeterByCollectorIdAndMeterNumber(meter);}
    /**
     * 查询表计 只返回ID和Number
     * @param map
     * @return
     */
    public List<Meter> selectMetersWithIDAndNumber(Map map){
        return meterMapper.selectMetersWithIDAndNumber(map);
    }
    /**
     * 批量删除
     * @param meters
     * @return
     */
    public int batchDeleteMeters(List<Meter> meters){
        return meterMapper.batchDeleteMeters(meters);
    }

    /**
     * 插入一条表计信息
     * @param meter
     * @return
     */
    public int insertMeter(Meter meter){
        return meterMapper.insertMeter(meter);
    }
    /**
     * 修改表计信息
     * @param meter
     * @return
     */
    public int updateMeter(Meter meter){
        return meterMapper.updateMeter(meter);
    }

    /**
     * 批量插入表计信息
     * @param meters
     * @return
     */
    public int batchInsertMeter(List<Meter> meters){return meterMapper.batchInsertMeter(meters);}

}
