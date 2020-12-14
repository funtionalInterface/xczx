package com.xuecheng.manage_cms.gridfs;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void testGridFs() throws FileNotFoundException {
        // 要储存的文件
        File file = new File("C:\\Users\\funtion\\Desktop\\index-banner.ftl");
        // 定义输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "index-banner");
        // 得到文件ID
        String fileId = objectId.toString();
        System.out.println(fileId);

    }


    // 取文件
    @Test
    public void queryFile() throws IOException {
        String fileId = "5f6664371871000081004d32";
        // 根据ID查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        // 打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        // 获取流中的数据
        InputStream inputStream = gridFsResource.getInputStream();
        String filename = gridFsResource.getFilename();
        // 下载文件
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(
                        new File("C:\\Users\\funtion\\Desktop\\11\\"+filename)));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = bufferedInputStream.read(b)) != -1) {
            bufferedOutputStream.write(b, 0, len);
        }
        // 输出文件信息
        String s = IOUtils.toString(inputStream, "utf-8");
        System.out.println(s);


        bufferedInputStream.close();
        bufferedOutputStream.flush();
        bufferedOutputStream.close();

    }

    // 删除文件
    @Test
    public void testDelFile() throws IOException {
        // 根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5b32480ed3a022164c4d2f92")));
    }
}