package com.ye.blog.admin.service;

import com.ye.blog.admin.vo.CategoryVo;
import com.ye.blog.admin.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
