package com.lrm.blog.dao;

import com.lrm.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Sort - 从最新往下排序
     List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
