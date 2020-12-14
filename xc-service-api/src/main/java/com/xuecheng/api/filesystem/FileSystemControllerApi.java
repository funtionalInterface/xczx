package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "文件管理接口", description = "文件管理接口，提供对文件的CRUD")
public interface FileSystemControllerApi {

    @ApiOperation("上传文件接口")
    UploadFileResult uploadFile(MultipartFile multipartFile,
                                String fileTage,
                                String businessKey,
                                String metaData);

    @ApiOperation("删除文件接口")
    ResponseResult deleteFile(String picPath);

}
