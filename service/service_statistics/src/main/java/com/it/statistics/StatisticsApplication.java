package com.it.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 *@author       :zxb
 *@data         :8/9/2022 13:55
 *@description  :
 */
@SpringBootApplication
@MapperScan("com.it.statistics.mapper")
@ComponentScan(basePackages = "com.it")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling  // 开启定时任务
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
