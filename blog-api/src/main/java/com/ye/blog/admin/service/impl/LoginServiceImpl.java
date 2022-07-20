package com.ye.blog.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.admin.service.LoginService;
import com.ye.blog.admin.service.SysUserService;
import com.ye.blog.utils.JWTUtils;
import com.ye.blog.admin.vo.ErrorCode;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.concurrent.TimeUnit;

//注册到我们的spring容器中
@Service
//当出现异常时回滚，不会进入数据库，一般加载接口时所有的实现都有事务注解，
// 接口实现多个实现的话可以加上，一般实现类很少有多个所以加载这里也可以
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String slat = "yelim!@#";//加密盐

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public Result login(LoginParam loginParam) {

        /**
         *1.检查参数是否合法
         * 2.根据用户名和密码去user表中查询是否存在
         * 3.如果不存在登录失败
         * 4.如果存在,使用jwt生成 token返回给前端
         * 5. token放入 redis当中, redis token:user信息设置过期时间
         * (登录认证的时候先认证 token字符串是否合法,去 redis认证是否存在)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //记得导入包
        /**
         *
         <dependency>
         <groupId>commons-codec</groupId>
         <artifactId>commons-codec</artifactId>
         </dependency>
         */
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         *1.判断参数是否合法
         * 2.判断账户是否存在，存在返回账户已经被注册
         * 3.不存在，注册用户
         * 4.生成token
         * 5.存入redis并返回
         * 6.注意加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚
         */

        //1.
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //2
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),"账户已经被注册了");
        }
        //3
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.5e8e495.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        //4token
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }


    // public static void main(String[] args) {
    //     Scanner input = new Scanner(System.in);
    //     System.out.println("请输入要加密的密码：");
    //     String password = input.nextLine();
    //     password = DigestUtils.md5Hex(password + slat);
    //     System.out.println("加密后的密码为:"+password);
    //
    // }

}
