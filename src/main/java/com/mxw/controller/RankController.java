package com.mxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RankController {

    @GetMapping(value = "/rank")
    public String getItemInfo(){
        return "rank";
    }

}
