package com.ye.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUser {

    // @TableId(type = IdType.ASSIGN_ID)//默认id类型
    //现在就使用着分布式id即可，不用自增的，以后用户多了之后，要进行分表操作，id就需要用分布式id了
    //想用自增也很简单@TableId(type = IdType.AUTO)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
