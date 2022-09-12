package com.it.statistics.client;

import com.it.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterDegradeClient.class)
public interface UcenterClient {
    @GetMapping("/ucenter/countregister/{day}")
    public R countregister(@PathVariable String day);
}
