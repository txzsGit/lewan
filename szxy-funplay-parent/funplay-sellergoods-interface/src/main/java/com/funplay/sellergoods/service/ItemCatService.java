package com.funplay.sellergoods.service;

import com.funplay.pojo.Goods;
import com.funplay.pojo.ItemCat;

import java.util.List;

public interface ItemCatService {
    /**
     * 通过parentid查找商品分类信息
     * @param id
     * @return
     */
    public List<ItemCat> findByParentId(Long id);

    /**
     * 添加分类
     * @param itemCat
     */
    public void add(ItemCat itemCat);

    /**
     * 修改分类
     * @param itemCat
     */
    public void update(ItemCat itemCat);

    /**
     * 根据id查询分类实体类
     * @param id
     */
    public ItemCat findOne(Long id);

    /**
     *查找该类下的子分类
     *
     */
    public int findCountByParentId(Long parentId);


    /**
     * 根据批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 查询所有
     * @return
     */
    public List<ItemCat> findAll();


}
