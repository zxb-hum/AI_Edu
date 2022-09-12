package com.it.edu.service;

import com.it.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

    void removeDescriptionByCourseId(String courseId);
}
