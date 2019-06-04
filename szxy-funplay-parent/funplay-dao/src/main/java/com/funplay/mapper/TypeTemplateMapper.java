package com.funplay.mapper;

import com.funplay.pojo.TypeTemplate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TypeTemplateMapper {

    @Select("<script>" +
            "select * from type_template" +
            "<where>" +
            "<if test='name != null'>" +
            "and name like concat('%', #{name}, '%')"+
            "</if>"+
            "</where>" +
            "</script>")
    List<TypeTemplate> findPage(TypeTemplate typeTemplate);

    @Select("select * from type_template where id=#{id}")
    TypeTemplate findOne(Long id);

    @Delete("delete from type_template where id=#{id}")
    Integer delete(Long id);

    @Insert("insert into type_template values(#{id},#{name},#{specids},#{brandids},#{customattributeitems})")
    Integer insert(TypeTemplate typeTemplate);

    @Update("update type_template set name=#{name},specids=#{specids},brandids=#{brandids},customattributeitems=#{customattributeitems} where id=#{id}")
    Integer update(TypeTemplate typeTemplate);

    @Select("select * from type_template")
    List<TypeTemplate> findAll();
}
