package com.funplay.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *商品spu实体类
 */
public class Goods implements Serializable {
    private Long id;//主键id

    private String sellerid;//商家id

    private String goodsname;//spu商品名

    private Long defaultitemid;//默认SKU

    private String auditstatus;//状态

    private String ismarketable;//是否上架

    private Long brandid;//品牌

    private String caption;//副标题

    private Long category1id;//一级类目

    private Long category2id;//二级类目

    private Long category3id;//三级类目

    private String smallpic;//小图

    private BigDecimal price;//价格

    private Long typetemplateid;//分类模板ID

    private String isenablespec;//是否启用规格

    private String isdelete;//是否删除

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public Long getDefaultitemid() {
        return defaultitemid;
    }

    public void setDefaultitemid(Long defaultitemid) {
        this.defaultitemid = defaultitemid;
    }

    public String getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(String auditstatus) {
        this.auditstatus = auditstatus;
    }

    public String getIsmarketable() {
        return ismarketable;
    }

    public void setIsmarketable(String ismarketable) {
        this.ismarketable = ismarketable;
    }

    public Long getBrandid() {
        return brandid;
    }

    public void setBrandid(Long brandid) {
        this.brandid = brandid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getCategory1id() {
        return category1id;
    }

    public void setCategory1id(Long category1id) {
        this.category1id = category1id;
    }

    public Long getCategory2id() {
        return category2id;
    }

    public void setCategory2id(Long category2id) {
        this.category2id = category2id;
    }

    public Long getCategory3id() {
        return category3id;
    }

    public void setCategory3id(Long category3id) {
        this.category3id = category3id;
    }

    public String getSmallpic() {
        return smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getTypetemplateid() {
        return typetemplateid;
    }

    public void setTypetemplateid(Long typetemplateid) {
        this.typetemplateid = typetemplateid;
    }

    public String getIsenablespec() {
        return isenablespec;
    }

    public void setIsenablespec(String isenablespec) {
        this.isenablespec = isenablespec;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }
}
