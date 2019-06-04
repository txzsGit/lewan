package com.funplay.user.service;

import com.funplay.pojo.Address;
import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AddressService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Address> findAll();

	/**
	 * 增加
	 */
	public void add(Address address);


	/**
	 * 修改
	 */
	public void update(Address address);


	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Address findOne(Long id);


	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long ids);


	/**
	 *根据用户查询地址
	 * @param userId
	 * @return
	 */
	public List<Address> findListByUserId(String userId);

}
