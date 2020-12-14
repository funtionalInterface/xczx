package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;

public interface SystemService {

    // 根据字典分类type查询字典信息
    SysDictionary findBydType(String dType);
}
