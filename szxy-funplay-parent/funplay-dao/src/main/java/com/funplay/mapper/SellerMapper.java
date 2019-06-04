package com.funplay.mapper;

import com.funplay.pojo.Seller;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;


public interface SellerMapper {

    @Insert("insert into seller values(#{sellerid},#{name},#{nickname},#{password},#{email},#{mobile}" +
            ",#{telephone},#{status},#{addressdetail},#{linkmanname},#{linkmanqq},#{linkmanmobile},#{linkmanemail}" +
            ",#{licensenumber},#{taxnumber},#{orgnumber},#{address},#{logopic},#{brief},#{createtime},#{legalperson}" +
            ",#{legalpersoncardid},#{bankuser},#{bankname},#{bankbranch},#{bankcard})")
    Integer add(Seller seller);

    @Select("<script>" +
            "select * from seller" +
            "<where>" +
            "<if test='name != null'>" +
            "and name like concat('%', #{name}, '%')"+
            "</if>"+
            "<if test='nickname != null'>" +
            "and nickname like concat('%', #{nickname}, '%')"+
            "</if>"+
            "<if test='status != null'>" +
            "and status=#{status}"+
            "</if>"+
            "</where>" +
            "</script>")
    List<Seller> findPage(Seller seller);

    @Select("select * from seller where sellerid=#{id}")
    Seller findOne(String id);

    @Update("update seller set status=#{status} where sellerid=#{sellerid} ")
    Integer updateStatus(Seller seller);

    @Update("update seller set name=#{name},nickname=#{nickname},password=#{password},email=#{email},mobile=#{mobile}"
    + ",telephone=#{telephone},status=#{status},addressdetail=#{addressdetail},linkmanname=#{linkmanname},linkmanqq=#{linkmanqq},linkmanmobile=#{linkmanmobile},linkmanemail=#{linkmanemail}"
    +",licensenumber=#{licensenumber},taxnumber=#{taxnumber},orgnumber=#{orgnumber},address=#{address},logopic=#{logopic},brief=#{brief},createtime=#{createtime},legalperson=#{legalperson}"+
    ",legalpersoncardid=#{legalpersoncardid},bankuser=#{bankuser},bankname=#{bankname},bankbranch=#{bankbranch},bankcard=#{bankcard} where sellerid=#{sellerid}")
    Integer update(Seller seller);

    @Update("update seller set logintime=#{logintime} where sellerid=#{sellerid}")
    Integer updateTime(Seller seller);
}
