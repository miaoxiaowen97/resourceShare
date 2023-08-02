package com.mxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

// 注册

@Controller
public class UserController {

    @PostMapping(value = "/user/loginPage")
    public String userLogin(){
        return "loginPage";
    }

}
