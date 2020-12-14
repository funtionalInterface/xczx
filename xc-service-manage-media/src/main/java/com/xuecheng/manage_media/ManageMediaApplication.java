package com.xuecheng.manage_media;

import com.xuecheng.framework.interceptor.FeignClientInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-23 11:53
 **/
@EnableFeignClients // 从Eureka Server获取服务
@EnableDiscoveryClient // 从Eureka Server注册服务
@SpringBootApplication // 扫描所在包及子包的bean，注入到ioc中
@EntityScan("com.xuecheng.framework.domain.media") // 扫描实体类
@ComponentScan(basePackages={"com.xuecheng.api"}) // 扫描接口
@ComponentScan(basePackages={"com.xuecheng.framework"}) // 扫描framework中通用类
@ComponentScan(basePackages={"com.xuecheng.manage_media"}) // 扫描本项目下的所有类
public class ManageMediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageMediaApplication.class,args);
    }
    @Bean
    @LoadBalanced // 开启ribbon负载均衡拦截器
    public RestTemplate getRestTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

    @Bean
    public FeignClientInterceptor getFeignClientInterceptor() {
        return new FeignClientInterceptor();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
