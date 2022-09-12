package com.it.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.commonutils.R;
import com.it.edu.entity.Teacher;
import com.it.edu.entity.vo.TeacherQuery;
import com.it.edu.service.TeacherService;
//import com.it.servicebase.exceptionHandler.GuliException;
import com.it.servicebase.exceptionHandler.GuliException;
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
 * 讲师 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-08-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    //注入service
    @Autowired
    private TeacherService teacherService;

    //1、查询所有
    @ApiOperation(value = "查询所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){

        try {
//            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(444,"出现自定义444异常");
        }

        List<Teacher> list = teacherService.list(null);
        return  R.ok().data("items",list);
    }

    //2、逻辑删除讲师的方法
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //3 分页查询
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeachers/{current}/{limit}")
    public R pageTeachers(@ApiParam(name = "current", value = "当前页码",required = true)@PathVariable long current,
                          @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit){
        Page<Teacher> page = new Page<>(current,limit);
        teacherService.page(page,null);

        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return  R.ok().data(map);

//        return R.ok().data("total",total).data("rows",records);
    }

    // 4 带条件查询的分页方法
    @ApiOperation(value = "讲师条件分页查询")
    @PostMapping("pageTeacherCondication/{current}/{limit}")
    public R pageTeacherCondication(@ApiParam(name = "current", value = "当前页码", required = true)@PathVariable long current,
                                    @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQuery){
        // 创建Page对象
        Page<Teacher> page = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper();

        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
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
        teacherService.page(page, wrapper);

        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    //5 添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.save(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 6根据讲师id 进行查询
    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    // 7  讲师信息修改
    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean b = teacherService.updateById(teacher);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

