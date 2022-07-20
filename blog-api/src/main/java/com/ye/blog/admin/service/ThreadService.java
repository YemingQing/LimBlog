package com.ye.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ye.blog.dao.mapper.ArticleMapper;
import com.ye.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    //期望此操作在线程池执行，不会影响原有的主线程
    //线程池名称，需要执行哪个任务，原来需要等待的5s放在了线程池异步执行了中，文章直接就能查看，控制台等5s后显示更新完成了
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程的环境下 线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        //琢磨下这两句的意思
        //update article set view_count = 100 where view_count where view_count = 99 and id = 11
        articleMapper.update(articleUpdate,updateWrapper);
        //线程池存在一个问题，原来Article在建立pojo类的时候，设置的是基本类型不是包装类
        //导致评论更新后变为0的现象
        //日志信息Preparing: UPDATE ms_article SET comment_counts=?, view_counts=?, weight=? WHERE (id = ? AND view_counts = ?)把comment_counts也修改了
        //int类型原有的默认值为0，mybatisplus在更新的时候但凡这个对象不为null，就会生成到这个sql语句当中进行更新，所以Article实体类需包装类封装

        try {
            //睡眠5秒 证明不会影响主线程的使用,如果没有放在线程池中查看文章就需要等待5s
            Thread.sleep(5000);
            System.out.println("更新完成了.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}