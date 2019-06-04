package com.funplay.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.funplay.cart.service.CartService;
import com.funplay.pojogroup.CartGroup;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    /**
     * 获取存入购物车列表
     * @return
     */
    @RequestMapping("/findCartList")
    public List<CartGroup> findCartList(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String cartListStr = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if(cartListStr==null||cartListStr.equals("")){
            cartListStr="[]";
        }
        List<CartGroup> cartList_cookie=  JSON.parseArray(cartListStr,CartGroup.class);
        if(name.equals("anonymousUser")){
            //未登录从cookie中获取购物车
            return cartList_cookie;
        }else{
            //已登录从redis中查
            List<CartGroup> cartList_redis = cartService.findCartListFromRedis(name);
            if(cartList_cookie.size()>0){//如果本地存在购物车则整合
                cartList_redis = cartService.mergeCartList(cartList_cookie, cartList_redis);//整合数据库
                cartService.saveCartListToRedis(name,cartList_redis);
                CookieUtil.deleteCookie(request,response,"cartList");//删除cookie中的数据
            }
            return cartList_redis;
        }
    }


    /**
     * 添加商品入购物车
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins ={"http://localhost","http://localhost:9104"},allowCredentials = "true")
    public Result addGoodsToCartList(Long itemId, Integer num){
       /* response.setHeader("Access-Control-Allow-Origin", "http://localhost:9105");//解决跨域问题
        response.setHeader("Access-Control-Allow-Credentials", "true");//解决跨域cookie问题*/

        try{
            List<CartGroup> cartList = findCartList();//获取购物车列表
            cartList=cartService.addGoodsToCartList(cartList,itemId,num);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if(name.equals("anonymousUser")){
                //未登录存入cookie
                CookieUtil.setCookie(request,response,"cartList",
                        JSON.toJSONString(cartList),3600*24,"UTF-8");
            }else{
                //存入redis
                cartService.saveCartListToRedis(name,cartList);
            }
            return  new Result(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
}
