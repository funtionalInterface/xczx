package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator.
 */

@Api(value = "课程管理接口", description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {
    @ApiOperation("课程计划列表查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("删除课程计划")
    ResponseResult delTeachplan(String teachplanId);

    @ApiOperation("按id查询课程计划")
    ResponseResult getTeachplanById(String teachplanId);

    @ApiOperation("编辑课程计划")
    ResponseResult editTeachplan(Teachplan teachplan);

    @ApiOperation("分页查询课程列表")
    QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("按课程id查询")
    CourseBase getCourseById(String baseId);

    @ApiOperation("添加课程")
    AddCourseResult addCourseBase(CourseBase base);

    @ApiOperation("修改课程")
    QueryResponseResult editCourseBase(String id, CourseBase base);

    @ApiOperation("按课程id查询课程营销")
    CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("修改课程营销（没有则创建）")
    QueryResponseResult editCourseMarketForm(String courseId, CourseMarket courseMarket);

    @ApiOperation("保存课程图片信息")
    ResponseResult saveCoursePic(String courseId, String pic);

    @ApiOperation("获得课程图片信息")
    CoursePic getCoursePic(String courseId);

    @ApiOperation("删除课程图片信息")
    ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    CourseView courseView(String courseId);

    @ApiOperation("课程发布预览")
    CoursePublishResult CoursePublishPreview(String courseId);

    @ApiOperation("查询课程预览url")
    CoursePublishResult findCoursePublishPreviewUrl();

    @ApiOperation("课程发布")
    CoursePublishResult CoursePublish(String courseId);

    @ApiOperation("查询指定公司下的所有课程")
    QueryResponseResult<CourseInfo> findCourseListByCompany(
            int page,
            int size,
            CourseListRequest courseListRequest
    );
}
