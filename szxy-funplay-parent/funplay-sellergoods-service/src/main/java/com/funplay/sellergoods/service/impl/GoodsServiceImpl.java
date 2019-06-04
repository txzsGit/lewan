package com.funplay.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.funplay.mapper.*;
import com.funplay.pojo.*;
import com.funplay.pojogroup.GoodsGroup;
import com.funplay.sellergoods.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private ItemMapper itemMapper;
    //添加商品
    @Override
    public void add(GoodsGroup goodsGroup) {
        goodsGroup.getGoods().setAuditstatus("0");//设置未申请状态
        goodsMapper.insert(goodsGroup.getGoods());
        goodsGroup.getGoodsDesc().setGoodsid(goodsGroup.getGoods().getId());//设置 ID
        goodsDescMapper.insert(goodsGroup.getGoodsDesc());//插入商品扩展数据
        saveItemList(goodsGroup);

    }

    //抽取item中共用代码
    private void setItemValus(GoodsGroup goodsGroup,Item item){
        item.setGoodsid(goodsGroup.getGoods().getId());//商品 SPU 编号
        item.setSellerid(goodsGroup.getGoods().getSellerid());//商家编号
        item.setCategoryid(goodsGroup.getGoods().getCategory3id());//商品分类编号（3 级）
        item.setCreatetime(new Date());//创建日期
        item.setUpdatetime(new Date());//修改日期
        //品牌名称
        Brand tbBrand = brandMapper.findOne(goodsGroup.getGoods().getBrandid());
        item.setBrand(tbBrand.getName());
        //分类名称
        ItemCat itemCat = itemCatMapper.findOne(goodsGroup.getGoods().getCategory3id());
        item.setCategory(itemCat.getName());
        //商家名称
        Seller seller = sellerMapper.findOne(goodsGroup.getGoods().getSellerid());
        item.setSeller(seller.getNickname());
        //图片地址（取 spu 的第一个图片）
        String itemImages = goodsGroup.getGoodsDesc().getItemimages();
        List<Map> maps = JSON.parseArray(itemImages, Map.class);
        if(maps.size()>0) {
            String url = (String) maps.get(0).get("url");
            item.setImage(url);
        }
    }

    //抽取保存商品代码
    private void saveItemList(GoodsGroup goodsGroup){
        if("1".equals(goodsGroup.getGoods().getIsenablespec())) {//开启规格
            //添加规格列表
            for (Item item : goodsGroup.getItemList()) {
                //设置标题
                String title = goodsGroup.getGoods().getGoodsname();
                Map<String, Object> map = JSON.parseObject(item.getSpec());
                for (String key : map.keySet()) {
                    title += " " + map.get(key);
                }
                item.setTitle(title);
                setItemValus(goodsGroup,item);
                itemMapper.insert(item);
            }
        }else{
            Item item=new Item();
            item.setTitle(goodsGroup.getGoods().getGoodsname());//商品 KPU+规格描述串作为SKU 名称
            item.setPrice( goodsGroup.getGoods().getPrice() );//价格
            item.setStatus("1");//状态
            item.setIsdefault("1");//是否默认
            item.setNum(99999);//库存数量
            item.setSpec("{}");
            setItemValus(goodsGroup,item);
            itemMapper.insert(item);
        }
    }

    /**
     * 条件查询+分页
     * @param goods
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Goods> pageList = (Page<Goods>) goodsMapper.findPage(goods);
        return  new PageResult(pageList.getTotal(),pageList.getResult());
    }


    /**
     * 修改商品录入回显
     * @param id
     * @return
     */
    @Override
    public GoodsGroup findOne(Long id) {
        GoodsGroup goodsGroup = new GoodsGroup();
        goodsGroup.setGoods(goodsMapper.findOne(id));//查找对应的Goods信息
        goodsGroup.setGoodsDesc(goodsDescMapper.findOne(id));//查找对应的GoodsDesc信息
        //查找对应的规格ids
        List<Item> items = itemMapper.findByGoodsId(id);
        goodsGroup.setItemList(items);
        return goodsGroup;
    }

    /**
     * 更行
     * @param goodsGroup
     */
    @Override
    public void update(GoodsGroup goodsGroup) {
        //设置未申请状态:如果是经过修改的商品，需要重新设置状态并下架
        goodsGroup.getGoods().setAuditstatus("0");
        goodsGroup.getGoods().setIsmarketable("2");
        goodsMapper.update(goodsGroup.getGoods());//更新商品基础表
        goodsDescMapper.update(goodsGroup.getGoodsDesc());//更新商品详情表
        //删除sku表再创建
        itemMapper.deleteByGoodsId(goodsGroup.getGoods().getId());
        //添加新的sku数据
        saveItemList(goodsGroup);
    }

    /**
     * 批量审批
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            Goods goods = goodsMapper.findOne(id);
            goods.setAuditstatus(status);
            goodsMapper.updateStatus(goods);
        }
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Goods goods = goodsMapper.findOne(id);
            goods.setIsdelete("1");
            goodsMapper.updateStatus(goods);
        }
    }

    /**
     * 根据商品id查询item表信息 从spu的id获取item
     * @param ids
     * @param status
     * @return
     */
    @Override
    public List<Item> findItemListByGoodsIdandStatus(Long[] ids, String status) {
        return itemMapper.findItemListByGoodsIdandStatus(ids,status);
    }

    /**
     * 商品下架
     * @param ids
     * @param maketable
     */
    @Override
    public void isMarketable(Long[] ids, String maketable) {
        for (Long id : ids) {
            Goods goods = goodsMapper.findOne(id);
            if("1".equals(goods.getAuditstatus())){
                if("1".equals(maketable)){
                    //上架，将item的状态改为1，goods的isMaketable改为1
                    goods.setIsmarketable(maketable);
                    goodsMapper.update(goods);
                    List<Item> itemList = itemMapper.findByGoodsId(id);
                    if(itemList!=null&&itemList.size()>0){
                        for (Item item : itemList) {
                            item.setStatus("1");
                            itemMapper.updateStatus(item);
                        }
                    }
                }else{
                    //下架
                    goods.setIsmarketable(maketable);
                    goodsMapper.update(goods);
                    List<Item> itemList = itemMapper.findByGoodsId(id);
                    if(itemList!=null&&itemList.size()>0){
                        for (Item item : itemList) {
                            item.setStatus("0");
                            itemMapper.updateStatus(item);
                        }
                    }
                }
            }
        }
    }


}
