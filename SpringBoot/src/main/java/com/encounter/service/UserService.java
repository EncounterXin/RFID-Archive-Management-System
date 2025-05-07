package com.encounter.service;

import com.encounter.bean.BsPhone;
import com.encounter.bean.BsScore;
import com.encounter.bean.BsUser;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService
    {
        
        
        List<BsUser> getUserByUname(String uname);
        
        void loginToken(String uuid, Integer uid);
        
        
        String getUserNameByToken(String token);
        
        Integer insertUser(String uname, String pwd);
        
        void insertToken(Integer uid, String token);
        
        PageInfo<BsUser> selectUserListByPage(Integer page, Integer limit);
        
        void deleteUserById(Integer uid);
        
        void updatePwdByUid(Integer uid, String pwd);
        
        BsUser selectUserByUid(Integer uid);
        
        BsUser getUserById(Integer uid);
        
        BsUser getUserByToken(String token);
        
        Integer insertUserBean(BsUser user);
        
        PageInfo<BsUser> getUserListByCondition(String uname, String email, String major, Integer page, Integer limit);
        
        Integer updateUserAllInfo(BsUser user);
        
        void insertPhone(BsPhone bsPhone);
        
        List<BsUser> getInactiveUser();
        
        List<BsUser> getCommonlyUser();
        
        List<BsUser> getActiveUser();
        
        List<BsPhone> getMessageList();
        
        void insertScore(Integer uid);
        
        List<BsScore> selectUserScoreByUid(Integer uid);
    }
