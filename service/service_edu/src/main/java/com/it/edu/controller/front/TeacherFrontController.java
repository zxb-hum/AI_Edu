package com.it.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.commonutils.R;
import com.it.edu.entity.Course;
import com.it.edu.entity.Teacher;
import com.it.edu.service.CourseService;
import com.it.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 *@author       :zxb
 *@data         :4/9/2022 16:50
 *@description  :
 */
@RequestMapping("/edu/teacherFront")
@CrossOrigin
@RestController
public class TeacherFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;


    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){

        Teacher teacher = teacherService.getById(teacherId);

        // 根据讲师id查询所教授的课程

        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(courseQueryWrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }

}
