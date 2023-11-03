package com.ooad.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ooad.dormitory.entity.StudentAccount;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentAccountMapper extends BaseMapper<StudentAccount> {
    @Select("SELECT * FROM student_account")
    @Results({
            @Result(column = "student_id", property = "studentId"),
            @Result(column = "name", property = "name"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "photo_url", property = "photoUrl"),
            @Result(column = "sleep_time", property = "sleepTime"),
            @Result(column = "wake_up_time", property = "wakeUpTime"),
            @Result(column = "air_conditioner_temperature", property = "airConditionerTemperature"),
            @Result(column = "is_snoring", property = "isSnoring"),
            @Result(column = "qq", property = "qq"),
            @Result(column = "email", property = "email"),
            @Result(column = "wechat", property = "wechat"),
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "team_id", property = "team", one = @org.apache.ibatis.annotations.One(select = "com.ooad.dormitory.mapper.TeamMapper.selectById"))
    })
    List<StudentAccount> selectAllWithTeam();
}
