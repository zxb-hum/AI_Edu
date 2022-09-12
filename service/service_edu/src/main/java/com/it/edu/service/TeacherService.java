package com.it.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-08-23
 */
public interface TeacherService extends IService<Teacher> {

    //  前端分页查询讲师
    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);
}
