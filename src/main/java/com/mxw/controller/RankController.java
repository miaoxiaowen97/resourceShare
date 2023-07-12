package com.mxw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.VisitorLog;
import com.mxw.service.ResourceService;
import com.mxw.service.VisitorLogService;
import com.mxw.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class RankController {


    @GetMapping(value = "/rank")
    public String getItemInfo(){
        return "rank";
    }

}
