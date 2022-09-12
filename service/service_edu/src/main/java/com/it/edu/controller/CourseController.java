package com.it.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.commonutils.R;
import com.it.edu.entity.Course;
import com.it.edu.entity.Teacher;
import com.it.edu.entity.vo.CourseInfoVo;
import com.it.edu.entity.vo.CoursePublishVo;
import com.it.edu.entity.vo.CourseQuery;
import com.it.edu.entity.vo.TeacherQuery;
import com.it.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-08-28
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    //1 添加课程信息
    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 添加成功后返回springBoot自动创建的课程id
        String id = courseService.saveCourseInfo(courseInfoVo);
//        System.out.println(id);
        return R.ok().data("courseId",id);
    }
    // 2 根据课程id获取课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    // 3下一步之后，更新课程信息
    @ApiOperation(value = "更新课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 4 根据课程id 查询发布的课程信息
    @ApiOperation(value = "课程id查询课程信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseIbfo(@ApiParam(name = "id", value = "课程id",required = true)@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    // 5 根据id 发布最终课程， 设置状态码status 为normal
    @ApiOperation(value = "根据课程id发布最终的课程")
    @PostMapping("publishCourseById/{id}")
    public R publishCourseById(@ApiParam(name = "id", value = "课程id",required = true)@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        /**
         *  mybatis-plus FieldStrategy 有三种策略：
         *
         * IGNORED：0 忽略
         * NOT_NULL：1 非 NULL，默认策略
         * NOT_EMPTY：2 非空
         * 而默认更新策略是NOT_NULL：非 NULL；即通过接口更新数据时数据为NULL值时将不更新进数据库。
         */
        courseService.updateById(course);
        return R.ok();
    }

    // 6 返回课程的列表
    @ApiOperation(value = "查询所有课程列表")
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<Course> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //7 分页查询
    @ApiOperation(value = "课程分页查询")
    @GetMapping("pageCourses/{current}/{limit}")
    public R pageCourses(@ApiParam(name = "current", value = "当前页码",required = true)@PathVariable long current,
                          @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit){
        Map map = courseService.pageCourse(current,limit);

        return  R.ok().data(map);  // 包含总的数据数total，以及course数据信息 rows

//        return R.ok().data("total",total).data("rows",records);
    }

    // 8 带条件的课程分页查询方法
    @ApiOperation(value = "课程条件分页查询")
    @PostMapping("pageCourseCondication/{current}/{limit}")
    public R pageCourseCondication(@ApiParam(name = "current", value = "当前页码", required = true)@PathVariable long current,
                                    @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit,
                                    @RequestBody(required = false) CourseQuery courseQuery){
        Map map =  courseService.pageCondicationCourse(current, limit, courseQuery);
        return R.ok().data(map);

    }

    // 9 根据课程id删除课程
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable String courseId){
        courseService.deleteById(courseId);
        return R.ok();
    }


}

