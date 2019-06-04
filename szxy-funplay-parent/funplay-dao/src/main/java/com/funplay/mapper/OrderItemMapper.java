package com.funplay.mapper;

import com.funplay.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;

public interface OrderItemMapper {
    @Insert("insert into order_item values(#{id},#{itemid},#{goodsid},#{orderid},#{title}," +
            "#{price},#{num},#{totalfee},#{picpath},#{sellerid})")
    Integer insert(OrderItem orderItem);
}
