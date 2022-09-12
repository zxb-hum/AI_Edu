package com.it.VodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

/*
 *@author       :zxb
 *@data         :30/8/2022 22:31
 *@description  :
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
        //  method=> 根据视频id获取视频的播放地址


        // 创建初始化对象
        DefaultAcsClient client = Init.initVodClient("LTAI5tMVHQ3Rmzy923L7PkxJ", "66uBq78oka9T0arIpDnll3unJFl7hk");
        // 创建获取视频地址的request和response

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向request对象设置视频id
        request.setVideoId("a68eec8b687a41c382a9889105e6a655");
        // 调用初始化对象里面的方法，传入request，获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
