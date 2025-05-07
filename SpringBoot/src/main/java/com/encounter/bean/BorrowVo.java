package com.encounter.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Encounter
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowVo extends BsBookInfo
    {
        Integer id;
        String endTime;
        String status;
        String getTime;
        String returnTime;
        String userNameCh;
        String userName;
        String overdue;
    }
