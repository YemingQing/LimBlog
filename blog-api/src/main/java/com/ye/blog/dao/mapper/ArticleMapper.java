package com.ye.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ye.blog.dao.dos.Archives;
import com.ye.blog.dao.pojo.Article;

import java.util.List;

/**
 * @author Ye
 * @desc
 * 实现BaseMapper,这是mybatis-plus给我们提供的，可以方便的查询aritcle这张表
 */

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
