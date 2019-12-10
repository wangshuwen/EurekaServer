package com.cst.xinhe.persistence.model.overman_area;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OvermanAreaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public OvermanAreaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOvermanAreaIdIsNull() {
            addCriterion("overman_area_id is null");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdIsNotNull() {
            addCriterion("overman_area_id is not null");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdEqualTo(Integer value) {
            addCriterion("overman_area_id =", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdNotEqualTo(Integer value) {
            addCriterion("overman_area_id <>", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdGreaterThan(Integer value) {
            addCriterion("overman_area_id >", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("overman_area_id >=", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdLessThan(Integer value) {
            addCriterion("overman_area_id <", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("overman_area_id <=", value, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdIn(List<Integer> values) {
            addCriterion("overman_area_id in", values, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdNotIn(List<Integer> values) {
            addCriterion("overman_area_id not in", values, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("overman_area_id between", value1, value2, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andOvermanAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("overman_area_id not between", value1, value2, "overmanAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdIsNull() {
            addCriterion("warning_area_id is null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdIsNotNull() {
            addCriterion("warning_area_id is not null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdEqualTo(Integer value) {
            addCriterion("warning_area_id =", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdNotEqualTo(Integer value) {
            addCriterion("warning_area_id <>", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdGreaterThan(Integer value) {
            addCriterion("warning_area_id >", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("warning_area_id >=", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdLessThan(Integer value) {
            addCriterion("warning_area_id <", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("warning_area_id <=", value, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdIn(List<Integer> values) {
            addCriterion("warning_area_id in", values, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdNotIn(List<Integer> values) {
            addCriterion("warning_area_id not in", values, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("warning_area_id between", value1, value2, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andWarningAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("warning_area_id not between", value1, value2, "warningAreaId");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanIsNull() {
            addCriterion("max_overman is null");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanIsNotNull() {
            addCriterion("max_overman is not null");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanEqualTo(Integer value) {
            addCriterion("max_overman =", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanNotEqualTo(Integer value) {
            addCriterion("max_overman <>", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanGreaterThan(Integer value) {
            addCriterion("max_overman >", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_overman >=", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanLessThan(Integer value) {
            addCriterion("max_overman <", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanLessThanOrEqualTo(Integer value) {
            addCriterion("max_overman <=", value, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanIn(List<Integer> values) {
            addCriterion("max_overman in", values, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanNotIn(List<Integer> values) {
            addCriterion("max_overman not in", values, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanBetween(Integer value1, Integer value2) {
            addCriterion("max_overman between", value1, value2, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andMaxOvermanNotBetween(Integer value1, Integer value2) {
            addCriterion("max_overman not between", value1, value2, "maxOverman");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeIsNull() {
            addCriterion("start_overtime is null");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeIsNotNull() {
            addCriterion("start_overtime is not null");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeEqualTo(Date value) {
            addCriterion("start_overtime =", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeNotEqualTo(Date value) {
            addCriterion("start_overtime <>", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeGreaterThan(Date value) {
            addCriterion("start_overtime >", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_overtime >=", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeLessThan(Date value) {
            addCriterion("start_overtime <", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeLessThanOrEqualTo(Date value) {
            addCriterion("start_overtime <=", value, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeIn(List<Date> values) {
            addCriterion("start_overtime in", values, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeNotIn(List<Date> values) {
            addCriterion("start_overtime not in", values, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeBetween(Date value1, Date value2) {
            addCriterion("start_overtime between", value1, value2, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andStartOvertimeNotBetween(Date value1, Date value2) {
            addCriterion("start_overtime not between", value1, value2, "startOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeIsNull() {
            addCriterion("end_overtime is null");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeIsNotNull() {
            addCriterion("end_overtime is not null");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeEqualTo(Date value) {
            addCriterion("end_overtime =", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeNotEqualTo(Date value) {
            addCriterion("end_overtime <>", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeGreaterThan(Date value) {
            addCriterion("end_overtime >", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_overtime >=", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeLessThan(Date value) {
            addCriterion("end_overtime <", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeLessThanOrEqualTo(Date value) {
            addCriterion("end_overtime <=", value, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeIn(List<Date> values) {
            addCriterion("end_overtime in", values, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeNotIn(List<Date> values) {
            addCriterion("end_overtime not in", values, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeBetween(Date value1, Date value2) {
            addCriterion("end_overtime between", value1, value2, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andEndOvertimeNotBetween(Date value1, Date value2) {
            addCriterion("end_overtime not between", value1, value2, "endOvertime");
            return (Criteria) this;
        }

        public Criteria andStaffIdIsNull() {
            addCriterion("staff_id is null");
            return (Criteria) this;
        }

        public Criteria andStaffIdIsNotNull() {
            addCriterion("staff_id is not null");
            return (Criteria) this;
        }

        public Criteria andStaffIdEqualTo(Integer value) {
            addCriterion("staff_id =", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotEqualTo(Integer value) {
            addCriterion("staff_id <>", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdGreaterThan(Integer value) {
            addCriterion("staff_id >", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("staff_id >=", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdLessThan(Integer value) {
            addCriterion("staff_id <", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdLessThanOrEqualTo(Integer value) {
            addCriterion("staff_id <=", value, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdIn(List<Integer> values) {
            addCriterion("staff_id in", values, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotIn(List<Integer> values) {
            addCriterion("staff_id not in", values, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdBetween(Integer value1, Integer value2) {
            addCriterion("staff_id between", value1, value2, "staffId");
            return (Criteria) this;
        }

        public Criteria andStaffIdNotBetween(Integer value1, Integer value2) {
            addCriterion("staff_id not between", value1, value2, "staffId");
            return (Criteria) this;
        }

        public Criteria andRemark1IsNull() {
            addCriterion("remark1 is null");
            return (Criteria) this;
        }

        public Criteria andRemark1IsNotNull() {
            addCriterion("remark1 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark1EqualTo(String value) {
            addCriterion("remark1 =", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotEqualTo(String value) {
            addCriterion("remark1 <>", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1GreaterThan(String value) {
            addCriterion("remark1 >", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1GreaterThanOrEqualTo(String value) {
            addCriterion("remark1 >=", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1LessThan(String value) {
            addCriterion("remark1 <", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1LessThanOrEqualTo(String value) {
            addCriterion("remark1 <=", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1Like(String value) {
            addCriterion("remark1 like", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotLike(String value) {
            addCriterion("remark1 not like", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1In(List<String> values) {
            addCriterion("remark1 in", values, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotIn(List<String> values) {
            addCriterion("remark1 not in", values, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1Between(String value1, String value2) {
            addCriterion("remark1 between", value1, value2, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotBetween(String value1, String value2) {
            addCriterion("remark1 not between", value1, value2, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark2IsNull() {
            addCriterion("remark2 is null");
            return (Criteria) this;
        }

        public Criteria andRemark2IsNotNull() {
            addCriterion("remark2 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark2EqualTo(String value) {
            addCriterion("remark2 =", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotEqualTo(String value) {
            addCriterion("remark2 <>", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2GreaterThan(String value) {
            addCriterion("remark2 >", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2GreaterThanOrEqualTo(String value) {
            addCriterion("remark2 >=", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2LessThan(String value) {
            addCriterion("remark2 <", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2LessThanOrEqualTo(String value) {
            addCriterion("remark2 <=", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2Like(String value) {
            addCriterion("remark2 like", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotLike(String value) {
            addCriterion("remark2 not like", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2In(List<String> values) {
            addCriterion("remark2 in", values, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotIn(List<String> values) {
            addCriterion("remark2 not in", values, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2Between(String value1, String value2) {
            addCriterion("remark2 between", value1, value2, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotBetween(String value1, String value2) {
            addCriterion("remark2 not between", value1, value2, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark3IsNull() {
            addCriterion("remark3 is null");
            return (Criteria) this;
        }

        public Criteria andRemark3IsNotNull() {
            addCriterion("remark3 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark3EqualTo(Integer value) {
            addCriterion("remark3 =", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotEqualTo(Integer value) {
            addCriterion("remark3 <>", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3GreaterThan(Integer value) {
            addCriterion("remark3 >", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3GreaterThanOrEqualTo(Integer value) {
            addCriterion("remark3 >=", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3LessThan(Integer value) {
            addCriterion("remark3 <", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3LessThanOrEqualTo(Integer value) {
            addCriterion("remark3 <=", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3In(List<Integer> values) {
            addCriterion("remark3 in", values, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotIn(List<Integer> values) {
            addCriterion("remark3 not in", values, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3Between(Integer value1, Integer value2) {
            addCriterion("remark3 between", value1, value2, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotBetween(Integer value1, Integer value2) {
            addCriterion("remark3 not between", value1, value2, "remark3");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table overman_area
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table overman_area
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}