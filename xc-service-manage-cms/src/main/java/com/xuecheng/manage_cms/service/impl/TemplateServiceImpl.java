package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplatesRepository;
import com.xuecheng.manage_cms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    CmsTemplatesRepository cmsTemplatesRepository;

    public QueryResponseResult<CmsTemplate> findTemplate() {
        List<CmsTemplate> all = cmsTemplatesRepository.findAll();
        // 封装到结果集
        QueryResult<CmsTemplate> queryResult = new QueryResult<>();
        queryResult.setList(all);
        queryResult.setTotal(all.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }
}
