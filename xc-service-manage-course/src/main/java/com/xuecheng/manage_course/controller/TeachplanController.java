package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class TeachplanController implements TeachplanControllerApi {
    @Autowired
    TeachPlanService teachPlanService;

    @Override
    @PostMapping("/savemedia")
    public ResponseResult saveTeachplanMedia(@RequestBody TeachplanMedia teachplanMedia) {
        return teachPlanService.saveTeachplanMedia(teachplanMedia);
    }
}
