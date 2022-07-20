package com.ye.blog.admin.service;

import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.LoginParam;


public interface LoginService {

    SysUser checkToken(String token);

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 退出登录功能
     * @param token
     * @return
     */
    Result logout(String token);


    /**
     * 注册功能
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
