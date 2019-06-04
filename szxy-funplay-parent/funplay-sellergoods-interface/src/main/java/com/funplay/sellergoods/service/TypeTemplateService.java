package com.funplay.sellergoods.service;


import com.funplay.pojo.TypeTemplate;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {

	PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize);

	TypeTemplate findOne(Long id);

	void delete(Long[] ids);

	void add(TypeTemplate typeTemplate);

	void update(TypeTemplate typeTemplate);

	List<Map> findSpeciList(Long id);

	List<TypeTemplate> findAll();

}
