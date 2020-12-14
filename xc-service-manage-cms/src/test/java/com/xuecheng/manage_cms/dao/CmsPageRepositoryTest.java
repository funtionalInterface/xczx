package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    PageService pageService;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);

    }

    // 分页查询
    @Test
    public void testFindPage() {
        // 分页参数
        int page = 1; // 从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        for (CmsPage cmsPage : all) {
            System.out.println(cmsPage);
        }
    }
    // 自定义条件查询
    @Test
    public void testDiyFindAll(){
        // 精确匹配条件值
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");

        // 条件匹配器,用于模糊匹配
        // ExampleMatcher matching = ExampleMatcher.matching();
        cmsPage.setPageAliase("详");
        // 条件匹配器,用于模糊匹配
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        // 条件查询实例
        Example<CmsPage> example = Example.of(cmsPage, matching);

        // 分页参数
        int page = 0;
        int size = 20;
        Pageable pageable = PageRequest.of(page,size);

        // 调用dao
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }
    // 修改
    @Test
    public void testUpdate() {
        // 查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5b4b1d8bf73c6623b03f8cec");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            // 设置要修改值
            cmsPage.setPageAliase("test01");
            // ...
            // 修改
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

    }

    // 根据页面名称查询
    @Test
    public void testfindByPageName() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面");
        System.out.println(cmsPage);
    }

    @Test
    public void generateHtml() {
        String pageHtml = pageService.getPageHtml("5a795ac7dd573c04508f3a56");
        System.out.println(pageHtml);
    }
}
