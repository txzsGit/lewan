package com.funplay.content.service;

import com.funplay.pojo.Content;
import entity.PageResult;

import java.util.List;

public interface ContentService {
    public PageResult findPage(Content content, Integer pageNum, Integer pageSize);

    /**
     * 增加
     */
    public void add(Content content);


    /**
     * 修改
     */
    public void update(Content content);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public Content findOne(Long id);


    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);


    /**
     * 根据广告类型 ID 查询列表
     * @param categoryid
     * @return
     */
    public List<Content> findByCategoryId(Long categoryid);

}
