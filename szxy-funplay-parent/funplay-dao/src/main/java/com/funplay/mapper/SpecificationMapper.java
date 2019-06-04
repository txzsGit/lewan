package com.funplay.mapper;

import com.funplay.pojo.Specification;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper {

    @Select("select * from specification")
    List<Specification> findAll();
    @Select("<script>" +
            "select * from specification" +
            "<where>" +
            "<if test='specname != null'>" +
            "and specname like concat('%', #{specname}, '%')"+
            "</if>"+
            "</where>" +
            "</script>")
    List<Specification> findPage(Specification specification);

    @Insert("insert into specification value(#{id},#{specname})")
    @Options(useGeneratedKeys=true)
    Integer insert(Specification specification);

    @Select("select * from specification where id=#{id}")
    Specification findOne(Long id);

    @Update("update specification set specname=#{specname} where id=#{id}")
    Integer update(Specification specification);

    @Delete("delete from specification where id=#{id}")
    Integer delete(Long id);

   //规格下拉列表
    @Select("select id,specname as text from specification")
    public List<Map> selectOptionList();
}
