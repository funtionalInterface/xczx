package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachplanMediaRepository;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import com.xuecheng.manage_course.service.TeachPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;

    public ResponseResult saveTeachplanMedia(TeachplanMedia teachplanMedia) {
        if (teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 课程计划
        String teachplanId = teachplanMedia.getTeachplanId();
        // 查询课程计划
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_ISNULL);
        }
        Teachplan teachplan = optional.get();
        // 只允许为叶子结点课程计划选择视频
        String grade = teachplan.getGrade();
        if (StringUtils.isEmpty(grade) || !grade.equals("3")) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        TeachplanMedia one;
        Optional<TeachplanMedia> teachplanMediaOptional =
                teachplanMediaRepository.findById(teachplanId);
        one = teachplanMediaOptional.orElseGet(TeachplanMedia::new);
        // 保存媒资信息与课程计划信息
        one.setTeachplanId(teachplanId);
        one.setCourseId(teachplanMedia.getCourseId());
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        one.setMediaId(teachplanMedia.getMediaId());
        one.setMediaUrl(teachplanMedia.getMediaUrl());
        TeachplanMedia save = teachplanMediaRepository.save(one);
        System.out.println(save);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
