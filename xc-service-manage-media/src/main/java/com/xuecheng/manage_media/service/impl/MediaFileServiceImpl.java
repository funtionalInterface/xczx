package com.xuecheng.manage_media.service.impl;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.domain.media.response.MediaCode;
import com.xuecheng.framework.domain.media.response.MediaFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.client.CourseClient;
import com.xuecheng.manage_media.client.EsSearchClient;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import com.xuecheng.manage_media.service.MediaFileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

@Service
public class MediaFileServiceImpl implements MediaFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaFileServiceImpl.class);

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    CourseClient courseClient;
    @Autowired
    EsSearchClient esSearchClient;

    /**
     * 分页查询文件信息
     *
     * @param page                  页码
     * @param size                  每页数量
     * @param queryMediaFileRequest 查询条件
     * @return QueryResponseResult
     */
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        MediaFile mediaFile = new MediaFile();
        // 查询条件
        if (queryMediaFileRequest == null) {
            queryMediaFileRequest = new QueryMediaFileRequest();
        }

        // 查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains()) //模糊匹配
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains()) //模糊匹配文件原始名称
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());//精确匹配
        // 设置查询条件对象
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getTag())) {
            // 设置标签
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())) {
            // 设置文件原始名称
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())) {
            // 设置处理状态
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }

        // 定义Example实例
        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);

        // 校验page和size参数的合法性,并设置默认值

        page = page <= 0 ? 0 : --page;
        size = size <= 0 ? 10 : size;

        // 分页对象
        PageRequest pageRequest = new PageRequest(page, size);
        // 分页查询
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageRequest);
        // 设置响应对象属性
        QueryResult<MediaFile> mediaFileQueryResult = new QueryResult<MediaFile>();
        mediaFileQueryResult.setList(all.getContent());
        mediaFileQueryResult.setTotal(all.getTotalElements());

        return new QueryResponseResult<>(CommonCode.SUCCESS, mediaFileQueryResult);
    }

    @Override
    public ResponseResult deleteMedia(String mediaId) {
        // 取出视频文件夹绝对路径
        Optional<MediaFile> byId = mediaFileRepository.findById(mediaId);
        if (!byId.isPresent()) ExceptionCast.cast(MediaCode.MEDIA_MONGO_DATA_ISNULL);
        MediaFile mediaFile = byId.get();
        String absolutePath = mediaFile.getAbsolutePath();
        String path = absolutePath + mediaId.charAt(0);
        // 删除 media_file
        mediaFileRepository.deleteById(mediaId);
        // 删除 teachplan_media_pub
        // 删除 teachplan_media
        courseClient.deleteMedia(mediaId);
        // 删除es缓存
        ResponseResult flag = esSearchClient.deleteEsCache(mediaId);
        // 删除失败不进行处理，进行下一步删除
//        if (!flag.isSuccess()) ExceptionCast.cast(MediaCode.ES_MEDIA_DELETE_FAIL);
        // 删除视频文件夹
        this.deleteDir(new File(path));
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult getMedia(String mediaId) {
        Optional<MediaFile> byId = mediaFileRepository.findById(mediaId);
        if (!byId.isPresent()) ExceptionCast.cast(MediaCode.MEDIA_MONGO_DATA_ISNULL);
        MediaFile mediaFile = byId.get();
        // 删除视频原始文件后缀名，优化用户体验
        String fileOriginalName = mediaFile.getFileOriginalName();
        String substring = fileOriginalName.substring(0, fileOriginalName.lastIndexOf("."));
        // 只返回原始文件名称和播放url，保证数据安全
        MediaFile file = new MediaFile();
        file.setFileOriginalName(substring);
        file.setFileUrl(mediaFile.getFileUrl());
        return new MediaFileResult(CommonCode.SUCCESS, file);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}