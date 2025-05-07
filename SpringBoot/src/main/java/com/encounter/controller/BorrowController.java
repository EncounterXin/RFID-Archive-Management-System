package com.encounter.controller;

import com.encounter.bean.*;
import com.encounter.service.BookInfoService;
import com.encounter.service.BorrowService;
import com.encounter.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 借用控制器
 *
 * @author Encounter
 * @date 2025/05/07
 */
@RestController
@RequestMapping("/auth/book/borrow")
public class BorrowController
    {
        
        @Resource
        BookInfoService bookInfoService;
        
        @Resource
        UserService userService;
        
        @Resource
        BorrowService borrowService;
        
        /**
         * 借书
         *
         * @param uid     uid
         * @param rfidId  RFID 身份证
         * @param endTime 结束时间
         * @return {@link ResultBean }
         */
        @RequestMapping("borrowBook")
        public ResultBean borrowBook(Integer uid, String rfidId, String endTime)
            {
                
                if (StringUtils.isEmpty(rfidId))
                    {
                        return ResultBean.returnError("请获取RFID");
                    }
                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = new Date();
                try
                    {
                        parse = format.parse(endTime);
                    }
                catch (ParseException e)
                    {
                        e.printStackTrace();
                        return ResultBean.returnError("时间转换失败");
                    }
                BsBookInfo bsBookInfo = bookInfoService.getBookByRfid(rfidId);
                if (bsBookInfo == null)
                    {
                        return ResultBean.returnError("书籍信息不存在");
                    }
                
                //判断书籍数量是否足够
                if (Integer.parseInt(bsBookInfo.getBookCount()) <= 0)
                    {
                        return ResultBean.returnError("书籍数量不足，不可借");
                    }
                
                
                //获取用户信息
                BsUser bsUser = userService.selectUserByUid(uid);
                if (bsUser == null)
                    {
                        return ResultBean.returnError("用户信息不存在");
                    }
                
                BsBorrow bsBorrow = borrowService.selectBorrowByUidAndrfidID(uid, bsBookInfo.getId());
                if (bsBorrow != null && "0".equals(bsBorrow.getBoorrwStatus()))
                    {
                        return ResultBean.returnError("本书籍已经存在借阅信息，请勿重复借阅");
                    }
                
                //bs_borrow(借阅信息表)  id uid book_id end_time(归还时间) boorrw_status(借阅状态 0- 未归还 1-已归还)
                BsBorrow borrow = new BsBorrow();
                borrow.setBookId(bsBookInfo.getId());
                borrow.setUid(uid);
                borrow.setBoorrwStatus("0");
                borrow.setGetTime(new Date());
                borrow.setEndTime(parse);
                
                Integer line = borrowService.insertBorrowBook(borrow);
                if (line > 0)
                    {
                        //更新书籍被借的次数 书籍数量-1 若数量小于0则不可借
                        Integer selectNumber = bsBookInfo.getSelectNumber();
                        selectNumber++;
                        BsBookInfo bsBookInfo1 = new BsBookInfo();
                        bsBookInfo1.setId(bsBookInfo.getId());
                        bsBookInfo1.setSelectNumber(selectNumber);
                        Integer bookCount = Integer.parseInt(bsBookInfo.getBookCount());
                        bookCount--;
                        bsBookInfo1.setBookCount(bookCount + "");
                        if (bookCount <= 0)
                            {
                                bsBookInfo1.setBookStatus("不可借");
                            }
                        bookInfoService.updateBookInfo(bsBookInfo1);
                        //更新用户借阅次数
                        Integer id = bsUser.getId();
                        Integer bookCount1 = bsUser.getBookCount();
                        BsUser user = new BsUser();
                        user.setId(id);
                        bookCount1++;
                        user.setBookCount(bookCount1);
                        userService.updateUserAllInfo(user);
                        
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
            }
        
        
        /**
         * 还书
         *
         * @param uid    uid
         * @param rfidId RFID 身份证
         * @param raters 评分
         * @return {@link ResultBean }
         */
        @RequestMapping("returnBook")
        public ResultBean returnBook(Integer uid, String rfidId, String raters)
            {
                
                if (StringUtils.isEmpty(rfidId))
                    {
                        return ResultBean.returnError("请获取RFID");
                    }
                
                if (StringUtils.isEmpty(raters))
                    {
                        return ResultBean.returnError("请进行评分");
                    }
                BsBookInfo bsBookInfo = bookInfoService.getBookByRfid(rfidId);
                if (bsBookInfo == null)
                    {
                        return ResultBean.returnError("书籍信息不存在");
                    }
                
                
                BsBorrow bsBorrow = borrowService.selectBorrowByUidAndrfidID(uid, bsBookInfo.getId());
                if (bsBorrow == null)
                    {
                        return ResultBean.returnError("不存在借阅信息");
                    }
                if (!"0".equals(bsBorrow.getBoorrwStatus()))
                    {
                        return ResultBean.returnError("借阅状态为已归还，无需重复归还");
                    }
                
                
                if (!bsBookInfo.getId().equals(bsBorrow.getBookId()))
                    {
                        return ResultBean.returnError("书籍信息与借阅信息不匹配");
                    }
                //获取用户信息
                BsUser bsUser = userService.selectUserByUid(uid);
                if (bsUser == null)
                    {
                        return ResultBean.returnError("用户信息不存在");
                    }
                //bs_borrow(借阅信息表)  id uid book_id end_time(归还时间) boorrw_status(借阅状态 0- 未归还 1-已归还)
                //根据uid rfid获取借阅信息
                //        BsBorrow bsBorrow = borrowService.selectBorrowByUidAndrfidID(uid,bsBookInfo.getId());
                //        if(bsBorrow == null){
                //            return ResultBean.returnError("不存在借阅信息");
                //        }
                //
                //        if("1".equals(bsBorrow.getBoorrwStatus())){
                //            return ResultBean.returnError("不存在借阅信息");
                //        }
                
                Integer line = borrowService.returnBook(bsBorrow.getId());
                
                if (line > 0)
                    {
                        //书籍剩余数量++
                        Integer id = bsBookInfo.getId();
                        Integer bookCount = Integer.parseInt(bsBookInfo.getBookCount());
                        String bookStatus = bsBookInfo.getBookStatus();
                        bookCount++;
                        BsBookInfo bsBookInfo1 = new BsBookInfo();
                        bsBookInfo1.setId(id);
                        bsBookInfo1.setBookCount(bookCount + "");
                        if ("不可借".equals(bookStatus))
                            {
                                bsBookInfo1.setBookStatus("可借");
                            }
                        
                        //更新图书评分
                        Integer ratersNumber = bsBookInfo.getRatersNumber();
                        bsBookInfo1.setRatersNumber(ratersNumber + 1);
                        String raters1 = bsBookInfo.getRaters();
                        double v = Double.parseDouble(raters1);
                        
                        double count = (v * ratersNumber + Double.parseDouble(raters)) / (ratersNumber + 1);
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
                        String format = df.format(count);
                        bsBookInfo1.setRaters(format);
                        
                        bookInfoService.updateBookInfo(bsBookInfo1);
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
            }
        
        
        /**
         * 查询借书列表
         *
         * @param uid   uid
         * @param page  页
         * @param limit 限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBorrowList")
        public ResultBean selectBorrowList(Integer uid, Integer page, Integer limit)
            {
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                PageInfo<BsBorrow> pageInfo = borrowService.selectBorrowList(uid, page, limit);
                long total = pageInfo.getTotal();
                List<BsBorrow> list = pageInfo.getList();
                
                List<BorrowVo> voList = new ArrayList<>();
                for (BsBorrow bsBorrow : list)
                    {
                        BorrowVo borrowVo = new BorrowVo();
                        borrowVo.setId(bsBorrow.getId());
                        
                        Integer bookId = bsBorrow.getBookId();
                        BsBookInfo bsBookInfo = bookInfoService.getBookById(bookId);
                        if (bsBookInfo != null)
                            {
                                BeanUtils.copyProperties(bsBookInfo, borrowVo);
                            }
                        
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String format1 = format.format(bsBorrow.getEndTime());
                        borrowVo.setEndTime(format1);
                        borrowVo.setGetTime(format.format(bsBorrow.getGetTime()));
                        if (bsBorrow.getReturnTime() != null)
                            {
                                borrowVo.setReturnTime(format.format(bsBorrow.getReturnTime()));
                            }
                        Calendar calendar = Calendar.getInstance(); //得到日历
                        calendar.setTime(bsBorrow.getEndTime());//把当前时间赋给日历
                        calendar.add(Calendar.DAY_OF_MONTH, 1);  //设置为前一天
                        Date yesterday = calendar.getTime();   //得到前一天的时间
                        bsBorrow.setEndTime(yesterday);
                        if ("0".equals(bsBorrow.getBoorrwStatus()))
                            {
                                borrowVo.setStatus("未归还");
                                
                                if (bsBorrow.getReturnTime() == null && !new Date().before(bsBorrow.getEndTime()))
                                    {
                                        
                                        borrowVo.setOverdue("逾期");
                                    }
                                else
                                    {
                                        borrowVo.setOverdue("正常");
                                    }
                            }
                        else
                            {
                                borrowVo.setStatus("已归还");
                                if (bsBorrow.getReturnTime() != null && bsBorrow.getEndTime().before(bsBorrow.getReturnTime()))
                                    {
                                        borrowVo.setOverdue("逾期");
                                    }
                                else
                                    {
                                        borrowVo.setOverdue("正常");
                                    }
                            }
                        //根据借阅信息获取借阅人信息
                        //            ;
                        BsUser userById = userService.getUserById(bsBorrow.getUid());
                        if (userById != null)
                            {
                                borrowVo.setUserName(userById.getUname());
                                borrowVo.setUserNameCh(userById.getEmail());
                                
                            }
                        
                        
                        voList.add(borrowVo);
                        
                    }
                return ResultBean.returnOk().pushData("list", voList).pushData("total", total);
            }
        
        
        /**
         * 根据归还状态查询借书列表
         *
         * @param status 地位
         * @param uid    uid
         * @param page   页
         * @param limit  限制
         * @return {@link ResultBean }
         */
        @RequestMapping("selectBorrowListByStatus")
        public ResultBean selectBorrowListByStatus(String status, Integer uid, Integer page, Integer limit)
            {
                
                if (page == null)
                    {
                        page = 1;
                    }
                if (limit == null)
                    {
                        limit = 10;
                    }
                PageInfo<BsBorrow> pageInfo = borrowService.selectBorrowListByStatus(status, uid, page, limit);
                long total = pageInfo.getTotal();
                List<BsBorrow> list = pageInfo.getList();
                
                List<BorrowVo> voList = new ArrayList<>();
                for (BsBorrow bsBorrow : list)
                    {
                        BorrowVo borrowVo = new BorrowVo();
                        borrowVo.setId(bsBorrow.getId());
                        
                        Integer bookId = bsBorrow.getBookId();
                        BsBookInfo bsBookInfo = bookInfoService.getBookById(bookId);
                        if (bsBookInfo != null)
                            {
                                BeanUtils.copyProperties(bsBookInfo, borrowVo);
                            }
                        
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String format1 = format.format(bsBorrow.getEndTime());
                        borrowVo.setEndTime(format1);
                        borrowVo.setGetTime(format.format(bsBorrow.getGetTime()));
                        if (bsBorrow.getReturnTime() != null)
                            {
                                borrowVo.setReturnTime(format.format(bsBorrow.getReturnTime()));
                            }
                        
                        //            if("0".equals(bsBorrow.getBoorrwStatus())){
                        //                borrowVo.setStatus("未归还");
                        //            }else{
                        //                borrowVo.setStatus("已归还");
                        //            }
                        
                        Calendar calendar = Calendar.getInstance(); //得到日历
                        calendar.setTime(bsBorrow.getEndTime());//把当前时间赋给日历
                        calendar.add(Calendar.DAY_OF_MONTH, 1);  //设置为前一天
                        Date yesterday = calendar.getTime();   //得到前一天的时间
                        bsBorrow.setEndTime(yesterday);
                        
                        if ("0".equals(bsBorrow.getBoorrwStatus()))
                            {
                                borrowVo.setStatus("未归还");
                                
                                if (bsBorrow.getReturnTime() == null && !new Date().before(bsBorrow.getEndTime()))
                                    {
                                        
                                        borrowVo.setOverdue("逾期");
                                    }
                                else
                                    {
                                        borrowVo.setOverdue("正常");
                                    }
                            }
                        else
                            {
                                borrowVo.setStatus("已归还");
                                if (bsBorrow.getReturnTime() != null && bsBorrow.getEndTime().before(bsBorrow.getReturnTime()))
                                    {
                                        borrowVo.setOverdue("逾期");
                                    }
                                else
                                    {
                                        borrowVo.setOverdue("正常");
                                    }
                            }
                        BsUser userById = userService.getUserById(bsBorrow.getUid());
                        if (userById != null)
                            {
                                borrowVo.setUserName(userById.getUname());
                                borrowVo.setUserNameCh(userById.getEmail());
                            }
                        
                        voList.add(borrowVo);
                        
                    }
                return ResultBean.returnOk().pushData("list", voList).pushData("total", total);
            }
        
    }
