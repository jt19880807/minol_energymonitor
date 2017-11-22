package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Collector;
import com.minol.energymonitor.repository.CollectorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class CollectorService {

    @Autowired
    CollectorMapper collectorMapper;
    /**
     * 查询指定ID的楼栋信息
     * @param map
     * @return
     */
    public List<Collector> selectCollectors(Map map){
        return collectorMapper.selectCollectors(map);
    } /**
     * 查询指定ID的楼栋信息
     * @param map
     * @return
     */
    public List<Collector> selectCollectorWithIDAndNumber(Map map){
        return collectorMapper.selectCollectorWithIDAndNumber(map);
    }

    /**
     * 根据主键ID查找楼栋信息
     * @param id
     * @return
     */
    public Collector selectCollectorById(int id){
        return collectorMapper.selectCollectorById(id);
    }

    /**
     * 批量删除
     * @param collectors
     * @return
     */
    public int batchDeleteCollectors(List<Collector> collectors){
        return collectorMapper.batchDeleteCollectors(collectors);
    }

    /**
     * 插入一条楼栋信息
     * @param collector
     * @return
     */
    public int insertCollector(Collector collector){
        return collectorMapper.insertCollector(collector);
    }
    /**
     * 修改楼栋信息
     * @param collector
     * @return
     */
    public int updateCollector(Collector collector){
        return collectorMapper.updateCollector(collector);
    }


}
