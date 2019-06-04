package com.funplay.solrutil;

import com.alibaba.fastjson.JSON;
import com.funplay.mapper.ItemMapper;
import com.funplay.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 导入商品数据
     */
    public void importItemData(){
        List<Item> itemList = itemMapper.findByStauts("1");
        System.out.println("===商品列表===");
        for(Item item:itemList){
            Map specMap= JSON.parseObject(item.getSpec());//将 spec 字段中的 json 字符串转换为 map
            item.setSpecMap(specMap);//给带注解的字段赋值
            System.out.println(item.getTitle());
        }
        solrTemplate.saveBeans(itemList);//千万不能写成saveBean
        solrTemplate.commit();
        System.out.println("===结束===");
    }

    public static void main(String[] args) {
        ApplicationContext ac=
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");//获取ioc容器
        SolrUtil solrUtil = (SolrUtil) ac.getBean("solrUtil");
        solrUtil.importItemData();
    }

}
