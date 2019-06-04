package com.funplay.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Value;
import util.HttpClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;
    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        //1.封装请求数据
        Map <String,String> param=new HashMap<>();
        param.put("appid",appid);//公众账号ID
        param.put("mch_id",partner);//商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body","乐玩3C");//商品描述
        param.put("out_trade_no",out_trade_no);//商号订单号
        param.put("total_fee",total_fee);//标价金额
        param.put("spbill_create_ip","127.0.0.1");//终端IP
        param.put("notify_url","http://www.itcast.cn");//通知地址
        param.put("trade_type","NATIVE");//交易类型
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);//将map转成xml字符串类型
            //2.发送请求数据(HttpClient)
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);//是否是https
            httpClient.setXmlParam(xmlParam);//设置封装的数据
            httpClient.post();//发送请求
            //3.获取结构
            String result = httpClient.getContent();//获取结果XML
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);//将结果xml转成map
            Map  map=new HashMap();
            map.put("code_url",resultMap.get("code_url"));//将二维码连接封装到map中,支付地址
            map.put("total_fee",total_fee);//总金额
            map.put("out_trade_no",out_trade_no);//订单号
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        //1.封装请求数据
        Map<String,String> param=new HashMap();
        param.put("appid",appid);
        param.put("mch_id",partner);
        param.put("out_trade_no",out_trade_no);
        param.put("nonce_str",WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取数据
            String result = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return  resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    @Override
    public Map closePay(String out_trade_no) {
        //1.封装请求数据
        Map<String,String> param=new HashMap();
        param.put("appid",appid);
        param.put("mch_id",partner);
        param.put("out_trade_no",out_trade_no);
        param.put("nonce_str",WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取数据
            String result = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return  resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }
}
