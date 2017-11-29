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
        //总耗热量
        double heat=heatMeterReadingService.selectHeatByProjectId(map)*3600;
        //总耗电量
        double powerConsumption=powerConsumptionService.selectPowerConsumptionByProjectId(map);
        double scop=0;
        //scop=总耗热量/3.6/总耗电量
        if (powerConsumption>0){
            scop=heat/3.6/powerConsumption;
        }
        //总能耗=0.31*总耗电量
        double totalEnergyConsumption=0.31*powerConsumption;
        //常规能源供热能耗=总耗热量/燃煤锅炉效率(70%)/标准煤热值(29.307MJ/kgcc)
        double conventionalEnergy=heat/0.7/29.307;
        //常规能源替代量=常规能源供热能耗-总能耗
        double replaceEnergy=conventionalEnergy-totalEnergyConsumption;
        //CO2减排量=常规能源替代量*二氧化碳排放银子(2.6kg/kgcc)
        double CO2=replaceEnergy*0.26;
        //SO2减排量=常规能源替代量*二氧化硫排放银子(7.4kg/kgcc)/1000
        double SO2=replaceEnergy*7.4/1000;
        //氮氧化物减排量=常规能源替代量*氮氧化物排放因子（1.6kg/kgcc）/1000
        double nitrogenOxides=replaceEnergy*1.6/1000;
        //颗粒减排量=常规能源替代量*颗粒物排放因子（13.5kg/kgcc）/1000
        double particulates=replaceEnergy*13.5/1000;
        if (energy!=null){
            energy.setHeat(heat);
            energy.setPowerConsumption(powerConsumption);
            energy.setSCOP(scop);
            energy.setTotalEnergyConsumption(totalEnergyConsumption);
            energy.setConventionalEnergy(conventionalEnergy);
            energy.setReplaceEnergy(replaceEnergy);
            energy.setCO2(CO2);
            energy.setNitrogenOxides(nitrogenOxides);
            energy.setParticulates(particulates);
        }
        List<Energy> energyList=new ArrayList<>();
        energyList.add(energy);
        return JsonUtils.fillResultString(0,"成功",energyList);
    }
}
