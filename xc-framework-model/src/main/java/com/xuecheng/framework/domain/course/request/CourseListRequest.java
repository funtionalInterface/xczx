package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by mrt on 2018/4/13.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CourseListRequest extends RequestData {
    // 公司Id
    private String companyId;

    private String users;

    private String name;
}
