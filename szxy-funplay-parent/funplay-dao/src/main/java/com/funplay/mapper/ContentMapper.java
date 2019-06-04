package com.funplay.mapper;

import com.funplay.pojo.Content;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ContentMapper {
    @Select("<script>" +
            "select * from content" +
            "<where>" +
            "<if test='title != null'>" +
            "and title like concat('%', #{title}, '%')"+
            "</if>"+
            "<if test='url != null'>" +
            "and url like concat('%', #{url}, '%')"+
            "</if>"+
            "<if test='pic != null'>" +
            "and pic like concat('%', #{pic}, '%')"+
            "</if>"+
            "<if test='status != null'>" +
            "and status =#{status}"+
            "</if>"+
            "</where>" +
            "</script>")
    List<Content> findPage(Content content);

    @Insert("insert into content values(#{id},#{categoryid},#{title},#{url},#{pic},#{status},#{sortorder})")
    Integer insert(Content content);

    @Delete("delete from content where id=#{id}")
    Integer delete(Long id);

    @Select("select * from content where id=#{id}")
    Content findOne(Long id);

    @Update("update content set categoryid=#{categoryid},title=#{title},url=#{url},pic=#{pic},status=#{status},sortorder=#{sortorder} where id=#{id}")
    Integer update(Content content);

    @Select("select * from content where categoryid=#{categoryid} and status='1' order by sortorder ASC")
    List<Content> findByCategoryId(Long categoryid);


    @Delete("delete from content where  categoryid=#{id}")
    Integer deleteByContentCategoryId(Long id);
}
