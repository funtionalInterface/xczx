package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author Administrator
 * @version 1.0
 **/
public interface CourseService {

    TeachplanNode findTeachplanList(String courseId);

    ResponseResult addTeachplan(Teachplan teachplan);
    // 删除课程计划
    ResponseResult delTeachplan(String teachPlanId);

    QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    CourseBase getCourseById(String baseId);

    QueryResponseResult<CourseBase> editCourse(String id, CourseBase base);

    AddCourseResult addCourseBase(CourseBase courseBase);

    QueryResponseResult<Teachplan> getTeachPlanById(String teachPlanId);

    QueryResponseResult<Teachplan> editTeachplan(Teachplan teachplan);

    CourseMarket findCourseMarketById(String courseId);

    QueryResponseResult<CourseMarket> editCourseMarketForm(String courseId, CourseMarket courseMarket);

    ResponseResult saveCoursePic(String courseId, String pic);

    CourseView getCourseView(String courseId);

    CourseBase findCourseBaseById(String courseId);

    CoursePublishResult coursePublishPreview(String courseId);
    // 发布课程
    CoursePublishResult coursePublish(String courseId);

    CoursePublishResult findPreUrl();

    /**
     * 分页查询指定公司下的课程信息
     */
     QueryResponseResult<CourseInfo> findCourseListByCompany(
             String companyId ,
             int pageNum,
             int size, CourseListRequest courseListRequest);
}
