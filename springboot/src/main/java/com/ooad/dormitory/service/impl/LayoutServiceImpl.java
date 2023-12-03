package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.Layout;
import com.ooad.dormitory.mapper.LayoutMapper;
import com.ooad.dormitory.service.LayoutService;
import org.springframework.stereotype.Service;

@Service
public class LayoutServiceImpl extends ServiceImpl<LayoutMapper, Layout> implements LayoutService {
}
