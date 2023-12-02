package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.TeamFavoriteDorm;
import com.ooad.dormitory.mapper.TeamFavoriteDormMapper;
import com.ooad.dormitory.service.TeamFavoriteDormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamFavoriteDormServiceImpl extends ServiceImpl<TeamFavoriteDormMapper, TeamFavoriteDorm> implements TeamFavoriteDormService {

    private final TeamFavoriteDormMapper teamFavoriteDormMapper;

    @Autowired
    public TeamFavoriteDormServiceImpl(TeamFavoriteDormMapper teamFavoriteDormMapper) {
        this.teamFavoriteDormMapper = teamFavoriteDormMapper;
    }

}
