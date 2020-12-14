package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    // 根据页面名称查询
    CmsPage findByPageName(String pageName);

    // 根据站点id、页面名称、页面访问路径查询
    CmsPage findBySiteIdAndPageNameAndPageWebPath(String siteId, String pageName, String pageWebPath);

    // 根据页面别名查询
    CmsPage findByPageAliase(String pageAliase);

    // 根据页面模板Id查询
    CmsPage findByTemplateId(String templateId);
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String PageName,String SiteId,String PageWebPath);
}
