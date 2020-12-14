package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.SiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSiteRepositoryTest {
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Test
    public void findSite() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CmsSite> all = cmsSiteRepository.findAll(pageable);
        System.out.println(all.getContent());

    }
}
