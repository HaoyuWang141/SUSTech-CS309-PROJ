package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@NoArgsConstructor
@Data
public class StudentAccount {
    @TableId(type = IdType.INPUT)
    private String studentId;
    private String name;
    private Integer gender;
    private String photoUrl;
    private Time sleepTime;
    private Time wakeUpTime;
    private Integer airConditionerTemperature;
    private Boolean snore;
    private String qq;
    private String email;
    private String wechat;
    private Integer teamId;
    @TableField(exist = false)
    private Team team;

    public Integer calEntryYear() {
        return Integer.parseInt("20" + this.studentId.substring(1, 3));
    }

    public Integer calDegree() {
        if (this.studentId.charAt(3) == '1') {
            return 0;
        }
        else if (this.studentId.charAt(3) == '3' || this.studentId.charAt(3) == '6') {
            return 1;
        }
        else {
            return 2;
        }
    }

    public Double calSimilarity(Object o) {
        if (o.getClass() != this.getClass()) {
            throw new RuntimeException("comparing object is not a student account!");
        }
        StudentAccount studentAccount = (StudentAccount) o;
        double timeWeight = 0.5;
        double temperatureWeight = 0.3;
        double snoreWeight = 0.2;
        return timeWeight * calTimeSimilarity(this.sleepTime, this.wakeUpTime, studentAccount.sleepTime, studentAccount.wakeUpTime)
                + temperatureWeight * calTemperatureSimilarity(this.airConditionerTemperature, studentAccount.airConditionerTemperature)
                + snoreWeight * calSnoreSimilarity(this.snore, studentAccount.snore);
    }

    private static Double calTimeSimilarity(Time sleep1, Time wake1, Time sleep2, Time wake2) {
        int delta_sleep = Math.abs((int) (sleep1.getTime() - sleep2.getTime()) / 1000 / 60);
        double sleep_similarity = Math.max( (double) (-1/57600) * (delta_sleep - 240) * (delta_sleep + 240), 0.0);
        int delta_wake = Math.abs((int) (wake1.getTime() - wake2.getTime()) / 1000 / 60);
        double wake_similarity = Math.max( (double) (-1/57600) * (delta_wake - 240) * (delta_wake + 240), 0.0);
        return (sleep_similarity + wake_similarity) / 2.0;
    }

    private static Double calTemperatureSimilarity(Integer temperature1, Integer temperature2) {
        int delta = Math.abs(temperature1 - temperature2);
        return Math.max( (double) (-1/100) * (delta - 10) * (delta + 10), 0.0);
    }

    private static Double calSnoreSimilarity(Boolean snore1, Boolean snore2) {
        return snore1.equals(snore2) ? 1.0 : 0.0;
    }
}