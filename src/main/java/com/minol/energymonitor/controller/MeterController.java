package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.*;
import com.minol.energymonitor.domain.model.ImportModel;
import com.minol.energymonitor.service.*;
import com.minol.energymonitor.utils.CommonUtils;
import com.minol.energymonitor.utils.JsonUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class MeterController {

    @Autowired
    MeterService meterService;
    @Autowired
    CollectorService collectorService;
    @Autowired
    ProjectService projectService;
    @Autowired
    AreaService areaService;
    @Autowired
    BuildingService buildingService;
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
        List<Meter> meters=getAllMeters(projectId, areaId, buildingId, collectorId, meterType);
        return new PageInfo<Meter>(meters);
    }

    private List<Meter> getAllMeters( int projectId,
                                      int areaId,
                                      int buildingId,
                                      int collectorId,
                                      int meterType){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        map.put("collectorId",collectorId);
        map.put("meterType",meterType);
        return meterService.selectMeters(map);
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
                                             @RequestParam int buildingId,
                                             @RequestParam int meterType){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        map.put("meterType",meterType);
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

    /**
     * 导入表计信息
     * @param multipartFile
     * @param meterType 表计类型
     * @return
     */
    @PostMapping("/importMeters")
    public String importMeters(MultipartFile multipartFile,@RequestParam int meterType){
        ImportModel importModel=new ImportModel();
        List<String> results=new ArrayList<>();
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            results.add("上传文件不能为空");
        }
        else {
            try {
                InputStream inputStream = multipartFile.getInputStream();
                XSSFWorkbook wb = new XSSFWorkbook(inputStream);
                //Excel工作表
                XSSFSheet sheet = wb.getSheetAt(0);
                //表头那一行
                XSSFRow row = sheet.getRow(0);
                //表头那个单元格
                XSSFCell cell = row.getCell(0);
                //查出所有的项目
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("projectIds", '*');
                List<Project> projects = projectService.selectProjects(map);
                List<Area> areas = areaService.selectAreas(map);
                map.put("projectId", '*');
                String projectName = "";
                Integer projectId = 0;
                String areaName = "";
                Integer areaId = 0;
                String buildNo = "";
                Integer buildId = 0;
                String number = "";
                String collectorNumber = "";
                String createTimeStr = "";
                String factory="";//厂家
                String model="";//型号
                String position="";//位置
                String doorNum="";//房间号
                Timestamp createTime;
                Building building = new Building();
                Collector collector;
                Meter meter;
                List<Meter> meters = new ArrayList<>();
                int j = 0;
                int aa=sheet.getLastRowNum();
                for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                    row = sheet.getRow(i);
                    j = 0;
                    cell = row.getCell(j);
                    projectName = cell.getStringCellValue();
                    projectId = CommonUtils.findProject(projects, projectName);
                    if (projectId == 0) {
                        //系统中不存在此项目
                        results.add((i+1)+":系统中不存在此项目"+projectName);
                        continue;
                    }
                    j++;
                    areaName = row.getCell(j).getStringCellValue();
                    areaId = CommonUtils.findArea(areas, areaName);
                    if (areaId == 0) {
                        //系统中找不到小区
                        results.add((i+1)+":系统中不存在此小区"+areaName);
                        continue;
                    }
                    j++;
                    building=new Building();
                    buildNo = row.getCell(j).getStringCellValue();
                    building.setArea_id(areaId);
                    building.setName(buildNo);
                    building = buildingService.selectBuildingByAreaIdAndBuildingNo(building);
                    if (building == null) {
                        //楼栋不存在
                        results.add((i+1)+":系统中不存在此楼栋");
                        continue;
                    }
                    j++;
                    collector = new Collector();
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    collectorNumber = row.getCell(j).getStringCellValue();
                    collector.setNumber(collectorNumber);
                    collector = collectorService.selectCollectorByNumber(collector);
                    if (collector == null) {
                        //此编号已经存在
                        results.add((i+1)+":系统中不存在此采集器"+collectorNumber);
                        continue;
                    }
                    j++;
                    meter = new Meter();
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    number = row.getCell(j).getStringCellValue();
                    meter.setNumber(number);
                    meter.setCollector_id(collector.getId());
                    meter.setMeter_type(meterType);
                    meter = meterService.getMeterByCollectorIdAndMeterNumber(meter);
                    if (meter != null) {
                        //此编号已经存在
                        results.add((i+1)+":此采集器已存在此表号"+number);
                        continue;
                    }
                    j++;
                    factory = row.getCell(j).getStringCellValue();
                    position = row.getCell(j).getStringCellValue();
                    j++;
                    model = row.getCell(j).getStringCellValue();
                    doorNum = row.getCell(j).getStringCellValue();
                    j++;
                    meter = new Meter();
                    createTime = new Timestamp(row.getCell(j).getDateCellValue().getTime());
                    meter.setCollector_id(collector.getId());
                    meter.setNumber(number);
                    meter.setMeter_type(meterType);
                    meter.setFactory(factory);
                    meter.setModel(model);
                    meter.setPosition(position);
                    meter.setDoorNum(doorNum);
                    meter.setCreate_time(createTime);
                    meter.setUpdate_time(createTime);
                    meter.setCreate_by(1);
                    meter.setUpdate_by(1);
                    meters.add(meter);
                }
                if (meters.size()>0){
                    importModel.setCount(meterService.batchInsertMeter(meters));
                }
                else {
                    importModel.setCount(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "后端异常...";
            }
        }
        importModel.setResults(results);
        List<ImportModel> returnResult=new ArrayList<>();
        returnResult.add(importModel);
        return JsonUtils.fillResultString(0,"成功",returnResult);
    }
    /**
     * 导出所有采集器信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @return
     */
    @GetMapping("/exportMeters")
    public void exportMeters(@RequestParam int projectId,
                                 @RequestParam int areaId,
                                 @RequestParam int buildingId,
                                 @RequestParam int collectorId,
                                 @RequestParam int meterType,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {

        String meterName="";
        switch (meterType){
            case 1:
                meterName="热量表";
                break;
            case 2:
                meterName="电表";
                break;
            case 3:
                meterName="测温设备";
                break;
        }
        List<Meter> meters = getAllMeters(projectId, areaId, buildingId,collectorId,meterType);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //创建HSSFWorkbook对象(excel的文档对象)
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFFont fontStyle = wb.createFont();
        //设置粗体
        fontStyle.setBold(true);
        fontStyle.setFamily(20);
        XSSFCellStyle cellStyle=wb.createCellStyle();
        cellStyle.setFont(fontStyle);
        //建立新的sheet对象（excel的表单）
        XSSFSheet sheet = wb.createSheet(meterName+"信息");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        XSSFRow row = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        XSSFCell cell = row.createCell(0);
        //设置单元格内容
        String cellHead0="项目";
        cell.setCellValue(cellHead0);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        String cellHead1="小区";
        cell.setCellValue(cellHead1);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("楼号");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("采集器编号");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue(meterName+"编号");
        cell.setCellStyle(cellStyle);
        String cellHead5="";
        if (meterType==3) {
            cell = row.createCell(5);
            cellHead5 = "位置";
            cell.setCellValue(cellHead5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue("房间号");
            cell.setCellStyle(cellStyle);
        }
        else {
            cell = row.createCell(5);
            cellHead5 = "厂家";
            cell.setCellValue(cellHead5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue("型号");
            cell.setCellStyle(cellStyle);
        }

        cell = row.createCell(7);
        cell.setCellValue("安装时间");
        cell.setCellStyle(cellStyle);

        String projectName="";
        String areaName="";
        String factor="";
        if (meters.size() > 0) {
            for (int i = 0; i < meters.size(); i++) {
                row = sheet.createRow(i + 1);
                projectName=meters.get(i).getCollector().getBuilding().getProject().getName();
                cellHead0=projectName.length()>cellHead0.length()?projectName:cellHead0;
                row.createCell(0).setCellValue(projectName);

                areaName=meters.get(i).getCollector().getBuilding().getArea().getName() ;
                cellHead1=areaName.length()>cellHead1.length()?areaName:cellHead1;
                row.createCell(1).setCellValue(areaName);
                row.createCell(2).setCellValue(meters.get(i).getCollector().getBuilding().getName());

                row.createCell(3).setCellValue(meters.get(i).getCollector().getNumber());
                row.createCell(4).setCellValue(meters.get(i).getNumber());

                if (meterType!=3) {
                    factor = meters.get(i).getFactory();
                    cellHead5 = factor.length() > cellHead5.length() ? factor : cellHead5;
                    row.createCell(5).setCellValue(cellHead5);
                    row.createCell(6).setCellValue(meters.get(i).getModel());
                }
                else {
                    row.createCell(5).setCellValue(meters.get(i).getPosition());
                    row.createCell(6).setCellValue(meters.get(i).getDoorNum());
                }
                row.createCell(7).setCellValue(sdf.format(meters.get(i).getCreate_time()));
            }
            sheet.setColumnWidth((short)0,cellHead0.length()*2*256);
            sheet.setColumnWidth((short)1,cellHead1.length()*2*256);
            sheet.autoSizeColumn((short)2);
            sheet.autoSizeColumn((short)3);
            sheet.autoSizeColumn((short)4);
            sheet.setColumnWidth((short)5,cellHead5.length()*2*256);
            sheet.autoSizeColumn((short)6);
            sheet.autoSizeColumn((short)7);
            String fileName = meters.get(0).getCollector().getBuilding().getProject().getName();
            if (areaId != 0) {
                fileName += "_" + meters.get(0).getCollector().getBuilding().getArea().getName();
            }
            if (buildingId != 0) {
                fileName += meters.get(0).getCollector().getBuilding().getName();
            }
            fileName += meterName+"导出信息.xlsx";
            //输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/msexcel");
            response.setCharacterEncoding("utf-8");
            wb.write(output);
            output.close();
        }
    }
}
