package com.funplay.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.funplay.mapper.UserMapper;
import com.funplay.pojo.User;
import com.funplay.user.service.UserService;
import entity.PageResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 增加
	 */
	@Override
	public void add(User user) {
		user.setCreated(new Date());//设置创建时间
		user.setUpdated(new Date());//设置更新时间
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));//密码加密
		userMapper.insert(user);
	}


	/**
	 * 修改
	 */
	@Override
	public void update(User user) {
		userMapper.update(user);
	}

	/**
	 * 根据ID获取实体
	 *
	 * @param id
	 * @return
	 */
	@Override
	public User findOne(Long id) {
		return userMapper.findOne(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			userMapper.delete(id);
		}
	}


	@Override
	public PageResult findPage(User user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<User> pageList= (Page<User>) userMapper.findPage(user);
		return new PageResult(pageList.getTotal(), pageList.getResult());
	}

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination smsDestination;
	@Value("${template_code}")
	private String template_code;
	@Value("${sign_name}")
	private String sign_name;

	/**
	 * 生成短信验证码
	 *
	 * @param phone
	 */

	@Override
	public void createSmsCode(final String phone) {
	final 	String smsCode = (long) (Math.random() * 1000000) + "";
		System.out.println("短信验证码" + smsCode);
		//存入缓存
		redisTemplate.boundHashOps("smsCode").put(phone, smsCode);
		//发送到activeMQ..
		jmsTemplate.send(smsDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("mobile", phone);
				mapMessage.setString("sign_name", sign_name);
				mapMessage.setString("template_code", template_code);
				Map map=new HashMap();
				map.put("code",smsCode);
				mapMessage.setString("param", JSON.toJSONString(map));
				return mapMessage;
			}
		});

	}

	@Override
	public boolean checkSmsCode(String phone, String code) {
		String smsCode = (String) redisTemplate.boundHashOps("smsCode").get(phone);
		if (code == null || "".equals(code)) {
			return false;//验证码为空
		}
		if (code.equals(smsCode)) {
			return true;
		} else {
			return false;
		}
	}

}
