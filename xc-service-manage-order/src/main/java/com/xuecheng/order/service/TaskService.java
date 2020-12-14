package com.xuecheng.order.service;

import com.xuecheng.framework.domain.task.XcTask;

import java.util.Date;
import java.util.List;

public interface TaskService {
    List<XcTask> findTaskList(int n, Date updateTime);

    void publishChooseMsg(XcTask xcTask, String ex, String routingKey);

    int getTask(String taskId, int version);

    // 删除任务
    void finishTask(String taskId);
}
