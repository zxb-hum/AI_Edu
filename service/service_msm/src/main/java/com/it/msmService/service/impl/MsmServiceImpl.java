package com.it.msmService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.it.msmService.service.MsmService;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.Map;

/*
 *@author       :zxb
 *@data         :2/9/2022 10:04
 *@description  :
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, Map<String, Object> param) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tMVHQ3Rmzy923L7PkxJ", "66uBq78oka9T0arIpDnll3unJFl7hk");
//        //"LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF"
//        "LTAI4FvvVEMiTJ3GNJJqJnk7", "9st82dv7EvFk9mTjYO1XXbM632fRbG"
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        // 设置发送相关参数
        request.putQueryParameter("PhoneNumbers", phone); // 手机号
        request.putQueryParameter("SignName", "阿里云短信测试");  // 申请阿里云  签名名称
        request.putQueryParameter("TemplateCode", "SMS_154950909");   // 模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            boolean send_result = response.getHttpResponse().isSuccess();
            return send_result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
