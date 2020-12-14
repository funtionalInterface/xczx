package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia, String> {

    List<TeachplanMedia> findByCourseId(String courseId);

    TeachplanMedia findByTeachplanId(String teachplanId);

    @Query(value = "select teachplan_id from teachplan_media where media_id = ?1", nativeQuery = true)
    List<String> findByMediaId(String mediaId);

    long deleteByTeachplanId(String teachplanId);
    // 按课程计划id批量删除
    @Query(value = "delete from teachplan_media where teachplan_id in ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteByTeachplanIdIn(List<String> teachplanIds);

    // 根据媒资id删除课程计划媒资信息
    long deleteByMediaId(String mediaId);

}