package com.xuecheng.manage_media;

import com.xuecheng.manage_media.dao.MediaFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MediaTest {
    @Autowired
    MediaFileRepository mediaFileRepository;
    private String chunkPath = "C:/Users/funtion/IdeaProjects/xczx-ui/media/";
    private String fileName = "lucene.mp4";
    private String name = fileName.substring(0, fileName.lastIndexOf("."));
    private File sourceFile = new File(chunkPath + fileName);
    private File chunkFolder = new File(chunkPath + name + "/chunk");

    /**
     * 测试文件分块
     */
    @Test
    public void testChunk() throws IOException {
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        // 分块大小
        long chunkSize = 1024 * 1024 * 1;
        // 分块数量
        // Math.ceil向上取整,例如 12.1=13,12.8=13
        System.out.println(sourceFile.length());
        long chunkNum = (long) Math.ceil((sourceFile.length() * 1.0) / chunkSize);
        chunkNum = chunkNum <= 0 ? 1 : chunkNum;
        // 缓冲区大小
        byte[] byte_cache = new byte[1024];
        // 使用RandomAccessFile访问文件
        RandomAccessFile rafRead = new RandomAccessFile(sourceFile, "r");
        // 分块
        for (int i = 0; i < chunkNum; i++) {
            // 创建分块文件
            File chunkFile = new File(chunkFolder.getPath() + "/" + i);
            boolean newFile = chunkFile.createNewFile();
            if (newFile) {
                // 向分块文件中写入数据
                RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
                int len;
                // 读取到-1则表示读取完成
                while ((len = rafRead.read(byte_cache)) != -1) {
                    raf_write.write(byte_cache, 0, len);
                    // 读取到预期块大小时结束
                    if (chunkFile.length() >= chunkSize) {
                        break;
                    }
                }
                raf_write.close();
            }
        }
        rafRead.close();
    }

    // 测试文件合并方法
    @Test
    public void testMerge() throws IOException {
        File file = new File(chunkPath + "/merge/");
        if (!file.exists()) file.mkdirs();
        // 合并文件
        File mergeFile = new File(file.getPath() + "/" + fileName);
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        // 创建新的合并文件
        mergeFile.createNewFile();
        // 用于写文件
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        // 指针指向文件顶端
        raf_write.seek(0);
        // 缓冲区
        byte[] b = new byte[1024];
        // 分块列表
        File[] fileArray = chunkFolder.listFiles();
        // 转成集合，便于排序
        assert fileArray != null;
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        // 从小到大排序
        fileList.sort((o1, o2) -> {
            if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                return -1;
            }
            return 1;
        });
        // 合并文件
        for (File chunkFile : fileList) {
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            while ((len = raf_read.read(b)) != -1) {
                raf_write.write(b, 0, len);
            }
            raf_read.close();
        }
        raf_write.close();
    }
}
