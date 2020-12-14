package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CoursePicService {

     CoursePic getCoursePic(String courseId);

     ResponseResult deleteByCourseId(String courseId);
}
