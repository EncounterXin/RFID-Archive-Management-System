package com.encounter.controller;

import com.encounter.bean.BsUser;
import com.encounter.bean.EchartBean;
import com.encounter.bean.ResultBean;
import com.encounter.service.BookInfoService;
import com.encounter.service.EchartService;
import com.encounter.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * echart 控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
@RequestMapping("/auth/echart")
public class EchartController
    {
        
        @Resource
        EchartService echartService;
        
        @Resource
        UserService userService;
        
        @Resource
        BookInfoService bookInfoService;
        
        /**
         * 获取用户信息图表
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("getUserInfoChart")
        public ResultBean getUserInfoChart()
            {
                //获取不活跃读者数量
                List<BsUser> inactiveList = userService.getInactiveUser();
                //获取一般读者数量
                List<BsUser> commonlyList = userService.getCommonlyUser();
                //获取活跃读者数量
                List<BsUser> activeList = userService.getActiveUser();
                List<EchartBean> list = new ArrayList<>();
                list.add(new EchartBean("不活跃", inactiveList.size()));
                list.add(new EchartBean("一般", commonlyList.size()));
                list.add(new EchartBean("活跃", activeList.size()));
                
                return ResultBean.returnOk().pushData("data", list);
            }
        
        /**
         * 获取书籍类型信息图表
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookTypeInfoChart")
        public ResultBean getBookTypeInfoChart()
            {
                List<EchartBean> echartBeans = bookInfoService.getBookTypeInfoChart();
                return ResultBean.returnOk().pushData("data", echartBeans);
            }
        
        
        /**
         * 按 UID 获取书籍类型信息图表
         *
         * @param uid uid
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookTypeInfoChartByUid")
        public ResultBean getBookTypeInfoChartByUid(Integer uid)
            {
                List<EchartBean> echartBeans = bookInfoService.getBookTypeInfoChartByUid(uid);
                return ResultBean.returnOk().pushData("data", echartBeans);
            }
        
        
        /**
         * 获取启动计数图表
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("getBootCountChart")
        public ResultBean getBootCountChart()
            {
                List<EchartBean> echartBeans = bookInfoService.getBootCountChart();
                List<String> xData = new ArrayList<>();
                List<Integer> yData = new ArrayList<>();
                
                for (EchartBean echartBean : echartBeans)
                    {
                        xData.add(echartBean.getName());
                        yData.add(echartBean.getValue());
                    }
                return ResultBean.returnOk().pushData("xData", xData).pushData("yData", yData);
            }
        
        
        /**
         * 按 uid 获取图书计数
         *
         * @param uid uid
         * @return {@link ResultBean }
         */
        @RequestMapping("getBookCountByUid")
        public ResultBean getBookCountByUid(String uid)
            {
                List<EchartBean> echartBeans = bookInfoService.getBootCountChartByUid(uid);
                List<String> xData = new ArrayList<>();
                List<Integer> yData = new ArrayList<>();
                
                for (EchartBean echartBean : echartBeans)
                    {
                        xData.add(echartBean.getName());
                        yData.add(echartBean.getValue());
                    }
                return ResultBean.returnOk().pushData("xData", xData).pushData("yData", yData);
            }
    }
