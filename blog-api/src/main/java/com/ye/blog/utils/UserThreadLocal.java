package com.ye.blog.utils;

import com.ye.blog.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}

    //线程变量隔离，这个ThreadLocal只存有这个Thread可以用的一些信息，也就是这个信息只在这个线程存储，而且只要你这个线程一直存在
    //它就能从这个线程中拿到对应的ThreadLocal存储的值，ThreadLocal之间是独立的，线程安全的
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    //放
    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    //取
    public static SysUser get(){
        return LOCAL.get();
    }
    //删除
    public static void remove(){
        LOCAL.remove();
    }
}