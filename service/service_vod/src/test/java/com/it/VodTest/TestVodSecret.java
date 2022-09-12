package com.it.VodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

/*
 *@author       :zxb
 *@data         :30/8/2022 22:48
 *@description  :
 */
public class TestVodSecret {
    public static void main(String[] args) throws ClientException {
    // 加密视频获取播放地址


        // 创建初始化对象
        DefaultAcsClient client = Init.initVodClient("LTAI5tMVHQ3Rmzy923L7PkxJ", "66uBq78oka9T0arIpDnll3unJFl7hk");
        // 创建获取视频地址的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("a68eec8b687a41c382a9889105e6a655");
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);




        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");

    }

}
