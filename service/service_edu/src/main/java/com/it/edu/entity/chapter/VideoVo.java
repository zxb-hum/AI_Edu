package com.it.edu.entity.chapter;

import com.it.edu.entity.Video;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 *@author       :zxb
 *@data         :29/8/2022 12:42
 *@description  :
 */
@Data
public class VideoVo {
    private String id;
    private String title;
    private String videoSourceId; // 视频id
}
