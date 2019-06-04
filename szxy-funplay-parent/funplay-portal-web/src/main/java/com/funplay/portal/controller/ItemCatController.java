package com.funplay.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.ItemCat;
import com.funplay.sellergoods.service.ItemCatService;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;

    /**
     * 返回查询到的商品分类结果
     * @param parentid
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentid){
        return itemCatService.findByParentId(parentid);
    }

}
