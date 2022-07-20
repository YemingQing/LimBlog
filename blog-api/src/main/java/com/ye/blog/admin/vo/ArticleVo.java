package com.ye.blog.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    //一定要记得加 要不然 会出现精度损失
    // @JsonSerialize(using = ToStringSerializer.class),把Long改为String就不需要了
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;//Article改为Integer后，此处也要改为Integer否则会复制不了信息

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;//数据库中是long

    private String author;//作者类型也换为String

    private ArticleBodyVo body;

    private List<TagVo> tags;//pojo的数据库中也有一个tag标签 ，也要建立对应的VO标识它

    private CategoryVo category;//body和类别先不要

}