package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.HeatMeterReading;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface HeatMeterReadingMapper {
    List<HeatMeterReading> selectHeatMeterReadings(Map map);
    HeatMeterReading selectHeatMeterReadingById(int id);
    int batchDeleteHeatMeterReadings(List<HeatMeterReading> heatMeterReadings);
    int insertHeatMeterReading(HeatMeterReading heatMeterReading);
    int updateHeatMeterReading(HeatMeterReading heatMeterReading);
}
