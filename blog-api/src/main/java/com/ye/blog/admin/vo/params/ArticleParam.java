package com.ye.blog.admin.vo.params;

import com.ye.blog.admin.vo.CategoryVo;
import com.ye.blog.admin.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    // @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
