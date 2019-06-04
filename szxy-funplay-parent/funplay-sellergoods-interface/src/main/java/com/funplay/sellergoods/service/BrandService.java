package com.funplay.sellergoods.service;

import com.funplay.pojo.Brand;
import entity.PageResult;

import java.util.List;
import java.util.Map;


public interface BrandService {

    public List<Brand> findAll();

    public PageResult findPage(Integer page,Integer pageSize);

    public void add(Brand brand);

    public void update(Brand brand);

    public Brand findOne(Long id);

    public void delete(Long [] ids);

    public PageResult findPage(Brand brand, int page,int pageSize);

    List<Map> selectOptionList();
}
