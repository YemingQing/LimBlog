package com.ye.blog.admin.service;

import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.CommentParam;

public interface CommentsService {

    /**
     * 根据文章id 查询所有的评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}