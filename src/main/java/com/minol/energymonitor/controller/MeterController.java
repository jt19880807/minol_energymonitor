package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Meter;
import com.minol.energymonitor.service.MeterService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MeterController {

    @Autowired
    MeterService meterService;

    /**
     * 分页查找设备表信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/meters")
    public PageInfo<Meter> selectMeters(@RequestParam int projectId,
                                            @RequestParam int areaId,
                                            @RequestParam int buildingId,
                                            @RequestParam int collectorId,
                                            @RequestParam int meterType,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        map.put("collectorId",collectorId);
        map.put("meterType",meterType);
        List<Meter> meters=meterService.selectMeters(map);
        return new PageInfo<Meter>(meters);
    }

    @GetMapping("/meters/{id}")
    public String selectMeterById(@PathVariable int id){
        Meter meter=meterService.selectMeterById(id);
        List<Meter> meters=new ArrayList<>();
        meters.add(meter);
        return JsonUtils.fillResultString(0,"成功",meters);
    }

    /**
     *
     * @param projectId
     * @param areaId
     * @param buildingId
     * @return
     */
    @GetMapping("/metersWithIDAndNumber")
    public String selectMeterWithIDAndNumber(@RequestParam int projectId,
                                                 @RequestParam int areaId,
                                                 @RequestParam int buildingId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        List<Meter> collectors=meterService.selectMetersWithIDAndNumber(map);
        return JsonUtils.fillResultString(0,"成功",collectors);
    }

    /**
     * 批量删除设备表信息
     * @param meters
     * @return
     */
    @PostMapping("/meters-del")
    public String batchDeleteMeters(@RequestBody List<Meter> meters){
        int result=meterService.batchDeleteMeters(meters);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条设备表信息
     * @param meter
     * @return
     */
    @PostMapping("/meter")
    public String insertMeter(@RequestBody Meter meter){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        meter.setCreate_time(timestamp);
        meter.setUpdate_time(timestamp);
        int result=meterService.insertMeter(meter);
        return JsonUtils.fillResultString(0,"成功",result);
    }


    /**
     * 修改一条设备表数据
     * @param id
     * @param meter
     * @return
     */
    @PutMapping("/meter/{id}")
    public String updateMeter(@PathVariable int id, @RequestBody Meter meter){
        Meter mmeter=meterService.selectMeterById(id);
        int result=0;
        if (mmeter!=null){
            mmeter.setNumber(meter.getNumber());
            mmeter.setCollector_id(meter.getCollector_id());
            mmeter.setMeter_type(meter.getMeter_type());
            mmeter.setFactory(meter.getFactory());
            mmeter.setModel(meter.getModel());
            mmeter.setPosition(meter.getPosition());
            mmeter.setDoorNum(meter.getDoorNum());
            mmeter.setUpdate_by(meter.getUpdate_by());
            result=meterService.updateMeter(mmeter);
        }
        return JsonUtils.fillResultString(0,"成功",result);
    }


}
