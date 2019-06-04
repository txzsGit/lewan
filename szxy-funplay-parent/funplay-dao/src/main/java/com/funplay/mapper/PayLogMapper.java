package com.funplay.mapper;

import com.funplay.pojo.PayLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PayLogMapper {
    @Insert("insert into pay_log values(#{outtradeno},#{createtime},#{paytime},#{totalfee},#{userid}," +
            "#{transactionid},#{tradestate},#{orderlist},#{paytype})")
    Integer insert(PayLog payLog);

    @Select("select * from pay_log where outtradeno=#{out_trade_no}")
    PayLog findOne(String out_trade_no);

    @Update("update pay_log set paytime=#{paytime},tradestate=#{tradestate},transactionid=#{transactionid} where outtradeno=#{outtradeno}")
    Integer update(PayLog payLog);
}
