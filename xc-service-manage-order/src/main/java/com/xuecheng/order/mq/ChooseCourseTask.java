package com.xuecheng.order.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    TaskService taskService;

    // 每隔1分钟扫描消息表，向mq发送消息
//    @Scheduled(cron = "0/60 * * * * *")
    @Scheduled(cron = "0/3 * * * * *")  //这里我们为了方便测试，时间改为了3秒一次
    public void sendChoosecourseTask() {
        // 取出当前时间1分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTask> taskList = taskService.findTaskList(10, time);
        for (XcTask xcTask : taskList) {
            String taskId = xcTask.getId();
            // 版本号
            Integer version = xcTask.getVersion();
            // 调用乐观锁方法校验任务是否可以执行
            try {
                if (taskService.getTask(taskId, version) > 0) {
                    // 发送选课消息
                    taskService.publishChooseMsg(xcTask, xcTask.getMqExchange(), xcTask.getMqRoutingkey());
                    LOGGER.info("send choose course task id:{}", taskId);
                }
            } catch (Exception e) {
                LOGGER.warn("taskService.getTask(taskId, version) > 0",e.getMessage());
            }


        }
    }
    /**
     * 接收选课响应结果
     */
    @RabbitListener(queues = {RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE})
    public void receiveFinishChoosecourseTask(XcTask task, Message message, Channel channel) throws
            IOException {
        LOGGER.info("receiveChoosecourseTask...{}",task.getId());
        // 接收到 的消息id
        String id = task.getId();
        // 删除任务，添加历史任务
        taskService.finishTask(id);
    }
}


    // @Scheduled(fixedRate = 5000) // 上次执行开始时间后5秒执行
    // @Scheduled(fixedDelay = 5000) // 上次执行完毕后的5秒执行
    // @Scheduled(initialDelay=3000, fixedRate=5000) // 第一次延迟3秒，以后每隔5秒执行一次
    /*@Scheduled(cron = "0/3 * * * * *") // 每隔3秒执行一次
    public void task1() {
        LOGGER.info("===============测试定时任务1开始===============");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("===============测试定时任务1结束===============");
    }*/

    /*@Scheduled(cron = "0/3 * * * * *") // 每隔3秒执行一次
    public void task2() {
        LOGGER.info("===============测试定时任务2开始===============");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("===============测试定时任务2结束===============");
    }*/
