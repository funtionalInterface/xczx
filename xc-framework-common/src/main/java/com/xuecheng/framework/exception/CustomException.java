package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super("错误代码：" + resultCode.code() + " 错误信息：" + resultCode.message());
        this.resultCode = resultCode;
    }
}
