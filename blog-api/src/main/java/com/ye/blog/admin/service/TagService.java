package com.ye.blog.admin.service;

import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);


    /**
     * 所有文章标签
     * @return
     */
    Result findAll();

    /**
     * 查看所有文章标签
     * @return
     */
    Result findAllDetail();

    Result findAllDetailById(Long id);

}
