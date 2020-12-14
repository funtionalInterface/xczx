package com.xuecheng.manage_cms;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.xuecheng.framework.interceptor.FeignClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:13
 **/
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms") // 扫描实体类
@ComponentScan("com.xuecheng.api") // 扫描接口
@ComponentScan("com.xuecheng.framework") // 扫描异常类
@ComponentScan("com.xuecheng.manage_cms") // 扫描本项目下的所有类
@EnableDiscoveryClient
public class ManageCmsApplication {
    @Value("${spring.data.mongodb.database}")
    String db;

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
    
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

    @Bean
    public GridFSBucket createGirdBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database);
    }
    @Bean
    public FeignClientInterceptor feignClientInterceptor(){
        return new FeignClientInterceptor();
    }
}
