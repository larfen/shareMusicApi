package com.jdxiang.shareMusicApi.service;

import com.jdxiang.shareMusicApi.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    String tokenHeader = "Authorization";

    User getCurrentUser(HttpServletRequest request);

    // 通过token登陆
    User loginByToken(String userName, String password, HttpServletResponse response);

    User register(User user, HttpServletResponse response);
}
