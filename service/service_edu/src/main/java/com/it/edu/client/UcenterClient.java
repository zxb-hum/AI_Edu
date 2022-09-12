package com.it.edu.client;

import com.it.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = UcenterFileDegradeFeignClient.class)
@Component
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/ucenter/getUcenterInfo/{memberId}")
    public UcenterMember getUcenterInfo(@PathVariable("memberId") String memberId);
}

