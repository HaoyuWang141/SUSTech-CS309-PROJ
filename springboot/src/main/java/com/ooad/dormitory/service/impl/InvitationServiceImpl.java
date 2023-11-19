package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.Invitation;
import com.ooad.dormitory.mapper.InvitationMapper;
import com.ooad.dormitory.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl extends ServiceImpl<InvitationMapper, Invitation> implements InvitationService {

    private final InvitationMapper invitationMapper;

    @Autowired
    public InvitationServiceImpl(InvitationMapper invitationMapper) {
        this.invitationMapper = invitationMapper;
    }
}
