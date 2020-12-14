package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;

public interface UserService {
    XcUser findXcUserByUsername(String username);

    // 根据账号查询用户的信息，返回用户扩展信息
    XcUserExt getUserExt(String username);
}
