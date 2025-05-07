package com.encounter.service.impl;

import com.encounter.bean.BsBookSort;
import com.encounter.bean.BsBookSortExample;
import com.encounter.mapper.BsBookSortMapper;
import com.encounter.service.BookSortService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookSortServiceImpl implements BookSortService
    {
        @Resource
        BsBookSortMapper bookSortMapper;
        
        @Override
        public Integer insertBookSort(String sortName)
            {
                BsBookSort bsBookSort = new BsBookSort();
                bsBookSort.setSortName(sortName);
                return bookSortMapper.insertSelective(bsBookSort);
            }
        
        @Override
        public Integer delBookSort(Integer sid)
            {
                return bookSortMapper.deleteByPrimaryKey(sid);
            }
        
        @Override
        public Integer updateBookSortById(Integer sid, String sortName)
            {
                BsBookSort bsBookSort = new BsBookSort();
                bsBookSort.setId(sid);
                bsBookSort.setSortName(sortName);
                return bookSortMapper.updateByPrimaryKeySelective(bsBookSort);
            }
        
        @Override
        public PageInfo<BsBookSort> selectBookSortByPage(Integer page, Integer limit)
            {
                PageHelper.startPage(page, limit);
                BsBookSortExample ex = new BsBookSortExample();
                ex.setOrderByClause("id desc");
                
                List<BsBookSort> bsBookSorts = bookSortMapper.selectByExample(ex);
                PageInfo<BsBookSort> bsBookSortPageInfo = new PageInfo<>(bsBookSorts);
                return bsBookSortPageInfo;
            }
        
        @Override
        public List<BsBookSort> getSortOption()
            {
                return bookSortMapper.selectByExample(null);
            }
        
        @Override
        public BsBookSort selectBookSortById(Integer sortId)
            {
                return bookSortMapper.selectByPrimaryKey(sortId);
            }
    }
