package com.funplay.sellergoods.service;

import com.funplay.pojo.Seller;
import entity.PageResult;

import java.util.List;

public interface SellerService {

    public void add(Seller seller);

    public PageResult findPage(Seller seller, Integer pageNum, Integer pageSize);

    public Seller findOne(String id);

    public void updateStatus(String sellerId, String status);

    void update(Seller seller);

    void updateTime(Seller seller);
}
