package com.encounter.controller;

import com.alibaba.fastjson.JSON;
import com.encounter.bean.BsRfid;
import com.encounter.bean.HttpUtils;
import com.encounter.bean.ResultBean;
import com.encounter.bean.StmData;
import com.encounter.service.RfidService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * RFID 控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
public class RfidController
    {
        
        @Resource
        RfidService rfidService;
        
        /**
         * 获取数据前调用，将状态更新为0  -- 功能废弃
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/book/rfid/startGetRfid")
        public ResultBean startGetRfid()
            {
                Integer line = rfidService.updateStatus("1");
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
            }
        
        /**
         * 获取 RFID
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/book/rfid/getRfid")
        public ResultBean getRfid()
            {
                BsRfid bsRfid = rfidService.getRfidId();
                if ("1".equals(bsRfid.getRfidStatus()))
                    {
                        return ResultBean.returnError("暂未获取到卡号，请重试");
                    }
                rfidService.setRfidStatusToOne();
                
                return ResultBean.returnOk().pushData("data", bsRfid.getRfidId());
            }
        
        /**
         * 获取数据
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("/auth/book/rfid/getRfid2222")
        public ResultBean getRfid2222()
            {
                try
                    {
                        HttpResponse httpResponse = HttpUtils.doGet("http://47.94.194.185:9999/stm/getCurrData?did=202403062152", "", "", new HashMap<>(), new HashMap<>());
                        String getRespStr = EntityUtils.toString(httpResponse.getEntity());
                        System.out.println(getRespStr);
                        if (!StringUtils.isEmpty(getRespStr))
                            {
                                StmData stmData = JSON.parseObject(getRespStr, StmData.class);
                                if (stmData == null || stmData.getCreateDate() == null)
                                    {
                                        return ResultBean.returnError("获取卡号失败，请检查");
                                    }
                                else
                                    {
                                        Date createDate = stmData.getCreateDate();
                                        System.out.println(createDate);
                                        
                                        long sTime = createDate.getTime();
                                        long eTime = new Date().getTime();
                                        long diff = (eTime - sTime) / 1000;
                                        
                                        System.out.println("时间差：" + diff);
                                        if (diff > 60)
                                            {
                                                return ResultBean.returnError("卡号已过期，请重刷");
                                            }
                                        
                                        return ResultBean.returnOk().pushData("data", stmData.getStmData());
                                        
                                    }
                            }
                        else
                            {
                                return ResultBean.returnError("获取卡号失败，请检查");
                            }
                    }
                catch (Exception e)
                    {
                        e.printStackTrace();
                        return ResultBean.returnError("获取卡号失败，请检查");
                    }
            }
        
        /**
         * 获取 RFID
         *
         * @param rfidId RFID 身份证
         * @return {@link ResultBean }
         */
        @RequestMapping("/user/puttRfid")
        public ResultBean getRfid(String rfidId)
            {
                System.out.println("接收到硬件传输的数据：" + rfidId);
                Integer line = rfidService.insertRfid(rfidId);
                if (line > 0)
                    {
                        return ResultBean.returnOk();
                    }
                return ResultBean.returnError();
                
            }
        
        /**
         * 测试
         *
         * @return {@link ResultBean }
         */
        @RequestMapping("/user/test")
        public ResultBean test()
            {
                System.out.println("test.....");
                return ResultBean.returnOk();
            }
    }
