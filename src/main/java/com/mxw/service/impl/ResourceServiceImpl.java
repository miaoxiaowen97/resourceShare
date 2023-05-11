package com.mxw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;
import com.mxw.mapper.ResourceMapper;
import com.mxw.service.ResourceService;
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
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void getOne() {
        Resource resource = resourceMapper.selectById(1);
        System.out.println(resource);
    }

    @Override
    public void saveOne(Resource resource) {
        resourceMapper.insert(resource);
    }

    @Override
    public void modifyOne(Resource resource) {
        resourceMapper.updateById(resource);
    }

    @Override
    public Page<ResourceItemDTO> getListByPage(PageInfo pageInfo) {
        Page<ResourceItemDTO> dtoPage = new Page<>();

        LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
        Page<Resource> resourcePage = resourceMapper.selectPage(new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize()), queryWrapper);
        List<Resource> records = resourcePage.getRecords();

        if (CollectionUtils.isEmpty(records)){
            return dtoPage;
        }

        List<ResourceItemDTO> dtoList= resourcePage.getRecords().stream().map(item -> {
            ResourceItemDTO dto = new ResourceItemDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(resourcePage,dtoPage);
        dtoPage.setRecords(dtoList);

        return dtoPage;
    }

    @Override
    public ResourceItemDTO getItemInfo(Long itemId) {

        ResourceItemDTO dto=new ResourceItemDTO();
        Resource resource = resourceMapper.selectById(itemId);
        resource.setViewCount(resource.getViewCount()+1);
        resourceMapper.updateById(resource);
        String resourceUrl = resource.getResourceUrl();
        String picUrl = resource.getPicUrl();

        List<String> resourceUrlList = JSON.parseArray(resourceUrl, String.class);
        List<String> picUrlList = JSON.parseArray(picUrl, String.class);

        BeanUtils.copyProperties(resource,dto);

        if (CollectionUtils.isEmpty(picUrlList)){
            picUrlList.add("001.jpg");
        }
        dto.setResourceUrlList(resourceUrlList);
        dto.setPicUrlList(picUrlList);

        return dto;
    }

    @Override
    public List<ResourceItemDTO> getListByQuery(PageInfo pageInfo, String queryKey) {

        LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Resource::getTitle,queryKey);
        List<Resource> resourceList = resourceMapper.selectList(queryWrapper);
        List<ResourceItemDTO> itemDTOS = resourceList.stream().map(item -> {
            ResourceItemDTO dto = new ResourceItemDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        return itemDTOS;
    }
}
