package com.lrm.blog.service;

import com.lrm.blog.po.Blog;
import com.lrm.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //根据一个ID查询blog
    Blog getBlog (Long id);

    Blog getAndConvert(Long id);

    //分页查询 - 传递参数Pageable & Blog
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //新增一个分页显示 - index page - 不用查询
    Page<Blog> listBlog(Pageable pageable);

    //新增一个分页显示 - index page - search query
    Page<Blog> listBlog(String query, Pageable pageable);

    //新增一个list top blog recommendation
    List<Blog> listRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    //新增blog
    Blog saveBlog(Blog blog);
    //更新blog - 根据Blog id来修改
    Blog updateBlog (Long id, Blog blog);
    //删除blog
    void deleteBlog(Long id);
}
