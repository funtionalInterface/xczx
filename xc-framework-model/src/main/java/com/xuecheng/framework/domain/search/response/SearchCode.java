package com.xuecheng.framework.domain.search.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Created by admin on 2018/3/5.
 */
@ToString
@NoArgsConstructor
public enum SearchCode implements ResultCode {
    ES_CACHE_DELETE_ERROR(false, 22000, "删除缓存媒资信息失败！"),
    ES_CACHE_DELETE_NULL(false, 22001, "删除的缓存媒资信息为空！");


    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    private SearchCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private static final ImmutableMap<Integer, SearchCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, SearchCode> builder = ImmutableMap.builder();
        for (SearchCode commonCode : values()) {
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
