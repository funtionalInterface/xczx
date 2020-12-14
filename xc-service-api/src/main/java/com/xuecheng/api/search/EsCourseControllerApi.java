package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Map;

@Api(value = "课程搜索", description = "基于ES构建的课程搜索API",tags = {"课程搜索"})
public interface EsCourseControllerApi {
    @ApiOperation("课程详细搜索")
    QueryResponseResult<CoursePub> findList(int page, int size, CourseSearchParam courseSearchParam) throws IOException;
    @ApiOperation("根据课程id搜索课程发布信息")
    Map<String,CoursePub> getdetail(String id);
    @ApiOperation("根据课程计划查询媒资信息")
    TeachplanMediaPub getmedia(String teachplanId);
    @ApiOperation("删除ES媒资信息缓存信息")
    ResponseResult deleteEsCache(String id);

}
