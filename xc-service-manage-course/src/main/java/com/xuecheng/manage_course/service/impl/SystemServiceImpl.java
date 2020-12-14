package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.dao.SysDictionaryRepository;
import com.xuecheng.manage_course.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    SysDictionaryRepository sysDictionaryRepository;

    // 根据字典分类type查询字典信息
    public SysDictionary findBydType(String dType) {
        if (StringUtils.isBlank(dType)) ExceptionCast.cast(CommonCode.INVALID_PARAM);
        SysDictionary dictionary = sysDictionaryRepository.findByDType(dType);
        if (dictionary == null) ExceptionCast.cast(CourseCode.COURSE_DICTIONARY_ISNULL);
        return dictionary;
    }
}
