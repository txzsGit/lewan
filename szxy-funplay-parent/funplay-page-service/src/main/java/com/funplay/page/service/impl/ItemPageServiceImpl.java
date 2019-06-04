package com.funplay.page.service.impl;

import com.funplay.mapper.GoodsDescMapper;
import com.funplay.mapper.GoodsMapper;
import com.funplay.mapper.ItemCatMapper;
import com.funplay.mapper.ItemMapper;
import com.funplay.page.service.ItemPageService;
import com.funplay.pojo.Goods;
import com.funplay.pojo.GoodsDesc;
import com.funplay.pojo.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Value("${pagedir}")
    private String pagedir;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public boolean genItemHtml(Long goodId) {
        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map dataMap=new HashMap();
            Goods goods = goodsMapper.findOne(goodId);
            dataMap.put("goods",goods);
            GoodsDesc goodsDesc = goodsDescMapper.findOne(goodId);
            dataMap.put("goodsDesc",goodsDesc);
            String category1 = itemCatMapper.findOne(goods.getCategory1id()).getName();
            String category2 = itemCatMapper.findOne(goods.getCategory2id()).getName();
            String category3 = itemCatMapper.findOne(goods.getCategory3id()).getName();
            dataMap.put("category1",category1);
            dataMap.put("category2",category2);
            dataMap.put("category3",category3);
            //读取sku列表
           //按照状态降序，保证第一个为默认
            List<Item> itemList = itemMapper.findByGoodsId(goodId);
            dataMap.put("itemList",itemList);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pagedir+goodId+".html"),"UTF-8"));
            template.process(dataMap,out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     * 删除生成的页面
     * @return
     */
    public boolean delItemHtml(Long[] ids){
        try{
            for (Long id : ids) {
                new File(pagedir+id+".html").delete();
            }
            return  true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
