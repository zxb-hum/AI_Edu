package com.it.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 *@author       :zxb
 *@data         :28/8/2022 15:15
 *@description  :
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    // 一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
