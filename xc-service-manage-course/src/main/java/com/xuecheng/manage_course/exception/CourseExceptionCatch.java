package com.xuecheng.manage_course.exception;

import com.xuecheng.framework.exception.ExceptionCatch;
import com.xuecheng.framework.model.response.CommonCode;
import feign.RetryableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CourseExceptionCatch extends ExceptionCatch {

    static {
        BUILDER.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
        BUILDER.put(RetryableException.class, CommonCode.FEIGN_ERROR);
    }
}
