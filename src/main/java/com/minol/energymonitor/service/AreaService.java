package com.minol.energymonitor.service;

import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.repository.AreaMapper;
import com.minol.energymonitor.repository.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class AreaService {
    @Autowired
    AreaMapper areaMapper;
    /**
     * 查询指定ID的和带搜索关键字的项目信息
     * @param map
     * @return
     */
    public List<Area> selectAreas(Map map){
        return areaMapper.selectAreas(map);
    }

    /**
     * 根据主键ID查找项目信息
     * @param id
     * @return
     */
    public Area selectAreaById(int id){
        return areaMapper.selectAreaById(id);
    }

    /**
     * 批量删除
     * @param areas
     * @return
     */
    public int batchDeleteAreas(List<Area> areas){
        return areaMapper.batchDeleteAreas(areas);
    }

    /**
     * 插入一条项目信息
     * @param area
     * @return
     */
    public int insertArea(Area area){
        return areaMapper.insertArea(area);
    }
    /**
     * 修改项目信息
     * @param area
     * @return
     */
    public int updateArea(Area area){
        return areaMapper.updateArea(area);
    }


}
