package com.funplay.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.OrderItemMapper;
import com.funplay.mapper.OrderMapper;
import com.funplay.mapper.PayLogMapper;
import com.funplay.order.service.OrderService;
import com.funplay.pojo.Order;
import com.funplay.pojo.OrderItem;
import com.funplay.pojo.PayLog;
import com.funplay.pojogroup.CartGroup;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * 添加订单
     * @param order
     */
    @Override
    public void add(Order order) {
        //1.获取订单的用户名
        String username = order.getUserid();
        //2.获取购物车,从redis中获取
        List<CartGroup> cartList = (List<CartGroup>) redisTemplate.boundHashOps("cartList").get(username);
        double money=0;//交易金额
        List<String> list=new ArrayList<>();//订单编号列表
        for (CartGroup cartGroup : cartList) {
            //3.生成订单id
            long orderId = idWorker.nextId();
            Order tborder=new Order();//创建新的订单
            tborder.setOrderid(orderId);//订单ID
            tborder.setUserid(order.getUserid());//用户名
            tborder.setPaymenttype(order.getPaymenttype());//支付类型
            tborder.setStatus("1");//状态：未付款
            tborder.setCreatetime(new Date());//订单创建日期
            tborder.setUpdatetime(new Date());//订单更新日期
            tborder.setReceiverareaname(order.getReceiverareaname());//地址
            tborder.setReceivermobile(order.getReceivermobile());//手机号
            tborder.setReceiver(order.getReceiver());//收货人
            tborder.setSourcetype(order.getSourcetype());//订单来源
            tborder.setSellerid(cartGroup.getSellerId());//商家 ID
            double totalMoney=0;//总金额
            //循环购物车明细
            for (OrderItem orderItem : cartGroup.getOrderItemList()) {
                orderItem.setId(idWorker.nextId());//设置orderItem的id
                orderItem.setOrderid(orderId);//设置订单Id
                orderItem.setSellerid(cartGroup.getSellerId());//设置商家Id
                totalMoney+=orderItem.getTotalfee().doubleValue();//金额累加
                money+=totalMoney;//一次付款金额总和
                list.add(orderId+"");//将订单添加入集合
                orderItemMapper.insert(orderItem);
            }
            tborder.setPayment(new BigDecimal(totalMoney));
            orderMapper.insert(tborder);//添加订单
            if("1".equals(order.getPaymenttype())){
                //微信付款
                PayLog payLog=new PayLog();
                payLog.setOuttradeno(idWorker.nextId()+"");//支付订单号
                payLog.setCreatetime(new Date());//创建时间
                payLog.setTotalfee((long)(money*100));//支付金额(分)
                payLog.setUserid(username);
                payLog.setTradestate("0");//交易状态
                payLog.setPaytype("1");
                payLog.setOrderlist(list.toString().replace("[","")
                        .replace("]",""));//订单编号列表
                payLogMapper.insert(payLog);//添加入日志表
                redisTemplate.boundHashOps("payLog").put(username,payLog);//将日志表存入缓存
            }
        }
        //清空购物车
        redisTemplate.boundHashOps("cartList").delete(username);
    }

    /**
     * 通过userId查询日志信息
     * @param userId
     * @return
     */
    @Override
    public PayLog searchPayLogFromRedis(String userId) {
        PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(userId);
        return payLog;
    }

    /**
     * 更新支付状态
     * @param out_trade_no 订单号
     * @param transaction_id 微信流水号
     */
    @Override
    public void updateOrderStatus(String out_trade_no, String transaction_id) {
        //1.修改支付日志状态
        PayLog payLog = payLogMapper.findOne(out_trade_no);
        payLog.setPaytime(new Date());//支付完成时间
        payLog.setTradestate("1");//已支付
        payLog.setTransactionid(transaction_id);//交易号
        payLogMapper.update(payLog);
        //2.改变订单状态
        String orderList = payLog.getOrderlist();
        String[] orderIds= orderList.split(",");

        for (String orderId : orderIds) {
            Order order = orderMapper.findOne(Long.parseLong(orderId));
            if(order!=null){
                order.setStatus("2");//已付款
                order.setUpdatetime(new Date());
                order.setPaymenttime(new Date());
                order.setSourcetype("2");
                System.out.println(order.getStatus());
                orderMapper.update(order);
            }
        }
        //3.清楚redis中的缓存数据
        redisTemplate.boundHashOps("payLog").delete(payLog.getUserid());
    }

}
