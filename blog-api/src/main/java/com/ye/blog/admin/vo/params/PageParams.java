package com.ye.blog.admin.vo.params;

import lombok.Data;

//lombok的方法
@Data
public class PageParams {

    // @JsonSerialize(using = ToStringSerializer.class)
    private Integer page = 1;

    private Integer pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }


}
