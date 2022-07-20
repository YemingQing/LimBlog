package com.ye.blog.admin.controller;

import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.utils.UserThreadLocal;
import com.ye.blog.admin.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        //想获取SysUser信息如何获取？使用ThreadLocal
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}