package com.ye.blog.admin.config;

import com.ye.blog.handle.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//别少了，不然加载不起来
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 跨域配置，前端项目是8080，访问后端的8888，两个域名不同，浏览器就会有这个跨域的问题，所以这要允许8080
     * 端口访问我们的服务
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");//http://localhost:8080本地配置，如果是部署就配置为ip地址或者域名
        // registry.addMapping("/**").allowedOrigins("");//http://localhost:8080本地配置，如果是部署就配置为ip地址或者域名
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续实际遇到需要拦截的接口时，再配置为真正的拦截接口
        //addPathPatterns("/comments/create/change")//未登录不能评论
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
             // .addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/register");博客一般很少这样配
    }
}
