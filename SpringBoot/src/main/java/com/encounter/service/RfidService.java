package com.encounter.service;

import com.encounter.bean.BsRfid;


public interface RfidService
    {
        Integer updateStatus(String s);
        
        BsRfid getRfidId();
        
        Integer insertRfid(String rfidId);
        
        void setRfidStatusToOne();
    }
