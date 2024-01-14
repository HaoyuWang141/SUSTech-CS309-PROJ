package com.ooad.dormitory.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ooad.dormitory.entity.Dormitory;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;
import java.util.List;

public interface DormitoryMapper extends BaseMapper<Dormitory> {

    @Select("SELECT * FROM dormitory")
    @Results({
            @Result(column = "dormitory_id", property = "dormitoryId"),
            @Result(column = "floor", property = "floor"),
            @Result(column = "room_number", property = "roomNumber"),
            @Result(column = "bed_count", property = "bedCount"),
            @Result(column = "description", property = "description"),
            @Result(column = "layout_id", property = "layoutId"),
            @Result(column = "layout_id", property = "layout", one = @org.apache.ibatis.annotations.One(select = "com.ooad.dormitory.mapper.LayoutMapper.selectById")),
            @Result(column = "is_empty", property = "isEmpty"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "degree", property = "degree"),
            @Result(column = "building_id", property = "buildingId"),
            @Result(column = "building_id", property = "building", one = @org.apache.ibatis.annotations.One(select = "com.ooad.dormitory.mapper.BuildingMapper.selectById"))
    })
    List<Dormitory> getDormitories();

    @Select("SELECT * FROM dormitory WHERE dormitory_id = #{id}")
    @Results({
            @Result(column = "dormitory_id", property = "dormitoryId"),
            @Result(column = "floor", property = "floor"),
            @Result(column = "room_number", property = "roomNumber"),
            @Result(column = "bed_count", property = "bedCount"),
            @Result(column = "description", property = "description"),
            @Result(column = "layout_id", property = "layoutId"),
            @Result(column = "layout_id", property = "layout", one = @org.apache.ibatis.annotations.One(select = "com.ooad.dormitory.mapper.LayoutMapper.selectById")),
            @Result(column = "is_empty", property = "isEmpty"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "degree", property = "degree"),
            @Result(column = "building_id", property = "buildingId"),
            @Result(column = "building_id", property = "building", one = @org.apache.ibatis.annotations.One(select = "com.ooad.dormitory.mapper.BuildingMapper.selectById"))
    })
    Dormitory selectDormitoryById(Integer id);
}
