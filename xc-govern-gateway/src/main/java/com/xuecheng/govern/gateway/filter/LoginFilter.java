package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 2; // int值来定义过滤器的执行顺序，数值越小优先级越高
    }
    @Override
    public boolean shouldFilter() { // 该过滤器需要执行
        return true;
    }
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();         //获取响应对象
        HttpServletRequest request = requestContext.getRequest();           //获取请求对象
        // 取出头部信息Authorization
        String authorization = request.getHeader("Authorization");
        // 判断用户的请求中是否带有 Authorization 字段,如果没有则表示未认证用户
        if(StringUtils.isEmpty(authorization)){
            requestContext.setSendZuulResponse(false); // 拒绝访问
            requestContext.setResponseStatusCode(200); // 设置响应状态码
            ResponseResult unauthenticated = new ResponseResult(CommonCode.UNAUTHENTICATED);
            String jsonString = JSON.toJSONString(unauthenticated);
            requestContext.setResponseBody(jsonString);
            requestContext.getResponse().setContentType("application/json;charset=UTF-8");
            return null;
        }
        return null;
    }
}