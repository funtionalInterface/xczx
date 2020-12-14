package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "数据字典管理接口", description = "数据字典管理接口，提供字典的增、删、改、查")
public interface SystemControllerApi {
    @ApiOperation("查询课程等级")
    SysDictionary getSysDictionary(String dictionaryTypeName);
}
