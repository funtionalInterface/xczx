package com.xuecheng.manage_course.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsClient;
import com.xuecheng.manage_course.dao.*;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class CourseServiceImpl implements CourseService {

    //从配置文件获取课程发布的基本配置
    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Autowired
    CmsClient cmsPageClient;
    @Autowired
    CoursePubRepository coursePubRepository;
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseMarketRepository courseMarketRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    // 添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        // 校验课程id和课程计划名称
        if (teachplan == null ||
                StringUtils.isEmpty(teachplan.getPname()) ||
                StringUtils.isEmpty(teachplan.getCourseid())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 课程id
        String courseid = teachplan.getCourseid();
        // 父结点的id
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            // 获取课程的根结点
            parentid = getTeachplanRoot(courseid);
        }
        // 查询根结点信息
        assert parentid != null;
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan teachplan1 = optional.get();
        // 父结点的级别
        String parent_grade = teachplan1.getGrade();
        // 创建一个新结点准备添加
        Teachplan teachplanNew = new Teachplan();
        // 将teachplan的属性拷贝到teachplanNew中
        BeanUtils.copyProperties(teachplan, teachplanNew);
        // 要设置必要的属性
        teachplanNew.setParentid(parentid);
         /* 设置新节点的级别
            方法1：根据父节点的级别进行设置，父节点级别为1，当前则为2，为2则当前为3
            方法2：在判断前端传入的父节点是否为空时进行设置，如果为空，表示需求为添加二级节点，设置2
                  如果不为空，则表示要添加的是三级节点，设置为3，与方法1相比可以减少一次查询。
         */
        if ("1".equals(parent_grade)) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachplanNew.setStatus("0");//未发布
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取课程的根结点
    private String getTeachplanRoot(String courseId) {
        // 校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
        // 调用dao查询teachplan表得到该课程的根结点（一级结点）
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        // 如果根节点不存在，则新增一个节点，并且作为该课程的根节点
        if (teachplanList == null || teachplanList.size() <= 0) {
            // 新添加一个课程的根结点
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setParentid("0");
            teachplan.setGrade("1"); // 一级结点
            teachplan.setStatus("0"); // 未发布
            teachplan.setPname(courseBase.getName());
            teachplanRepository.save(teachplan);
            return teachplan.getId();

        }
        // 返回根结点的id
        return teachplanList.get(0).getId();

    }

    @Transactional
    public ResponseResult delTeachplan(String teachPlanId) {
        if (StringUtils.isBlank(teachPlanId)) ExceptionCast.cast(CommonCode.INVALID_PARAM);
        Optional<Teachplan> byId = teachplanRepository.findById(teachPlanId);
        if (!byId.isPresent()) ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_ISNULL);
        // 删除本身和所有子的media
        List<String> media = teachplanMapper.getTeachplanChildMedia(teachPlanId);
        teachplanMediaRepository.deleteByTeachplanIdIn(media);
        // 删除本身和所有子的teachplan
        teachplanMapper.delTeachplan(teachPlanId);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    // 查询课程列表
    @Transactional
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 20;
        }
        PageHelper.startPage(page, size);  //设置分页参数
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        QueryResult<CourseInfo> queryResult = new QueryResult<CourseInfo>();
        queryResult.setList(courseListPage.getResult());
        queryResult.setTotal(courseListPage.getTotal());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    // 查询单个课程
    public CourseBase getCourseById(String baseId) {
        Optional<CourseBase> courseBaseTemp = courseBaseRepository.findById(baseId);
        return courseBaseTemp.orElse(null);
    }

    // 更新课程信息
    public QueryResponseResult<CourseBase> editCourse(String id, CourseBase base) {
        if (base == null) ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_ISNULL);
        Optional<CourseBase> byId = courseBaseRepository.findById(id);
        // 返回响应结果
        CourseBase courseBase = byId.orElse(null);
        QueryResult<CourseBase> queryResult = new QueryResult<>();

        ArrayList<CourseBase> courses = new ArrayList<>();

        if (base.equals(courseBase)) {
            courses.add(base);
            queryResult.setList(courses);
            queryResult.setTotal(courses.size());
            return new QueryResponseResult<>(CourseCode.COURSE_INFO_CHANGE, queryResult);
        }
        base.setId(id);
        CourseBase save = courseBaseRepository.save(base);
        courses.add(save);
        queryResult.setList(courses);
        queryResult.setTotal(courses.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    // 添加课程提交
    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase) {
        if (courseBase == null) ExceptionCast.cast(CourseCode.COURSE_PARAMS_ISNULL);
        // 设置状态未发布
        courseBase.setStatus("202001");
        courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS, courseBase.getId());
    }

    public QueryResponseResult<Teachplan> getTeachPlanById(String teachPlanId) {
        Optional<Teachplan> one = teachplanRepository.findById(teachPlanId);
        if (!one.isPresent()) ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_ISNULL);
        QueryResult<Teachplan> queryResult = new QueryResult<>();
        ArrayList<Teachplan> teachplans = new ArrayList<>();
        teachplans.add(one.get());
        queryResult.setList(teachplans);
        queryResult.setTotal(teachplans.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    @SuppressWarnings("all")
    @Transactional
    public QueryResponseResult<Teachplan> editTeachplan(Teachplan teachplan) {
        if (teachplan == null) ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_ISNULL);
        Teachplan save = teachplanRepository.save(teachplan);
        if (save == null) ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_SAVE_ERROR);
        QueryResult<Teachplan> queryResult = new QueryResult<>();
        ArrayList<Teachplan> teachplans = new ArrayList<>();
        teachplans.add(save);
        queryResult.setList(teachplans);
        queryResult.setTotal(teachplans.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    // 按课程id查询课程营销
    public CourseMarket findCourseMarketById(String courseId) {
        Optional<CourseMarket> byId = courseMarketRepository.findById(courseId);
        return byId.orElse(null);
    }

    // 修改课程营销（没有则创建）
    @Transactional
    public QueryResponseResult<CourseMarket> editCourseMarketForm(String courseId, CourseMarket courseMarket) {
        if (courseMarket == null) ExceptionCast.cast(CourseCode.COURSE_COURSEMARKET_ISNULL);
        courseMarket.setId(courseId);
        CourseMarket save = courseMarketRepository.save(courseMarket);
        QueryResult<CourseMarket> queryResult = new QueryResult<>();
        ArrayList<CourseMarket> courseMarkets = new ArrayList<>();
        courseMarkets.add(save);
        queryResult.setList(courseMarkets);
        queryResult.setTotal(courseMarkets.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 保存课程图片信息到数据库
     *
     * @param courseId 课程id
     * @param pic      图片id
     * @return
     */

    @Transactional  // Mysql操作需要添加到Spring事务
    public ResponseResult saveCoursePic(String courseId, String pic) {
        CoursePic coursePic = null;
        // 判断该课程id是否已经存在图片
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        if (byId.isPresent()) {
            coursePic = byId.get();
        }
        // 不存在则重新创建一个课程图片对象并保存信息
        if (coursePic == null) {
            coursePic = new CoursePic();
        }

        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        CoursePic save = coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程视图数据模型
     *
     * @param courseId
     * @return
     */
    public CourseView getCourseView(String courseId) {
        CourseView courseView = new CourseView();
        // 获取课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }

        // 获取课程营销信息
        CourseMarket courseMarketById = courseMarketRepository.findById(courseId).orElse(null);
        courseView.setCourseMarket(courseMarketById);

        // 获取课程图片
        CoursePic coursePic = coursePicRepository.findById(courseId).orElse(null);
        courseView.setCoursePic(coursePic);

        // 获取课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }


    // 根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId) {
        // 获取课程信息
        Optional<CourseBase> optionalCourseBase = courseBaseRepository.findById(courseId);
        if (!optionalCourseBase.isPresent()) {
            // 课程不存在抛出异常
            ExceptionCast.cast(CourseCode.COURSE_COURSE_ISNULL);
        }
        return optionalCourseBase.get();
    }

    /**
     * 课程详细页面发布前预览
     *
     * @param courseId
     * @return
     */
    @SuppressWarnings("all")
    public CoursePublishResult coursePublishPreview(String courseId) {
        // 获取课程信息
        CourseBase courseBaseById = this.findCourseBaseById(courseId);

        // 拼装页面基本信息
        CmsPage cmsPage = new CmsPage();
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setTemplateId(publish_templateId);

        // 页面别名
        cmsPage.setPageAliase(courseBaseById.getName());

        // 远程调用，保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }

        // 页面id
        String cmsPageId = cmsPageResult.getCmsPage().getPageId();
        // 返回预览url
        String url = previewUrl + cmsPageId;
        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }

    @SuppressWarnings("all")
    private CmsPage setPageInfo(String courseId) {
        // 获取课程信息
        CourseBase courseBaseById = this.findCourseBaseById(courseId);

        // 拼装页面基本信息
        CmsPage cmsPage = new CmsPage();
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setTemplateId(publish_templateId);
        cmsPage.setPageName(courseId + ".html");

        // 页面别名
        cmsPage.setPageAliase(courseBaseById.getName());
        return cmsPage;
    }

    /**
     * 课程详细页面发布
     */
    @Transactional
    public CoursePublishResult coursePublish(String courseId) {
        // 拼装页面信息
        CmsPage cmsPage = setPageInfo(courseId);

        // 发布课程详细页面
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }

        // 更新课程状态
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        courseBaseById.setStatus("202002");
        courseBaseRepository.save(courseBaseById);

        // 课程索引...
        CoursePub coursePub = createCoursePub(courseId);
        // 课程缓存...
        saveCoursePub(courseId, coursePub);
        // 保存课程计划媒资信息到待索引表
        saveTeachplanMediaPub(courseId);
        // 页面url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    // 保存课程计划媒资信息
    private void saveTeachplanMediaPub(String courseId) {
        // 查询课程媒资信息
        List<TeachplanMedia> byCourseId = teachplanMediaRepository.findByCourseId(courseId);
        if (byCourseId == null) return;  // 没有查询到媒资数据则直接结束该方法

        // 将课程计划媒资信息储存到待索引表

        // 删除原有的索引信息
        long l = teachplanMediaPubRepository.deleteByCourseId(courseId);
        // 一个课程可能会有多个媒资信息,遍历并使用list进行储存
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : byCourseId) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPub.setTimestamp(new Date());
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        // 保存所有信息
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
    }

    // 保存CoursePub
    private CoursePub saveCoursePub(String id, CoursePub coursePub) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        if (coursePubOptional.isPresent()) {
            coursePubNew = coursePubOptional.get();
        }
        if (coursePubNew == null) {
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub, coursePubNew);
        // 设置主键
        coursePubNew.setId(id);
        // 更新时间戳为最新时间
        coursePub.setTimestamp(new Date());
        // 发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub.setPubTime(date);
        coursePubRepository.save(coursePub);
        return coursePub;
    }

    // 创建coursePub对象
    private CoursePub createCoursePub(String id) {
        CoursePub coursePub = new CoursePub("", "", "",
                "", "", "", "", "",
                "", "", null, "", "",
                "", 0d, 0d, null, "", "");
        coursePub.setId(id);
        // 基础信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if (courseBaseOptional.isPresent()) {
            CourseBase courseBase = courseBaseOptional.get();
            BeanUtils.copyProperties(courseBase, coursePub);
//            coursePub.setCharge(courseBase.getStatus());
        }
        // 查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }
        // 课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if (marketOptional.isPresent()) {
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
            coursePub.setPrice(courseMarket.getPrice());
            coursePub.setPrice_old(courseMarket.getPrice_old());
        }
        // 课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);

        List<TeachplanNode> secondTeachPlan = teachplanNode.getChildren();
        // 校验是否有2级课程计划
        if (secondTeachPlan == null || secondTeachPlan.size() == 0)
            ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_SECOND_ISNULL);
        for (TeachplanNode child : secondTeachPlan) {
            List<TeachplanNode> thirdTeachPlan = child.getChildren();
            // 校验是否给所有3级课程计划选择视频
            if (thirdTeachPlan == null || thirdTeachPlan.size() == 0)
                ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_THIRD_MEDIA_ISNULL);
        }
        // 将课程计划转成json
        String teachplanString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachplanString);


        return coursePub;
    }

    public CoursePublishResult findPreUrl() {
        return new CoursePublishResult(CommonCode.SUCCESS, previewUrl);
    }

    /**
     * 分页查询指定公司下的课程信息
     */
    public QueryResponseResult findCourseListByCompany(String companyId ,int pageNum, int size, CourseListRequest courseListRequest){
        if(pageNum<=0){
            pageNum = 0;
        }
        if(size<=0){
            size = 20;
        }
        PageHelper.startPage(pageNum,size);  // 设置分页参数

        // 设置公司信息到查询条件内
        courseListRequest.setCompanyId(companyId);
        Page<CourseInfo> courseList = courseMapper.findCourseListByCompany(courseListRequest);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseList.getResult());
        queryResult.setTotal(courseList.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
