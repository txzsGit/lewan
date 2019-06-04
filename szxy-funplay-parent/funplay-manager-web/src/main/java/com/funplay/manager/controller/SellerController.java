package com.funplay.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.Seller;
import com.funplay.sellergoods.service.SellerService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    /**
     * 查询+分页
     *
     * @param seller
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Seller seller, int page, int rows) {
        return sellerService.findPage(seller, page, rows);
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Seller findOne(String id) {
        return sellerService.findOne(id);
    }

    /**
     * 审核
     * @param sellerid
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerid, String status) {
        try {
            sellerService.updateStatus(sellerid, status);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
}
