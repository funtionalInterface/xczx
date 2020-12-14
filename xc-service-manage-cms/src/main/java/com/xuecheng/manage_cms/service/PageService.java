package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
public interface PageService {

    QueryResponseResult<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest);

    CmsPageResult addPage(CmsPage cmsPage);

    CmsPage findById(String id);

    CmsPageResult updatePage(String id, CmsPage cmsPage);

    ResponseResult deletePage(String id);

    // 页面静态化
    String getPageHtml(String pageId);

    ResponseResult postPage(String pageId);

    CmsPageResult saveCmsPage(CmsPage cmsPage);

    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
