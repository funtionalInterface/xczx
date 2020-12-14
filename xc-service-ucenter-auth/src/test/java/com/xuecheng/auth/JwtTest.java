package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    // 生成一个jwt令牌
    @Test
    public void testCreateJwt(){
        // 证书文件
        String key_location = "xc.keystore";
        // 密钥库密码
        String keystore_password = "xuecheng";
        // 访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        // 密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, keystore_password.toCharArray());
        // 密钥的密码，此密码和别名要匹配
        String keypassword = "xuecheng";
        // 密钥别名
        String alias = "xckey";
        // 密钥对（密钥和公钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypassword.toCharArray());
        // 私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        // 定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "mrt");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
        // 生成jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(aPrivate));
        // 取出jwt令牌
        String token = jwt.getEncoded();
        System.out.println(token);
    }
    // 资源服务使用公钥验证jwt的合法性，并对jwt解码
    @Test
    public void testVerify(){
        // jwt令牌
        String token ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYwMjkwMTQ1MCwianRpIjoiN2RkMDJkZjItZGM3MS00ZTIwLTgxYzgtZDZkOTJjNjUwZmMwIiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.FJk3qlExoel1k4o7x_W7OOI-x9i7BODr5uXGUxVlMnJCknbbKZRjZoqi1WEm7QKN1C44iDz-RreEDqT9LIC-IRGgLPrZvj67SkzS3PhrVLqkgVMJd8BzD3onV0nvFnsgEsz12pe6OtaMe7FiFsaXJWiHI3hPE5CmsGty-5bXCaVVbum6Kl-fpoukbHwabaiMFisoqpW7prw8Axpa54fMhU3sVkerLPKQPkoVOGIraUpWAX1CnCvN8FuEI7dYPSMtN5XOtVwAKZEOld7lwsLZr_8ITQd_Mx81CziQ6JkvuofKVKNKx8mg1RyhW8KEifqncq7edTnHNnD_uwplR42wVQ";
        // 公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmqz74jmebx7X7VP8YUAAnm2VWTdJ+dmt+eR0pS27kQyQ/Pliaynqsw37L7WLy0ERFuYyaIhhJQhjbmKcKg6Gl5zbWKKhtnLiSdCMbWNVrCpi5zu1zrktJ5DsPILp3mAhhEW6+/hg+Z8QxiDot7eAkSU07tIlDr6E3MqgD7KPI9g4G1w9s+LHDzRYuIgsBL1hj9m2igXJ8N52A3kplH10Cgu0m3LAK5MYenK8jc2m+ppAEDmwix9Yojb4cM+Xk9DnY8LwgaDPdUHHWk/9G1GYt2lHMh5elDlh1dePdGR9c/BLFh2HFQ+3qV8YwPoDK/KhBpHKs0RqZdXK319ZtGmkwwIDAQAB-----END PUBLIC KEY-----";
        // 校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        // 获取jwt原始内容
        String claims = jwt.getClaims();
        Map maps = (Map)JSON.parse(claims);
        System.out.println(maps);
        // jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void testPasswrodEncoder(){
        String password = "111111";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for(int i=0;i<10;i++) {
            // 每个计算出的Hash值都不一样
            String hashPass = passwordEncoder.encode(password);
            System.out.println(hashPass);
            // 虽然每次计算的密码Hash值不一样但是校验是通过的
            boolean f = passwordEncoder.matches(password, hashPass);
            System.out.println(f);
        }
    }
}
