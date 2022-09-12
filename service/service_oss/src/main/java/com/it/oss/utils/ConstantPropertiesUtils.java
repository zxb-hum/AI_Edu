package com.it.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 *@author       :zxb
 *@data         :27/8/2022 16:38
 *@description  :
 */
// 当项目已启动，spring接口，spring加载之后，执行接口一个方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {
   // 读取配置文件注解
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    // 定义一个公共静态常量
    public static String END_POINT;
    public static String KEYID;
    public static String KEYSCRET;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEYID = keyid;
        KEYSCRET = keysecret;
        BUCKETNAME = bucketname;
    }
}
