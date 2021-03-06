package com.xuecheng.auth.service.impl;

import com.xuecheng.auth.client.UserClient;
import com.xuecheng.auth.service.UserJwt;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret
        // 开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                // 密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 请求ucenter查询用户
        XcUserExt userext = userClient.getUserext(username);
        if (userext == null) return null; // 如果获取到的用信息为空,则返回null,spring security则会抛出异常

        // 从数据库查询用户正确的密码，Spring Security会去比对输入密码的正确性
        String password = userext.getPassword();
        // 指定用户的权限,从数据库中获取
        List<XcMenu> permissions = userext.getPermissions();
        if (permissions == null) {
            permissions = new ArrayList<>();
        }

        List<String> permissionsCode = new ArrayList<>();
        // 遍历权限对象中的code字段
        permissions.forEach(item -> permissionsCode.add(item.getCode()));
        // 将权限串中间以逗号分隔
        String user_permission_string = StringUtils.join(permissionsCode.toArray(), ",");

        // 设置用户信息到userDetails对象
        UserJwt userDetails = new UserJwt(
                username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));

        // 用户id
        userDetails.setId(userext.getId());
        // 用户名称
        userDetails.setName(userext.getName());
        // 用户头像
        userDetails.setUserpic(userext.getUserpic());
        // 用户类型
        userDetails.setUtype(userext.getUtype());
        // 用户所属企业id
        userDetails.setCompanyId(userext.getCompanyId());

        // 返回用信息给到Spring Security进行处理
        return userDetails;
    }
}
