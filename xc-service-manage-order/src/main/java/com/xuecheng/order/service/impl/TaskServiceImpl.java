package com.xuecheng.order.service.impl;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import com.xuecheng.order.service.TaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    XcTaskRepository xcTaskRepository;
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 查询任务列表的实现
     *
     * @param n          查询数量
     * @param updateTime 上次更新时间
     * @return
     */
    @Override
    public List<XcTask> findTaskList(int n, Date updateTime) {
        Pageable pageable = new PageRequest(0, n);
        Page<XcTask> byUpdateTimeBefore = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        return byUpdateTimeBefore.getContent();
    }

    /**
     * 发送添加选课消息的实现
     *
     * @param xcTask     消息内容
     * @param ex         交换机
     * @param routingKey 路由key
     */
    @Transactional
    @Override
    public void publishChooseMsg(XcTask xcTask, String ex, String routingKey) {
        // 查询任务是否存在
        Optional<XcTask> byId = xcTaskRepository.findById(xcTask.getId());
        if (byId.isPresent()) {
            xcTask = byId.get();
            // 发送消息到MQ
            rabbitTemplate.convertAndSend(ex, routingKey, xcTask);
            // 更细当前任务的时间
            xcTaskRepository.updateTaskTime(xcTask.getId(), new Date());
        }
    }

    @Transactional
    public int getTask(String taskId, int version) {
        return xcTaskRepository.updateTaskVersion(taskId, version);
    }

    // 删除任务
    @Transactional
    public void finishTask(String taskId) {
        Optional<XcTask> taskOptional = xcTaskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            XcTask xcTask = taskOptional.get();
            xcTask.setDeleteTime(new Date());
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask, xcTaskHis);
            // 保存任务到 task_hit 表内
            xcTaskHisRepository.save(xcTaskHis);
            // 删除当前任务
            xcTaskRepository.delete(xcTask);
        }
    }
}