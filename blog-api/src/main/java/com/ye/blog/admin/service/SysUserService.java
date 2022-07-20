package com.ye.blog.admin.service;

import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.UserVo;

public interface SysUserService {

    UserVo findUserVoById(Long articleId);


    //查询作者
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);


    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);


    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
