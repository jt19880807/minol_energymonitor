package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Collector;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface CollectorMapper {
    List<Collector> selectCollectors(Map map);
    Collector selectCollectorById(int id);
    int batchDeleteCollectors(List<Collector> collectors);
    int insertCollector(Collector collector);
    int updateCollector(Collector collector);

}
