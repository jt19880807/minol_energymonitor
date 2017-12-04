package com.minol.energymonitor.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.minol.energymonitor.domain.model.*;
import com.minol.energymonitor.service.*;
import com.minol.energymonitor.utils.JsonUtils;
import com.minol.energymonitor.utils.PDFUtil;
import com.minol.energymonitor.view.PdfView;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 * 数据分析控制器
 */
@RestController
public class DataAnalysisController {

    @Bean
    public PdfView pdfView() {
        return new PdfView();
    }
    @Autowired
    AverageTempService averageTempService;
    @Autowired
    PowerConsumptionService powerConsumptionService;
    @Autowired
    HeatMeterReadingService heatMeterReadingService;
    @Autowired
    ProjectService projectService;
    @Autowired
    PdfView pdfView;

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

    @RequestMapping("/exportEnergyReport")
    public void downloadPdf(@RequestParam int projectId,
                            @RequestParam Date startDate,
                            @RequestParam Date endDate,
                            @RequestParam boolean shownhxy,
                            @RequestParam boolean showhjxy,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        EnergyReport energyReport=getEnergyReportByProjectId(projectId,startDate,endDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String start=formatter.format(startDate);
        String end=formatter.format(endDate);
        String fileName=energyReport.getProjectName()+"能耗报告.pdf";
        request.setCharacterEncoding("utf-8");
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf;charset=UTF-8");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setCharacterEncoding("utf-8");
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        // 默认A4 大小
        document.setPageSize(PageSize.A4);
        //头部标题
        Paragraph title = PDFUtil.getParagraph(new Chunk(String.valueOf("能耗报告"), new Font(PDFUtil.bfChinese, 14, Font.BOLD)));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER,-2);

        PdfPTable table = new PdfPTable(4);
        table.setSpacingBefore(20f);
        PdfPCell cell = new PdfPCell(PDFUtil.getParagraph("项目名称:"));
        cell.setFixedHeight(20);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        Paragraph text = PDFUtil.getParagraph(energyReport.getProjectName());
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setColspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("项目地址:"));
        cell.setPaddingTop(10);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getProjectAddress());
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setPaddingTop(10);
        cell.setColspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("制冷/制热面积:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getHeating_area()+"㎡");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph("建筑性质:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getBuilding_type());
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("建筑年限:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getBuilding_years()+"");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph("户数/房间数:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getHouse_count()+"");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("评估时间:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(start+"-"+end);
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("室内平均温度:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getAverageTemp()+"℃");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph(" "));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("温度达标天数:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getStandardDays()+"天");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph("温度未达标天数:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getNoStandardDays()+"天");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("总耗热量:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getHeat()+"MJ");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph("总耗电量:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getPowerConsumption()+"kwh");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("制热性能系数:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getSCOP()+"");
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(PDFUtil.getParagraph("(不小于1.8)"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(PDFUtil.getParagraph("评估结果:"));
        cell.setBorder(0);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        text = PDFUtil.getParagraph(energyReport.getReportResult());
        text.add(UNDERLINE);
        cell = new PdfPCell(text);
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        table.addCell(cell);

        File directory = new File("src/main/resources/static/upload");//设定为当前文件夹
        String filePath=directory.getCanonicalPath();
        if (shownhxy) {
            cell = new PdfPCell(PDFUtil.getParagraph(new Chunk(String.valueOf("能耗效益"), new Font(PDFUtil.bfChinese, 14, Font.BOLD))));
            cell.setColspan(4);
            cell.setBorder(0);
            cell.setPaddingTop(20);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(PDFUtil.getParagraph("常规能源能耗"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnergyEfficiency().getConventionalEnergy()+"kgcc");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(PDFUtil.getParagraph("总能耗"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnergyEfficiency().getTotalEnergyConsumption()+"kgcc");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);

            cell = new PdfPCell(PDFUtil.getParagraph("能源替代量"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnergyEfficiency().getReplaceEnergy()+"kgcc");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(PDFUtil.getParagraph(" "));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(2);
            table.addCell(cell);
            //插入能耗效益图片
            Image img = Image.getInstance(filePath+"/nhxy.png");
            PdfPCell pdfPCell=new PdfPCell();
            pdfPCell.setPaddingTop(10);
            pdfPCell.setColspan(4);
            pdfPCell.setImage(img);
            table.addCell(pdfPCell);
        }

        if (showhjxy) {
            cell = new PdfPCell(PDFUtil.getParagraph(new Chunk(String.valueOf("环境效益"), new Font(PDFUtil.bfChinese, 14, Font.BOLD))));
            cell.setColspan(4);
            cell.setBorder(0);
            cell.setPaddingTop(20);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(PDFUtil.getParagraph("二氧化碳减排量"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnvironmentalBenefits().getCO2()+"kg");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(PDFUtil.getParagraph("二氧化硫减排量"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnvironmentalBenefits().getSO2()+"kg");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);

            cell = new PdfPCell(PDFUtil.getParagraph("颗粒物减排量"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnvironmentalBenefits().getParticulates()+"kg");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(PDFUtil.getParagraph("氮氧化物减排量"));
            cell.setBorder(0);
            cell.setPaddingTop(10);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            text = PDFUtil.getParagraph(energyReport.getEnvironmentalBenefits().getNitrogenOxides()+"kg");
            text.add(UNDERLINE);
            cell = new PdfPCell(text);
            cell.setBorder(0);
            cell.setPaddingTop(10);
            table.addCell(cell);
            Image img = Image.getInstance(filePath+"/hjxy.png");
            PdfPCell pdfPCell=new PdfPCell();
            pdfPCell.setColspan(4);
            pdfPCell.setImage(img);
            table.addCell(pdfPCell);
        }


        document.add(table);
        document.close();
    }

    /**
     * 上传图片
     * @param reportPicModel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/uploadpic",method=RequestMethod.POST)
    public String base64UpLoad(@RequestBody ReportPicModel reportPicModel, HttpServletRequest request, HttpServletResponse response){
        if (reportPicModel.getEnergyEfficiency_picinfo()!= null&&!"".equals(reportPicModel.getEnergyEfficiency_picinfo())){
            savePic(reportPicModel.getEnergyEfficiency_picinfo(),"nhxy");
        }
        if (reportPicModel.getEnvironmentalBenefits_picinfo()!= null&&!"".equals(reportPicModel.getEnvironmentalBenefits_picinfo())){
            savePic(reportPicModel.getEnvironmentalBenefits_picinfo(),"hjxy");
        }
        return JsonUtils.fillResultString(0,"成功",null);
    }

    /**
     * 保存图片
     * @param base64Data
     * @param name
     * @return
     */
    private static String savePic(String base64Data,String name){
        try{
            //String base64Data=reportPicModel.getEnergyEfficiency_picinfo();
            String dataPrix = "";
            String data = "";
            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            String tempFileName = name + suffix;

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);

            File directory = new File("src/main/resources/static/upload");//设定为当前文件夹
            String filePath=directory.getCanonicalPath();
            try{
                //使用apache提供的工具类操作流
                FileUtils.writeByteArrayToFile(new File(filePath, tempFileName), bs);
            }catch(Exception ee){
                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }
            return "success";
        }catch (Exception e) {
            return "error";
        }
    }

}
