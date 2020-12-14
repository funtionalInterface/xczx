package com.xuecheng.manage_course.client;

import com.xuecheng.framework.model.response.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemClientTest {
    @Autowired
    FileSystemClient fileSystemClient;

    @Test
    public void deleteFile() {
        ResponseResult responseResult = fileSystemClient.deleteFile("group1/M00/00/00/wKj-h19OGVOATRuaAAApF02F6pw627.sql");
        System.out.println(responseResult);
    }
}
