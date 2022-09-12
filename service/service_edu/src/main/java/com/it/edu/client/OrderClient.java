package com.it.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order", fallback = OrderDegradeClient.class)
public interface OrderClient {
    @GetMapping("/order/order/isBUyCourse/{courseId}/{memberId}")
    public boolean isBUyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId); // pathVariable不加说明会报错
}
