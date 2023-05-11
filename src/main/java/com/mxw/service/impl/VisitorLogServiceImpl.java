package com.mxw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;
import com.mxw.entity.VisitorLog;
import com.mxw.mapper.ResourceMapper;
import com.mxw.mapper.VisitorLogMapper;
import com.mxw.service.ResourceService;
import com.mxw.service.VisitorLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author miao
 */
@Service
public class VisitorLogServiceImpl implements VisitorLogService {

    @Autowired
    private VisitorLogMapper visitorLogMapper;


    @Override
    public void saveVisitorLog(VisitorLog visitorLog) {
        visitorLogMapper.insert(visitorLog);
    }
}
