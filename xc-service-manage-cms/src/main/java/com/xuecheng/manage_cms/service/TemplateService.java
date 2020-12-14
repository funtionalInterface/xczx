package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface TemplateService {

    QueryResponseResult<CmsTemplate> findTemplate();
}
