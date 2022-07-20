package com.ye.blog.admin.vo.params;

import lombok.Data;

@Data
public class CommentParam {

    // @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
