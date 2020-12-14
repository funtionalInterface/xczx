package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachplanMediaPubRepository;
import com.xuecheng.manage_course.dao.TeachplanMediaRepository;
import com.xuecheng.manage_course.service.TeachPlanMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeachPlanMediaServiceImpl implements TeachPlanMediaService {

    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Override
    @Transactional
    public ResponseResult deleteMedia(String mediaId) {
        teachplanMediaPubRepository.deleteByMediaId(mediaId);
        teachplanMediaRepository.deleteByMediaId(mediaId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
