package com.xuecheng.framework.domain.media.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by admin on 2018/3/5.
 */
@ToString
public enum MediaCode implements ResultCode {
    UPLOAD_FILE_REGISTER_FAIL(false,22001,"上传文件在系统注册失败，请刷新页面重试！"),
    UPLOAD_FILE_REGISTER_EXIST(false,22002,"上传文件在系统已存在！"),
    CHUNK_FILE_UPLOAD_FAIL(false,22003,"上传文件失败！"),
    CHUNK_FILE_EXIST_CHECK(true,22004,"分块文件在系统已存在！"),
    MERGE_FILE_FAIL_EXIST(false,22005,"合并文件失败，文件在系统已存在！"),
    CREATE_MERGE_FILE_FAIL(false,22006,"创建合并文件失败！"),
    MERGE_FILE_FAIL(false,22007,"合并文件失败！"),
    MERGE_FILE_CHECKFAIL(false,22008,"合并文件校验失败！"),
    MEDIA_MONGO_DATA_ISNULL(false,22009,"数据库找不到该视频信息！"),
    MEDIA_DATA_ISNULL(false,22010,"找不到该视频的物理信息！"),
    MEDIA_PROCESS_STATUS(false,22011,"视频已处理完成，无需重复处理！！"),
    MEDIA_PROCESS_FAIL(false,22012,"视频处理失败！"),
    ES_MEDIA_DELETE_FAIL(false,22013,"ES缓存删除失败！");

    // 操作代码
    @ApiModelProperty(value = "媒资系统操作是否成功", example = "true", required = true)
    boolean success;

    // 操作代码
    @ApiModelProperty(value = "媒资系统操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "媒资系统操作提示", example = "文件在系统已存在！", required = true)
    String message;
    MediaCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, MediaCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, MediaCode> builder = ImmutableMap.builder();
        for (MediaCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
