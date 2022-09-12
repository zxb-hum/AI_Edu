package com.it.Vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*
 *@author       :zxb
 *@data         :31/8/2022 10:01
 *@description  :
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 不加载数据库配置
@ComponentScan(basePackages = "com.it")
@EnableDiscoveryClient
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }
}
