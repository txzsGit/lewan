package com.funplay.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.content.service.ContentService;
import com.funplay.mapper.ContentMapper;
import com.funplay.pojo.Content;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page= (Page<Content>)contentMapper.findPage(content);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Content content) {
        contentMapper.insert(content);
        //清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryid());
    }

    @Override
    public void update(Content content) {
        //查询修改前的分类 Id
        Long categoryId = contentMapper.findOne(content.getId()).getCategoryid();
        redisTemplate.boundHashOps("content").delete(categoryId);
        contentMapper.update(content);
        //如果分类 ID 发生了修改,清除修改后的分类 ID 的缓存
        if(categoryId.longValue()!=content.getCategoryid().longValue()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryid());
        }
    }

    @Override
    public Content findOne(Long id) {
        return contentMapper.findOne(id);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            //清除缓存
            Long categoryId = contentMapper.findOne(id).getCategoryid();//广告分类 ID
            redisTemplate.boundHashOps("content").delete(categoryId);
            contentMapper.delete(id);
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 用于显示广告 默认状态为1开启，排序
     * @param categoryid
     * @return
     */
    @Override
    public List<Content> findByCategoryId(Long categoryid) {
        List<Content> contentList= (List<Content>)
                redisTemplate.boundHashOps("content").get(categoryid);
        if(contentList==null){
            System.out.println("从数据库读取数据放入缓存");
            //根据广告分类 ID 查询广告列表
            contentList = contentMapper.findByCategoryId(categoryid);
            redisTemplate.boundHashOps("content").put(categoryid, contentList);//存入缓存
        }else{
            System.out.println("从缓存读取数据");
        }
        return contentList;
    }
}
