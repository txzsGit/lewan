package com.funplay.sellergoods.service;

import com.funplay.pojo.Specification;
import com.funplay.pojogroup.SpecificationGroup;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    public List<Specification> findAll();


    public PageResult findPage(Specification specification, int pageNum, int pageSize);

    public void add(SpecificationGroup specificationGroup);


    public SpecificationGroup findOne(Long id);

    public void update(SpecificationGroup specificationGroup);

    public void delete(Long[] ids);


    //查询规格列表加入模板的新增中
    public List<Map> selectOptionList();

}
