package com.encounter.service;

import com.encounter.bean.BsBookAddress;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface BookAddressService
    {
        Integer insertBookAddress(String addressName);
        
        Integer delBookAddress(Integer aid);
        
        Integer updateBookAddressById(Integer sid, String addressName);
        
        PageInfo<BsBookAddress> selectBookAddressByPage(Integer page, Integer limit);
        
        List<BsBookAddress> getAddressOption();
        
        BsBookAddress selectBookAddressById(Integer addressId);
    }
