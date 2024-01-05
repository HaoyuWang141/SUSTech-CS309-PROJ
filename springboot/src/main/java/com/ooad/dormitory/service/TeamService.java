package com.ooad.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ooad.dormitory.entity.Team;

import java.util.List;

public interface TeamService extends IService<Team> {
    public Team getTeamById(Integer id);
}
