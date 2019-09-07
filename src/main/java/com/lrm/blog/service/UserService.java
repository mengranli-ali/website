package com.lrm.blog.service;

import com.lrm.blog.po.User;

public interface UserService {

    //Define an interface
    User checkUser(String username, String password);

}
