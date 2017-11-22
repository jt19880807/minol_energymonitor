package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Collector;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface CollectorMapper {
    List<Collector> selectCollectors(Map map);
    List<Collector> selectCollectorWithIDAndNumber(Map map);
    Collector selectCollectorById(int id);
    int batchDeleteCollectors(List<Collector> collectors);
    int insertCollector(Collector collector);
    int updateCollector(Collector collector);

}
