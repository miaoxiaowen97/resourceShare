package com.mxw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.ITResourceDTO;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.ITResource;
import com.mxw.entity.Resource;
import com.mxw.mapper.ITResourceMapper;
import com.mxw.mapper.ResourceMapper;
import com.mxw.service.ITResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author miao
 */
@Service
public class ITResourceServiceImpl implements ITResourceService {

    @Autowired
    private ITResourceMapper itResourceMapper;

    @Override
    public ITResourceDTO getITResourceDTO(Long itemId) {
        ITResourceDTO dto=new ITResourceDTO();
        ITResource itResource = itResourceMapper.selectById(itemId);
        BeanUtils.copyProperties(itResource,dto);
        return dto;
    }

    @Override
    public Page<ITResourceDTO> getListByPage(PageInfo pageInfo) {
        Page<ITResourceDTO> dtoPage = new Page<>();

        LambdaQueryWrapper<ITResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ITResource::getId);
        queryWrapper.eq(ITResource::getType,pageInfo.getType());
        Page<ITResource> resourcePage = itResourceMapper.selectPage(new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize()), queryWrapper);
        List<ITResource> records = resourcePage.getRecords();

        if (CollectionUtils.isEmpty(records)){
            return dtoPage;
        }

        List<ITResourceDTO> dtoList= resourcePage.getRecords().stream().map(item -> {
            ITResourceDTO dto = new ITResourceDTO();
            BeanUtils.copyProperties(item, dto);
            // Date时间格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dto.setCreateTime(sdf.format(item.getCreateTime()));
            dto.setModifyTime(sdf.format(item.getModifyTime()));
            return dto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(resourcePage,dtoPage);
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
}
