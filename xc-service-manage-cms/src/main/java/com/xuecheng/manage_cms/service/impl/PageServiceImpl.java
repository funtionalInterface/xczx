package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplatesRepository;
import com.xuecheng.manage_cms.service.PageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsTemplatesRepository cmsTemplatesRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     * 页面查询方法
     *
     * @param page             页码，从1开始记数
     * @param size             每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) queryPageRequest = new QueryPageRequest();
        // 分页参数
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        // 多条件查询
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        // 赋值查询条件
        if (!StringUtils.isBlank(queryPageRequest.getSiteId()))
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        if (!StringUtils.isBlank(queryPageRequest.getTemplateId()))
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        if (!StringUtils.isBlank(queryPageRequest.getPageAliase()))
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        Example<CmsPage> example = Example.of(cmsPage, matching);
        // 分页查询
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        List<CmsPage> content = all.getContent();

        queryResult.setList(content); // 数据列表
        queryResult.setTotal(all.getTotalElements()); // 数据总记录数
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 添加页面数据
     */
    public CmsPageResult addPage(CmsPage cmsPage) {
        // 效验cmsPage是否为空
        cmsPage = cmsPage != null ? cmsPage : new CmsPage();
        CmsPage page = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());
        // 抛出页面已存在自定义异常
        if (page != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        // 添加页面
        cmsPage.setPageId(null); // jpa自动生成id，无需id
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    public CmsPage findById(String id) {
        Optional<CmsPage> page = cmsPageRepository.findById(id);
        return page.orElse(null);
    }

    public CmsPageResult updatePage(String id, CmsPage cmsPage) {
        CmsPage one = findById(id);
        // 没有该页面信息
        if (one == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 修改页面信息
        one.setSiteId(cmsPage.getSiteId());
        one.setTemplateId(cmsPage.getTemplateId());
        one.setPageName(cmsPage.getPageName());
        one.setPageAliase(cmsPage.getPageAliase());
        one.setPageWebPath(cmsPage.getPageWebPath());
        one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        one.setDataUrl(cmsPage.getDataUrl());
        one.setPageType(cmsPage.getPageType());
        one.setPageCreateTime(cmsPage.getPageCreateTime());
        cmsPageRepository.save(one);
        return new CmsPageResult(CommonCode.SUCCESS, one);
    }

    public ResponseResult deletePage(String id) {
        Optional<CmsPage> page = cmsPageRepository.findById(id);
        if (page.isPresent()) {
            cmsPageRepository.delete(page.get());
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    // 页面静态化
    public String getPageHtml(String pageId) {
        // 获取模板数据
        Map model = getModelByPageId(pageId);
        if (model == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        // 模板信息
        String templateByPageId = getTemplateByPageId(pageId);
        if (templateByPageId == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        // 静态化
        String html = generateHtml(templateByPageId, model);
        if (StringUtils.isEmpty(html)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        return html;

    }

    private Map getModelByPageId(String pageId) {
        CmsPage cmsPage = findById(pageId);
        // 页面不存在
        if (cmsPage == null) ExceptionCast.cast(CmsCode.CMS_PAGE_NOEXIST);
        // 取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);

        // 通过获取当前请求的request对象来取出jwt认证信息,并且传递到下一个请求中
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        String jwt = request.getHeader("Authorization");
        // 使用LinkedMultiValueMap储存多个header信息
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", jwt);

        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            headers.add(element, request.getHeader(element));
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        // 发送请求获取模型数据
        ResponseEntity<Map> entity = restTemplate.exchange(dataUrl, HttpMethod.GET, httpEntity, Map.class);
        return entity.getBody();

    }

    private String getTemplateByPageId(String pageId) {
        // 查找页面
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) ExceptionCast.cast(CmsCode.CMS_PAGE_NOEXIST);
        // 获取页面模板id
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        // 查找模板
        Optional<CmsTemplate> templates = cmsTemplatesRepository.findById(templateId);
        if (templates.isPresent()) {
            CmsTemplate cmsTemplate = templates.get();
            String templateFileId = cmsTemplate.getTemplateFileId();
            // 取出模板内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            assert gridFSFile != null;
            // 打开下载流对象
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(Objects.requireNonNull(gridFSFile).getObjectId());
            // 创建GridResource
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, downloadStream);
            try {
                return IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private String generateHtml(String template, Map model) {
        // 生成配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 模板加载器
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template", template);
        // 配置模板加载器
        configuration.setTemplateLoader(templateLoader);
        try {
            Template t = configuration.getTemplate("template");
            // 获取模板
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 页面发布
     *
     * @param pageId
     * @return
     */
    public ResponseResult postPage(String pageId) {
        // 执行静态化
        String pageHtml = this.getPageHtml(pageId);
        if (StringUtils.isEmpty(pageHtml)) {
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }

        // 保存静态化文件
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        // 发送消息
        this.sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    // 发送页面发布消息
    private void sendPostPage(String pageId) {
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOEXIST);
        }
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("pageId", pageId);
        // 消息内容
        String msg = JSON.toJSONString(msgMap);
        // 获取站点id作为routing key
        String siteId = cmsPage.getSiteId();
        // 发送消息到指定交换机、routingKey、以及要发送的消息
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, siteId, msg);
    }

    // 保存静态化页面内容
    private CmsPage saveHtml(String pageId, String content) {
        // 查询页面是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOEXIST);
        }

        CmsPage cmsPage = optional.get();
        // 储存之前先删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isNotEmpty(htmlFileId)) {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        // 保存html文件到GridFS
        try {
            InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            // 文件id
            String fileId = objectId.toString();
            // 将文件id储存到cmspage中
            cmsPage.setHtmlFileId(fileId);
            cmsPageRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cmsPage;
    }

    /**
     * 保存页面：如果不存在则添加，存在则更新。
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult saveCmsPage(CmsPage cmsPage) {
        // 效验cmsPage是否为空
        if (cmsPage == null) {
            // 抛出异常，非法参数
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 验证数据唯一性：sizeId、pageName、pageWebPath
        CmsPage one = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        // 如果页面已存在则进行更新操作
        if (one != null) {
            return this.updatePage(one.getPageId(), cmsPage);
        }
        // 不存在则直接添加
        return this.addPage(cmsPage);
    }

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        // 添加页面，这里直接调用我们在做预览页面时候开发的保存页面方法
        CmsPageResult cmsPageResult = this.saveCmsPage(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            // 添加页面失败
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }

        CmsPage saveCmsPage = cmsPageResult.getCmsPage();
        String pageId = saveCmsPage.getPageId();

        // 发布页面,通知cms client发布页面
        ResponseResult responseResult = this.postPage(pageId);
        if (!responseResult.isSuccess()) {
            // 发布失败
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }

        // 得到页面的url，页面url=站点域名+站点webpath+页面webpath+页面名称
        // 所以这里我们需要获取站点信息
        String siteId = saveCmsPage.getSiteId();
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(siteId);
        if (!cmsSiteOptional.isPresent()) {
            // 获取站点异常
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        CmsSite cmsSite = cmsSiteOptional.get();
        // 站点和页面的信息
        String siteDomain = cmsSite.getSiteDomain();
        String siteWebPath = cmsSite.getSiteWebPath();
        String pageWebPath = saveCmsPage.getPageWebPath();
        String pageName = saveCmsPage.getPageName();
        // 发布页面的访问地址
        String pageUrl = siteDomain + siteWebPath + pageWebPath + pageName;
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }
}
