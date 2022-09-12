package com.it.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.edu.entity.Subject;
import com.it.edu.entity.excel.SubjectData;
import com.it.edu.service.SubjectService;
import com.it.servicebase.exceptionHandler.GuliException;

/*
 *@author       :zxb
 *@data         :27/8/2022 22:32
 *@description  :
 */
// 将excel中的subject数据 读取并写入到数据库中
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(SubjectService subjectService){
        this.subjectService=subjectService;
    }
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){ //如果读到excel数据为空
            throw new GuliException(20001,"文件数据为空");
        }
        // 一行一行的读数据
        // 判断以及分类是否重复
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject==null){ // 数据库表中没有相同的以及分类，进行添加
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        // 二级分类
        String pid = existOneSubject.getId();
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if (existTwoSubject==null){ // 表中没有相同的以及分类，进行添加
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name){
        QueryWrapper<Subject> wrapper =new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name,String pid){
        QueryWrapper<Subject> wrapper =new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {


    }
}
