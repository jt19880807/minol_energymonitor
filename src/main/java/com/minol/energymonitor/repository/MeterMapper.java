package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Collector;
import com.minol.energymonitor.domain.entity.Meter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface MeterMapper {
    List<Meter> selectMeters(Map map);
    List<Meter> selectMetersWithIDAndNumber(Map map);
    Meter selectMeterById(int id);
    Meter getMeterByCollectorIdAndMeterNumber(Meter meter);
    int batchDeleteMeters(List<Meter> meters);
    int batchInsertMeter(List<Meter> meters);
    int insertMeter(Meter meter);
    int updateMeter(Meter meter);

}
