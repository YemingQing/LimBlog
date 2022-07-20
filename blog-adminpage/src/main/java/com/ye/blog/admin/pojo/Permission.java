package com.ye.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {

    // @TableId(type = IdType.AUTO)
    private String id;

    private String name;

    private String path;

    private String description;
}
