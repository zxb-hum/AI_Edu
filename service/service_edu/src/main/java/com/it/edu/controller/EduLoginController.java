package com.it.edu.controller;

import com.it.commonutils.R;
import org.springframework.web.bind.annotation.*;

/*
 *@author       :zxb
 *@data         :26/8/2022 10:43
 *@description  :
 */

@RestController
@RequestMapping("/edu/user")
@CrossOrigin  //解决跨域问题
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }


    // info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
    }
}
