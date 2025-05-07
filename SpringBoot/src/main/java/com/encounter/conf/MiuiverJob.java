package com.encounter.conf;

import com.encounter.MsgUtils;
import com.encounter.bean.*;
import com.encounter.mapper.BsScoreMapper;
import com.encounter.mapper.BsVerdueRecordMapper;
import com.encounter.service.BorrowService;
import com.encounter.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Miuiver Job
 *
 * @author Encounter
 * @since 2025/05/07
 */
@Slf4j
@Component
public class MiuiverJob extends QuartzJobBean
    {
        
        
        @Resource
        UserService userService;
        @Resource
        BorrowService borrowService;
        @Resource
        BsVerdueRecordMapper verdueRecordMapper;
        @Resource
        BsScoreMapper scoreMapper;
        
        @Override
        protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException
            {
                List<BsPhone> bsPhones = userService.getMessageList();
                for (BsPhone bsPhone : bsPhones)
                    {
                        MsgUtils.sendMsg(bsPhone.getPhone());
                    }
                System.out.println("定时任务执行");
                
                //处理信誉分
                PageInfo<BsBorrow> pageInfo = borrowService.selectBorrowList(null, 1, Integer.MAX_VALUE);
                List<BsBorrow> list = pageInfo.getList();
                List<BsBorrow> scoreList = new ArrayList<>();
                
                
                for (BsBorrow bsBorrow : list)
                    {
                        if (bsBorrow.getReturnTime() == null && bsBorrow.getEndTime().before(new Date()))
                            {
                                scoreList.add(bsBorrow);
                            }
                        else if (bsBorrow.getReturnTime() != null && bsBorrow.getReturnTime().before(bsBorrow.getEndTime()))
                            {
                                scoreList.add(bsBorrow);
                            }
                    }
                
                //得到实际的逾期列表，判断信誉分有没有扣过
                List<BsVerdueRecord> bsVerdueRecords = verdueRecordMapper.selectByExample(null);
                
                for (BsBorrow bsBorrow : scoreList)
                    {
                        Integer id = bsBorrow.getId();
                        Boolean e = isE(bsVerdueRecords, id);
                        if (!e)
                            {
                                //不存在则扣分并插入记录表
                                BsScoreExample bsScoreExample = new BsScoreExample();
                                bsScoreExample.createCriteria().andUidEqualTo(bsBorrow.getUid());
                                List<BsScore> bsScores = scoreMapper.selectByExample(bsScoreExample);
                                if (bsScores != null && bsScores.size() > 0)
                                    {
                                        BsScore bsScore = bsScores.get(0);
                                        Integer score = bsScore.getScore();
                                        score--;
                                        bsScore.setScore(score);
                                        scoreMapper.updateByPrimaryKeySelective(bsScore);
                                        
                                        //插入记录表
                                        BsVerdueRecord bsVerdueRecord = new BsVerdueRecord();
                                        bsVerdueRecord.setBorrowId(bsBorrow.getId());
                                        bsVerdueRecord.setUid(bsBorrow.getUid());
                                        verdueRecordMapper.insertSelective(bsVerdueRecord);
                                        
                                    }
                                
                            }
                    }
                
                
            }
        
        //逾期记录表中是否有记录
        public Boolean isE(List<BsVerdueRecord> bsVerdueRecords, Integer id)
            {
                for (BsVerdueRecord bsVerdueRecord : bsVerdueRecords)
                    {
                        
                        if (bsVerdueRecord.getBorrowId().equals(id))
                            {
                                return true;
                            }
                    }
                
                return false;
            }
        
    }
