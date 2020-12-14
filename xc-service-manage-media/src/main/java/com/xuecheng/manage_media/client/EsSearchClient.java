package com.xuecheng.manage_media.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(XcServiceList.XC_SERVICE_SEARCH)
public interface EsSearchClient {
    @DeleteMapping("/search/course/es/delete/{id}")
    ResponseResult deleteEsCache(@PathVariable String id);
}
