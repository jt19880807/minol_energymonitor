package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.HeatConsumption;
import com.minol.energymonitor.domain.entity.PowerConsumption;
import com.minol.energymonitor.domain.model.ChartModal;
import com.minol.energymonitor.service.HeatConsumptionService;
import com.minol.energymonitor.service.PowerConsumptionService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HeatConsumptionController {

    @Autowired
    HeatConsumptionService heatConsumptionService;

    /**
     * 查找最新一个月的热耗量
     * @param projectId 项目ID
     * @return
     */
    @GetMapping("/lastMonthHeatConsumptions")
    public String selectLastMonthHeatConsumptions(@RequestParam int projectId,
                                            @RequestParam Date startDate,
                                            @RequestParam Date endDate){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<HeatConsumption> heatConsumptionList=heatConsumptionService.selectPowerConsumptions(map);
        List<ChartModal> chartModals=new ArrayList<>();
        ChartModal chartModal=new ChartModal();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        List<String> texts=new ArrayList<>();
        List<Double> values=new ArrayList<>();
        if (heatConsumptionList.size()>0){
            for (int i = 0; i < heatConsumptionList.size(); i++) {
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
