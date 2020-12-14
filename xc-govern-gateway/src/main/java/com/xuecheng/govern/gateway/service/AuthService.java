package com.xuecheng.govern.gateway.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    // 查询身份令牌
    String getTokenFromCookie(HttpServletRequest request);
    // 从header中查询jwt令牌
    String getJwtFromHeader(HttpServletRequest request);
    // 查询令牌的有效期
    long getExpire(String access_token);
}