package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.PowerConsumption;
import com.minol.energymonitor.service.PowerConsumptionService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

//    @GetMapping("/powerConsumptions/{id}")
//    public String selectPowerConsumptionById(@PathVariable int id){
//        PowerConsumption powerConsumption=powerConsumptionService.selectPowerConsumptionById(id);
//        List<PowerConsumption> powerConsumptions=new ArrayList<>();
//        powerConsumptions.add(powerConsumption);
//        return JsonUtils.fillResultString(0,"成功",powerConsumptions);
//    }
//    /**
//     * 批量删除设备表信息
//     * @param powerConsumptions
//     * @return
//     */
//    @PostMapping("/powerConsumptions-del")
//    public String batchDeletePowerConsumptions(@RequestBody List<PowerConsumption> powerConsumptions){
//        int result=powerConsumptionService.batchDeletePowerConsumptions(powerConsumptions);
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
//
//    /**
//     * 插入一条设备表信息
//     * @param powerConsumption
//     * @return
//     */
//    @PostMapping("/powerConsumption")
//    public String insertPowerConsumption(@RequestBody PowerConsumption powerConsumption){
//        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//        powerConsumption.setCreate_time(timestamp);
//        int result=powerConsumptionService.insertPowerConsumption(powerConsumption);
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
//
//
//    /**
//     * 修改一条设备表数据
//     * @param id
//     * @param powerConsumption
//     * @return
//     */
//    @PutMapping("/powerConsumption/{id}")
//    public String updatePowerConsumption(@PathVariable int id, @RequestBody PowerConsumption powerConsumption){
//        PowerConsumption mpowerConsumption=powerConsumptionService.selectPowerConsumptionById(id);
//        int result=0;
//        if (mpowerConsumption!=null){
//            mpowerConsumption.setInstantaneousflow(powerConsumption.getInstantaneousflow());
//            mpowerConsumption.setDate(powerConsumption.getDate());
//            mpowerConsumption.setPower(powerConsumption.getPower());
//            mpowerConsumption.setAccumulationheat(powerConsumption.getAccumulationheat());
//            mpowerConsumption.setAccumulationcooling(powerConsumption.getAccumulationcooling());
//            mpowerConsumption.setSupplywatertemp(powerConsumption.getSupplywatertemp());
//            mpowerConsumption.setReturnwatertemp(powerConsumption.getReturnwatertemp());
//            result=powerConsumptionService.updatePowerConsumption(mpowerConsumption);
//        }
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
}
