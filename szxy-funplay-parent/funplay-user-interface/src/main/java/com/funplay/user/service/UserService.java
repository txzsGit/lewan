package com.funplay.user.service;

import com.funplay.pojo.User;
import entity.PageResult;
import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 增加
	*/
	public void add(User user);
	
	
	/**
	 * 修改
	 */
	public void update(User user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public User findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(User user, int pageNum, int pageSize);

	/**
	 * 生成短信验证码
	 * @param phone
	 */
	public void createSmsCode(String phone);

	/**
	 * 检查验证码是否一致
	 * @param phone
	 * @param code
	 * @return
	 */
	public boolean  checkSmsCode(String phone, String code);

	}
