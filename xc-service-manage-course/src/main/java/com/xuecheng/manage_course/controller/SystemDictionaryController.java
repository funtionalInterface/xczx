package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.SystemControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/sys")
public class SystemDictionaryController implements SystemControllerApi {
    @Autowired
    SystemService systemService;

    @Override
    @GetMapping("/dictionary/get/{dictionaryTypeName}")
    public SysDictionary getSysDictionary(@PathVariable("dictionaryTypeName") String dictionaryTypeName) {
        return systemService.findBydType(dictionaryTypeName);
    }
}
