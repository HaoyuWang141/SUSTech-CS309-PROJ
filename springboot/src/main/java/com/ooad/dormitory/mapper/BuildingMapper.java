package com.ooad.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ooad.dormitory.entity.Building;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BuildingMapper extends BaseMapper<Building> {

    @Select("select * from building where building_id = #{buildingId}")
    @Results({
            @Result(column = "building_id", property = "buildingId"),
            @Result(column = "building_name", property = "buildingName"),
            @Result(column = "description", property = "description"),
            @Result(column = "region_id", property = "region", one = @One(select = "com.ooad.dormitory.mapper.RegionMapper.selectById"))
    })
    Building selectById(Integer buildingId);
}
