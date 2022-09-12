package com.it.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.it.oss.service.OssService;
import com.it.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/*
 *@author       :zxb
 *@data         :27/8/2022 16:47
 *@description  :
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSCRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;

        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = file.getOriginalFilename();
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。

        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        objectName = uuid+objectName;

        // 获取当前时间
        String datePath= new DateTime().toString("yyyy/MM/dd");
        objectName = datePath+"/"+objectName;
//        String filePath= "D:\\localpath\\examplefile.txt";

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求
            ossClient.putObject(bucketName, objectName, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            // 手动拼接出来路径
//            https://zxb-edu.oss-cn-beijing.aliyuncs.com/IMG_4730.JPG
            String url = "https://"+bucketName+"."+endpoint+"/"+objectName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
