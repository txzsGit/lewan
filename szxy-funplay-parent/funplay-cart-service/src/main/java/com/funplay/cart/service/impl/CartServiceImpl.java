package com.funplay.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.cart.service.CartService;
import com.funplay.mapper.ItemMapper;
import com.funplay.pojo.Item;
import com.funplay.pojo.OrderItem;
import com.funplay.pojogroup.CartGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 添加商品进购物车
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public List<CartGroup> addGoodsToCartList(List<CartGroup> cartList, Long itemId, Integer num) {
        //1.根据商品 SKU ID 查询 SKU 商品信息
        Item item = itemMapper.findOne(itemId);
        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品已下架");
        }
        //2.获取商家 ID
        String sellerId = item.getSellerid();
        //3.根据商家 ID 判断购物车列表中是否存在该商家的购物车
        CartGroup cartGroup = searchCartBySellerId(cartList, sellerId);
        if(cartGroup==null){
            //4.如果购物车列表中不存在该商家的购物车
            //5.新建购物车对象
            cartGroup=new CartGroup();
            cartGroup.setSellerId(sellerId);
            cartGroup.setSellerName(item.getSeller());
            OrderItem orderItem = createOrderItem(item, num);
            List orderItemList=new ArrayList();
            orderItemList.add(orderItem);
            cartGroup.setOrderItemList(orderItemList);
            //6.将新建的购物车对象添加到购物车列表
            cartList.add(cartGroup);
        }else{
            //7.如果购物车列表中存在该商家的购物车
            OrderItem orderItem = searchOrderItemByItemId(cartGroup.getOrderItemList(), itemId);
            if(orderItem==null){
                //8.查询购物车明细列表中是否存在该商品
                //9.如果没有，新增购物车明细
                orderItem=createOrderItem(item,num);
                cartGroup.getOrderItemList().add(orderItem);
            }else{
                //5.2. 如果有，在原购物车明细上添加数量，更改金额
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalfee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
                //如果数量操作后小于等于0，则移出
                if(orderItem.getNum()<=0){
                    cartGroup.getOrderItemList().remove(orderItem);//移除购物车明细
                }
                if(cartGroup.getOrderItemList().size()==0){
                    cartList.remove(cartGroup);
                }
            }
        }
        return cartList;
    }

    /**
     * 通过商家ID查询购物车ID
     * @param cartList
     * @param sellerId
     * @return
     */
    private CartGroup searchCartBySellerId(List<CartGroup> cartList,String sellerId){
        for (CartGroup cartGroup : cartList) {
            if(cartGroup.getSellerId().equals(sellerId)){
                return cartGroup;
            }
        }
        return  null;
    }

    /**
     * 创建订单明细
     * @param item
     * @param num
     * @return
     */
    private OrderItem createOrderItem(Item item,Integer num){
        if(num<=0){
            throw new RuntimeException("参数不合法");
        }
        OrderItem orderItem=new OrderItem();
        orderItem.setGoodsid(item.getGoodsid());
        orderItem.setItemid(item.getId());
        orderItem.setNum(num);
        orderItem.setPicpath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerid(item.getSellerid());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalfee(new BigDecimal(item.getPrice().doubleValue()*num));
        return  orderItem;
    }

    /**
     *根据商品明细ID查询是否已添加到购物车中
     * @param orderItemList
     * @param itemId
     * @return
     */
    private OrderItem  searchOrderItemByItemId(List<OrderItem> orderItemList,Long itemId){
        for (OrderItem orderItem : orderItemList) {
            if(orderItem.getItemid().longValue()==itemId){
                return orderItem;
            }
        }
        return  null;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名从redis中查询购物车信息
     * @param username
     * @return
     */
    @Override
    public List<CartGroup> findCartListFromRedis(String username) {
        List<CartGroup> cartList = (List<CartGroup>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList==null){
            cartList=new ArrayList();
        }
        return cartList;
    }

    /**
     * 保存购物车信息入redis
     * @param username
     * @param cartList
     */
    @Override
    public void saveCartListToRedis(String username, List<CartGroup> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    /**
     * 整合cookie和缓存中的购物车信息
     * @param cartList1
     * @param cartList2
     * @return
     */
    @Override
    public List<CartGroup> mergeCartList(List<CartGroup> cartList1, List<CartGroup> cartList2) {
        for (CartGroup cartGroup : cartList1) {
            for (OrderItem orderItem : cartGroup.getOrderItemList()) {
                cartList2=addGoodsToCartList(cartList2,orderItem.getItemid(),orderItem.getNum());
            }
        }
        return cartList2;
    }
}
