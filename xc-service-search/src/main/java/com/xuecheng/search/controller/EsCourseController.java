package com.xuecheng.search.controller;

import com.xuecheng.api.search.EsCourseControllerApi;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    EsCourseService esCourseService;


    /**
     * 课程信息搜索
     *
     * @param page              页码
     * @param size              每页数量
     * @param courseSearchParam 搜索参数
     * @return
     * @throws IOException
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CoursePub> findList(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) throws IOException {
        return esCourseService.findList(page, size, courseSearchParam);
    }

    /**
     * 根据id搜索课程发布信息
     *
     * @param id 课程id
     * @return JSON数据
     */
    @GetMapping("/getdetail/{id}")
    public Map<String, CoursePub> getdetail(@PathVariable("id") String id) {
        return esCourseService.getdetail(id);
    }

    /**
     * 根据课程计划id搜索发布后的媒资信息
     *
     * @param teachplanId
     * @return
     */
    @GetMapping(value = "/getmedia/{teachplanId}")
    @Override
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId) {
        // 为了service的拓展性,所以我们service接收的是数组作为参数,以便后续开发查询多个ID的接口
        String[] teachplanIds = new String[]{teachplanId};
        // 通过service查询ES获取课程媒资信息
        QueryResponseResult<TeachplanMediaPub> mediaPubQueryResponseResult = esCourseService.getmedia(teachplanIds);
        QueryResult<TeachplanMediaPub> queryResult = mediaPubQueryResponseResult.getQueryResult();
        if (queryResult != null && queryResult.getList() != null
                && queryResult.getList().size() > 0) {
            // 返回课程计划对应课程媒资
            return queryResult.getList().get(0);
        }
        return new TeachplanMediaPub();
    }

    @Override
    @DeleteMapping("/es/delete/{id}")
    public ResponseResult deleteEsCache(@PathVariable String id) {
        return esCourseService.deleteEsCache(id);
    }
}
