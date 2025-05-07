package com.encounter.controller;

import com.encounter.bean.BsBookAddress;
import com.encounter.bean.OptionVO;
import com.encounter.bean.ResultBean;
import com.encounter.service.BookAddressService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Book 地址控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
@RequestMapping("/auth/book/address")
public class BookAddressController
    {
        
        @Resource
        BookAddressService bookAddressService;
        
        /**
         * 添加书籍地址
         *
         * @param addressName 地址名称
         * @return {@link ResultBean }
         */
        @RequestMapping("addBookAddress")
        public ResultBean addBookAddress(String addressName)
            {
                
                Integer line = bookAddressService.insertBookAddress(addressName);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 删除图书物理区域
         *
         * @param aid 援助
         * @return {@link ResultBean }
         */
        @RequestMapping("delBookAddressById")
        public ResultBean delBookAddressById(Integer aid)
            {
                
                Integer line = bookAddressService.delBookAddress(aid);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 更新图书物理区域
         *
         * @param sid         SID
         * @param addressName 地址名称
         * @return {@link ResultBean }
         */
        @RequestMapping("updateBookAddressById")
        public ResultBean updateBookAddressById(Integer sid, String addressName)
            {
                
                Integer line = bookAddressService.updateBookAddressById(sid, addressName);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 查询图书物理区域
         *
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookAddressByPage")
        public ResultBean selectBookAddressByPage(Integer page, Integer limit)
            {
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookAddress> pageInfo = bookAddressService.selectBookAddressByPage(page, limit);
                
                long total = pageInfo.getTotal();
                List<BsBookAddress> list = pageInfo.getList();
                return ResultBean.returnOk().pushData("total", total).pushData("list", list);
            }
        
        /**
         * 获取地址信息 用于下拉框
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("getAddressOption")
        public ResultBean getAddressOption()
            {
                List<BsBookAddress> bsBookAddressses = bookAddressService.getAddressOption();
                List<OptionVO> list = new ArrayList<>();
                for (BsBookAddress bookAddress : bsBookAddressses)
                    {
                        OptionVO optionVO = new OptionVO();
                        optionVO.setLabel(bookAddress.getAddressName());
                        optionVO.setValue(bookAddress.getId().toString());
                        list.add(optionVO);
                    }
                return ResultBean.returnOk().pushData("data", list);
            }
    }
