package com.it.statistics.schedele;

import com.it.statistics.service.DailyService;
import com.it.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 *@author       :zxb
 *@data         :8/9/2022 16:22
 *@description  :
 */
@Component
public class ScheduleTask {

    @Autowired
    private DailyService dailyService;
//
//    @Scheduled(cron = "0/1 * * * * ?") // 每隔5秒执行此任务
//    public void task1(){
//        System.out.println("task1执行");
//    }

    @Scheduled(cron = "0 0 1 * * ?") // 每隔5秒执行此任务
    public void task2(){
        // 获取上一天的时间
        String day= DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStatisticsByDay(day);
    }
}
