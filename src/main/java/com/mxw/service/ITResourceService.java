package com.mxw.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.ITResourceDTO;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.ITResource;
import com.mxw.entity.Resource;

import java.util.List;

/**
 * @author miao
 */
public interface ITResourceService {


    ITResourceDTO getITResourceDTO(Long itemId);

    Page<ITResourceDTO> getListByPage(PageInfo pageInfo);
}
