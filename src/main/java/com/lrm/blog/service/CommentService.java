package com.lrm.blog.service;
import com.lrm.blog.po.Comment;
import java.util.List;

public interface CommentService {

    //获取列表
    List<Comment> listCommentByBlogId(Long blogId);

    //保存Comment对象
    Comment saveComment(Comment comment);

}
