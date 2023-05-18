package com.mxw.controller;

import com.mxw.dto.PageInfo;
import com.mxw.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * @author miao
 */
@Controller
public class BlogController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/blogList")
    public String getList(@RequestParam(value = "pageIndex",defaultValue = "1") Long pageIndex, Model model){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(8L);

        return "itemList";
    }


    @GetMapping(value = "/blogItem")
    public String getBlogItemInfo(@RequestParam(value = "itemId") Long itemId, Model model){
        if (itemId<0){
            return "erro";
        }


        return "item";
    }

}
