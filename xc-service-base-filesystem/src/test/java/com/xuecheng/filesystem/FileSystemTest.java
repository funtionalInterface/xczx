package com.xuecheng.filesystem;

import com.xuecheng.filesystem.dao.FileSystemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemTest {
    @Autowired
    FileSystemRepository fileSystemRepository;

    @Test
    public void deleteById() {
        Long aLong = fileSystemRepository.deleteByFileId("group1/M00/00/00/wKj-h19jWWuAZ_W0AASBaHsZeYs133.jpg");
        System.out.println(aLong);
    }
}
