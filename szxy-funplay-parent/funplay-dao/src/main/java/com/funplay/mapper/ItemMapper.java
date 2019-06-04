package com.funplay.mapper;

import com.funplay.pojo.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ItemMapper {
    @Insert("insert into item values(#{id},#{title},#{sellpoint},#{price},#{stockcount},#{num}" +
            ",#{image},#{categoryid},#{status},#{createtime},#{updatetime}," +
            "#{isdefault},#{goodsid},#{sellerid},#{category},#{brand},#{spec},#{seller})")
    Integer insert(Item item);

    @Select("select * from item where goodsid=#{id} order by isdefault DESC")
    List<Item> findByGoodsId(Long id);//按照状态降序，保证第一个为默认

    @Delete("delete from item where goodsid=#{id}")
    Integer deleteByGoodsId(Long id);

    @Select("select * from item where status=#{status}")
    List<Item> findByStauts(String status);

    @Select("<script>" +
            "select * from item" +
            "<where> " +
            " status=#{status}"+
            "<if test='ids != null '>" +
            "and goodsid in"+
            "<foreach collection='ids' item='goodsid' index='index' open='(' close=')' separator=','>" +
            "#{goodsid}" +
            "</foreach>"+
            "</if>"+
            "</where>" +
            "</script>")
    List<Item> findItemListByGoodsIdandStatus(@Param("ids")Long[] ids, @Param("status") String status);

    @Update("update item set status=#{status} where id=#{id}")
    Integer updateStatus(Item item);

    @Select("select * from item where id=#{itemId}")
    Item findOne(Long itemId);
}
