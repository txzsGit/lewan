package com.funplay.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.content.service.ContentCategoryService;
import com.funplay.mapper.ContentCategoryMapper;
import com.funplay.mapper.ContentMapper;
import com.funplay.pojo.ContentCategory;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 增加
     */
    @Override
    public void add(ContentCategory contentCategory) {
        contentCategoryMapper.insert(contentCategory);
    }


    /**
     * 修改
     */
    @Override
    public void update(ContentCategory contentCategory){
        contentCategoryMapper.update(contentCategory);
        //广告分类修改,缓存清除再生成
        redisTemplate.boundHashOps("content").delete(contentCategory.getId());
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public ContentCategory findOne(Long id){
        return contentCategoryMapper.findOne(id);
    }

    @Autowired
    private ContentMapper contentMapper;
    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            //广告分类修改,缓存清除再生成
            redisTemplate.boundHashOps("content").delete(id);
            contentCategoryMapper.delete(id);
            //对应的广告也必须清掉 防止有冗余数据
            contentMapper.deleteByContentCategoryId(id);


        }
    }

    @Override
    public List<ContentCategory> findAll() {
        return contentCategoryMapper.findAll();
    }


    @Override
    public PageResult findPage(ContentCategory contentCategory, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<ContentCategory> page= (Page<ContentCategory>)contentCategoryMapper.findPage(contentCategory);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
