package com.xuecheng.auth.controller;

import com.xuecheng.api.auth.AuthControllerApi;
import com.xuecheng.auth.service.AuthService;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

// 在配置文件中设置了context-path: /auth 所以这里我们就不用再配置RequestMapping
@RestController
public class AuthController implements AuthControllerApi {

    // 客户端认证信息
    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;

    // cookie域
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    // cookie生命周期
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    // 生命周期
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    @Autowired
    AuthService authService;

    /**
     * 用户登陆接口
     *
     * @param loginRequest 登陆参数
     * @return LoginResult
     */
    @PostMapping("/userlogin")
    @Override
    public LoginResult login(LoginRequest loginRequest) {
        // 校验账号是否输入
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        // 校验密码是否输入
        if (StringUtils.isEmpty(loginRequest.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        // 获取用户token信息并且保存到redis内
        AuthToken authToken = authService.login(
                loginRequest.getUsername(), loginRequest.getPassword(), clientId, clientSecret);
        // 将用户token写入cookie
        String jtw_token = authToken.getJwt_token();
        // 将访问令牌存储到cookie
        this.saveCookie(jtw_token);
        return new LoginResult(CommonCode.SUCCESS, jtw_token);
    }


    @Override
    @GetMapping("/userjwt")
    public JwtResult userjwt() {
        // 获取cookie中的令牌
        String access_token = getTokenFormCookie();
        // 根据令牌从redis查询jwt
        AuthToken authToken = authService.getUserToken(access_token);
        if (authToken == null) {
            return new JwtResult(CommonCode.FAIL, null);
        }
        return new JwtResult(CommonCode.SUCCESS, authToken.getJwt_token());
    }


    // 从cookie中读取访问令牌
    private String getTokenFormCookie() {
        //获取request对象
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        return cookieMap.get("uid");
    }

    // 将令牌保存到cookie
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        // 添加cookie 认证令牌，最后一个参数设置为false，表示允许浏览器获取
        assert response != null;
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, cookieMaxAge, false);
    }


    @PostMapping("/userlogout")
    @Override
    public ResponseResult logout() {
        // 取出用户身份令牌
        String uid = getTokenFormCookie();
        // 删除用户在redis中的身份信息
        Boolean delToken = authService.delToken(uid);
        // 通过修改返回的response来实现用户前端收到响应后删除浏览器的cookie信息
        clearCookie(uid);
        if (delToken) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    // 清除cookie
    private void clearCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        // 设置maxAge为实现删除cookie
        assert response != null;
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, 0, false);
    }
}