package com.encounter.controller;

import com.encounter.bean.BsBookSort;
import com.encounter.bean.OptionVO;
import com.encounter.bean.ResultBean;
import com.encounter.service.BookSortService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 书籍排序控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
@RequestMapping("/auth/book/sort/")
public class BookSortController
    {
        
        @Resource
        BookSortService bookSortService;
        
        /**
         * 添加图书分类
         *
         * @param sortName 排序名称
         * @return {@link ResultBean }
         */
        @RequestMapping("addBookSort")
        public ResultBean addBookSort(String sortName)
            {
                
                Integer line = bookSortService.insertBookSort(sortName);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 删除图书分类
         *
         * @param sid SID
         * @return {@link ResultBean }
         */
        @RequestMapping("delBookSortById")
        public ResultBean delBookSortById(Integer sid)
            {
                
                Integer line = bookSortService.delBookSort(sid);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 更新图书分类
         *
         * @param sid      SID
         * @param sortName 排序名称
         * @return {@link ResultBean }
         */
        @RequestMapping("updateBookSortById")
        public ResultBean updateBookSortById(Integer sid, String sortName)
            {
                
                Integer line = bookSortService.updateBookSortById(sid, sortName);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 查询图书分类
         *
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookSortByPage")
        public ResultBean selectBookSortByPage(Integer page, Integer limit)
            {
                
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookSort> pageInfo = bookSortService.selectBookSortByPage(page, limit);
                
                long total = pageInfo.getTotal();
                List<BsBookSort> list = pageInfo.getList();
                return ResultBean.returnOk().pushData("total", total).pushData("list", list);
            }
        
        /**
         * 获取分类信息 用于下拉框
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("getSortOption")
        public ResultBean getSortOption()
            {
                List<BsBookSort> bsBookSorts = bookSortService.getSortOption();
                List<OptionVO> list = new ArrayList<>();
                for (BsBookSort bsBookSort : bsBookSorts)
                    {
                        OptionVO optionVO = new OptionVO();
                        optionVO.setLabel(bsBookSort.getSortName());
                        optionVO.setValue(bsBookSort.getId().toString());
                        list.add(optionVO);
                    }
                return ResultBean.returnOk().pushData("data", list);
            }
        
    }
