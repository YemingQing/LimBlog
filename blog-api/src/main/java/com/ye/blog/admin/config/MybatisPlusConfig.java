package com.ye.blog.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//让spring能够识别
//扫包，将此包下的接口生成代理实现类，并且注册到spring容器中
@MapperScan("com.ye.blog.dao.mapper")//代表mybati的扫描包，扫包路径，mybatis相关的这些接口会写到这个包下面
public class MybatisPlusConfig {
    /**
     * mybatis-plus的分页插件,先定义这个拦截器
     */
    //别少了这个Bean注解不然会加载不到
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


}
