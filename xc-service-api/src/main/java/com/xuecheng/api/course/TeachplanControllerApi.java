package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface TeachplanControllerApi {
    @ApiOperation("保存媒资信息")
    ResponseResult saveTeachplanMedia(TeachplanMedia teachplanMedia);
}
