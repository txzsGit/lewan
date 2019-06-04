package com.funplay.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.User;
import com.funplay.user.service.UserService;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.PhoneFormatCheckUtils;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/add")
    public Result add(@RequestBody User user, String sms){
       if(!userService.checkSmsCode(user.getPhone(),sms)) {
           return new Result(false, "验证码输入有误");
       }
       try{
           userService.add(user);
           return new Result(true,"注册成功");
       }catch (Exception e){
           e.printStackTrace();
           return  new Result(false,"注册失败");
       }
    }

    @RequestMapping("/sendCode")
    public Result sendCode(String phone){
        if(!PhoneFormatCheckUtils.isPhoneLegal(phone)){
            return  new Result(false,"手机格式不正确");
        }
        try{
            userService.createSmsCode(phone);
            return new Result(true,"验证码发送成功");
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"验证码发送失败");
        }
    }
}
