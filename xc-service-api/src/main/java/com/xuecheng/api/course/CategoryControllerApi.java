package com.xuecheng.api.course;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="课程分类管理接口",description = "课程分类管理接口，提供课程的增、删、改、查")
public interface CategoryControllerApi {
    @ApiOperation("分类查询课程列表")
    QueryResponseResult findCategoryList();
}
