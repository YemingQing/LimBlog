package com.ye.blog.admin.service;

import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.ArticleParam;
import com.ye.blog.admin.vo.params.PageParams;
public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章功能
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();


    /**
     *查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);


    /**
     * 文章发布功能
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
