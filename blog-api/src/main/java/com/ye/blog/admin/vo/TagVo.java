package com.ye.blog.admin.vo;

import lombok.Data;

//VO就相当于和页面交互的数据
@Data
public class TagVo {

    //一定要记得加 要不然 会出现精度损失
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String tagName;

    private String avatar;
}
