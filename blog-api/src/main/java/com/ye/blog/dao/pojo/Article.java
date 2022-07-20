package com.ye.blog.dao.pojo;

import lombok.Data;


@Data
public class Article {
    /**
     * 和数据库上的表相关联
     */
    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;//不能给默认值否则每次都会重置


    /**
     * 创建时间
     */
    private Long createDate;
}