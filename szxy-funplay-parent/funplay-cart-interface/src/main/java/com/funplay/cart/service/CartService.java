package com.funplay.cart.service;

import com.funplay.pojogroup.CartGroup;

import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    public List<CartGroup> addGoodsToCartList(List<CartGroup> cartList, Long itemId, Integer num );

    /**
     * 从redis中查询出购物车
     * @param username
     * @return
     */
    public List<CartGroup> findCartListFromRedis(String username);

    /**
     * 将购物车保存到redis中
     * @param username
     * @param cartList
     */
    public void   saveCartListToRedis(String username,List<CartGroup> cartList);

    /**
     * 合并购物车
     * @param cartList1
     * @param cartList2
     * @return
     */
    public List<CartGroup> mergeCartList(List<CartGroup> cartList1,List<CartGroup> cartList2);

}
