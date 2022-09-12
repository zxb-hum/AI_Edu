package com.it.order.client;

import com.it.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "service-ucenter",fallback = UcenterDegradeClient.class)
@Component
public interface UcenterClient {
    @GetMapping("/ucenter/getUcenterInfo/{memberId}")
    public UcenterMember getUcenterInfo(@PathVariable("memberId") String memberId);
}
