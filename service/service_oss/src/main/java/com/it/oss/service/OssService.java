package com.it.oss.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    // 上传头像到oss中
    String uploadFileAvatar(MultipartFile file);
}
