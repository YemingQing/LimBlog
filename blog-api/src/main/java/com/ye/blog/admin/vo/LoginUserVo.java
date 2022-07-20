package com.ye.blog.admin.vo;

import lombok.Data;

@Data
public class LoginUserVo {

    //一定要记得加 要不然 会出现精度损失
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
