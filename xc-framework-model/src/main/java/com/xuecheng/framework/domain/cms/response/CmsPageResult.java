package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by mrt on 2018/3/31.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;

    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
