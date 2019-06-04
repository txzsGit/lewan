package com.funplay.mapper;

import com.funplay.pojo.ContentCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ContentCategoryMapper {
    @Select("<script>" +
            "select * from content_category" +
            "<where>" +
            "<if test='name != null'>" +
            "and name like concat('%', #{name}, '%')"+
            "</if>"+
            "</where>" +
            "</script>")
    List<ContentCategory> findPage(ContentCategory contentCategory);

    @Select("select * from content_category where id=#{id}")
    ContentCategory findOne(Long id);

    @Insert("insert into content_category values(#{id},#{name})")
    Integer insert(ContentCategory contentCategory);

    @Update("update content_category set name=#{name} where id=#{id}")
    Integer update(ContentCategory contentCategory);

    @Delete("delete from content_category where id=#{id}")
    Integer delete(Long id);

    @Select("select * from content_category")
    List<ContentCategory> findAll();
}
