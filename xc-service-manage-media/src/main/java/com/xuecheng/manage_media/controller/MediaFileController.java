package com.xuecheng.manage_media.controller;

import com.xuecheng.api.media.MediaFileControllerApi;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
    @Autowired
    MediaFileService mediaFileService;

    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult<MediaFile> findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryMediaFileRequest queryMediaFileRequest) {
        // 媒资文件信息查询
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }

    @PreAuthorize("hasAuthority('xc_manager_meida_all')")
    @Override
    @DeleteMapping("/delete/{mediaId}")
    public ResponseResult deleteMedia(@PathVariable String mediaId) {
        return mediaFileService.deleteMedia(mediaId);
    }

    @Override
    @GetMapping("/get/{mediaId}")
    public ResponseResult getMedia(@PathVariable String mediaId) {
        return mediaFileService.getMedia(mediaId);
    }

}