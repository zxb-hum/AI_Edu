package com.it.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.commonutils.JwtUtils;
import com.it.commonutils.R;
import com.it.order.entity.Order;
import com.it.order.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/order/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 生成订单
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    // 根据订单id获取订单信息  (前端显示)
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("orderInfo",order);
    }

    // 根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBUyCourse/{courseId}/{memberId}")
    public boolean isBUyCourse(@PathVariable String courseId, @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int result = orderService.count(wrapper);
        if (result==0){
            // 未支付
            return false;
        }else {
            return true;
        }
    }

}

