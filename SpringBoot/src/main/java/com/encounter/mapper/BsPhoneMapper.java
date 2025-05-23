package com.encounter.mapper;

import com.encounter.bean.BsPhone;
import com.encounter.bean.BsPhoneExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsPhoneMapper
    {
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        long countByExample(BsPhoneExample example);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int deleteByExample(BsPhoneExample example);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int deleteByPrimaryKey(Integer id);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int insert(BsPhone record);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int insertSelective(BsPhone record);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        List<BsPhone> selectByExample(BsPhoneExample example);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        BsPhone selectByPrimaryKey(Integer id);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int updateByExampleSelective(@Param("record") BsPhone record, @Param("example") BsPhoneExample example);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int updateByExample(@Param("record") BsPhone record, @Param("example") BsPhoneExample example);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int updateByPrimaryKeySelective(BsPhone record);
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_phone
         *
         * @mbg.generated
         */
        int updateByPrimaryKey(BsPhone record);
    }