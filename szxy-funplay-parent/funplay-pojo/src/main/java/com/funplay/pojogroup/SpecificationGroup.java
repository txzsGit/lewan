package com.funplay.pojogroup;

import com.funplay.pojo.Specification;
import com.funplay.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;
/**
 * 规格和规格选项组合实体类
 * @author txzs
 */
public class SpecificationGroup implements Serializable {
    //从前端传来的规格名称
    private Specification specification;

    //从前端传来的规格选项数组
    private List<SpecificationOption> specificationOptionList;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
