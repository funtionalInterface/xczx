package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableBiMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    // 将我们知道的不可预知异常存入map，用于抛出自定义的错误信息
    private static ImmutableBiMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    protected static final ImmutableBiMap.Builder<Class<? extends Throwable>, ResultCode> BUILDER = ImmutableBiMap.builder();

    static {
        BUILDER.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        BUILDER.put(HttpRequestMethodNotSupportedException.class, CommonCode.HTTP_METHOD_ERROR);
        BUILDER.put(InvalidDataAccessApiUsageException.class, CommonCode.DATABASE_DATA_ERROR);
        BUILDER.put(MissingServletRequestPartException.class, CommonCode.PIC_DATA_ERROR);
        BUILDER.put(RetryableException.class, CommonCode.FEIGN_ERROR);
    }

    // 处理可预知异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        LOGGER.error("catch exception:{}/r/n exception：", customException.getMessage(), customException);
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    // 处理不可预知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        LOGGER.error("catch exception:{}/r/n exception：", exception.getMessage(), exception);
        if (EXCEPTIONS == null) EXCEPTIONS = BUILDER.build();
        ResultCode code = EXCEPTIONS.get(exception.getClass());
        if (code != null) return new ResponseResult(code);
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

}
