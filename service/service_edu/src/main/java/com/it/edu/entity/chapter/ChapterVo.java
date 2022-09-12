package com.it.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 *@author       :zxb
 *@data         :29/8/2022 12:42
 *@description  :
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();


}
