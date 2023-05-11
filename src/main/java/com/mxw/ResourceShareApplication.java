package com.mxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 资源分享网站服务
 * @author miao
 */
@SpringBootApplication
public class ResourceShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceShareApplication.class, args);
        System.out.println("Successful");
    }
}
