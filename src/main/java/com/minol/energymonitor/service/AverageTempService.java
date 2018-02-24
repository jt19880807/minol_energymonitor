package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.AverageTemp;
import com.minol.energymonitor.domain.model.Energy;
import com.minol.energymonitor.repository.AverageTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class AverageTempService {

    @Autowired
    AverageTempMapper averageTempMapper;
    /**
     * 查询指定想项目ID的平均温度信息
     * @param map
     * @return
     */
    public List<AverageTemp> selectAverageTemps(Map map){
        return averageTempMapper.selectAverageTemps(map);
    }

    /**
     * 获取指定项目的在某个时间段内的平均温度，最高温度，最低温度
     * @param map
     * @return
     */
    public Energy selectAverageTempsByProjectId(Map map){
        return averageTempMapper.selectAverageTempsByProjectId(map);
    }
    /**
     * 获取指定项目的在某个时间段内的平均温度，最高温度，最低温度
     * @param projectId
     * @return
     */
    public Energy selectLastAverageTempsByProjectId(int projectId){
        return averageTempMapper.selectLastAverageTempsByProjectId(projectId);
    }
}
