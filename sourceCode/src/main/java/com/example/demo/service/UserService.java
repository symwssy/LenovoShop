package com.example.demo.service;

import com.example.demo.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findbyUserName(String username);
    //注册
    void register(String username, String password);

    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
