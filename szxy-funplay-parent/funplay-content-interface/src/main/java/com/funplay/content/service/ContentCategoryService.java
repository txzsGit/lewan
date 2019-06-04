package com.funplay.content.service;

import com.funplay.pojo.ContentCategory;
import entity.PageResult;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(ContentCategory contentCategory, Integer pageNum, Integer pageSize);


    /**
     * 增加
     */
    public void add(ContentCategory contentCategory);


    /**
     * 修改
     */
    public void update(ContentCategory contentCategory);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public ContentCategory findOne(Long id);


    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    public List<ContentCategory> findAll();
}
