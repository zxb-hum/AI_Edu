package com.it.edu.client;

import com.it.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)  // 调用的服务名称
@Component
public interface VideoClient {
    @DeleteMapping("/video/removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable("id") String id);

    @DeleteMapping("/video/delete-batch")
    public R deleteBatch(@PathVariable("videoList") List<String> videoList);
}
