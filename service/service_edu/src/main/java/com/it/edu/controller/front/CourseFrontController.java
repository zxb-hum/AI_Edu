package com.it.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.commonutils.JwtUtils;
import com.it.commonutils.R;
import com.it.commonutils.vo.CourseWebVoOrder;
import com.it.edu.client.OrderClient;
import com.it.edu.entity.Course;
import com.it.edu.entity.Teacher;
import com.it.edu.entity.chapter.ChapterVo;
import com.it.edu.entity.frontVo.CourseFrontVo;
import com.it.edu.entity.frontVo.CourseWebVo;
import com.it.edu.service.ChapterService;
import com.it.edu.service.CourseService;
import com.it.edu.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/*
 *@author       :zxb
 *@data         :4/9/2022 16:50
 *@description  :
 */
@RequestMapping("/edu/courseFront")
@CrossOrigin
@RestController
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    // 课程列表
    @PostMapping("getCourseFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit,
                                 @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request){
        // 根据课程id，编写sql语句查询课程信息(前端展示)
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        // 根据课程id查询章节和小节
        List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);

        //  根据课程id和用户id查询当前课程是否被购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buy_flag = orderClient.isBUyCourse(courseId, memberId);

//        System.out.println(memberId);
//        System.out.println(buy_flag);
        return R.ok().data("chapterVideoByCourseId",chapterVideoByCourseId).data("courseWebVo",courseWebVo).data("isBuy",buy_flag);
    }


    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId){
        // 根据课程id，编写sql语句查询课程信息(订单展示)
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
