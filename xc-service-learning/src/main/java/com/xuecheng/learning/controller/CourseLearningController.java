package com.xuecheng.learning.controller;

import com.xuecheng.api.course.CourseLearningControllerApi;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {
    @Autowired
    LearningService learningService;

    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getMediaPlayUrl(@PathVariable String courseId, @PathVariable String teachplanId) {
        // 获取课程学习地址
        return learningService.getMediaPlayUrl(courseId, teachplanId);
    }
}