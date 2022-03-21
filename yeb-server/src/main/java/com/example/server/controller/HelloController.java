package com.example.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @Author qxy
 * @Date 2022/3/21 0:45
 * @Version 1.0
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "Hello2";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "Hello3";
    }
}
