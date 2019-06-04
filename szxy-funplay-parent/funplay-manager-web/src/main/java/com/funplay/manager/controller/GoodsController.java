package com.funplay.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.Goods;
import com.funplay.pojogroup.GoodsGroup;
import com.funplay.search.service.SearchService;
import com.funplay.sellergoods.service.GoodsService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;
    @Reference
    private SearchService searchService;
    /**
     * 增加
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsGroup goodsGroup){
        //获取登录名
        String sellerId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        goodsGroup.getGoods().setSellerid(sellerId);//设置商家 ID
        try {
            goodsService.add(goodsGroup);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 查询+分页
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int rows  ){
        return goodsService.findPage(goods, page, rows);
    }

    /**
     * 获取实体,商品录入回显
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public GoodsGroup findOne(Long id){
        return goodsService.findOne(id);
    }

    /**
     * 修改
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsGroup goodsGroup) {
        //出于安全考虑，在商户后台执行的商品修改，必须要校验提交的商品属于该商户,将操作者的id和数据库中id以及前端传来的id作比较
        //先获取该商品数据库中的商家id
        GoodsGroup goodsGroup2 = goodsService.findOne(goodsGroup.getGoods().getId());
        //获取当前的用户id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (sellerId.equals(goodsGroup.getGoods().getSellerid()) && sellerId.equals(goodsGroup.getGoods().getSellerid())) {
            try {
                goodsService.update(goodsGroup);
                return new Result(true, "修改成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "修改失败");
            }
        }else{
            return  new Result(false,"非法操作");
        }
    }
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topicDeleteDestination;//发布订阅模式
    /**
     * 批量审批
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            goodsService.updateStatus(ids, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            searchService.deleteByGoodsIds(Arrays.asList(ids));//删除掉solr中的已删除的商品
            //删除静态页面
            jmsTemplate.send(topicDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }


}
