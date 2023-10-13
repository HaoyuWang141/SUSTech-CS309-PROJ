package com.ooad.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ooad.dormitory.entity.Team;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface TeamMapper extends BaseMapper<Team> {

    @Select("SELECT * FROM team WHERE team_id = #{teamId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "dormitory_id", property = "dormitory", one = @One(select = "com.ooad.dormitory.mapper.DormitoryMapper.selectById"))
    })
    Team selectById(Integer teamId);
}
