package com.xuecheng.manage_media_process.mq;

public interface MediaProcessTask {
    void receiveMediaProcessTask(String msg);
}