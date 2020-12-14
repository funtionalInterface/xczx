package com.xuecheng.framework.domain.course.response;

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
public enum CourseCode implements ResultCode {
    COURSE_DENIED_DELETE(false,31001,"删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(false,31002,"还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(false,31003,"创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(false,31004,"课程Id为空！"),
    COURSE_PUBLISH_VIEWERROR(false,31005,"发布课程视图出错！"),
    COURSE_MEDIA_DATE_PARSE_ERROR(false,31006,"字符串转换日期出错！"),
    COURSE_MEDIS_URLISNULL(false,31007,"选择的媒资文件访问地址为空！"),
    COURSE_MEDIS_NAMEISNULL(false,31008,"选择的媒资文件名称为空！"),
    COURSE_DICTIONARY_ISNULL(false,31009,"数据字典为空！"),
    COURSE_PARAMS_ISNULL(false,31010,"课程或课程数据为空！"),
    COURSE_TEACHPLAN_ISNULL(false,31011,"课程计划为空！"),
    COURSE_TEACHPLAN_SAVE_ERROR(false,31012,"课程计划修改失败！"),
    COURSE_COURSE_ISNULL(false,31013,"课程为空！"),
    COURSE_COURSE_SAVE_ERROR(false,31014,"课程修改失败！"),
    COURSE_COURSEMARKET_ISNULL(false,31015,"课程营销为空！"),
    COURSE_INFO_CHANGE(false,31016,"课程信息无变更！"),
    COURSE_MARKET_ISNULL(false,31017,"获取课程营销为空！"),
    COURSE_PIC_ISNULL(false,31018,"获取课程图片为空！"),
    COURSE_MEDIA_TEACHPLAN_ISNULL(false,31019,"课程视频为空！"),
    COURSE_MEDIA_TEACHPLAN_GRADEERROR(false,31020,"只能选等级为3的课程计划！"),
    COURSE_TEACHPLAN_THIRD_MEDIA_ISNULL(false,31021,"有3级课程计划没有选择视频！"),
    COURSE_TEACHPLAN_SECOND_ISNULL(false,31022,"该课程没有添加2级课程计划！"),
    TEACHPLAN_MEDIA_DELETE_FAIL(false,31023,"课程计划绑定的媒资信息表信息删除失败！"),
    ES_TEACHPLAN_MEDIA_DELETE_FAIL(false,31024,"ES媒资信息删除失败！");

    //操作代码
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    private CourseCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CourseCode> CACHE ;

    static {
        final ImmutableMap.Builder<Integer, CourseCode> builder = ImmutableMap.builder();
        for (CourseCode commonCode : values()) {
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
