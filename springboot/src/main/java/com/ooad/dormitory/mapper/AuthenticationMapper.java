package com.ooad.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ooad.dormitory.entity.Authentication;
import com.ooad.dormitory.entity.Building;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface AuthenticationMapper extends BaseMapper<Authentication> {
    @Select("select * from authentication where student_id = #{studentId}")
    @Results({
            @Result(column = "student_id", property = "studentId"),
            @Result(column = "student_password", property = "studentPassword"),
            @Result(column = "token", property = "token"),
            @Result(column = "token_failure_time", property = "tokenFailureTime"),
            @Result(column = "online_amount", property = "onlineAmount")
    })
    Authentication selectById(String studentId);

    @Select("select * from authentication where token = #{token}")
    @Results({
            @Result(column = "student_id", property = "studentId"),
            @Result(column = "student_password", property = "studentPassword"),
            @Result(column = "token", property = "token"),
            @Result(column = "token_failure_time", property = "tokenFailureTime"),
            @Result(column = "online_amount", property = "onlineAmount")
    })
    Authentication selectByToken(String token);
}
