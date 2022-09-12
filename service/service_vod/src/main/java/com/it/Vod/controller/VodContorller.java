package com.it.Vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.it.Vod.service.VodService;
import com.it.Vod.utils.ConstantVodUtils;
import com.it.Vod.utils.Init;

import com.it.commonutils.R;
import com.it.servicebase.exceptionHandler.GuliException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 *@author       :zxb
 *@data         :31/8/2022 10:05
 *@description  :
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class VodContorller {

    @Autowired
    private VodService vodService;
    // 视频上传至阿里云
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadAliyunVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable String id){
        try{
//            初始化对象
            DefaultAcsClient client = Init.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
//            创建删除视频的request
            DeleteVideoRequest request =new DeleteVideoRequest();
//            向request中设置要删除视频的id
            request.setVideoIds(id);
//            调用方法，实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e ){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }

    }

    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList){
        vodService.removeMoreVideos(videoList);
        return R.ok();
    }


    //根据视频id获取视频的播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 初始化对象
            DefaultAcsClient client = Init.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 向request中设置视频id
            request.setVideoId(id);

            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            // 返回response中的视频凭证
            String playAuth = response.getPlayAuth();
//            System.out.println(playAuth);
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new  GuliException(20001, "视频播放异常");
        }

    }
}
