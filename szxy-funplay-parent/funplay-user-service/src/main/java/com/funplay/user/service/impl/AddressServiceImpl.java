package com.funplay.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funplay.mapper.AddressMapper;
import com.funplay.pojo.Address;
import com.funplay.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> findAll() {
        return addressMapper.findAll();
    }

    @Override
    public void add(Address address) {
            addressMapper.add(address);
    }

    @Override
    public void update(Address address) {
            addressMapper.update(address);
    }

    @Override
    public Address findOne(Long id) {
        return addressMapper.findOne(id);
    }

    @Override
    public void delete(Long id) {
            addressMapper.delete(id);
    }

    @Override
    public List<Address> findListByUserId(String userId) {
        return addressMapper.findListByUserId(userId);
    }
}
