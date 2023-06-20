package com.mxw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.dto.ITResourceDTO;
import com.mxw.dto.PageInfo;
import com.mxw.service.ITResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author miao
 */
@Controller
public class ItjcController {

    @Autowired
    private ITResourceService itResourceService;

    @GetMapping(value = "/it-resource")
    public String getItemInfo(@RequestParam(value = "itemId") Long itemId, Model model){

        if (itemId<0){
            return "erro";
        }

        ITResourceDTO itResourceDTO=itResourceService.getITResourceDTO(itemId);
        model.addAttribute("itResourceDTO",itResourceDTO);

        return "ItResourceItem";
    }

    @GetMapping(value = "/it-resource-list")
    public String getList(@RequestParam(value = "pageIndex",defaultValue = "1") Long pageIndex,@RequestParam(value = "type",defaultValue = "1") Integer type, Model model){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(8L);
        pageInfo.setType(type);
        Page<ITResourceDTO> page=itResourceService.getListByPage(pageInfo);
        pageInfo.setPageCount(page.getTotal()/pageInfo.getPageSize());
        model.addAttribute("ItItemList",page.getRecords());
        model.addAttribute("pageInfo",pageInfo);
        return "ItResourceList";
    }
}
