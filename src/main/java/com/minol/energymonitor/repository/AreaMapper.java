package com.minol.energymonitor.repository;

import com.minol.energymonitor.domain.entity.Area;
import com.minol.energymonitor.domain.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
@Repository
public interface AreaMapper {
    List<Area> selectAreas(Map map);
    List<Area> selectAreaWithIDAndName(Map map);
    Area selectAreaById(int id);
    int batchDeleteAreas(List<Area> areas);
    int insertArea(Area area);
    int updateArea(Area area);
}
