package com.it.order.controller;


import com.it.commonutils.R;
import com.it.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/order/payLog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    // 生成微信二维码接口
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 返回信息，包含二维码地址，还有其他信息
        Map map =payLogService.createNative(orderNo);
        System.out.println("****支付二维码信息*****"+map);
        return R.ok().data(map);
    }

    // 查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return  R.ok().message("支付中---");
    }
}

