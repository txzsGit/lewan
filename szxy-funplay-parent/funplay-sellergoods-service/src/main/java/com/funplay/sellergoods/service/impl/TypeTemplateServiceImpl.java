package com.funplay.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.funplay.mapper.SpecificationMapper;
import com.funplay.mapper.SpecificationOptionMapper;
import com.funplay.mapper.TypeTemplateMapper;
import com.funplay.pojo.SpecificationOption;
import com.funplay.pojo.TypeTemplate;
import com.funplay.sellergoods.service.TypeTemplateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 条件查询+分页
     * @param typeTemplate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TypeTemplate> pageList = (Page<TypeTemplate>) typeTemplateMapper.findPage(typeTemplate);
        saveToRedis();//存入数据到缓存
        return new PageResult(pageList.getTotal(), pageList.getResult());
    }

    /**
     * 根据id查询模板实体
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.delete(id);
        }
    }

    /**
     * 添加模板
     * @param typeTemplate
     */
    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.update(typeTemplate);
    }


    /**
     * 将数据存入缓存
     */
    private void saveToRedis(){
        //获取模板数据
        List<TypeTemplate> typeTemplateList = findAll();
        //循环模板
        for(TypeTemplate typeTemplate :typeTemplateList){
            //存储品牌列表
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandids(),
                    Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(),
                    brandList);
            //存储规格列表
            List<Map> specList = findSpeciList(typeTemplate.getId());//根据模板 ID 查询规格列表
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(),specList);
        }
    }

    @Override
    public List<Map> findSpeciList(Long id) {
        //查询模板
        TypeTemplate typeTemplate = typeTemplateMapper.findOne(id);
        String specIds = typeTemplate.getSpecids();
        List<Map> list = JSON.parseArray(specIds, Map.class);//将json字符串形式转成json对象
        for (Map map : list) {
            List<SpecificationOption> option = specificationOptionMapper.findBySpecId(new Long((Integer)map.get("id")));//先将取得的specId转换成Integer类型 在装成Long
            map.put("option",option);
        }
        return list;
    }

    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.findAll();
    }
}
