package com.xuecheng.api.course;

import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface TeachplanMediaControllerApi {
    @ApiOperation("删除媒资信息")
    ResponseResult deleteMedia(String mediaId);
}
