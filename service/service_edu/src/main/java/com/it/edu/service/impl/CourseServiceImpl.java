package com.it.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.commonutils.R;
import com.it.edu.entity.Course;
import com.it.edu.entity.CourseDescription;
import com.it.edu.entity.Teacher;
import com.it.edu.entity.frontVo.CourseFrontVo;
import com.it.edu.entity.frontVo.CourseWebVo;
import com.it.edu.entity.vo.CourseInfoVo;
import com.it.edu.entity.vo.CoursePublishVo;
import com.it.edu.entity.vo.CourseQuery;
import com.it.edu.mapper.CourseMapper;
import com.it.edu.service.ChapterService;
import com.it.edu.service.CourseDescriptionService;
import com.it.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.edu.service.VideoService;
import com.it.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@Service
public class CourseServiceImpl  extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表添加数据
        // 将courseInfoVo 转换为Course对象
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        course.setIsDeleted(0); // 设置逻辑删除，要不然默认的话后面查不到数据
        int insert = baseMapper.insert(course);
        if (insert==0){
            // 添加失败
            throw new GuliException(20001,"课程信息添加失败");
        }

        // 获取添加课程后的Id
        String cid = course.getId();
        // 向课程描述表添加数据
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        // 1 查询课程表
        Course course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        // 2 查询课程描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.updateById(course);
        if(i==0){
            // 修改失败
            throw new GuliException(20001,"修改课程信息失败");
        }


        // 修改描述表
        CourseDescription courseDescription =  new CourseDescription() ;
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public Map pageCourse(long current, long limit) {
        Page<Course> page = new Page<>(current,limit);
        baseMapper.selectPage(page, null);
        List<Course> records = page.getRecords();
        long total = page.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public Map pageCondicationCourse(long current, long limit, CourseQuery courseQuery) {
        // 创建Page对象
        Page<Course> page = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Course> wrapper = new QueryWrapper();

        // 多条件组合查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status", status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_begin", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_end", end);
        }
        // 排序
        wrapper.orderByDesc("gmt_create");

        // 调用方法实现条件分页查询
        baseMapper.selectPage(page, wrapper);

        List<Course> records = page.getRecords();

        long total = page.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public void deleteById(String courseId) {
        // 删除小节
        videoService.removeVideoByCourseId(courseId);
        // 删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 删除描述
        courseDescriptionService.removeDescriptionByCourseId(courseId);
        // 删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result==0){
            throw new GuliException(20001,"删除课程信息异常");
        }
    }

    //=================================****************前端页面****************=============================

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, queryWrapper);

        List<Course> records = coursePage.getRecords();

        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();

        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    // 根据课程id查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return  baseMapper.getBaseCourseInfo(courseId);
    }



}
