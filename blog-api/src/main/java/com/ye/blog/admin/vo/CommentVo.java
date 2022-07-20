package com.ye.blog.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {

    //防止前端精度损失，把id转为String
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}