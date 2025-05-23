package com.encounter.service.impl;

import com.encounter.bean.BsRfid;
import com.encounter.mapper.BsRfidMapper;
import com.encounter.service.RfidService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RfidServiceImpl implements RfidService
    {
        
        @Resource
        BsRfidMapper rfidMapper;
        
        @Override
        public Integer updateStatus(String s)
            {
                BsRfid bsRfid = new BsRfid();
                bsRfid.setRfidStatus(s);
                bsRfid.setId(1);
                return rfidMapper.updateByPrimaryKeySelective(bsRfid);
            }
        
        @Override
        public BsRfid getRfidId()
            {
                return rfidMapper.selectByPrimaryKey(1);
            }
        
        @Override
        public Integer insertRfid(String rfidId)
            {
                BsRfid bsRfid = new BsRfid();
                bsRfid.setId(1);
                bsRfid.setRfidStatus("0");
                bsRfid.setRfidId(rfidId);
                return rfidMapper.updateByPrimaryKeySelective(bsRfid);
            }
        
        @Override
        public void setRfidStatusToOne()
            {
                BsRfid bsRfid = new BsRfid();
                bsRfid.setId(1);
                bsRfid.setRfidStatus("1");
                rfidMapper.updateByPrimaryKeySelective(bsRfid);
            }
    }
