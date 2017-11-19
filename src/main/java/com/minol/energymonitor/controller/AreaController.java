package com.minol.energymonitor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Project;
import com.minol.energymonitor.service.AreaService;
import com.minol.energymonitor.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@RestController
public class AreaController {
    @Autowired
    AreaService areaService;

    /**
     * 分页查找带搜索关键字的小区信息
     * @param keywords 搜索关键字
     * @param num 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/areas")
    public PageInfo<Area> selectAreas(@RequestParam String keywords,
                                            @RequestParam int num,
                                            @RequestParam int size){
        PageHelper.startPage(num,size);//分页语句
        Map<String, Object> map = new HashMap<>();
        map.put("keywords",keywords);//加入关键字
        List<Area> areas=areaService.selectAreas(map);
        return new PageInfo<Area>(areas);
    }
    /**
     * 查找指定ID或者全部项目信息（用于下拉框，只返回ID和Name）
     * @param protectIds 指定ID，默认为1,2,3... *为查找所有项目
     * @return
     */
    @GetMapping("/selectAreaWithIDAndName/{ids}")
    public String selectAreaWithIDAndName(@PathVariable String protectIds){
        Map<String, Object> map = new HashMap<String, Object>();
        if(protectIds.equals("*")){//加入ID
            map.put("ids",'*');
        }
        else {
            map.put("ids",protectIds.split(","));
        }
        return JsonUtils.fillResultString(0,"成功",areaService.selectAreaWithIDAndName(map));
    }

    /**
     * 批量删除小区信息
     * @param areas
     * @return
     */
    @PostMapping("/areas-del")
    public String batchDeleteAreas(@RequestBody List<Area> areas){
        int result=areaService.batchDeleteAreas(areas);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 插入一条小区信息
     * @param area
     * @return
     */
    @PostMapping("/area")
    public String insertArea(@RequestBody Area area){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        area.setCreate_time(timestamp);
        area.setUpdate_time(timestamp);
        int result=areaService.insertArea(area);
        return JsonUtils.fillResultString(0,"成功",result);
    }

    /**
     * 修改一条数据
     * @param id
     * @param area
     * @return
     */
    @PutMapping("/area/{id}")
    public String updateArea(@PathVariable int id, @RequestBody Area area){
        Area marea=areaService.selectAreaById(id);
        if (marea!=null){
            //Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            marea.setName(area.getName());
            marea.setDistrict(area.getDistrict());
            marea.setAddress(area.getAddress());
            marea.setUpdate_by(area.getUpdate_by());
            //marea.setUpdate_time(timestamp);
        }
        int result=areaService.updateArea(marea);
        return JsonUtils.fillResultString(0,"成功",result);
    }
}
