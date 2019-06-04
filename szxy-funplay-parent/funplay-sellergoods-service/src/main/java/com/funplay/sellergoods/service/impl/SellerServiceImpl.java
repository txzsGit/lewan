package com.funplay.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.SellerMapper;
import com.funplay.pojo.Seller;
import com.funplay.sellergoods.service.SellerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerMapper sellerMapper;
    @Override
    public void add(Seller seller) {
        seller.setStatus("0");//入驻申请初始化状态为未审核
        seller.setCreatetime(new Date());//申请时间
        sellerMapper.add(seller);
    }

    @Override
    public PageResult findPage(Seller seller, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Seller> pageList= (Page<Seller>) sellerMapper.findPage(seller);
        return  new PageResult(pageList.getTotal(), pageList.getResult());
    }

    @Override
    public Seller findOne(String id) {
        return sellerMapper.findOne(id);
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = sellerMapper.findOne(sellerId);
        seller.setStatus(status);
        sellerMapper.updateStatus(seller);
    }

    //修改个人资料
    @Override
    public void update(Seller seller) {
        sellerMapper.update(seller);
    }
    //记录上次登陆时间
    @Override
    public void updateTime(Seller seller) {
        sellerMapper.updateTime(seller);
    }
}
