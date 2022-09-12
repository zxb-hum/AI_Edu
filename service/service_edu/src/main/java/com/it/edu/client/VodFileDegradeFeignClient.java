package com.it.edu.client;

import com.it.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *@author       :zxb
 *@data         :1/9/2022 10:58
 *@description  :
 */
@Component
public class VodFileDegradeFeignClient implements VideoClient {

    @Override
    public R removeAliyunVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
