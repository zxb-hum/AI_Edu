package com.it.edu.entity.vo;

import lombok.Data;

/*
 *@author       :zxb
 *@data         :29/8/2022 23:12
 *@description  :
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
