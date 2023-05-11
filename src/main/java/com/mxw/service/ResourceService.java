package com.mxw.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;

import java.util.List;

/**
 * @author miao
 */
public interface ResourceService {
     void getOne();

     void  saveOne(Resource resource);


     void  modifyOne(Resource resource);

     Page<ResourceItemDTO> getListByPage(PageInfo pageInfo);

    ResourceItemDTO getItemInfo(Long itemId);

    List<ResourceItemDTO> getListByQuery(PageInfo pageInfo, String queryKey);
}
