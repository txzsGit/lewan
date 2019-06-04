package com.funplay.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.Seller;
import com.funplay.sellergoods.service.SellerService;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;

    @RequestMapping("/add")
    public Result add(@RequestBody Seller seller){
        //对密码进行BCrypt加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(seller.getPassword());
        seller.setPassword(encode);
        try {
            sellerService.add(seller);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Seller seller){
        String id= SecurityContextHolder.getContext().getAuthentication().getName();
        seller.setSellerid(id);
        seller.setStatus("0");
        try{
            sellerService.update(seller);
            return  new Result(true,"修改成功");
        }catch (Exception e){
            return  new Result(false,"修改失败");
        }
    }

    @RequestMapping("/findSeller")
    public Seller findSeller(){
        String id= SecurityContextHolder.getContext().getAuthentication().getName();
        return  sellerService.findOne(id);
    }

    //记录上次登陆时间
    @RequestMapping("/updateTime")
    public Result updateTime(){
        Seller seller=new Seller();
        String id= SecurityContextHolder.getContext().getAuthentication().getName();
        Date date = new Date();
        seller.setSellerid(id);
        seller.setLogintime(date);
        try{
            sellerService.updateTime(seller);
            return  new Result(true,"记录成功");
        }catch (Exception e){
            return new Result(true,"记录失败");
        }
    }
    //读取上次登陆时间
    @RequestMapping("/readTime")
    public String readTime(){
        String id= SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller = sellerService.findOne(id);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(seller.getLogintime()!=null){
          return   sf.format(seller.getLogintime());
        }else{
            return  "";
        }
    }

    /**
     * 修改密码
     * @param fpassword
     * @param spassword
     * @return
     */
    @RequestMapping("/updatePassword")
    public Result updatePassword(String fpassword,String spassword ){
        //对密码进行BCrypt加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String id= SecurityContextHolder.getContext().getAuthentication().getName();
        Seller seller = sellerService.findOne(id);
        try {
            if (fpassword.equals(spassword)) {
                seller.setPassword(bCryptPasswordEncoder.encode(fpassword));
                sellerService.update(seller);
            } else {
                throw new RuntimeException("两次密码不一致");
            }
            return new Result(true,"修改成功");
        }catch (Exception e){
            return  new Result(false,e.getMessage());
        }
    }
}
