package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.model.response.QueryResponseResult;


public interface CategoryService {
    QueryResponseResult<CategoryNode> findCategoryList();
}
