package com.it.order.service.impl;

import com.it.commonutils.vo.CourseWebVoOrder;
import com.it.commonutils.vo.UcenterMember;
import com.it.order.client.EduClient;
import com.it.order.client.UcenterClient;
import com.it.order.entity.Order;
import com.it.order.mapper.OrderMapper;
import com.it.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2022-09-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;


    @Override
    public String saveOrder(String courseId, String memberId) {
        // 通过远程调用用户id获取用户信息
        UcenterMember ucenterMember = ucenterClient.getUcenterInfo(memberId);
        // 通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);


        //创建订单,并将订单添加到数据库
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0); // 支付状态  0：未支付
        order.setPayType(1);  // 支付类型，微信  1
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }
}
