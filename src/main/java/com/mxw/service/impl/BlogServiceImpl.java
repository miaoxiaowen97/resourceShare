package com.mxw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;
import com.mxw.mapper.BlogMapper;
import com.mxw.mapper.ResourceMapper;
import com.mxw.service.BlogService;
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
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

}
