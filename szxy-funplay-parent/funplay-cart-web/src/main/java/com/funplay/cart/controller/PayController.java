package com.funplay.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.order.service.OrderService;
import com.funplay.pay.service.WeixinPayService;
import com.funplay.pojo.PayLog;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference
    private WeixinPayService weixinPayService;
    @Reference
    private OrderService orderService;

    /**
     * 创建二维码
     * @return
     */
    @RequestMapping("/createNative")
    public Map createNative(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        PayLog payLog = orderService.searchPayLogFromRedis(userId);//从redis中查询支付日志
        if(payLog!=null){
            String outTradeNo = payLog.getOuttradeno();
            Long totalFee = payLog.getTotalfee();
            return   weixinPayService.createNative(outTradeNo,totalFee.toString());
        }else{
            return new HashMap();
        }
    }

    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        Result result=null;
        int x=0;
        while(true){
            //一直循环查询支付状态
            Map map = weixinPayService.queryPayStatus(out_trade_no);
            if(map==null){
                result=new Result(false,"支付出错");
                break;
            }
            if(map.get("trade_state").equals("SUCCESS")){
                orderService.updateOrderStatus(out_trade_no,map.get("transaction_id")+"");//更新订单和日志状态
                result=new Result(true,"支付成功");
                break;
            }
            //节省资源
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if(x>=100){
                //大约五分钟，二维码超时
                result=new Result(false,"二维码超时");
                break;
            }
        }

        return  result;
    }
}
