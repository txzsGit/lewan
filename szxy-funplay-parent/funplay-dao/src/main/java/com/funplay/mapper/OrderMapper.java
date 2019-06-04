package com.funplay.mapper;

import com.funplay.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {

    @Insert("insert into `order` values(#{orderid},#{payment},#{paymenttype},#{postfee}," +
            "#{status},#{createtime},#{updatetime},#{paymenttime},#{consigntime},#{endtime},#{closetime},#{shippingname}," +
            "#{shippingcode},#{userid},#{buyermessage},#{buyernick},#{buyerrate},#{receiverareaname},#{receivermobile}," +
            "#{receiverzipcode},#{receiver},#{expire},#{invoicetype},#{sourcetype},#{sellerid})")
    Integer insert(Order order);

    @Select("select * from `order` where orderid=#{orderid}")
    Order findOne(long l);

    @Update("update `order` set status=#{status},updatetime=#{updatetime},paymenttime=#{paymenttime},sourcetype=#{sourcetype} where orderid=#{orderid}")
    Integer update(Order order);
}
