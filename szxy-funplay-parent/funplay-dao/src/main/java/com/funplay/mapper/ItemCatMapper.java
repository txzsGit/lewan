package com.funplay.mapper;

import com.funplay.pojo.ItemCat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ItemCatMapper {
    @Select("select * from item_cat where parentid=#{id}")
    List<ItemCat> findByParentId(Long id);

    @Insert("insert into item_cat values(#{id},#{parentid},#{name},#{typeid})")
    Integer insert(ItemCat itemCat);

    @Update("update item_cat set parentid=#{parentid},name=#{name},typeid=#{typeid} where id=#{id}")
    Integer update(ItemCat itemCat);

    @Select("select * from item_cat where id=#{id}")
    ItemCat findOne(Long id);

    @Select("select count(id) from item_cat where parentid=#{parentid}")
    Integer findCountByParentId(Long parentid);

    @Delete("delete from item_cat where id=#{id}")
    Integer delete(Long id);

    @Select("select * from item_cat")
    List<ItemCat> findAll();
}
