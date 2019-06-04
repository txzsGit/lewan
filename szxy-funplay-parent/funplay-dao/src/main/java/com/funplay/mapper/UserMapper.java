package com.funplay.mapper;

import com.funplay.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Insert("insert into user values(#{id},#{username},#{password},#{phone}," +
            "#{email},#{created},#{updated},#{sourcetype},#{nickname},#{name},#{status}," +
            "#{headpic},#{qq},#{accountbalance},#{ismobilecheck},#{isemailcheck},#{sex},#{userlevel}," +
            "#{points},#{experiencevalue},#{birthday},#{lastlogintime})")
    Integer insert(User user);

    @Update("update user set username=#{username},password=#{password},phone=#{phone}," +
            "email=#{email},created=#{created},updated=#{updated},sourcetype=#{sourcetype},nickname=#{nickname},name=#{name},status=#{status}," +
            "headpic=#{headpic},qq=#{qq},accountbalance=#{accountbalance},ismobilecheck=#{ismobilecheck},isemailcheck=#{isemailcheck},sex=#{sex},userlevel=#{userlevel}," +
            "points=#{points},experiencevalue=#{experiencevalue},birthday=#{birthday},lastlogintime=#{lastlogintime} where id=#{id}")
    Integer update(User user);

    @Select("select * from user where id=#{id}")
    User findOne(Long id);

    @Delete("delete from user where id=#{id}")
    Integer delete(Long id);

    @Select("<script>" +
            "select * from user" +
            "<where>" +
            "<if test='username != null'>" +
            "and username like concat('%', #{username}, '%')"+
            "</if>"+
            "<if test='password != null'>" +
            "and password like concat('%', #{password}, '%')"+
            "</if>"+
            "<if test='phone != null'>" +
            "and phone like concat('%', #{phone}, '%')"+
            "</if>"+
            "<if test='email != null'>" +
            "and email like concat('%', #{email}, '%')"+
            "</if>"+
            "<if test='sourcetype != null'>" +
            "and sourcetype=#{sourcetype}"+
            "</if>"+
            "<if test='nickname != null'>" +
            "and nickname like concat('%', #{nickname}, '%')"+
            "</if>"+
            "<if test='name != null'>" +
            "and name like concat('%', #{name}, '%')"+
            "</if>"+
            "<if test='status != null'>" +
            "and status=#{status}"+
            "</if>"+
            "<if test='sex != null'>" +
            "and sex=#{sex}"+
            "</if>"+
            "</where>" +
            "</script>")
    List<User> findPage(User user);
}
