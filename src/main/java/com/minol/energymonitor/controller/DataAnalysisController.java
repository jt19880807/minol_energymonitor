package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.model.*;
import com.minol.energymonitor.service.*;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;
import java.text.DecimalFormat;
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
    @Autowired
    ProjectService projectService;

    /**
     * 获取指定项目的指定时间段内的能耗信息（总热耗，总电耗，平均温度，最高温度，最低温度以及计算出来的能耗效益和环境效益）
     * @param projectId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/energys")
    public String getEnergys(@RequestParam int projectId,
                             @RequestParam Date startDate,
                             @RequestParam Date endDate) {
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Energy energy =new Energy();
        energy=averageTempService.selectAverageTempsByProjectId(map);
        if (energy != null) {
            //总耗热量
            double heat = heatMeterReadingService.selectHeatByProjectId(map) * 3600;
            //总耗电量
            double powerConsumption = powerConsumptionService.selectPowerConsumptionByProjectId(map);
            double scop = 0;
            //scop=总耗热量/3.6/总耗电量
            if (powerConsumption > 0) {
                scop = heat / 3.6 / powerConsumption;
            }
            //总能耗=0.31*总耗电量
            double totalEnergyConsumption = 0.31 * powerConsumption;
            //常规能源供热能耗=总耗热量/燃煤锅炉效率(70%)/标准煤热值(29.307MJ/kgcc)
            double conventionalEnergy = heat / 0.7 / 29.307;
            //常规能源替代量=常规能源供热能耗-总能耗
            double replaceEnergy = conventionalEnergy - totalEnergyConsumption;
            //CO2减排量=常规能源替代量*二氧化碳排放银子(2.6kg/kgcc)
            double CO2 = replaceEnergy * 0.26;
            //SO2减排量=常规能源替代量*二氧化硫排放银子(7.4kg/kgcc)/1000
            double SO2 = replaceEnergy * 7.4 / 1000;
            //氮氧化物减排量=常规能源替代量*氮氧化物排放因子（1.6kg/kgcc）/1000
            double nitrogenOxides = replaceEnergy * 1.6 / 1000;
            //颗粒减排量=常规能源替代量*颗粒物排放因子（13.5kg/kgcc）/1000
            double particulates = replaceEnergy * 13.5 / 1000;
                energy.setHeat(heat);
                energy.setPowerConsumption(powerConsumption);
                energy.setSCOP(Double.parseDouble(df.format(scop)));
                energy.setTotalEnergyConsumption(Double.parseDouble(df.format(totalEnergyConsumption)));
                energy.setConventionalEnergy(Double.parseDouble(df.format(conventionalEnergy)));
                energy.setReplaceEnergy(Double.parseDouble(df.format(replaceEnergy)));
                energy.setCO2(Double.parseDouble(df.format(CO2)));
                energy.setSO2(Double.parseDouble(df.format(SO2)));
                energy.setNitrogenOxides(Double.parseDouble(df.format(nitrogenOxides)));
                energy.setParticulates(Double.parseDouble(df.format(particulates)));

        }
        List<Energy> energyList = new ArrayList<>();
        energyList.add(energy);
        return JsonUtils.fillResultString(0, "成功", energyList);
    }

    /**
     * 获取指定项目的指定时间段内的能耗信息的历史对比数据
     * @param projectId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/historydata")
    public String getHistoryData(@RequestParam int projectId,
                                 @RequestParam Date startDate,
                                 @RequestParam Date endDate,
                                 @RequestParam int count) {
        String[] keys=new String[]{"耗热量(MJ)","耗电量(kwh)","室内平均温度(℃)","室内最高温度(℃)","室内最低温度(℃)","室外平均温度(℃)","室外最高温度(℃)","室外最低温度(℃)","室内平均湿度","室内最高湿度","室内最低湿度"};
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<Energy> energyList = new ArrayList<>();
        List<HistoryDataModel> historyDataModelArrayList = new ArrayList<>();
        Energy energy =new Energy();
        HistoryDataModel historyDataModel =new HistoryDataModel();
        Calendar calendar = Calendar.getInstance();
        for (int i=0;i<count;i++){

            energy=averageTempService.selectAverageTempsByProjectId(map);
            if (energy==null){
                energy=new Energy();
                energy.setAverageTemp(-10000);
                energy.setMaxTemp(-10000);
                energy.setMinTemp(-10000);
                energy.setOutdoor_averageTemp(-10000);
                energy.setOutdoor_maxTemp(-10000);
                energy.setOutdoor_minTemp(-10000);
                energy.setAverageHumidity(-10000);
                energy.setMaxHumidity(-10000);
                energy.setMinHumidity(-10000);
            }
            energy.setHeat(heatMeterReadingService.selectHeatByProjectId(map) * 3600);
            energy.setPowerConsumption(powerConsumptionService.selectPowerConsumptionByProjectId(map));
            energyList.add(energy);
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, -1);
            startDate=calendar.getTime();
            map.put("startDate", startDate);

            calendar.setTime(endDate);
            calendar.add(Calendar.YEAR, -1);
            endDate=calendar.getTime();
            map.put("endDate", endDate);
        }

        for (int j=0;j<11;j++){
            historyDataModel =new HistoryDataModel();
            historyDataModel.setKey(keys[j]);
            switch (j){
                case 0:
                    historyDataModel.setValue0(energyList.get(0).getHeat()<0?"暂无记录":energyList.get(0).getHeat()+"");
                    historyDataModel.setValue1(energyList.get(1).getHeat()<0?"暂无记录":energyList.get(1).getHeat()+"");
                    break;
                case 1:
                    historyDataModel.setValue0(energyList.get(0).getPowerConsumption()<0?"暂无记录":energyList.get(0).getPowerConsumption()+"");
                    historyDataModel.setValue1(energyList.get(1).getPowerConsumption()<0?"暂无记录":energyList.get(1).getPowerConsumption()+"");
                    break;
                case 2:
                    historyDataModel.setValue0(energyList.get(0).getAverageTemp()==-10000?"暂无数据":energyList.get(0).getAverageTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getAverageTemp()==-10000?"暂无数据":energyList.get(1).getAverageTemp()+"");
                    break;
                case 3:
                    historyDataModel.setValue0(energyList.get(0).getMaxTemp()==-10000?"暂无数据":energyList.get(0).getMaxTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getMaxTemp()==-10000?"暂无数据":energyList.get(1).getMaxTemp()+"");
                    break;
                case 4:
                    historyDataModel.setValue0(energyList.get(0).getMinTemp()==-10000?"暂无数据":energyList.get(0).getMinTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getMinTemp()==-10000?"暂无数据":energyList.get(1).getMinTemp()+"");
                    break;
                case 5:
                    historyDataModel.setValue0(energyList.get(0).getOutdoor_averageTemp()==-10000?"暂无数据":energyList.get(0).getOutdoor_averageTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getOutdoor_averageTemp()==-10000?"暂无数据":energyList.get(1).getOutdoor_averageTemp()+"");
                    break;
                case 6:
                    historyDataModel.setValue0(energyList.get(0).getOutdoor_maxTemp()==-10000?"暂无数据":energyList.get(0).getOutdoor_maxTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getOutdoor_maxTemp()==-10000?"暂无数据":energyList.get(1).getOutdoor_maxTemp()+"");
                    break;
                case 7:
                    historyDataModel.setValue0(energyList.get(0).getOutdoor_minTemp()==-10000?"暂无数据":energyList.get(0).getOutdoor_minTemp()+"");
                    historyDataModel.setValue1(energyList.get(1).getOutdoor_minTemp()==-10000?"暂无数据":energyList.get(1).getOutdoor_minTemp()+"");
                    break;
                case 8:
                    historyDataModel.setValue0(energyList.get(0).getAverageHumidity()==-10000?"暂无数据":energyList.get(0).getAverageHumidity()+"%");
                    historyDataModel.setValue1(energyList.get(1).getAverageHumidity()==-10000?"暂无数据":energyList.get(1).getAverageHumidity()+"%");
                    break;
                case 9:
                    historyDataModel.setValue0(energyList.get(0).getMaxHumidity()==-10000?"暂无数据":energyList.get(0).getMaxHumidity()+"%");
                    historyDataModel.setValue1(energyList.get(1).getMaxHumidity()==-10000?"暂无数据":energyList.get(1).getMaxHumidity()+"%");
                    break;
                case 10:
                    historyDataModel.setValue0(energyList.get(0).getMinHumidity()==-10000?"暂无数据":energyList.get(0).getMinHumidity()+"%");
                    historyDataModel.setValue1(energyList.get(1).getMinHumidity()==-10000?"暂无数据":energyList.get(1).getMinHumidity()+"%");
                    break;
            }
            historyDataModelArrayList.add(historyDataModel);
        }
        return JsonUtils.fillResultString(0,"成功",historyDataModelArrayList);
    }
    @GetMapping("/energyReport")
    public String getEnergyReport(@RequestParam int projectId,
                                             @RequestParam Date startDate,
                                             @RequestParam Date endDate){
        List<EnergyReport> energyReportList=new ArrayList<>();
        EnergyReport energyReport=getEnergyReportByProjectId(projectId,startDate,endDate);
        energyReportList.add(energyReport);
        if (energyReport!=null) {
            return JsonUtils.fillResultString(0, "成功", energyReportList);
        }else {
            return JsonUtils.fillResultString(1, "无数据", energyReportList);
        }
        //return null;
    }

    public EnergyReport getEnergyReportByProjectId(int projectId, Date startDate, Date endDate){
        EnergyReport energyReport = new EnergyReport();
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Energy energy =new Energy();
        energy=averageTempService.selectAverageTempsByProjectId(map);
        if (energy!=null) {
            energyReport=projectService.selectProjectForReport(projectId);
            //总耗热量
            double heat = heatMeterReadingService.selectHeatByProjectId(map) * 3600;
            //总耗电量
            double powerConsumption = powerConsumptionService.selectPowerConsumptionByProjectId(map);
            energyReport.setHeat(heat);
            energyReport.setPowerConsumption(powerConsumption);
            double scop = 0;
            //scop=总耗热量/3.6/总耗电量
            if (powerConsumption > 0) {
                scop = heat / 3.6 / powerConsumption;
            }
            energyReport.setSCOP(Double.parseDouble(df.format(scop)));
            energyReport.setAverageTemp(energy.getAverageTemp());
            //总能耗=0.31*总耗电量
            double totalEnergyConsumption = 0.31 * powerConsumption;
            //常规能源供热能耗=总耗热量/燃煤锅炉效率(70%)/标准煤热值(29.307MJ/kgcc)
            double conventionalEnergy = heat / 0.7 / 29.307;
            //常规能源替代量=常规能源供热能耗-总能耗
            double replaceEnergy = conventionalEnergy - totalEnergyConsumption;
            //CO2减排量=常规能源替代量*二氧化碳排放银子(2.6kg/kgcc)
            double CO2 = replaceEnergy * 0.26;
            //SO2减排量=常规能源替代量*二氧化硫排放银子(7.4kg/kgcc)/1000
            double SO2 = replaceEnergy * 7.4 / 1000;
            //氮氧化物减排量=常规能源替代量*氮氧化物排放因子（1.6kg/kgcc）/1000
            double nitrogenOxides = replaceEnergy * 1.6 / 1000;
            //颗粒减排量=常规能源替代量*颗粒物排放因子（13.5kg/kgcc）/1000
            double particulates = replaceEnergy * 13.5 / 1000;
            EnergyEfficiency energyEfficiency=new EnergyEfficiency();
            energyEfficiency.setTotalEnergyConsumption(Double.parseDouble(df.format(totalEnergyConsumption)));
            energyEfficiency.setConventionalEnergy(Double.parseDouble(df.format(conventionalEnergy)));
            energyEfficiency.setReplaceEnergy(Double.parseDouble(df.format(replaceEnergy)));
            energyReport.setEnergyEfficiency(energyEfficiency);
            EnvironmentalBenefits environmentalBenefits=new EnvironmentalBenefits();
            environmentalBenefits.setCO2(Double.parseDouble(df.format(CO2)));
            environmentalBenefits.setSO2(Double.parseDouble(df.format(SO2)));
            environmentalBenefits.setNitrogenOxides(Double.parseDouble(df.format(nitrogenOxides)));
            environmentalBenefits.setParticulates(Double.parseDouble(df.format(particulates)));
            energyReport.setEnvironmentalBenefits(environmentalBenefits);
            if (energyReport.getSCOP()>=1.8&&energyReport.getAverageTemp()>=16){
                energyReport.setReportResult("合格");
            }
            else {
                energyReport.setReportResult("不合格");
            }
            return energyReport;
        }
        return null;
    }
}
