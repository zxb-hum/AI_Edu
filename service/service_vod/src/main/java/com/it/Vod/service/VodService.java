package com.it.Vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 上传视频到阿里云
    String uploadAliyunVideo(MultipartFile file);

    void removeMoreVideos(List videoList);
}
