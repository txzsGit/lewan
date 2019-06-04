package com.funplay.mapper;

import com.funplay.pojo.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper {
     //添加商品
     @Insert("insert into goods values(#{id}," +
             "#{sellerid},#{goodsname},#{defaultitemid}" +
             ",#{auditstatus},#{ismarketable},#{brandid},#{caption},#{category1id}" +
             ",#{category2id},#{category3id},#{smallpic},#{price},#{typetemplateid}" +
             ",#{isenablespec},#{isdelete})")
     @Options(useGeneratedKeys=true)
     Integer insert(Goods goods);
     //查询商品,并分页
     @Select("<script>" +
             "select * from goods" +
             "<where>" +
             " isdelete is null " +
             "<if test='goodsname != null'>" +
             "and goodsname like concat('%', #{goodsname}, '%')"+
             "</if>"+
             "<if test='sellerid != null'>" +
             "and sellerid = #{sellerid}"+
             "</if>"+
             "<if test='auditstatus != null and auditstatus!=\"\" '>" +
             "and auditstatus = #{auditstatus}"+
             "</if>"+
             "</where>" +
             "</script>")
     List<Goods> findPage(Goods goods);
     //根据id查找摸一个商品
     @Select("select * from goods where id=#{id}")
     Goods findOne(Long id);
     //更新商品
     @Update("update goods set  sellerid = #{sellerid},goodsname=#{goodsname},defaultitemid=#{defaultitemid},auditstatus=#{auditstatus},ismarketable=#{ismarketable},brandid=#{brandid},caption=#{caption}," +
             "category1id=#{category1id},category2id=#{category2id},category3id=#{category3id},smallpic=#{smallpic},price=#{price},typetemplateid=#{typetemplateid},isenablespec=#{isenablespec},isdelete=#{isdelete} where id=#{id}")
     Integer update(Goods goods);
     //更新商品状态
     @Update("update goods set auditstatus=#{auditstatus},isdelete=#{isdelete} where id=#{id}")
     Integer updateStatus(Goods goods);

}
