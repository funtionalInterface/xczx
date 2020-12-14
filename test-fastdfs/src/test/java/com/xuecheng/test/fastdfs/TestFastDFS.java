package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 **/
/*@SpringBootTest
@RunWith(SpringRunner.class)*/
public class TestFastDFS {
    private StorageClient1 storageClient1;
    private TrackerServer trackerServer;
    private StorageServer storeStorage;

    @Before
    public void load() {
        try {
            // 加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            // 定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            // 连接tracker
            trackerServer = trackerClient.getConnection();
            // 获取Stroage
            storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 创建stroageClient
            storageClient1 = new StorageClient1(trackerServer, storeStorage);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closed() {
        try {
            if (trackerServer != null) {
                trackerServer.close();
            }
            if (storeStorage != null) {
                storeStorage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 上传文件
    @Test
    public void testUpload() {
        try {
            // 向stroage服务器上传文件
            // 本地文件的路径
            // String filePath = "C:\\Users\\funtion\\IdeaProjects\\JavaDemo\\sqldata\\my_bill.sql";
            String filePath = "C:\\Users\\funtion\\Desktop\\1.jpg";
            // 上传成功后拿到文件Id
            String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(filePath, suffix, null);
            System.out.println(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 下载文件并打印文件内容
    @Test
    public void testDownload() {
        BufferedOutputStream fileOutputStream = null;
        try {
            // 下载文件
            // 文件id
            String fileId = "group1/M00/00/00/wKifgV96h1WALVQYAAB9YpwggMs373.jpg";

            String filePath = "C:\\Users\\funtion\\Desktop\\1\\logo." +
                    fileId.substring(fileId.lastIndexOf(".") + 1);
            byte[] bytes = storageClient1.download_file1(fileId);
            // 使用输出流保存文件
            fileOutputStream = new BufferedOutputStream(
                    new FileOutputStream(new File(filePath)));
            fileOutputStream.write(bytes);

            // 打印文件内容
            String context = new String(bytes);
            System.out.println(context);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testDelete() {
        String fileId = "group1/M00/00/00/wKj-h19OSCiAdkZ2AASBaHsZeYs509.jpg";
        try {
            int i = storageClient1.delete_file1(fileId);
            System.out.println(i);
        } catch (IOException ignored) {

        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
