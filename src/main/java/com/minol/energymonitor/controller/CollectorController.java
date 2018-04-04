package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Building;
import com.minol.energymonitor.domain.entity.Collector;
import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.domain.model.ImportModel;
import com.minol.energymonitor.service.AreaService;
import com.minol.energymonitor.service.BuildingService;
import com.minol.energymonitor.service.CollectorService;
import com.minol.energymonitor.service.ProjectService;
import com.minol.energymonitor.utils.CommonUtils;
import com.minol.energymonitor.utils.JsonUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CollectorController {

    @Autowired
    CollectorService collectorService;
    @Autowired
    ProjectService projectService;
    @Autowired
    AreaService areaService;
    @Autowired
    BuildingService buildingService;

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
        List<Collector> collectors=getCollectors(projectId,areaId,buildingId);
        return new PageInfo<Collector>(collectors);
    }
    /**
     * 查找所有采集器信息（导出）
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @return
     */
    @GetMapping("/allcollectors")
    public String selectAllCollectors(@RequestParam int projectId,
                                                @RequestParam int areaId,
                                                @RequestParam int buildingId){
        //PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        List<Collector> collectors=collectorService.selectCollectors(map);
        return JsonUtils.fillResultString(0,"成功",collectors);
    }
    /**
     * 导出所有采集器信息
     * @param projectId 项目ID
     * @param areaId 小区ID
     * @param buildingId 楼栋ID
     * @return
     */
    @GetMapping("/exportcollectors")
    public void exportCollectors(@RequestParam int projectId,
                                 @RequestParam int areaId,
                                 @RequestParam int buildingId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {

        List<Collector> collectors = getCollectors(projectId, areaId, buildingId);
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
        XSSFSheet sheet = wb.createSheet("采集器信息");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        XSSFRow row = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        XSSFCell cell = row.createCell(0);
        //设置单元格内容
        String cellHead0="项目";
        cell.setCellValue(cellHead0);
        cell.setCellStyle(cellStyle);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        //sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        cell = row.createCell(1);
        String cellHead1="小区";
        cell.setCellValue(cellHead1);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue("楼号");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellValue("编号");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(4);
        cell.setCellValue("安装时间");
        cell.setCellStyle(cellStyle);
        String projectName="";
        String areaName="";
        if (collectors.size() > 0) {
            for (int i = 0; i < collectors.size(); i++) {
                row = sheet.createRow(i + 1);
                projectName=collectors.get(i).getBuilding().getProject().getName();
                cellHead0=projectName.length()>cellHead0.length()?projectName:cellHead0;
                row.createCell(0).setCellValue(projectName);

                areaName=collectors.get(i).getBuilding().getArea().getName() ;
                cellHead1=areaName.length()>cellHead1.length()?areaName:cellHead1;
                row.createCell(1).setCellValue(areaName);
                row.createCell(2).setCellValue(collectors.get(i).getBuilding().getName());

                row.createCell(3).setCellValue(collectors.get(i).getNumber());
                row.createCell(4).setCellValue(sdf.format(collectors.get(i).getCreate_time()));
            }
            sheet.setColumnWidth((short)0,cellHead0.length()*2*256);
            sheet.setColumnWidth((short)1,cellHead1.length()*2*256);
            sheet.autoSizeColumn((short)2); //调整第三列宽度
            sheet.autoSizeColumn((short)3); //调整第四列宽度
            sheet.autoSizeColumn((short)4); //调整第四列宽度
            String fileName = collectors.get(0).getBuilding().getProject().getName();
            if (areaId != 0) {
                fileName += "_" + collectors.get(0).getBuilding().getArea().getName();
            }
            if (buildingId != 0) {
                fileName += collectors.get(0).getBuilding().getName();
            }
            fileName += "采集器导出信息.xlsx";
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

    private List<Collector> getCollectors( int projectId,
                                           int areaId,
                                           int buildingId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("areaId",areaId);
        map.put("buildingId",buildingId);
        return collectorService.selectCollectors(map);
    }

    /**
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

    /**
     * 根据主键查询采集器信息
     * @param id
     * @return
     */
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

    /**
     * 导入采集器信息
     * @param multipartFile
     * @return
     */
    @PostMapping("/importCollector")
    public String importCollector(MultipartFile multipartFile){
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
                String createTimeStr = "";
                Timestamp createTime;
                Building building = new Building();
                Collector collector;
                List<Collector> collectors = new ArrayList<>();
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
                    number = row.getCell(j).getStringCellValue();
                    collector.setNumber(number);
                    collector = collectorService.selectCollectorByNumber(collector);
                    if (collector != null) {
                        //此编号已经存在
                        results.add((i+1)+":系统中已存在此编号"+number);
                        continue;
                    }
                    j++;
                    createTime = new Timestamp(row.getCell(j).getDateCellValue().getTime());
                    collector = new Collector();
                    collector.setBuilding_id(building.getId());
                    collector.setNumber(number);
                    collector.setCurrent_version(0);
                    collector.setLast_version(0);
                    collector.setIsdeleted(0);
                    collector.setCreate_time(createTime);
                    collector.setUpdate_time(createTime);
                    collector.setCreate_by(1);
                    collector.setUpdate_by(1);
                    collectors.add(collector);
                }
                if (collectors.size()>0){
                    importModel.setCount(collectorService.batchInsertCollector(collectors));
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
}
