package com.funplay.pojogroup;

import com.funplay.pojo.Goods;
import com.funplay.pojo.GoodsDesc;
import com.funplay.pojo.Item;

import java.io.Serializable;
import java.util.List;

/**
 * 商品组合实体类
 */
public class GoodsGroup implements Serializable {
    private Goods goods;//商品 SPU

    private GoodsDesc goodsDesc;//商品扩展

    private List<Item> itemList;//商品 SKU 列表

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
