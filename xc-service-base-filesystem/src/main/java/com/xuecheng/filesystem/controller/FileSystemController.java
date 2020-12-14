package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult uploadFile(
            @RequestParam(value = "file", required = true) MultipartFile multipartFile,
            String fileTage, String businessKey, String metaData) {
        return fileSystemService.uploadFile(multipartFile, fileTage, businessKey, metaData);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseResult deleteFile(@RequestParam("picPath") String picPath) {
        return fileSystemService.deleteFile(picPath);
    }
}
