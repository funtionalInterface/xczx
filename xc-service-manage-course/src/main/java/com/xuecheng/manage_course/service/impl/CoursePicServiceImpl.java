package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.FileSystemClient;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import com.xuecheng.manage_course.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoursePicServiceImpl implements CoursePicService {
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    FileSystemClient fileSystemClient;
    /**
     * 根据课程id获得课程的图片信息
     * @param courseId
     * @return
     */
    public CoursePic getCoursePic(String courseId) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        return byId.orElse(null);
    }

    @Transactional
    public ResponseResult deleteByCourseId(String courseId) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        String pic = "";
        if (byId.isPresent()) {
            CoursePic coursePic = byId.get();
            pic = coursePic.getPic();
        }
        long l = coursePicRepository.deleteByCourseid(courseId);
        if (l > 0) {
            // 删除fastdfs上的图片文件
            ResponseResult flag = fileSystemClient.deleteFile(pic);
            if (flag.SUCCESS) return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CourseCode.COURSE_PIC_ISNULL);
    }
}
