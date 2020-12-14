package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface MediaUploadService {
    // 注册上传文件
    ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    // 检查分块是否存在
    CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize);

    // 上传文件分块
    ResponseResult uploadChunk(MultipartFile file, Integer chunk, String fileMd5);

    // 合并分块生成文件
    ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    // 发送视频处理信息
    ResponseResult quickUploadProcess(String mediaId);
}
