package com.ye.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ye.blog.dao.dos.Archives;
import com.ye.blog.dao.mapper.ArticleBodyMapper;
import com.ye.blog.dao.mapper.ArticleMapper;
import com.ye.blog.dao.mapper.ArticleTagMapper;
import com.ye.blog.dao.pojo.Article;
import com.ye.blog.dao.pojo.ArticleBody;
import com.ye.blog.dao.pojo.ArticleTag;
import com.ye.blog.dao.pojo.SysUser;
import com.ye.blog.admin.service.*;
import com.ye.blog.utils.UserThreadLocal;
import com.ye.blog.admin.vo.ArticleBodyVo;
import com.ye.blog.admin.vo.ArticleVo;
import com.ye.blog.admin.vo.Result;
import com.ye.blog.admin.vo.TagVo;
import com.ye.blog.admin.vo.params.ArticleParam;
import com.ye.blog.admin.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//标注为service，注册到容器里面去，实现对应的接口
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagsService;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }

    /**
     * 展示列表
     * @param pageParams
     * @return
     */
    // @Override
    // public Result listArticle(PageParams pageParams) {
    //
    //     /**
    //      * 需求：
    //      *          * 1.分页查询 article数据库表
    //      */
    //
    //     //需要两个参数,这个Page导入的包是mybatis的，一定不要导错
    //     Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
    //     //查询条件
    //     LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    //     //查询文章的参数 加上分类id，判断不为空 加上分类条件
    //     if (pageParams.getCategoryId() != null) {
    //         // and category_id = #{categoryId}
    //         queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
    //     }
    //     List<Long> articleIdList = new ArrayList<>();
    //     if (pageParams.getTagId() != null){
    //         //加入标签 条件查询
    //         //article表中 并没有tag字段 一篇文章有多个标签
    //         //article_tag article_id  1 : n tag_id
    //         LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
    //         articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
    //         List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
    //         for (ArticleTag articleTag : articleTags) {
    //             articleIdList.add(articleTag.getArticleId());
    //         }
    //         if (articleIdList.size() > 0){
    //             // and id in {1,2,3}
    //             queryWrapper.in(Article::getId,articleIdList);
    //         }
    //     }
    //
    //     //是否置顶进行排序，权重为1为置顶文章，因为看orderByDesc源码知后面是可以跟多个参数的，两句合成一条
    //     // queryWrapper.orderByDesc(Article::getWeight);
    //     //按照时间顺序倒序排序，相当于order by create_dte desc
    //     queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
    //     Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);//在最后.var快速补充前面部分
    //     //得到page后自然得到list
    //     List<Article> records = articlePage.getRecords();
    //     //能直接返回吗？很明显不能，article对应的是数据库的数据，实际页面上不一定是数据库展示的数据，需要vo对象
    //     List<ArticleVo> articleVoList = copyList(records,true,true);//articleVoList转成对应的vo对象返回
    //     return Result.success(articleVoList);
    // }


    /**
     * 复制一份信息
     * @param records
     * @param isTag
     * @param isAuthor
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        //copy后循环遍历
        List<ArticleVo> articleVoList = new ArrayList<>();
        //使用records.for即可实现foreach
        for (Article record : records) {
            ArticleVo articleVo = copy(record,isTag,isAuthor,false,false);
            articleVoList.add(articleVo);
        }
        return articleVoList;//最后返回
    }

    /**
     * 重载复用
     * @param records
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        //copy后循环遍历
        List<ArticleVo> articleVoList = new ArrayList<>();
        //使用records.for即可实现foreach
        for (Article record : records) {
            ArticleVo articleVo = copy(record,isTag,isAuthor,isBody,isCategory);
            articleVoList.add(articleVo);
        }
        return articleVoList;//最后返回
    }


    @Autowired
    private CategoryService categoryService;

    /**
     * 这个地方再做一个转移，再使用BeanUtils这个类，由Spring提供
     * @param article
     * @param isTag
     * @param isAuthor
     * @return
     */
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));//防止空指针问题把toString()方法，改为String.valueOf()
        BeanUtils.copyProperties(article,articleVo);//把相同的属性copy进来了
        //因为Article类中的createDate是long型，copy不过来
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签，作者信息
        if(isTag){
            //文章的tag
            Long articleId = article.getId();
            articleVo.setTags(tagsService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }



    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);//只要这两个数据
        queryWrapper.last("limit " + limit);
        //select id,title from article order by view_counts desc limit 5;
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }


    /**
     * 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);//只要这两个数据
        queryWrapper.last("limit " + limit);
        //select id,title from article order by create_date desc limit 5;
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {

        /**
         * 1.根据id查询 文章信息
         * 2.根据bodyId和categoryid 去做关联查询
         */

        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);
        //查看完文章了之后新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，只额时候做了一个更新操作，更新时写加锁，阻塞了其他的读操作，性能就会比较低
        //更新  增加了此次接口的耗时 如果一旦更新出问题，不能影响 查看文章的操作
        //线程池 可以把更新操作 扔到线程池中去执行，和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }


    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result publish(ArticleParam articleParam) {
        //此接口 要加入到登录拦截当中
        SysUser sysUser = UserThreadLocal.get();
        /**
         *1.发布文章 目的 构建Article对象
         * 2.作者id 当前的登录用户
         * 3.标签 要讲标签加入到关联列表当中
         * 4.body 内容存储 也是要关联article  bodyId
         */
        //1
        Article article = new Article();
        //2
        article.setAuthorId(sysUser.getId());
        //后增加的
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        // article.setBodyId(-1L);//这句代码会导致发布不成功
        //插入之后 会生成一个文章id
        this.articleMapper.insert(article);
        //3.tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));//强转一下
                articleTag.setArticleId(articleId);
                this.articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);//先插入才会产生对应的id
        article.setBodyId(articleBody.getId());
        //进行更新
        articleMapper.updateById(article);
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        //也可以放回vo对象，但要在前面加上json序列化，不然会有精度损失
        // ArticleVo articleVo = new ArticleVo();
        // articleVo.setId(article.getId());
        return Result.success(map);
    }




}
