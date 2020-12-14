package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanMediaControllerApi;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachPlanMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachplan/media")
public class TeachplanMediaController implements TeachplanMediaControllerApi {
    @Autowired
    TeachPlanMediaService teachPlanMediaService;
    @PreAuthorize("hasAuthority('xc_teachmanager_course_plan_media_delete')")
    @Override
    @DeleteMapping("/delete/{mediaId}")
    public ResponseResult deleteMedia(@PathVariable String mediaId) {
        return teachPlanMediaService.deleteMedia(mediaId);
    }
}
