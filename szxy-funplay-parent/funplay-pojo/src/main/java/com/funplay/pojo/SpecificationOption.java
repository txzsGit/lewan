package com.funplay.pojo;

import java.io.Serializable;
/**
 * 规格选项实体类
 * @author txzs
 */
public class SpecificationOption implements Serializable {
    private Long id;//主键id

    private String optionname;//规格选项名

    private Long specid;//规格主键

    private Integer orders;//排序

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionname() {
        return optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
    }

    public Long getSpecid() {
        return specid;
    }

    public void setSpecid(Long specid) {
        this.specid = specid;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}
