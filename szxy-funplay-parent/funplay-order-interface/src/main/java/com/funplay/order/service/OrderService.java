package com.funplay.order.service;

import com.funplay.pojo.Order;
import com.funplay.pojo.PayLog;
import entity.PageResult;

public interface OrderService {

    /**
     * 增加
     */
    public void add(Order order);


    /**
     * 查询支付日志
     * @param userId
     * @return
     */
    public PayLog searchPayLogFromRedis(String userId);

    /**
     * 支付成功修改订单状态
     * @param out_trade_no 订单号
     * @param transaction_id 微信流水号
     */
    public void updateOrderStatus(String out_trade_no,String transaction_id);

}
