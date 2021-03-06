package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);


    @ApiOperation("站点列表")
    QueryResponseResult findSite();

    @ApiOperation("模板列表")
    QueryResponseResult findTemplate();

    @ApiOperation("添加界面")
    CmsPageResult addPage(CmsPage cmsPage);
    @ApiOperation("id查询页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面模板Id",required=true,paramType="path",dataType="String"),
    })
    CmsPage findById(String id);

    @ApiOperation("修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面模板Id",required=true,paramType="path",dataType="String"),
//            @ApiImplicitParam(name="cmsPage",value = "修改页面信息",required=true),
    })
    CmsPageResult updatePage(String id,CmsPage cmsPage);
    @ApiOperation("删除页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "页面模板Id",required=true,paramType="path",dataType="String"),
    })
    ResponseResult deletePage(String id);
    @ApiOperation("发布页面")
    ResponseResult post(String pageId);
    /**
     * 保存页面数据
     */
    @ApiOperation("保存页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cmsPage",value = "请提交json形式的页面数据",required=true,paramType="CmsPage",dataType="CmsPage"),
    })
     CmsPageResult saveCmsPage(CmsPage cmsPage);
    /**
     * 一键发布页面
     */
    @ApiOperation("一键发布页面")
    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
