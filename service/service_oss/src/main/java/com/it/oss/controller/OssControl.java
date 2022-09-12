package com.it.oss.controller;

import com.it.commonutils.R;
import com.it.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 *@author       :zxb
 *@data         :27/8/2022 16:48
 *@description  :
 */
@RestController
@RequestMapping("/oss/fileoss")
@CrossOrigin
public class OssControl {

    @Autowired
    private OssService ossService;

    // 上传头
    @PostMapping("add")
    public R uploadOssFile(MultipartFile file){
        // 返回上传的文件oss路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

    @GetMapping("test")
    public R test(){
        // 返回上传的文件oss路径

        return R.ok();
    }
}
