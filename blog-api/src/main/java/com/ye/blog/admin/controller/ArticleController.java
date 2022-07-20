package com.ye.blog.admin.controller;

import com.ye.blog.common.aop.LogAnnotation;
import com.ye.blog.common.cache.Cache;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.ArticleParam;
import com.ye.blog.admin.vo.params.PageParams;
import com.ye.blog.admin.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据进行交互
@RestController
//路径
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     *
     * 首页 文章列表
     * 接口文档上写是post的就写postmapper
     * Result是统一结果返回
     * @param pageParams
     * @return
     */
    @PostMapping
    //加上此注解 代表要对接口记录日志
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 100,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams) {

        // int i = 10/0;测试统一异常的

        //ArticleVo 页面接收的数据
        // List<ArticleVo> articles = (List<ArticleVo>) articleService.listArticle(pageParams);
        return articleService.listArticle(pageParams);//业务逻辑交给success处理
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 100,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 100,name = "new_article")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }


    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
       return articleService.findArticleById(articleId);
    }


    /**
     * 接口url：/articles/publish
     *
     * 请求方式：POST
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }


}
