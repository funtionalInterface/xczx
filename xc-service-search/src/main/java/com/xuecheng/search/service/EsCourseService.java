package com.xuecheng.search.service;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

import java.io.IOException;
import java.util.Map;

public interface EsCourseService {
    QueryResponseResult<CoursePub> findList(int page, int size, CourseSearchParam courseSearchParam) throws IOException;

    Map<String, CoursePub> getdetail(String id);

    QueryResponseResult<TeachplanMediaPub> getmedia(String[] teachplanIds);

    ResponseResult deleteEsCache(String id);
}
