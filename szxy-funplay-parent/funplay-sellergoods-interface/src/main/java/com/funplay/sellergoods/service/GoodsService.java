package com.funplay.sellergoods.service;

import com.funplay.pojo.Goods;
import com.funplay.pojo.Item;
import com.funplay.pojogroup.GoodsGroup;
import entity.PageResult;

import java.util.List;

public interface GoodsService {
    /**
     * 添加商品
     * @param goodsGroup
     */
    public void add(GoodsGroup goodsGroup);

    /**
     * 条件查询+分页
     * @param goods
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findPage(Goods goods, Integer pageNum, Integer pageSize);

    /**
     * 通过id获取goodsGroup,前端回显
     * @param id
     * @return
     */
    public GoodsGroup findOne(Long id);

    /**
     * 更新
     * @param goodsGroup
     */
    public void update(GoodsGroup goodsGroup);

    /**
     * 批量修改状态
     * @param ids
     * @param status
     */
    public void updateStatus(Long []ids,String status);

    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 根据商品id查询item表信息 从spu的id获取item
     * @param ids
     * @param status
     * @return
     */
    List<Item> findItemListByGoodsIdandStatus(Long[] ids, String status);

    void isMarketable(Long[] ids, String maketable);
}
