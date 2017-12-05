package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.AverageTemp;
import com.minol.energymonitor.domain.model.Energy;
import com.minol.energymonitor.service.AverageTempService;
import com.minol.energymonitor.service.HeatMeterReadingService;
import com.minol.energymonitor.service.PowerConsumptionService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AverageTempController {

    @Autowired
    AverageTempService averageTempService;
    @Autowired
    HeatMeterReadingService heatMeterReadingService;
    @Autowired
    PowerConsumptionService powerConsumptionService;
    /**
     * 分页按照项目ID查找平均温度信息
     * @param projectId 项目ID
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/averageTemps")
    public PageInfo<AverageTemp> selectAverageTemps(@RequestParam int projectId,
                                                    @RequestParam Date startDate,
                                                    @RequestParam Date endDate,
                                                    @RequestParam int num,
                                                    @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<AverageTemp> averageTemps=averageTempService.selectAverageTemps(map);
        return new PageInfo<AverageTemp>(averageTemps);
    }

    /**
     * 获取指定项目的最新的平均温度等参数
     * @param projectId
     * @return
     */
    @GetMapping("/lastAverageTempsByProjectId")
    public String selectLastAverageTempsByProjectId(@RequestParam int projectId) throws ParseException {
        List<Energy> energyList=new ArrayList<>();
        Energy energy=averageTempService.selectLastAverageTempsByProjectId(projectId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        Date dtNow=new Date();
        Date dtStart=null;
        int month=now.get(Calendar.MONTH) + 1;
        int year=now.get(Calendar.YEAR);
        if (month<11){
            dtStart=sdf.parse((year-1)+"-11-01");
        }
        else {
            dtStart=sdf.parse((year)+"-11-01");
        }
        if (energy != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId",projectId);
            map.put("startDate",dtStart);
            map.put("endDate",dtNow);
            //总耗热量
            double heat = heatMeterReadingService.selectHeatByProjectId(map) * 3600;
            //总耗电量
            double powerConsumption = powerConsumptionService.selectPowerConsumptionByProjectId(map);
            energy.setHeat(heat);
            energy.setPowerConsumption(powerConsumption);
        }
        energyList.add(energy);
        return JsonUtils.fillResultString(0,"成功",energyList);

    }
}
