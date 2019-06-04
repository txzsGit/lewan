package com.funplay.mapper;

import com.funplay.pojo.GoodsDesc;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface GoodsDescMapper {
    @Insert("insert into goods_desc values(#{goodsid},#{introduction},#{specificationitems}," +
            "#{customattributeitems},#{itemimages},#{packagelist},#{saleservice})")
    Integer insert(GoodsDesc goodsDesc);


    @Select("select * from goods_desc where goodsid=#{id}")
    GoodsDesc findOne(Long id);

    @Update("update goods_desc set goodsid=#{goodsid},introduction=#{introduction},specificationitems=#{specificationitems}," +
            "customattributeitems=#{customattributeitems},itemimages=#{itemimages},packagelist=#{packagelist},saleservice=#{saleservice} where goodsid=#{goodsid} ")
    Integer update(GoodsDesc goodsDesc);
}
