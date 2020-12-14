package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.XcOauth2Util;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.service.CoursePicService;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CoursePicService coursePicService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);

    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_plan_add')")
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }
    @PreAuthorize("hasAuthority('xc_teachmanager_course_plan_delete')")
    @Override
    @DeleteMapping("/teachplan/del/{teachPlanId}")
    public ResponseResult delTeachplan(@PathVariable("teachPlanId") String teachplanId) {
        return courseService.delTeachplan(teachplanId);
    }

    @Override
    @GetMapping("/teachplan/get/{teachPlanId}")
    public ResponseResult getTeachplanById(@PathVariable("teachPlanId") String teachPlanId) {
        return courseService.getTeachPlanById(teachPlanId);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_plan_edit')")
    @Override
    @PutMapping("/teachplan/edit")
    public ResponseResult editTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.editTeachplan(teachplan);
    }

    @PreAuthorize("hasAnyAuthority('course_find_list')")
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page,
                                                          @PathVariable("size") int size,
                                                          CourseListRequest courseListRequest) {
        return courseService.findCourseList(page, size, courseListRequest);
    }


    @PreAuthorize("hasAuthority('xc_teachmanager_course_add')")
    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourseBase(@RequestBody CourseBase base) {
        return courseService.addCourseBase(base);
    }

    @Override
    @GetMapping("/coursebase/get/{baseId}")
    public CourseBase getCourseById(@PathVariable("baseId") String baseId) {
        return courseService.getCourseById(baseId);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_base_edit')")
    @Override
    @PutMapping("/coursebase/edit/{id}")
    public QueryResponseResult editCourseBase(@PathVariable("id") String id, @RequestBody CourseBase base) {
        return courseService.editCourse(id, base);
    }

    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.findCourseMarketById(courseId);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_coursemarket_edit')")
    @Override
    @PutMapping("/coursemarket/edit/{courseId}")
    public QueryResponseResult editCourseMarketForm(@PathVariable("courseId") String courseId, @RequestBody CourseMarket courseMarket) {
        return courseService.editCourseMarketForm(courseId, courseMarket);
    }

    /**
     * 保存课程信息与图片的对应关系
     *
     * @param courseId 课程id
     * @param pic      图片文件id
     * @return
     */
    @PreAuthorize("hasAuthority('xc_teachmanager_course_pic_add')")
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult saveCoursePic(
            @RequestParam("courseId") String courseId,
            @RequestParam("pic") String pic) {
        return courseService.saveCoursePic(courseId, pic);
    }

    /**
     * 根据课程id获取该课程的课程图片信息
     *
     * @param courseId
     * @return 由于这里每个课程只有一个图片，所以只返回一个 CoursePic 对象
     */
    @Override
    @GetMapping("coursepic/list/{courseId}")
    public CoursePic getCoursePic(@PathVariable("courseId") String courseId) {
        return coursePicService.getCoursePic(courseId);
    }

    /**
     * 删除课程图片信息
     *
     * @param courseId
     * @return
     */
    @PreAuthorize("hasAuthority('xc_teachmanager_course_pic_delete')")
    @Override
    @DeleteMapping("coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return coursePicService.deleteByCourseId(courseId);
    }

    /**
     * 课程预览数据模型查询
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/preview/model/{id}")
    public CourseView courseView(@PathVariable("id") String courseId) {
        return courseService.getCourseView(courseId);
    }

    /**
     * 发布页面
     *
     * @param pageId
     * @return
     */
    @PreAuthorize("hasAuthority('xc_teachmanager_course_publish')")
    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult CoursePublishPreview(@PathVariable("id") String courseId) {
        return courseService.coursePublishPreview(courseId);
    }

    /**
     * 课程发布
     *
     * @param courseId
     * @return
     */
    @PreAuthorize("hasAuthority('xc_teachmanager_course_publish')")
    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult CoursePublish(@PathVariable("id") String courseId) {
        return courseService.coursePublish(courseId);
    }

    @Override
    @GetMapping("/preurl")
    public CoursePublishResult findCoursePublishPreviewUrl() {
        return courseService.findPreUrl();
    }

    /**
     * 查询指定公司下的所有课程
     *
     * @param page              页码
     * @param size              数量
     * @param courseListRequest 查询参数
     * @return QueryResponseResult
     */
    @GetMapping("/coursebase/company/list/{page}/{size}")
    @Override
    public QueryResponseResult<CourseInfo> findCourseListByCompany(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            CourseListRequest courseListRequest
    )
    { // 调用工具类取出用户信息
        XcOauth2Util xcOauth2Util = new XcOauth2Util();
        // 从用户header中附带的jwt令牌取出用户信息
        XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
        // 从用户信息获取该用户所属的公司id，根据该公司id查询该公司下的所有课程
        String companyId = userJwt.getCompanyId();
        return courseService.findCourseListByCompany(companyId, page, size, courseListRequest);
    }
}
