package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 课程计划mapper
 * Created by Administrator.
 */
@Mapper
public interface TeachplanMapper {
    // 课程计划查询
    TeachplanNode selectList(String courseId);
    // 删除当前计划与所有子计划
    void delTeachplan(String teachPlanId);
    // 获取子计划有课程视频的课程计划id
    List<String> getTeachplanChildMedia(String parentId);
}
