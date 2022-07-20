package com.ye.blog.admin.controller;

import com.ye.blog.admin.service.CommentsService;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;


    /**
     * 评论列表
     * @param articleId
     * @return
     */
    //  /comments/article/{id}
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);
    }


    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}