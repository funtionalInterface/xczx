package com.xuecheng.framework.model.response;

import lombok.ToString;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 */

@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 10000, "操作成功！"),
    FAIL(false, 11111, "操作失败！"),
    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
    INVALID_PARAM(false, 10003, "非法参数！"),
    HTTP_METHOD_ERROR(false,10004,"请求头方法错误！"),
    DATABASE_DATA_ERROR(false,10005,"数据库操作失败！"),
    PIC_DATA_ERROR(false,10006,"找不到上传文件数据！"),
    FEIGN_ERROR(false,10007,"远程调用服务超时！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");
    // private static ImmutableMap<Integer, CommonCode> codes ;
    // 操作是否成功
    boolean success;
    // 操作代码
    int code;
    // 提示信息
    String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
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
