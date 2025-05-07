package com.encounter.controller;

import com.alibaba.druid.util.StringUtils;
import com.encounter.bean.BsBookAddress;
import com.encounter.bean.BsBookInfo;
import com.encounter.bean.BsBookSort;
import com.encounter.bean.ResultBean;
import com.encounter.service.BookAddressService;
import com.encounter.service.BookInfoService;
import com.encounter.service.BookSortService;
import com.encounter.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Book Info 控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
@RequestMapping("/auth/book/info")
public class BookInfoController
    {
        
        @Resource
        BookInfoService bookInfoService;
        @Resource
        BookSortService bookSortService;
        @Resource
        BookAddressService bookAddressService;
        
        @Resource
        UserService userService;
        
        /**
         * 添加书籍信息
         *
         * @param bookName        书籍名称
         * @param sortId          排序 ID
         * @param rfidId          RFID 身份证
         * @param addressId       地址 ID
         * @param author          作者
         * @param press           压
         * @param publicationDate 发布日期
         * @param imageUrl        图片 URL
         * @param bookCount       图书计数
         * @param isbn            国际标准书号
         * @return {@link ResultBean }
         */
        @RequestMapping("addBookInfo")
        public ResultBean addBookInfo(String bookName, Integer sortId, String rfidId, Integer addressId,
                                      String author, String press, String publicationDate, String imageUrl, String bookCount, String isbn)
            {
                Integer count = 0;
                try
                    {
                        count = Integer.parseInt(bookCount);
                    }
                catch (NumberFormatException e)
                    {
                        return ResultBean.returnError("图书数量必输且为数字");
                    }
                if (count < 0)
                    {
                        return ResultBean.returnError("图书数量不能小于0");
                    }
                
                
                if (StringUtils.isEmpty(imageUrl))
                    {
                        return ResultBean.returnError("图书封面图片必输");
                    }
                if (StringUtils.isEmpty(rfidId))
                    {
                        return ResultBean.returnError("请获取RFID");
                    }
                
                if (sortId == null || addressId == null)
                    {
                        return ResultBean.returnError("请将信息输入完整");
                    }
                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = new Date();
                try
                    {
                        parse = format.parse(publicationDate);
                    }
                catch (ParseException e)
                    {
                        e.printStackTrace();
                        return ResultBean.returnError("时间转换失败");
                    }
                
                BsBookInfo bsBookInfoRfid = bookInfoService.getBookByRfid(rfidId);
                if (bsBookInfoRfid != null)
                    {
                        return ResultBean.returnError("该RFID信息已经存在");
                    }
                
                BsBookInfo bsBookInfo = new BsBookInfo();
                bsBookInfo.setBookName(bookName);
                bsBookInfo.setSortId(sortId);
                bsBookInfo.setAddressId(addressId);
                bsBookInfo.setRfidId(rfidId);
                bsBookInfo.setSelectNumber(0);
                bsBookInfo.setAuthor(author);
                bsBookInfo.setPublicationDate(parse);
                bsBookInfo.setPress(press);
                bsBookInfo.setPhotoUrl(imageUrl);
                bsBookInfo.setCreateTime(new Date());
                bsBookInfo.setIsbn(isbn);
                bsBookInfo.setRaters("5");
                bsBookInfo.setRatersNumber(1);
                if (count == 0)
                    {
                        bsBookInfo.setBookStatus("不可借");
                    }
                else
                    {
                        bsBookInfo.setBookStatus("可借");
                    }
                
                bsBookInfo.setBookCount(bookCount);
                Integer line = bookInfoService.insertBookInfo(bsBookInfo);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 按 ID 更新所有书籍信息
         *
         * @param bid             出价
         * @param bookName        书籍名称
         * @param sortId          排序 ID
         * @param rfidId          RFID 身份证
         * @param addressId       地址 ID
         * @param author          作者
         * @param press           压
         * @param publicationDate 发布日期
         * @param imageUrl        图片 URL
         * @param bookCount       图书计数
         * @return {@link ResultBean }
         */
        @RequestMapping("updateAllBookInfoById")
        public ResultBean updateAllBookInfoById(Integer bid, String bookName, Integer sortId, String rfidId, Integer addressId,
                                                String author, String press, String publicationDate, String imageUrl, String bookCount)
            {
                Integer count = 0;
                try
                    {
                        count = Integer.parseInt(bookCount);
                    }
                catch (NumberFormatException e)
                    {
                        return ResultBean.returnError("图书数量必输且为数字");
                    }
                if (count < 0)
                    {
                        return ResultBean.returnError("图书数量不能小于0");
                    }
                
                
                if (StringUtils.isEmpty(imageUrl))
                    {
                        return ResultBean.returnError("图书封面图片必输");
                    }
                if (StringUtils.isEmpty(rfidId))
                    {
                        return ResultBean.returnError("请获取RFID");
                    }
                
                if (sortId == null || addressId == null)
                    {
                        return ResultBean.returnError("请将信息输入完整");
                    }
                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = new Date();
                try
                    {
                        parse = format.parse(publicationDate);
                    }
                catch (ParseException e)
                    {
                        e.printStackTrace();
                        return ResultBean.returnError("时间转换失败");
                    }
                
                
                BsBookInfo bsBookInfo = new BsBookInfo();
                bsBookInfo.setId(bid);
                bsBookInfo.setBookName(bookName);
                bsBookInfo.setSortId(sortId);
                bsBookInfo.setAddressId(addressId);
                bsBookInfo.setRfidId(rfidId);
                bsBookInfo.setAuthor(author);
                bsBookInfo.setPublicationDate(parse);
                bsBookInfo.setPress(press);
                bsBookInfo.setPhotoUrl(imageUrl);
                if (count == 0)
                    {
                        bsBookInfo.setBookStatus("不可借");
                    }
                else
                    {
                        bsBookInfo.setBookStatus("可借");
                    }
                
                bsBookInfo.setBookCount(bookCount);
                Integer line = bookInfoService.updateAllBookInfoById(bsBookInfo);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 按 ID 获取书籍信息
         *
         * @param bid 出价
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookInfoById")
        public ResultBean getBookInfoById(Integer bid)
            {
                
                BsBookInfo bookById = bookInfoService.getBookById(bid);
                BsBookSort bsBookSort = bookSortService.selectBookSortById(bookById.getSortId());
                BsBookAddress bookAddress = bookAddressService.selectBookAddressById(bookById.getAddressId());
                bookById.setSortName(bsBookSort.getSortName());
                bookById.setAddressName(bookAddress.getAddressName());
                return ResultBean.returnOk().pushData("data", bookById);
                
            }
        
        /**
         * 删除图书信息
         *
         * @param aid 援助
         * @return {@link ResultBean }
         */
        @RequestMapping("delBookInfoById")
        public ResultBean delBookInfoById(Integer aid)
            {
                
                Integer line = bookInfoService.delBookInfo(aid);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 更新图书信息  分类id以及地址id
         *
         * @param sid       SID
         * @param sortId    排序 ID
         * @param addressId 地址 ID
         * @return {@link ResultBean }
         */
        @RequestMapping("updateBookInfoById")
        public ResultBean updateBookInfoById(Integer sid, Integer sortId, Integer addressId)
            {
                
                if (sortId == null || addressId == null)
                    {
                        return ResultBean.returnError();
                    }
                
                Integer line = bookInfoService.updateBookInfoById(sid, sortId, addressId);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 按 ID 更新书籍信息
         *
         * @param sid       SID
         * @param addressId 地址 ID
         * @return {@link ResultBean }
         */
        @RequestMapping("updateBookAddresssInfoById")
        public ResultBean updateBookInfoById(Integer sid, Integer addressId)
            {
                
                if (addressId == null)
                    {
                        return ResultBean.returnError();
                    }
                
                Integer line = bookInfoService.updateBookInfoById(sid, addressId);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 查询图书信息 根据借阅信息倒序
         *
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookInfoByPage")
        public ResultBean selectBookInfoByPage(Integer page, Integer limit)
            {
                
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookInfo> pageInfo = bookInfoService.selectBookInfoByPage(page, limit);
                
                List<BsBookAddress> addressOption = bookAddressService.getAddressOption();
                List<BsBookSort> sortOption = bookSortService.getSortOption();
                
                long total = pageInfo.getTotal();
                List<BsBookInfo> list = pageInfo.getList();
                List<BsBookInfo> retuenList = new ArrayList<>();
                
                for (BsBookInfo bsBookInfo : list)
                    {
                        bsBookInfo.setAddressName(getAddressName(addressOption, bsBookInfo.getAddressId()));
                        bsBookInfo.setSortName(getSortName(sortOption, bsBookInfo.getSortId()));
                        retuenList.add(bsBookInfo);
                    }
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", retuenList);
            }
        
        /**
         * 查询图书信息 根据上架时间倒序
         *
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookInfoCreatList")
        public ResultBean getBookInfoCreatList(Integer page, Integer limit)
            {
                
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookInfo> pageInfo = bookInfoService.getBookInfoCreatList(page, limit);
                
                List<BsBookAddress> addressOption = bookAddressService.getAddressOption();
                List<BsBookSort> sortOption = bookSortService.getSortOption();
                
                long total = pageInfo.getTotal();
                List<BsBookInfo> list = pageInfo.getList();
                List<BsBookInfo> retuenList = new ArrayList<>();
                
                for (BsBookInfo bsBookInfo : list)
                    {
                        bsBookInfo.setAddressName(getAddressName(addressOption, bsBookInfo.getAddressId()));
                        bsBookInfo.setSortName(getSortName(sortOption, bsBookInfo.getSortId()));
                        retuenList.add(bsBookInfo);
                    }
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", retuenList);
            }
        
        /**
         * 按页选择书籍信息条件
         *
         * @param bookName 书籍名称
         * @param author   作者
         * @param press    压
         * @param page     页
         * @param limit    限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookInfoConditionByPage")
        public ResultBean selectBookInfoConditionByPage(String bookName, String author, String press, Integer page, Integer limit)
            {
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookInfo> pageInfo = bookInfoService.selectBookInfoConditionByPage(bookName, author, press, page, limit);
                
                List<BsBookAddress> addressOption = bookAddressService.getAddressOption();
                List<BsBookSort> sortOption = bookSortService.getSortOption();
                
                long total = pageInfo.getTotal();
                List<BsBookInfo> list = pageInfo.getList();
                List<BsBookInfo> retuenList = new ArrayList<>();
                
                for (BsBookInfo bsBookInfo : list)
                    {
                        bsBookInfo.setAddressName(getAddressName(addressOption, bsBookInfo.getAddressId()));
                        bsBookInfo.setSortName(getSortName(sortOption, bsBookInfo.getSortId()));
                        retuenList.add(bsBookInfo);
                    }
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", retuenList);
            }
        
        /**
         * 查询图书信息 根据图书id
         *
         * @param id 身份证
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookInfoById")
        public ResultBean selectBookInfoById(Integer id)
            {
                
                BsBookInfo bsBookInfo = bookInfoService.selectBookInfoById(id);
                
                return ResultBean.returnOk().pushData("date", bsBookInfo);
            }
        
        /**
         * 按书名选择图书信息
         *
         * @param bookName 书籍名称
         * @param page     页
         * @param limit    限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookInfoByBookName")
        public ResultBean selectBookInfoByBookName(String bookName, Integer page, Integer limit)
            {
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookInfo> pageInfo = bookInfoService.selectBookInfoByBookName(bookName, page, limit);
                
                long total = pageInfo.getTotal();
                List<BsBookInfo> list = pageInfo.getList();
                return ResultBean.returnOk().pushData("total", total).pushData("list", list);
            }
        
        /**
         * 按图书分类 ID 选择图书信息
         *
         * @param sortId 排序 ID
         * @param page   页
         * @param limit  限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBookInfoByBookSortId")
        public ResultBean selectBookInfoByBookSortId(Integer sortId, Integer page, Integer limit)
            {
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                
                PageInfo<BsBookInfo> pageInfo = bookInfoService.selectBookInfoByBookSortId(sortId, page, limit);
                
                
                List<BsBookAddress> addressOption = bookAddressService.getAddressOption();
                List<BsBookSort> sortOption = bookSortService.getSortOption();
                
                long total = pageInfo.getTotal();
                List<BsBookInfo> list = pageInfo.getList();
                List<BsBookInfo> retuenList = new ArrayList<>();
                
                for (BsBookInfo bsBookInfo : list)
                    {
                        bsBookInfo.setAddressName(getAddressName(addressOption, bsBookInfo.getAddressId()));
                        bsBookInfo.setSortName(getSortName(sortOption, bsBookInfo.getSortId()));
                        retuenList.add(bsBookInfo);
                    }
                
                return ResultBean.returnOk().pushData("total", total).pushData("list", retuenList);
            }
        
        /**
         * 获取地址名称
         *
         * @param list 列表
         * @param key  钥匙
         * @return {@link String }
         */
        private String getAddressName(List<BsBookAddress> list, Integer key)
            {
                for (BsBookAddress bsBookAddress : list)
                    {
                        if (key.equals(bsBookAddress.getId()))
                            {
                                return bsBookAddress.getAddressName();
                            }
                    }
                
                return "获取失败";
            }
        
        /**
         * 获取排序名称
         *
         * @param list 列表
         * @param key  钥匙
         * @return {@link String }
         */
        private String getSortName(List<BsBookSort> list, Integer key)
            {
                for (BsBookSort bsBookSort : list)
                    {
                        if (key.equals(bsBookSort.getId()))
                            {
                                return bsBookSort.getSortName();
                            }
                    }
                
                return "获取失败";
            }
        
        /**
         * 按 RFID 获取图书信息
         *
         * @param rfid 无线射频识别
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookInfoByRfid")
        public ResultBean getBookInfoByRfid(String rfid)
            {
                BsBookInfo bookByRfid = bookInfoService.getBookByRfid(rfid);
                return ResultBean.returnOk().pushData("data", bookByRfid);
            }
        
        
    }
