package com.mxw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.PageInfo;
import com.mxw.dto.QueryParm;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.VisitorLog;
import com.mxw.service.ResourceService;
import com.mxw.service.VisitorLogService;
import com.mxw.util.IpUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;


import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;


@Controller
public class ItemController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private VisitorLogService visitorLogService;

    @GetMapping(value = "/itemList")
    public String getList(@RequestParam(value = "pageIndex",defaultValue = "1") Long pageIndex, Model model){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(8L);
        Page<ResourceItemDTO> page=resourceService.getListByPage(pageInfo);
        pageInfo.setPageCount(page.getTotal()/pageInfo.getPageSize());
        model.addAttribute("itemList",page.getRecords());
        model.addAttribute("pageInfo",pageInfo);
        return "itemList";
    }


    @GetMapping(value = "/item")
    public String getItemInfo(@RequestParam(value = "itemId") Long itemId, Model model){
        if (itemId<0){
            return "erro";
        }
        ResourceItemDTO itemInfo=resourceService.getItemInfo(itemId);
        model.addAttribute("itemInfo",itemInfo);
        System.out.println(itemInfo);
        return "item";
    }

    @GetMapping(value = "/")
    public String getIndex(Model model, HttpServletRequest request){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(1L);
        pageInfo.setPageSize(4L);
        Page<ResourceItemDTO> page=resourceService.getListByPage(pageInfo);
        String ipAddr = IpUtil.getIpAddr(request);
        VisitorLog visitorLog=new VisitorLog();
        visitorLog.setIp(ipAddr);
        visitorLogService.saveVisitorLog(visitorLog);
        pageInfo.setPageCount(page.getTotal()/pageInfo.getPageSize());
        model.addAttribute("itemList",page.getRecords());
        return "index";
    }

    @GetMapping(value = "/search")
    public String getSeacrh(@RequestParam(value = "queryKey") String queryKey, Model model){
        PageInfo pageInfo = new PageInfo();
        List<ResourceItemDTO> list=resourceService.getListByQuery(pageInfo,queryKey);
        pageInfo.setPageCount(1L);
        pageInfo.setPageIndex(1L);
        pageInfo.setPageSize((long) list.size());
        model.addAttribute("itemList",list);
        model.addAttribute("pageInfo",pageInfo);
        return "itemList";
    }


    @GetMapping(value = "/circle")
    public String getCircle(){
        return "circle";
    }

}
