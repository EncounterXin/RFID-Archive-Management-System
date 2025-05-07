package com.encounter.service.impl;

import com.encounter.bean.BsBookAddress;
import com.encounter.mapper.BsBookAddressMapper;
import com.encounter.service.BookAddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookAddressServiceImpl implements BookAddressService
    {
        @Resource
        BsBookAddressMapper bookAddressMapper;
        
        @Override
        public Integer insertBookAddress(String sortName)
            {
                BsBookAddress bsBookAddress = new BsBookAddress();
                bsBookAddress.setAddressName(sortName);
                return bookAddressMapper.insertSelective(bsBookAddress);
            }
        
        @Override
        public Integer delBookAddress(Integer sid)
            {
                return bookAddressMapper.deleteByPrimaryKey(sid);
            }
        
        @Override
        public Integer updateBookAddressById(Integer sid, String sortName)
            {
                BsBookAddress bsBookAddress = new BsBookAddress();
                bsBookAddress.setId(sid);
                bsBookAddress.setAddressName(sortName);
                return bookAddressMapper.updateByPrimaryKeySelective(bsBookAddress);
            }
        
        @Override
        public PageInfo<BsBookAddress> selectBookAddressByPage(Integer page, Integer limit)
            {
                PageHelper.startPage(page, limit);
                List<BsBookAddress> bsBookSorts = bookAddressMapper.selectByExample(null);
                PageInfo<BsBookAddress> bsBookSortPageInfo = new PageInfo<>(bsBookSorts);
                return bsBookSortPageInfo;
            }
        
        @Override
        public List<BsBookAddress> getAddressOption()
            {
                return bookAddressMapper.selectByExample(null);
            }
        
        @Override
        public BsBookAddress selectBookAddressById(Integer addressId)
            {
                return bookAddressMapper.selectByPrimaryKey(addressId);
            }
    }
