package com.xuecheng.manage_media.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.domain.media.response.MediaCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.config.RabbitMQConfig;
import com.xuecheng.manage_media.controller.MediaUploadController;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import com.xuecheng.manage_media.service.MediaUploadService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class MediaUploadServiceImpl implements MediaUploadService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MediaUploadController.class);

    @Autowired
    MediaFileRepository mediaFileRepository;
    // 视频处理路由
    @Value("${xc-service-manage-media.mq.routingkey-media-video}")
    public String routingkey_media_video;

    @Autowired
    RabbitTemplate rabbitTemplate;
    // 上传文件根目录
    @Value("${xc-service-manage-media.upload-location}")
    String uploadPath;

    /**
     * 检查文件信息是否已经存在本地以及mongodb内,其中一者不存在则重新注册
     *
     * @param fileMd5 文件md5值
     * @param fileExt 文件扩展名
     * @return 文件路径
     */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        // 1.检查文件在磁盘上是否存在
        // 2.检查文件信息在mongodb上是否存在

        // 获取文件所属目录以及文件路径
        String fileFloderPath = this.getFileFloderPath(fileMd5);
        String filePath = this.getFileFullPath(fileMd5, fileExt);
        File file = new File(filePath);
        boolean exists = file.exists();

        // 查询mongodb上的文件信息
        Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
        if (exists && optional.isPresent()) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        // 其中一者不存在则重新注册文件信息
        File fileFloder = new File(fileFloderPath);
        if (!fileFloder.exists()) {
            // 创建文件目录
            fileFloder.mkdirs();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }


    /**
     * 根据文件md5得到文件的所属目录
     * 规则：
     * 一级目录：md5的第一个字符
     * 二级目录：md5的第二个字符
     * 三级目录：md5
     */
    private String getFileFloderPath(String fileMd5) {
        return uploadPath + fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/";
    }

    /**
     * 获取全文件路径
     * 文件名：md5+文件扩展名
     */
    private String getFileFullPath(String fileMd5, String fileExt) {
        String floderPath = this.getFileFloderPath(fileMd5);
        return floderPath + fileMd5 + "." + fileExt;
    }

    /**
     * 获取文件路径
     * 文件名：md5+文件扩展名
     */
    private String getFilePath(String fileMd5, String fileExt) {
        return "/" + fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/";
    }

    /**
     * 检查文件块是否存在
     *
     * @param fileMd5   文件md5
     * @param chunk     块编号
     * @param chunkSize 块大小
     * @return CheckChunkResult
     */
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        // 获取文件块路径
        String chunkFloder = this.getChunkFloderPath(fileMd5);
        File chunkFile = new File(chunkFloder + chunk);
        if (chunkFile.exists()) {
            return new CheckChunkResult(CommonCode.SUCCESS, true);
        }

        return new CheckChunkResult(CommonCode.SUCCESS, false);
    }

    // 获取文件块路径
    private String getChunkFloderPath(String fileMd5) {
        // 获取分块文件所属目录
        String fileFloderPath = this.getFileFloderPath(fileMd5);
        String chunkFloder = fileFloderPath + "chunk/";
        File fileChunkFloder = new File(chunkFloder);
        // 如果分块所属目录不存在则创建
        if (!fileChunkFloder.exists()) {
            fileChunkFloder.mkdirs();
        }
        return chunkFloder;
    }

    /**
     * 上传分块文件
     *
     * @param file    上传的文件
     * @param chunk   分块号
     * @param fileMd5 文件MD5
     * @return
     */
    public ResponseResult uploadChunk(MultipartFile file, Integer chunk, String fileMd5) {
        // 获取分块文件所属目录
        String chunkFloder = this.getChunkFloderPath(fileMd5);
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = file.getInputStream();
            fileOutputStream = new FileOutputStream(chunkFloder + chunk);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            // 文件保存失败
            e.printStackTrace();
            LOGGER.error("upload chunk file fail:{}", e.getMessage());
            ExceptionCast.cast(MediaCode.CHUNK_FILE_UPLOAD_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 合并文件块信息
     *
     * @param fileMd5  文件MD5
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param mimetype 文件类型
     * @param fileExt  文件拓展名
     * @return ResponseResult
     */
    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        // 获取文件块路径
        String chunkFloderPath = getChunkFloderPath(fileMd5);
        // 合并文件路径
        String fileFullPath = this.getFileFullPath(fileMd5, fileExt);
        File mergeFile = new File(fileFullPath);
        // 创建合并文件,如果存在则先删除再创建
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        boolean newFile = false;
        try {
            newFile = mergeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("mergechunks..create mergeFile fail:{}", e.getMessage());
        }
        if (!newFile) {
            // 文件创建失败
            ExceptionCast.cast(MediaCode.CREATE_MERGE_FILE_FAIL);
        }

        // 获取块文件列表,此列表是已经排序好的
        List<File> chunkFiles = this.getChunkFiles(chunkFloderPath);

        // 合并文件
        mergeFile = this.mergeFile(mergeFile, chunkFiles);
        if (mergeFile == null) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(mergeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 校验文件
        boolean checkResult = this.checkFileMd5(mergeFile, fileMd5);
        if (!checkResult) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }

        // 将文件信息保存到数据库
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileName(fileMd5 + "." + fileExt);
        mediaFile.setFileOriginalName(fileName);

        // 文件路径保存相对路径
        String filePath = this.getFilePath(fileMd5, fileExt);
        mediaFile.setFilePath(filePath);
        mediaFile.setFileUrl(filePath + mediaFile.getFileName());
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        mediaFile.setAbsolutePath(uploadPath);

        // 状态为上传成功
        mediaFile.setFileStatus("301002");
        mediaFileRepository.save(mediaFile);
        // 调用垃圾回收器，防止删除物理文件时被占用
        System.gc();
        // 向MQ发送视频处理消息
        this.sendProcessVideoMsg(fileMd5);

        return new ResponseResult(CommonCode.SUCCESS);

    }


    // 向MQ发送视频处理消息
    private ResponseResult sendProcessVideoMsg(String mediaId) {
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        if (!optional.isPresent()) {
            return new ResponseResult(MediaCode.MEDIA_MONGO_DATA_ISNULL);
        }
        MediaFile mediaFile = optional.get();
        // 发送视频处理消息
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("mediaId", mediaId);
        // 发送的消息
        String msg = JSON.toJSONString(msgMap);
        try {
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK, routingkey_media_video, msg);
            LOGGER.info("send media process task msg:{}", msg);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("send media process task error,msg is:{},error:{}", msg, e.getMessage());
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    // 校验文件MD5
    private boolean checkFileMd5(File mergeFile, String fileMd5) {
        if (mergeFile == null || StringUtils.isEmpty(fileMd5)) {
            return false;
        }
        // 进行md5校验
        try {
            FileInputStream fileInputStream = new FileInputStream(mergeFile);
            // 得到文件的MD5
            String md5Hex = DigestUtils.md5Hex(fileInputStream);
            // 比较两个MD5值
            if (md5Hex.equalsIgnoreCase(fileMd5)) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("未找到该文件 {}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 合并文件
    private File mergeFile(File mergeFile, List<File> chunkFiles) {
        RandomAccessFile raf_write = null;
        RandomAccessFile raf_read = null;
        try {
            // 创建写文件对象
            raf_write = new RandomAccessFile(mergeFile, "rw");
            // 遍历分块文件开始合并
            // 读取文件缓冲区
            byte[] b = new byte[1024];
            for (File chunkFile : chunkFiles) {
                raf_read = new RandomAccessFile(chunkFile, "r");
                int len = -1;
                // 读取分块文件
                while ((len = raf_read.read(b)) != -1) {
                    // 向合并文件中写数据
                    raf_write.write(b, 0, len);
                }
                raf_read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("merge file error:{}", e.getMessage());
            return null;
        } finally {
            if (raf_read != null) {
                try {
                    raf_read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf_write != null) {
                try {
                    raf_write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return mergeFile;
    }

    // 获取块文件列表
    private List<File> getChunkFiles(String chunkFloderPath) {
        // 块文件目录
        File chunkFolder = new File(chunkFloderPath);
        // 分块文件列表
        File[] fileArray = chunkFolder.listFiles();
        // 将分块列表转为集合,便于排序
        assert fileArray != null;
        ArrayList<File> fileList = new ArrayList<>(Arrays.asList(fileArray));
        // 从小到大排序,按名称升序
        fileList.sort((o1, o2) -> {
            // 比较两个文件的名称
            if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName()))
                return -1;
            return 1;
        });
        return fileList;
    }

    @Override
    public ResponseResult quickUploadProcess(String mediaId) {
        Optional<MediaFile> byId = mediaFileRepository.findById(mediaId);
        if (!byId.isPresent()) ExceptionCast.cast(MediaCode.MEDIA_MONGO_DATA_ISNULL);
        MediaFile mediaFile = byId.get();
        String processStatus = mediaFile.getProcessStatus();
        if (processStatus.equals("303002")) return new ResponseResult(MediaCode.MEDIA_PROCESS_STATUS);
        return this.sendProcessVideoMsg(mediaId);
    }
}
