package com.funplay.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.ItemCatMapper;
import com.funplay.pojo.ItemCat;
import com.funplay.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *根据上级 ID 查询列表
     * @param id
     * @return
     */
    @Override
    public List<ItemCat> findByParentId(Long id) {
        //每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
        List<ItemCat> list = findAll();
        for(ItemCat itemCat:list){
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),
                    itemCat.getTypeid());
        }
        System.out.println("更新缓存:商品分类表");
        return itemCatMapper.findByParentId(id);
    }

    @Override
    public void add(ItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }

    @Override
    public void update(ItemCat itemCat) {
        itemCatMapper.update(itemCat);
    }

    @Override
    public ItemCat findOne(Long id) {
         return itemCatMapper.findOne(id);
    }

    /**
     * 通过parentId查找子分类
     * @param parentid
     * @return
     */
    @Override
    public int findCountByParentId(Long parentid) {
        return 	itemCatMapper.findCountByParentId(parentid);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            if(findCountByParentId(id)==0){//如果没有下一集分类才进行删除
                itemCatMapper.delete(id);
            }
        }
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<ItemCat> findAll() {
        return itemCatMapper.findAll();
    }


}
