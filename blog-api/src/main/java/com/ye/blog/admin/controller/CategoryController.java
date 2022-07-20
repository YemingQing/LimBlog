package com.ye.blog.admin.controller;

import com.ye.blog.admin.service.CategoryService;
import com.ye.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //  /categorys
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }


    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }


    //  /category/detail/{id}
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

}