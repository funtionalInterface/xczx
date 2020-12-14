package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.CategoryMapper;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Transactional
    public QueryResponseResult<CategoryNode> findCategoryList() {
        List<CategoryNode> categoryList = categoryMapper.findCategoryList();
        QueryResult<CategoryNode> queryResult = new QueryResult<>();
        queryResult.setList(categoryList);
        queryResult.setTotal(categoryList.size());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }
}
