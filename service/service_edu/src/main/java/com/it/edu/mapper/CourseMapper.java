package com.it.edu.mapper;

import com.it.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.edu.entity.frontVo.CourseWebVo;
import com.it.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublishVo getPublishCourseInfo(String id);

    CourseWebVo getBaseCourseInfo(String courseId);
}
