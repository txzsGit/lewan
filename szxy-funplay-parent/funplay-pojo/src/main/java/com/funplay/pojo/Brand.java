package com.funplay.pojo;

import java.io.Serializable;

/**
 * 品牌实体类
 * @author txzs
 */
public class Brand implements Serializable {
    private Long id;//主键d

    private String name;//品牌名称

    private String firstchar;//首字母

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

    public String getFirstchar() {
        return firstchar;
    }

    public void setFirstchar(String firstchar) {
        this.firstchar = firstchar;
    }
}
