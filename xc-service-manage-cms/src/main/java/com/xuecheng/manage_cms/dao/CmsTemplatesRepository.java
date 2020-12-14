package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsTemplatesRepository extends MongoRepository<CmsTemplate,String> {
    // 根据模板查询
    CmsTemplate findByTemplateName(String templateName);
}
