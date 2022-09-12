package com.it.edu.service.impl;

import com.it.edu.entity.CourseDescription;
import com.it.edu.mapper.CourseDescriptionMapper;
import com.it.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

    // 根据课程id（描述id）  删除课程描述
    @Override
    public void removeDescriptionByCourseId(String courseId) {
        baseMapper.deleteById(courseId);
    }
}
