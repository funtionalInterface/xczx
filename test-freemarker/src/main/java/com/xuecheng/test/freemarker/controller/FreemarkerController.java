package com.xuecheng.test.freemarker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/freemarker")
@Controller
public class FreemarkerController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test1")
    public String test1(Map<String, Object> map) {
        map.put("name", 1);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(13);
        list.add(12);
        HashMap<String, String> map1 = new HashMap<>();
        map.put("list", list);
        return "test1";
    }

    // 课程详情页面测试
    @RequestMapping("/course")
    public String course(Map<String, Object> map) {
        ResponseEntity<Map> forEntity =
                restTemplate.getForEntity("http://localhost:31200/course/preview/model/297e7c7c62b888f00162b8a7dec20000", Map.class);
        Map body = forEntity.getBody();
        map.put("model", body);
        return "course";
    }
}
