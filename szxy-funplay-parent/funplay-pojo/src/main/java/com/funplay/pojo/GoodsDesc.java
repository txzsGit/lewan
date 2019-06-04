package com.funplay.pojo;

import java.io.Serializable;

/**
 *spu详情
 */
public class GoodsDesc implements Serializable {
    private Long goodsid;//SPU_ID

    private String introduction;//描述

    private String specificationitems;//规格结果集，所有规格，包含isSelected

    private String customattributeitems;//自定义属性（参数结果）

    private String itemimages;//图片

    private String packagelist;//包装列表

    private String saleservice;//售后服务


    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecificationitems() {
        return specificationitems;
    }

    public void setSpecificationitems(String specificationitems) {
        this.specificationitems = specificationitems;
    }

    public String getCustomattributeitems() {
        return customattributeitems;
    }

    public void setCustomattributeitems(String customattributeitems) {
        this.customattributeitems = customattributeitems;
    }

    public String getItemimages() {
        return itemimages;
    }

    public void setItemimages(String itemimages) {
        this.itemimages = itemimages;
    }

    public String getPackagelist() {
        return packagelist;
    }

    public void setPackagelist(String packagelist) {
        this.packagelist = packagelist;
    }

    public String getSaleservice() {
        return saleservice;
    }

    public void setSaleservice(String saleservice) {
        this.saleservice = saleservice;
    }
}
