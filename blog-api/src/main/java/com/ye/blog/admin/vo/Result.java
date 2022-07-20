package com.ye.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//生成所有的构造方法
@AllArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }
}