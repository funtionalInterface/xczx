package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface SiteService {


     QueryResponseResult<CmsSite> findSite();
}
