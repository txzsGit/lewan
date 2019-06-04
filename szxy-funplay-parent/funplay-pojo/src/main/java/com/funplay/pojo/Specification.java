package com.funplay.pojo;

import java.io.Serializable;

/**
 * 规格实体类
 * @author txzs
 */
public class Specification implements Serializable{
    private Long id;//规格id

    private String specname;//规格名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname;
    }
}
