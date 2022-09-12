package com.it.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.edu.entity.frontVo.CourseFrontVo;
import com.it.edu.entity.frontVo.CourseWebVo;
import com.it.edu.entity.vo.CourseInfoVo;
import com.it.edu.entity.vo.CoursePublishVo;
import com.it.edu.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    Map pageCourse(long current, long limit);

    Map pageCondicationCourse(long current, long limit, CourseQuery courseQuery);

    void deleteById(String courseId);

    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
