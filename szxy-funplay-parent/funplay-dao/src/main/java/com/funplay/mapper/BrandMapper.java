package com.funplay.mapper;

import com.funplay.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 品牌dao
 * @author txzs
 *
 */
public interface BrandMapper {
    //查询所有
    @Select("select * from brand")
    List<Brand> findAll();
    //添加品牌
    @Insert("insert into brand values(#{id},#{name},#{firstchar})")
    Integer insert(Brand brand);
    //修改品牌
    @Update("update brand set name=#{name},firstchar=#{firstchar} where id=#{id}")
    Integer update(Brand brand);
    //查询某个具体的品牌
    @Select("select * from brand where id=#{id}")
    Brand findOne(Long id);
    //根据id删除品牌
    @Delete("delete from brand where id=#{id}")
    Integer delete(Long id);
    //根据品牌名称和首字母模糊查询
    @Select("<script>" +
            "select * from brand" +
            "<where>" +
            "<if test='name != null'>" +
            "and name like concat('%', #{name}, '%')"+
            "</if>"+
            "<if test='firstchar != null'>" +
            "and firstchar like concat('%', #{firstchar}, '%')"+
            "</if>"+
            "</where>" +
            "</script>")
    List<Brand>  findPage(Brand brand);

    //品牌下拉框
    @Select("select id,name as text from brand ")
    List<Map>  selectOptionList();
}
