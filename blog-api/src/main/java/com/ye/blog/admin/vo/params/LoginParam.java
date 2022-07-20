package com.ye.blog.admin.vo.params;

import lombok.Data;


@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
