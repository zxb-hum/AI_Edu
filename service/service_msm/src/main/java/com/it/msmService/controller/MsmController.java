package com.it.msmService.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.it.commonutils.R;
import com.it.msmService.service.MsmService;
import com.it.msmService.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 *@author       :zxb
 *@data         :2/9/2022 10:06
 *@description  :
 */
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

   @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return R.ok();

        code = RandomUtils.generateNumberString(4);  // 获取4位随机生成的数字验证吗
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(phone, param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,3, TimeUnit.MINUTES);  // 设置验证码5min过期（param4:时间单位）
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }

    @GetMapping("test_8005Port")
    public R test_8005Port(){
       return R.ok().data("aaa","success");
    }


}
