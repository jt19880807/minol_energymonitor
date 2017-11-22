package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Collector;
import com.minol.energymonitor.service.CollectorService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CollectorController {

    @Autowired
    CollectorService collectorService;

    /**
     * 分页查找采集器信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/collectors")
    public PageInfo<Collector> selectCollectors(@RequestParam int projectId,
                                            @RequestParam int areaId,
                                            @RequestParam int buildingId,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        List<Collector> collectors=collectorService.selectCollectors(map);
        return new PageInfo<Collector>(collectors);
    }/**
     * 根据项目ID或者小区ID或者楼栋ID查找下面的采集器,仅返回ID和Number
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @return
     */
    @GetMapping("/collectorsWithIDAndNumber")
    public String selectCollectorWithIDAndNumber(@RequestParam int projectId,
                                            @RequestParam int areaId,
                                            @RequestParam int buildingId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        List<Collector> collectors=collectorService.selectCollectorWithIDAndNumber(map);
        return JsonUtils.fillResultString(0,"成功",collectors);
    }

    @GetMapping("/collectors/{id}")
    public String selectCollectorById(@PathVariable int id){
        Collector collector=collectorService.selectCollectorById(id);
        List<Collector> collectors=new ArrayList<>();
        collectors.add(collector);
        return JsonUtils.fillResultString(0,"成功",collectors);
    }
    /**
     * 批量删除采集器信息
     * @param collectors
     * @return
     */
    @PostMapping("/collectors-del")
    public String batchDeleteCollectors(@RequestBody List<Collector> collectors){
        int result=collectorService.batchDeleteCollectors(collectors);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条采集器信息
     * @param collector
     * @return
     */
    @PostMapping("/collector")
    public String insertCollector(@RequestBody Collector collector){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        collector.setCreate_time(timestamp);
        collector.setUpdate_time(timestamp);
        int result=collectorService.insertCollector(collector);
        return JsonUtils.fillResultString(0,"成功",result);
    }


    /**
     * 修改一条采集器数据
     * @param id
     * @param collector
     * @return
     */
    @PutMapping("/collector/{id}")
    public String updateCollector(@PathVariable int id, @RequestBody Collector collector){
        Collector mcollector=collectorService.selectCollectorById(id);
        if (mcollector!=null){
            mcollector.setNumber(collector.getNumber());
            mcollector.setBuilding_id(collector.getBuilding_id());
            mcollector.setUpdate_by(collector.getUpdate_by());
        }
        int result=collectorService.updateCollector(mcollector);
        return JsonUtils.fillResultString(0,"成功",result);
    }


}
