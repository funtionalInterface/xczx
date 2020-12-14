package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsClientTest {
    @Autowired
    CmsClient cmsClient;

    @Test
    public void get() {
        CmsPage cmsPageById = cmsClient.findCmsPageById("5a754adf6abb500ad05688d9");
        System.out.println(cmsPageById);
    }

    @Test
    public void save() {
        CmsPage page = new CmsPage();
        CmsPageResult cmsPageResult = cmsClient.saveCmsPage(page);
        System.out.println(cmsPageResult);
    }
}
