package com.encounter.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BsBorrowExample
    {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        protected String orderByClause;
        
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        protected boolean distinct;
        
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        protected List<Criteria> oredCriteria;
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public BsBorrowExample()
            {
                oredCriteria = new ArrayList<Criteria>();
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public String getOrderByClause()
            {
                return orderByClause;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public void setOrderByClause(String orderByClause)
            {
                this.orderByClause = orderByClause;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public boolean isDistinct()
            {
                return distinct;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public void setDistinct(boolean distinct)
            {
                this.distinct = distinct;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public List<Criteria> getOredCriteria()
            {
                return oredCriteria;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public void or(Criteria criteria)
            {
                oredCriteria.add(criteria);
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public Criteria or()
            {
                Criteria criteria = createCriteriaInternal();
                oredCriteria.add(criteria);
                return criteria;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public Criteria createCriteria()
            {
                Criteria criteria = createCriteriaInternal();
                if (oredCriteria.size() == 0)
                    {
                        oredCriteria.add(criteria);
                    }
                return criteria;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        protected Criteria createCriteriaInternal()
            {
                Criteria criteria = new Criteria();
                return criteria;
            }
        
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public void clear()
            {
                oredCriteria.clear();
                orderByClause = null;
                distinct = false;
            }
        
        /**
         * This class was generated by MyBatis Generator.
         * This class corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        protected abstract static class GeneratedCriteria
            {
                protected List<Criterion> criteria;
                
                protected GeneratedCriteria()
                    {
                        super();
                        criteria = new ArrayList<Criterion>();
                    }
                
                public boolean isValid()
                    {
                        return criteria.size() > 0;
                    }
                
                public List<Criterion> getAllCriteria()
                    {
                        return criteria;
                    }
                
                public List<Criterion> getCriteria()
                    {
                        return criteria;
                    }
                
                protected void addCriterion(String condition)
                    {
                        if (condition == null)
                            {
                                throw new RuntimeException("Value for condition cannot be null");
                            }
                        criteria.add(new Criterion(condition));
                    }
                
                protected void addCriterion(String condition, Object value, String property)
                    {
                        if (value == null)
                            {
                                throw new RuntimeException("Value for " + property + " cannot be null");
                            }
                        criteria.add(new Criterion(condition, value));
                    }
                
                protected void addCriterion(String condition, Object value1, Object value2, String property)
                    {
                        if (value1 == null || value2 == null)
                            {
                                throw new RuntimeException("Between values for " + property + " cannot be null");
                            }
                        criteria.add(new Criterion(condition, value1, value2));
                    }
                
                public Criteria andIdIsNull()
                    {
                        addCriterion("id is null");
                        return (Criteria) this;
                    }
                
                public Criteria andIdIsNotNull()
                    {
                        addCriterion("id is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andIdEqualTo(Integer value)
                    {
                        addCriterion("id =", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdNotEqualTo(Integer value)
                    {
                        addCriterion("id <>", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdGreaterThan(Integer value)
                    {
                        addCriterion("id >", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdGreaterThanOrEqualTo(Integer value)
                    {
                        addCriterion("id >=", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdLessThan(Integer value)
                    {
                        addCriterion("id <", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdLessThanOrEqualTo(Integer value)
                    {
                        addCriterion("id <=", value, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdIn(List<Integer> values)
                    {
                        addCriterion("id in", values, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdNotIn(List<Integer> values)
                    {
                        addCriterion("id not in", values, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdBetween(Integer value1, Integer value2)
                    {
                        addCriterion("id between", value1, value2, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andIdNotBetween(Integer value1, Integer value2)
                    {
                        addCriterion("id not between", value1, value2, "id");
                        return (Criteria) this;
                    }
                
                public Criteria andUidIsNull()
                    {
                        addCriterion("uid is null");
                        return (Criteria) this;
                    }
                
                public Criteria andUidIsNotNull()
                    {
                        addCriterion("uid is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andUidEqualTo(Integer value)
                    {
                        addCriterion("uid =", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidNotEqualTo(Integer value)
                    {
                        addCriterion("uid <>", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidGreaterThan(Integer value)
                    {
                        addCriterion("uid >", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidGreaterThanOrEqualTo(Integer value)
                    {
                        addCriterion("uid >=", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidLessThan(Integer value)
                    {
                        addCriterion("uid <", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidLessThanOrEqualTo(Integer value)
                    {
                        addCriterion("uid <=", value, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidIn(List<Integer> values)
                    {
                        addCriterion("uid in", values, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidNotIn(List<Integer> values)
                    {
                        addCriterion("uid not in", values, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidBetween(Integer value1, Integer value2)
                    {
                        addCriterion("uid between", value1, value2, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andUidNotBetween(Integer value1, Integer value2)
                    {
                        addCriterion("uid not between", value1, value2, "uid");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdIsNull()
                    {
                        addCriterion("book_id is null");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdIsNotNull()
                    {
                        addCriterion("book_id is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdEqualTo(Integer value)
                    {
                        addCriterion("book_id =", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdNotEqualTo(Integer value)
                    {
                        addCriterion("book_id <>", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdGreaterThan(Integer value)
                    {
                        addCriterion("book_id >", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdGreaterThanOrEqualTo(Integer value)
                    {
                        addCriterion("book_id >=", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdLessThan(Integer value)
                    {
                        addCriterion("book_id <", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdLessThanOrEqualTo(Integer value)
                    {
                        addCriterion("book_id <=", value, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdIn(List<Integer> values)
                    {
                        addCriterion("book_id in", values, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdNotIn(List<Integer> values)
                    {
                        addCriterion("book_id not in", values, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdBetween(Integer value1, Integer value2)
                    {
                        addCriterion("book_id between", value1, value2, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andBookIdNotBetween(Integer value1, Integer value2)
                    {
                        addCriterion("book_id not between", value1, value2, "bookId");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeIsNull()
                    {
                        addCriterion("end_time is null");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeIsNotNull()
                    {
                        addCriterion("end_time is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeEqualTo(Date value)
                    {
                        addCriterion("end_time =", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeNotEqualTo(Date value)
                    {
                        addCriterion("end_time <>", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeGreaterThan(Date value)
                    {
                        addCriterion("end_time >", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeGreaterThanOrEqualTo(Date value)
                    {
                        addCriterion("end_time >=", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeLessThan(Date value)
                    {
                        addCriterion("end_time <", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeLessThanOrEqualTo(Date value)
                    {
                        addCriterion("end_time <=", value, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeIn(List<Date> values)
                    {
                        addCriterion("end_time in", values, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeNotIn(List<Date> values)
                    {
                        addCriterion("end_time not in", values, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeBetween(Date value1, Date value2)
                    {
                        addCriterion("end_time between", value1, value2, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andEndTimeNotBetween(Date value1, Date value2)
                    {
                        addCriterion("end_time not between", value1, value2, "endTime");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusIsNull()
                    {
                        addCriterion("boorrw_status is null");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusIsNotNull()
                    {
                        addCriterion("boorrw_status is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusEqualTo(String value)
                    {
                        addCriterion("boorrw_status =", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusNotEqualTo(String value)
                    {
                        addCriterion("boorrw_status <>", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusGreaterThan(String value)
                    {
                        addCriterion("boorrw_status >", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusGreaterThanOrEqualTo(String value)
                    {
                        addCriterion("boorrw_status >=", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusLessThan(String value)
                    {
                        addCriterion("boorrw_status <", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusLessThanOrEqualTo(String value)
                    {
                        addCriterion("boorrw_status <=", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusLike(String value)
                    {
                        addCriterion("boorrw_status like", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusNotLike(String value)
                    {
                        addCriterion("boorrw_status not like", value, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusIn(List<String> values)
                    {
                        addCriterion("boorrw_status in", values, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusNotIn(List<String> values)
                    {
                        addCriterion("boorrw_status not in", values, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusBetween(String value1, String value2)
                    {
                        addCriterion("boorrw_status between", value1, value2, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andBoorrwStatusNotBetween(String value1, String value2)
                    {
                        addCriterion("boorrw_status not between", value1, value2, "boorrwStatus");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeIsNull()
                    {
                        addCriterion("get_time is null");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeIsNotNull()
                    {
                        addCriterion("get_time is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeEqualTo(Date value)
                    {
                        addCriterion("get_time =", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeNotEqualTo(Date value)
                    {
                        addCriterion("get_time <>", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeGreaterThan(Date value)
                    {
                        addCriterion("get_time >", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeGreaterThanOrEqualTo(Date value)
                    {
                        addCriterion("get_time >=", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeLessThan(Date value)
                    {
                        addCriterion("get_time <", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeLessThanOrEqualTo(Date value)
                    {
                        addCriterion("get_time <=", value, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeIn(List<Date> values)
                    {
                        addCriterion("get_time in", values, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeNotIn(List<Date> values)
                    {
                        addCriterion("get_time not in", values, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeBetween(Date value1, Date value2)
                    {
                        addCriterion("get_time between", value1, value2, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andGetTimeNotBetween(Date value1, Date value2)
                    {
                        addCriterion("get_time not between", value1, value2, "getTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeIsNull()
                    {
                        addCriterion("return_time is null");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeIsNotNull()
                    {
                        addCriterion("return_time is not null");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeEqualTo(Date value)
                    {
                        addCriterion("return_time =", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeNotEqualTo(Date value)
                    {
                        addCriterion("return_time <>", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeGreaterThan(Date value)
                    {
                        addCriterion("return_time >", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeGreaterThanOrEqualTo(Date value)
                    {
                        addCriterion("return_time >=", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeLessThan(Date value)
                    {
                        addCriterion("return_time <", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeLessThanOrEqualTo(Date value)
                    {
                        addCriterion("return_time <=", value, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeIn(List<Date> values)
                    {
                        addCriterion("return_time in", values, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeNotIn(List<Date> values)
                    {
                        addCriterion("return_time not in", values, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeBetween(Date value1, Date value2)
                    {
                        addCriterion("return_time between", value1, value2, "returnTime");
                        return (Criteria) this;
                    }
                
                public Criteria andReturnTimeNotBetween(Date value1, Date value2)
                    {
                        addCriterion("return_time not between", value1, value2, "returnTime");
                        return (Criteria) this;
                    }
            }
        
        /**
         * This class was generated by MyBatis Generator.
         * This class corresponds to the database table bs_borrow
         *
         * @mbg.generated do_not_delete_during_merge
         */
        public static class Criteria extends GeneratedCriteria
            {
                
                protected Criteria()
                    {
                        super();
                    }
            }
        
        /**
         * This class was generated by MyBatis Generator.
         * This class corresponds to the database table bs_borrow
         *
         * @mbg.generated
         */
        public static class Criterion
            {
                private final String condition;
                private final String typeHandler;
                private Object value;
                private Object secondValue;
                private boolean noValue;
                private boolean singleValue;
                private boolean betweenValue;
                private boolean listValue;
                
                protected Criterion(String condition)
                    {
                        super();
                        this.condition = condition;
                        this.typeHandler = null;
                        this.noValue = true;
                    }
                
                protected Criterion(String condition, Object value, String typeHandler)
                    {
                        super();
                        this.condition = condition;
                        this.value = value;
                        this.typeHandler = typeHandler;
                        if (value instanceof List<?>)
                            {
                                this.listValue = true;
                            }
                        else
                            {
                                this.singleValue = true;
                            }
                    }
                
                protected Criterion(String condition, Object value)
                    {
                        this(condition, value, null);
                    }
                
                protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
                    {
                        super();
                        this.condition = condition;
                        this.value = value;
                        this.secondValue = secondValue;
                        this.typeHandler = typeHandler;
                        this.betweenValue = true;
                    }
                
                protected Criterion(String condition, Object value, Object secondValue)
                    {
                        this(condition, value, secondValue, null);
                    }
                
                public String getCondition()
                    {
                        return condition;
                    }
                
                public Object getValue()
                    {
                        return value;
                    }
                
                public Object getSecondValue()
                    {
                        return secondValue;
                    }
                
                public boolean isNoValue()
                    {
                        return noValue;
                    }
                
                public boolean isSingleValue()
                    {
                        return singleValue;
                    }
                
                public boolean isBetweenValue()
                    {
                        return betweenValue;
                    }
                
                public boolean isListValue()
                    {
                        return listValue;
                    }
                
                public String getTypeHandler()
                    {
                        return typeHandler;
                    }
            }
    }