package com.xuecheng.filesystem.controller;

import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.model.response.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemController {
    @Autowired
    FileSystemService fileSystemService;

    @Test
    public void delete() {
        ResponseResult responseResult = fileSystemService.deleteFile("group1/M00/00/00/wKj-h19jWhWAIm4_AASBaHsZeYs648.jpg");
        System.out.println(responseResult);
    }
}
