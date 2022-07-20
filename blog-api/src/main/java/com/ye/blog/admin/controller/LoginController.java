package com.ye.blog.admin.controller;

import com.ye.blog.admin.service.LoginService;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginSercice;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户 访问用户表
        return loginSercice.login(loginParam);
    }
}
