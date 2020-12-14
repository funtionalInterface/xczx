package com.xuecheng.manage_cms_client.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 将页面html保存到页面物理路径
     *
     * @param pageId
     */
    public void savePageToServerPath(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        // 页面不存在则抛出异常
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOEXIST);
        }

        CmsPage cmsPage = optional.get();
        CmsSite cmsSite = this.getCmsSiteById(cmsPage.getSiteId());
        if (cmsSite == null) {
            ExceptionCast.cast(CmsCode.CMS_SIZE_NOT_EXISTS);
        }

        // 原讲义和视频中使用的是sizePathysicaiPath,但是SizePage中没有这个字段，使用PageCms中的PathysicaiPath代替
        String pagePath = cmsPage.getPagePhysicalPath() + cmsPage.getPageWebPath() + cmsPage.getPageName();

        // 获取页面文件id
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isEmpty(htmlFileId)) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_FILEID_NOT_EXISTS);
        }
        // 获取文件流
        InputStream inputStream = this.getFileInputStreamFileById(htmlFileId);
        if (inputStream == null) {
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            // 将文件保存至物理路径
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fileOutputStream != null;
                Objects.requireNonNull(fileOutputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据文件id获取文件的gridFS输入流
    private InputStream getFileInputStreamFileById(String fileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        // 获取gridFs下载流
        assert gridFSFile != null;
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile != null ? Objects.requireNonNull(gridFSFile).getObjectId() : null);
        try {
            // 获取文件输入流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据站点id得到站点信息
    private CmsSite getCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        return optional.orElse(null);
    }
}
