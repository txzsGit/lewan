package com.funplay.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.SpecificationMapper;
import com.funplay.mapper.SpecificationOptionMapper;
import com.funplay.pojo.Specification;
import com.funplay.pojo.SpecificationOption;
import com.funplay.pojogroup.SpecificationGroup;
import com.funplay.sellergoods.service.SpecificationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询所有规格
     * @return
     */
    @Override
    public List<Specification> findAll() {
        return specificationMapper.findAll();
    }

    /**
     * 条件查询规格+分页
     * @param specification
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Specification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Specification> pageList=(Page<Specification>)specificationMapper.findPage(specification);
        return new PageResult(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 添加规格及详细信息
     * @param specificationGroup
     */
    @Override
    public void add(SpecificationGroup specificationGroup) {
        //添加规格名称
        specificationMapper.insert(specificationGroup.getSpecification());
        List<SpecificationOption> specificationOptionList = specificationGroup.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            specificationOption.setSpecid(specificationGroup.getSpecification().getId());//通过在mybatis的配置文件中进行相关配置，获取相关联的id
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 根据 ID 获取实体
     * @param id
     * @return
     */
    @Override
    public SpecificationGroup findOne(Long id) {
        //查询规格
        Specification specification = specificationMapper.findOne(id);
        //查询规格选项
        List<SpecificationOption> list=specificationOptionMapper.findBySpecId(id);
        SpecificationGroup specificationGroup = new SpecificationGroup();
        specificationGroup.setSpecification(specification);
        specificationGroup.setSpecificationOptionList(list);
        return specificationGroup;
    }

    /**
     * 更新
     * @param specificationGroup
     */
    @Override
    public void update(SpecificationGroup specificationGroup) {
        //保存修改的规格
        specificationMapper.update(specificationGroup.getSpecification());
        //删除原有的规格选项
        specificationOptionMapper.deleteBySpecId(specificationGroup.getSpecification().getId());
        //循环插入规格选项
        for (SpecificationOption specificationOption : specificationGroup.getSpecificationOptionList()) {
            specificationOption.setSpecid(specificationGroup.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 删除选中规格
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.delete(id);
            specificationOptionMapper.deleteBySpecId(id);
        }
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }


}
