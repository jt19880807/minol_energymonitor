package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.HeatConsumption;
import com.minol.energymonitor.domain.entity.PowerConsumption;
import com.minol.energymonitor.domain.model.ChartModal;
import com.minol.energymonitor.service.PowerConsumptionService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PowerConsumptionController {

    @Autowired
    PowerConsumptionService powerConsumptionService;

    /**
     * 分页查找设备表信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/powerConsumptions")
    public PageInfo<PowerConsumption> selectPowerConsumptions(@RequestParam int projectId,
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
        List<PowerConsumption> powerConsumptions=powerConsumptionService.selectPowerConsumptions(map);
        return new PageInfo<PowerConsumption>(powerConsumptions);
    }
    /**
        * 查找最新一个月的耗电量
     * @param projectId 项目ID
     * @return
     */
    @GetMapping("/lastMonthPowerConsumptions")
    public String selectLastMonthPowerConsumptions(@RequestParam int projectId,
                                                  @RequestParam Date startDate,
                                                  @RequestParam Date endDate){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",0);
        map.put("buildingId",0);
        map.put("meterId",0);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<PowerConsumption> heatConsumptionList=powerConsumptionService.selectPowerConsumptions(map);
        List<ChartModal> chartModals=new ArrayList<>();
        ChartModal chartModal=new ChartModal();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        List<String> texts=new ArrayList<>();
        List<Double> values=new ArrayList<>();
        if (heatConsumptionList.size()>0){
            for (int i = heatConsumptionList.size()-1; i >-1 ; i--) {
                texts.add(formatter.format(heatConsumptionList.get(i).getDate()));
                values.add(heatConsumptionList.get(i).getConsumption());
            }
        }
        chartModal.setText(texts);
        chartModal.setValue(values);
        chartModals.add(chartModal);
        return JsonUtils.fillResultString(0,"成功",chartModals);
    }
}
