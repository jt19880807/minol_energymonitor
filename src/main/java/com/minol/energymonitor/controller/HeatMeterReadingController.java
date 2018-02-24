package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.HeatMeterReading;
import com.minol.energymonitor.service.HeatMeterReadingService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class HeatMeterReadingController {

    @Autowired
    HeatMeterReadingService heatMeterReadingService;

    /**
     * 分页查找设备表信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/heatMeterReadings")
    public PageInfo<HeatMeterReading> selectHeatMeterReadings(@RequestParam int projectId,
                                            @RequestParam int areaId,
                                            @RequestParam int buildingId,
                                            @RequestParam int meterId,
                                            @RequestParam Date startDate,
                                            @RequestParam Date endDate,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        map.put("meterId",meterId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<HeatMeterReading> heatMeterReadings=heatMeterReadingService.selectHeatMeterReadings(map);
        return new PageInfo<HeatMeterReading>(heatMeterReadings);
    }

    @GetMapping("/heatMeterReadings/{id}")
    public String selectHeatMeterReadingById(@PathVariable int id){
        HeatMeterReading heatMeterReading=heatMeterReadingService.selectHeatMeterReadingById(id);
        List<HeatMeterReading> heatMeterReadings=new ArrayList<>();
        heatMeterReadings.add(heatMeterReading);
        return JsonUtils.fillResultString(0,"成功",heatMeterReadings);
    }
    /**
     * 批量删除设备读数信息
     * @param heatMeterReadings
     * @return
     */
    @PostMapping("/heatMeterReadings-del")
    public String batchDeleteHeatMeterReadings(@RequestBody List<HeatMeterReading> heatMeterReadings){
        int result=heatMeterReadingService.batchDeleteHeatMeterReadings(heatMeterReadings);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条设备读数信息
     * @param heatMeterReading
     * @return
     */
    @PostMapping("/heatMeterReading")
    public String insertHeatMeterReading(@RequestBody HeatMeterReading heatMeterReading){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        heatMeterReading.setCreate_time(timestamp);
        int result=heatMeterReadingService.insertHeatMeterReading(heatMeterReading);
        return JsonUtils.fillResultString(0,"成功",result);
    }


    /**
     * 修改一条设备读数数据
     * @param id
     * @param heatMeterReading
     * @return
     */
    @PutMapping("/heatMeterReading/{id}")
    public String updateHeatMeterReading(@PathVariable int id, @RequestBody HeatMeterReading heatMeterReading){
        HeatMeterReading mheatMeterReading=heatMeterReadingService.selectHeatMeterReadingById(id);
        int result=0;
        if (mheatMeterReading!=null){
            mheatMeterReading.setInstantaneousflow(heatMeterReading.getInstantaneousflow());
            mheatMeterReading.setDate(heatMeterReading.getDate());
            mheatMeterReading.setPower(heatMeterReading.getPower());
            mheatMeterReading.setAccumulationheat(heatMeterReading.getAccumulationheat());
            mheatMeterReading.setAccumulationcooling(heatMeterReading.getAccumulationcooling());
            mheatMeterReading.setSupplywatertemp(heatMeterReading.getSupplywatertemp());
            mheatMeterReading.setReturnwatertemp(heatMeterReading.getReturnwatertemp());
            result=heatMeterReadingService.updateHeatMeterReading(mheatMeterReading);
        }
        return JsonUtils.fillResultString(0,"成功",result);
    }
}
