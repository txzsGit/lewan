package com.funplay.mapper;

import com.funplay.pojo.SpecificationOption;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecificationOptionMapper {
    @Insert("insert into specification_option values(#{id},#{optionname},#{specid},#{orders})")
    Integer insert(SpecificationOption specificationOption);

    @Select("select * from specification_option where specid=#{id} order by orders ASC")
    List<SpecificationOption> findBySpecId(Long id);

    @Delete("delete from specification_option where specid=#{id}")
    Integer deleteBySpecId(Long id);


}
