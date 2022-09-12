package com.it.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.commonutils.R;
import com.it.edu.entity.Course;
import com.it.edu.entity.Teacher;
import com.it.edu.service.CourseService;
import com.it.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *@author       :zxb
 *@data         :1/9/2022 19:27
 *@description  :
 */
@Api(description = "banner-课程/教师")
@RestController
@RequestMapping("/edu/indexFront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    // 查询前8条热门课程，查询前4名名师
    @ApiOperation(value = "查询8门课程，4位讲师")
    @GetMapping("index")
    public R index(){
//        8门课程
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseWrapper);

//        4位讲师
        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);

        return R.ok().data("courses",courseList).data("teachers",teacherList);
    }
}
