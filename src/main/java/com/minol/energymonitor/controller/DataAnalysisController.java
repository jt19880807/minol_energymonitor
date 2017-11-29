package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.model.Energy;
import com.minol.energymonitor.service.AreaService;
import com.minol.energymonitor.service.AverageTempService;
import com.minol.energymonitor.service.HeatMeterReadingService;
import com.minol.energymonitor.service.PowerConsumptionService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/11/8.
 * 数据分析控制器
 */
@RestController
public class DataAnalysisController {
    @Autowired
    AverageTempService averageTempService;
    @Autowired
    PowerConsumptionService powerConsumptionService;
    @Autowired
    HeatMeterReadingService heatMeterReadingService;

    /**
     * 获取指定项目的指定时间段内的能耗信息（总热耗，总电耗，平均温度，最高温度，最低温度）
     * @param projectId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/energys")
    public String getEnergys(@RequestParam int projectId,
                             @RequestParam Date startDate,
                             @RequestParam Date endDate){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        Energy energy=averageTempService.selectAverageTempsByProjectId(map);
        if (energy!=null){
            energy.setHeat(heatMeterReadingService.selectHeatByProjectId(map)*3600);
            energy.setPowerConsumption(powerConsumptionService.selectPowerConsumptionByProjectId(map));
        }
        List<Energy> energyList=new ArrayList<>();
        energyList.add(energy);
        return JsonUtils.fillResultString(0,"成功",energyList);
    }
}
