package com.funplay.pojogroup;

import com.funplay.pojo.OrderItem;

import java.io.Serializable;
import java.util.List;

public class CartGroup implements Serializable {
    private String sellerId;//商家 ID

    private String sellerName;//商家名称

    private List<OrderItem> orderItemList;//购物车明细

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
