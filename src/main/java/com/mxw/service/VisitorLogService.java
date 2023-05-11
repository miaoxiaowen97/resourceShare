package com.mxw.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;
import com.mxw.entity.VisitorLog;

/**
 * @author miao
 */
public interface VisitorLogService {

   void saveVisitorLog(VisitorLog visitorLog);
}
