package com.funplay.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.funplay.pojo.Brand;
import com.funplay.pojo.Item;
import com.funplay.pojo.Specification;
import com.funplay.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(timeout = 50000)
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map search(Map searchMap) {
        //去空格处理
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords",keywords.replace(" ",""));
        Map map = new HashMap();
        map.putAll(searchList(searchMap));//高亮显示
        //分组查询，查询出分类信息显示在前端页面
        map.put("categoryList",searchCategoryList(searchMap));
        //查询出规格和品牌表,如果没有分组条件就默认是第一个
        if(!"".equals(searchMap.get("category"))){
            map.putAll(searchBrandAndSpecList((String) searchMap.get("category")));
        }else{
            if(searchCategoryList(searchMap).size()>0) {
                map.putAll(searchBrandAndSpecList((String)searchCategoryList(searchMap).get(0)));
            }

        }
        return map;
    }


    /**
     * 高亮显示
     * @param searchMap
     * @return
     */
    private Map searchList(Map searchMap){
        Map map = new HashMap();
        //设置高亮显示
        HighlightQuery query = new SimpleHighlightQuery();
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");//设置高亮显示域
        highlightOptions.setSimplePrefix("<em style='color:red'>");//设置高亮前缀
        highlightOptions.setSimplePostfix("</em>");//设置高亮后缀
        query.setHighlightOptions(highlightOptions);//将高亮设置加入查询条件

        //1.1关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //1.2分类查询
        if(!"".equals(searchMap.get("category"))){
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            FilterQuery filterQuery = new SimpleFacetQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.3品牌查询
        if(!"".equals(searchMap.get("brand"))){
            Criteria filterCriteria=new Criteria("item_brand").is(searchMap.get("brand"));
            FilterQuery filterQuery=new SimpleFacetQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.4规格查询
        if(searchMap.get("spec")!=null){
            Map<String,String> specMap=(Map)searchMap.get("spec");
            for (String key : specMap.keySet()) {
                Criteria filterCriteria=new Criteria("item_spec_"+key).is(specMap.get(key));
                FilterQuery filterQuery=new SimpleFacetQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.5价格查询
        String price= (String) searchMap.get("price");
        if(!"".equals(price)){
            String[] priceList = price.split("-");
            Criteria filteCriteria=new Criteria("item_price");
            if(!"0".equals(priceList[0])){
                filteCriteria.greaterThanEqual(priceList[0]);
                FilterQuery filteQuery=new SimpleFilterQuery(filteCriteria);
                query.addFilterQuery(filteQuery);
            }
            if(!"*".equals(priceList[1])){
                filteCriteria.lessThanEqual(priceList[1]);
                FilterQuery filteQuery=new SimpleFilterQuery(filteCriteria);
                query.addFilterQuery(filteQuery);
            }
        }

        //1.6分页查询
        Integer pageNo= (Integer) searchMap.get("pageNo");
        if(pageNo==null){
            pageNo=1;
        }
        Integer pageSize= (Integer) searchMap.get("pageSize");
        if(pageSize==null){
            pageSize=20;
        }
        //开始索引
        query.setOffset(pageSize*(pageNo-1));
        //设置每页条数
        query.setRows(pageSize);


        //1.7排序
        String sortFiled = (String) searchMap.get("sortFiled");
        String sortValue = (String) searchMap.get("sortValue");
        if(sortFiled!=null&&!"".equals(sortFiled)){
            if("ASC".equals(sortValue)){//升序
                Sort sort= new Sort(Sort.Direction.ASC,"item_"+sortFiled);
                query.addSort(sort);
            }
            if("DESC".equals(sortValue)){//降序
                Sort sort= new Sort(Sort.Direction.DESC,"item_"+sortFiled);
                query.addSort(sort);
            }
        }

        //*********************************************************************************
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(query, Item.class);
        List<HighlightEntry<Item>> entryList = page.getHighlighted();//高亮入口集合
        for (HighlightEntry<Item> entry : entryList) {
            Item item = entry.getEntity();//获取原来对象
            if(entry.getHighlights().size()>0&&entry.getHighlights().get(0).getSnipplets().size()>0) {
                item.setTitle(entry.getHighlights().get(0).getSnipplets().get(0));//将高亮显示的内容设置回去
            }
        }
        map.put("rows", page.getContent());
        map.put("totalPages",page.getTotalPages());//设置总页数
        map.put("total",page.getTotalElements());//设置总记录数
        return map;
    }


    /**
     * 分组查询
     * @param searchMap
     * @return
     */
    private List  searchCategoryList(Map searchMap){
        List list=new ArrayList();
        Query query=new SimpleQuery("*:*");
        //设置分组查询
        GroupOptions groupOptions=new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        //设置查询条件
        Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //得到分组页
        GroupPage<Item> page = solrTemplate.queryForGroupPage(query, Item.class);
        //得到分组结果集
        GroupResult<Item> groupResult = page.getGroupResult("item_category");
        Page<GroupEntry<Item>> groupEntries = groupResult.getGroupEntries();//得到分组结果入口页
        List<GroupEntry<Item>> content = groupEntries.getContent();//得到分组入口结合
        for (GroupEntry<Item> entry : content) {
            list.add(entry.getGroupValue());//将分组结果的名称封装到返回值中
        }
        return list;
    }
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 通过分类名称查询品牌表和规格表
     * @param category 分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category){
        Map map=new HashMap();
        Long typeId  = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        //通过缓存获取品牌表和规格表
        if (typeId!=null) {
            List<Brand> brandList = (List<Brand>) redisTemplate.boundHashOps("brandList").get(typeId);
            map.put("brandList", brandList);
            List<Specification> specList = (List<Specification>) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList", specList);
        }
        return map;
    }
    @Override
    public void importList(List list) {
        System.out.println("===商品列表===");
        List<Item> itemList= (List<Item>) list;
        for(Item item:itemList){
            Map specMap= JSON.parseObject(item.getSpec());//将 spec 字段中的 json 字符串转换为 map
            item.setSpecMap(specMap);//给带注解的字段赋值
            System.out.println(item.getTitle());
        }
        solrTemplate.saveBeans(itemList);//千万不能写成saveBean
        solrTemplate.commit();
        System.out.println("===结束===");
    }

    @Override
    public void deleteByGoodsIds(List goodsIds) {
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("item_goodsid").in(goodsIds);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
