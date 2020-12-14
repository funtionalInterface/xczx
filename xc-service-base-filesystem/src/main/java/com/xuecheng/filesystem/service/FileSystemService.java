package com.xuecheng.filesystem.service;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {

    // 上传文件到FastDFS
    UploadFileResult uploadFile(MultipartFile multipartFile,
                                String fileTag,
                                String businessKey,
                                String metaData);

    // 删除fastdfs上的文件，并删除mongodb的数据
    ResponseResult deleteFile(String picPath);



}
