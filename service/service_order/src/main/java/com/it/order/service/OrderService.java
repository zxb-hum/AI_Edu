package com.it.order.service;

import com.it.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zxb
 * @since 2022-09-06
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);
}
