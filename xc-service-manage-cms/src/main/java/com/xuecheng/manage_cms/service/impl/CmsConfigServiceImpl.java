package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    public CmsConfig getModelById(String id) {
        Optional<CmsConfig> cmsConfig = cmsConfigRepository.findById(id);
        return cmsConfig.orElse(null);
    }

}
