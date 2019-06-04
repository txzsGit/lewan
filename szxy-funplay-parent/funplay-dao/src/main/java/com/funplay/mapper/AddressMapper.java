package com.funplay.mapper;

import com.funplay.pojo.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AddressMapper {

    @Select("select * from address")
    List<Address> findAll();

    @Insert("insert into address values(#{id},#{userid}," +
            "#{mobile},#{address},#{contact},#{isdefault},#{createdate},#{alias})")
    Integer add(Address address);

    @Update("update address set userid=#{userid}," +
            "mobile=#{mobile},address=#{address},contact=#{contact},isdefault=#{isdefault},createdate=#{createdate},alias=#{alias}" +
            "where id=#{id}")
    Integer update(Address address);

    @Select("select * from address where id=#{id}")
    Address findOne(Long id);

    @Delete("delete from address where id=#{id}")
    Integer delete(Long id);

    @Select("select * from address where userid=#{userId}")
    List<Address> findListByUserId(String userId);
}
