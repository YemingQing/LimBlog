package com.ye.blog.admin.vo;

import lombok.Data;

@Data
public class CategoryVo {

    //一定要记得加 要不然 会出现精度损失
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}