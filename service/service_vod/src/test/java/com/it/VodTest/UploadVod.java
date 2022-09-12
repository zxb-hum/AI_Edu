package com.it.VodTest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;

/*
 *@author       :zxb
 *@data         :31/8/2022 9:35
 *@description  :
 */
public class UploadVod {
    // 上传视频
    public static void main(String[] args) {
        /**
         * 本地文件上传接口
         *
         * @param accessKeyId
         * @param accessKeySecret
         * @param title
         * @param fileName
         */
        String accessKeyId="LTAI5tMVHQ3Rmzy923L7PkxJ";
        String accessKeySecret="66uBq78oka9T0arIpDnll3unJFl7hk";
        String title="test uploading video";  // 上传文件名称（在阿里云的名字）
        String fileName="I:\\java_learning\\projects\\sources\\uploadVideo\\6 - What If I Want to Move Faster.mp4";  // 本地文件路径和名称
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
