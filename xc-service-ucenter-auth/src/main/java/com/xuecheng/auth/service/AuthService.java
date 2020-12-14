package com.xuecheng.auth.service;

import com.xuecheng.framework.domain.ucenter.ext.AuthToken;

public interface AuthService {

    AuthToken login(String username, String password, String clientId, String clientSecret);

    // 从redis查询令牌
    AuthToken getUserToken(String token);
    Boolean delToken(String uid);
}