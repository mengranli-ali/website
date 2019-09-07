package com.lrm.blog.service;

import com.lrm.blog.dao.UserRepository;
import com.lrm.blog.po.User;
import com.lrm.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//可用的service
@Service
public class UserServiceImpl implements UserService{

    //引用才能使用
    @Autowired
    private UserRepository userRepository;

    //Override the method -
    //根据用户名和密码查是否有这个user，查到就return a user; If not, return null
    @Override
    public User checkUser(String username, String password) {
        //就要操作数据库了 - 构建DAO层
        //引用DAO层JPA中的method
        //MD5加密密码！！！！！
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
