package com.funplay.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.order.service.OrderService;
import com.funplay.pojo.Order;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 * @author txzs
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService orderService;
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Order order){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setUserid(username);
		order.setSourcetype("2");//订单来源PC
		try {
			orderService.add(order);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

}
