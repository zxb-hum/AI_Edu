package com.it.Vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.it.Vod.service.VodService;
import com.it.Vod.utils.ConstantVodUtils;
import com.it.Vod.utils.Init;
import com.it.commonutils.R;
import com.it.servicebase.exceptionHandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/*
 *@author       :zxb
 *@data         :31/8/2022 10:07
 *@description  :
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadAliyunVideo(MultipartFile file) {

        /**
         * title : 在阿里云显示的名称
         * fileName：上传的文件原始名称
         * inputStream：上传文件输入流
         */
        try{
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId=null;

            if (response.isSuccess()) {
                videoId = response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreVideos(List videoList) {
        try{
//            初始化对象
            DefaultAcsClient client = Init.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
//            创建删除视频的request
            DeleteVideoRequest request =new DeleteVideoRequest();

            String videoids = StringUtils.join(videoList.toArray(), ",");
//            向request中设置要删除视频的id
            request.setVideoIds(videoids);
//            调用方法，实现删除
            client.getAcsResponse(request);

        }catch (Exception e ){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
