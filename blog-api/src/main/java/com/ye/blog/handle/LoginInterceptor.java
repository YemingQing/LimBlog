package com.ye.blog.handle;

import com.alibaba.fastjson.JSON;
import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.admin.service.LoginService;
import com.ye.blog.utils.UserThreadLocal;
import com.ye.blog.admin.vo.ErrorCode;
import com.ye.blog.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//让spring先识别到这个组件，不然拦截器不起作用
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法(Handler)之前进行执行
        /**
         * 1.需要判断请求的接口路径是含为 HandlerMethod( control1er方法)
         * 2.判断 token是否为空,如果为空 未登录
         * 3.如果 token不为空,登录验证 loginService checkToken
         * 4.如果认证成功 放行即可
         */
        //1
        if (!(handler instanceof HandlerMethod)){
            //handle 可能是RequestResourceHandler  springboot 程序 访问静态资源 默认去 classpath 下的static目录去查询
            return true;
        }
        //2
        String token = request.getHeader("Authorization");
        //sl4j日志
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行

        //我希望在controller中直接获取用户的信息，需要怎么获取？
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中用完的信息，会有内存泄露的风险
        UserThreadLocal.remove();//非常有技术含量，内存泄露
    }
}
