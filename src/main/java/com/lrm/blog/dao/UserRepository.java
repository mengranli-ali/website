package com.lrm.blog.dao;

import com.lrm.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //Long - 组件类型
    //UserRepository extends CRUD - 增删改查

    //Define the method to find username&password
    User findByUsernameAndPassword(String username, String password);


}
