package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.AverageTemp;
import com.minol.energymonitor.service.AverageTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AverageTempController {

    @Autowired
    AverageTempService averageTempService;

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

//    @GetMapping("/averageTemps/{id}")
//    public String selectAverageTempById(@PathVariable int id){
//        AverageTemp averageTemp=averageTempService.selectAverageTempById(id);
//        List<AverageTemp> averageTemps=new ArrayList<>();
//        averageTemps.add(averageTemp);
//        return JsonUtils.fillResultString(0,"成功",averageTemps);
//    }
//    /**
//     * 批量删除设备表信息
//     * @param averageTemps
//     * @return
//     */
//    @PostMapping("/averageTemps-del")
//    public String batchDeleteAverageTemps(@RequestBody List<AverageTemp> averageTemps){
//        int result=averageTempService.batchDeleteAverageTemps(averageTemps);
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
//
//    /**
//     * 插入一条设备表信息
//     * @param averageTemp
//     * @return
//     */
//    @PostMapping("/averageTemp")
//    public String insertAverageTemp(@RequestBody AverageTemp averageTemp){
//        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//        averageTemp.setCreate_time(timestamp);
//        int result=averageTempService.insertAverageTemp(averageTemp);
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
//
//
//    /**
//     * 修改一条设备表数据
//     * @param id
//     * @param averageTemp
//     * @return
//     */
//    @PutMapping("/averageTemp/{id}")
//    public String updateAverageTemp(@PathVariable int id, @RequestBody AverageTemp averageTemp){
//        AverageTemp maverageTemp=averageTempService.selectAverageTempById(id);
//        int result=0;
//        if (maverageTemp!=null){
//            maverageTemp.setInstantaneousflow(averageTemp.getInstantaneousflow());
//            maverageTemp.setDate(averageTemp.getDate());
//            maverageTemp.setPower(averageTemp.getPower());
//            maverageTemp.setAccumulationheat(averageTemp.getAccumulationheat());
//            maverageTemp.setAccumulationcooling(averageTemp.getAccumulationcooling());
//            maverageTemp.setSupplywatertemp(averageTemp.getSupplywatertemp());
//            maverageTemp.setReturnwatertemp(averageTemp.getReturnwatertemp());
//            result=averageTempService.updateAverageTemp(maverageTemp);
//        }
//        return JsonUtils.fillResultString(0,"成功",result);
//    }
}
