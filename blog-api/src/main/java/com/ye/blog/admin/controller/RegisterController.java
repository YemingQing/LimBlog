package com.ye.blog.admin.controller;

import com.ye.blog.admin.service.LoginService;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso单点登录，后期如果把登录注册功能踢出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}