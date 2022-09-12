package com.it.statistics.controller;


import com.it.commonutils.R;
//import com.it.statistics.client.UcenterClient;
import com.it.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    // 根据日期创建统计数据
    @PostMapping("registerCount/{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end){
        Map<String,Object> map = dailyService.showData(type,begin,end);
        return R.ok().data(map);
    }
}

