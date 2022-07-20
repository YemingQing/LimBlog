package com.ye.blog.admin.controller;

import com.ye.blog.admin.service.TagService;
import com.ye.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

//代表返回的是一个json数据不是一个页面
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagsService;

    // /tags/hot
    @GetMapping("/hot")
    public Result hot() {
        int limit = 6;
        return tagsService.hots(limit);
    }


    /**
     *   路径就为/tags
     *   所有文章标签
     * @return
     */
    @GetMapping
    public Result findAll() {
        return tagsService.findAll();
    }


    /**
     * 所有分类信息
     * @return
     */
    @GetMapping("detail")
    public Result findAllDetail() {
        return tagsService.findAllDetail();
    }

    /**
     * 标签文章列表
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result findAllDetailById(@PathParam("id") Long id) {
        return tagsService.findAllDetailById(id);
    }

}
