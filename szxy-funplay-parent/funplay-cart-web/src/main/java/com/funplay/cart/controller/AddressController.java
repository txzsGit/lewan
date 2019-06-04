package com.funplay.cart.controller;


import com.funplay.pojo.Address;
import com.funplay.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;

import java.util.Date;
import java.util.List;

/**
 * controller
 * @author txzs
 *
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<Address> findAll(){
        return addressService.findAll();
    }

    /**
     * 增加
     * @param address
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Address address){
        try {
            //将用户id设置进去
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            address.setUserid(name);
            address.setCreatedate(new Date());//添加时间
            addressService.add(address);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param address
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Address address){
        try {
            addressService.update(address);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Address findOne(Long id){
        return addressService.findOne(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long id){
        try {
            addressService.delete(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/findListByLoginUser")
    public List<Address> findListByLoginUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Address> addressList = addressService.findListByUserId(name);
        return  addressList;
    }
}
