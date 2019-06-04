package com.funplay.sellergoods.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.BrandMapper;
import com.funplay.pojo.Brand;
import com.funplay.sellergoods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 返回所有品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    /**
     * 分页
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<Brand> pageList=(Page<Brand>)brandMapper.findAll();
        return new PageResult(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 添加品牌信息
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 修改品牌信息
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    /**
     * 查询品牌实体
     * @param id
     * @return
     */
    @Override
    public Brand findOne(Long id) {
        return brandMapper.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.delete(id);
        }
    }

    /**
     * 条件查询
     * @param brand
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Brand brand, int page, int pageSize) {
            PageHelper.startPage(page,pageSize);
            Page<Brand> pageList=(Page<Brand>)brandMapper.findPage(brand);
            return new PageResult(pageList.getTotal(), pageList.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }


}
