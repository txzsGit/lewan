package com.funplay.pojo;

import java.io.Serializable;
/**
 * 模板实体类
 * @author txzs
 */
public class TypeTemplate implements Serializable{
    private Long id;

    private String name;

    private String specids;

    private String brandids;

    private String customattributeitems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecids() {
        return specids;
    }

    public void setSpecids(String specids) {
        this.specids = specids;
    }

    public String getBrandids() {
        return brandids;
    }

    public void setBrandids(String brandids) {
        this.brandids = brandids;
    }

    public String getCustomattributeitems() {
        return customattributeitems;
    }

    public void setCustomattributeitems(String customattributeitems) {
        this.customattributeitems = customattributeitems;
    }
}