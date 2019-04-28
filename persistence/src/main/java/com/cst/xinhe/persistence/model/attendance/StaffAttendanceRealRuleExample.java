package com.cst.xinhe.persistence.model.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StaffAttendanceRealRuleExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public StaffAttendanceRealRuleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
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
     * This method corresponds to the database table staff_attendance_real_rule
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
     * This method corresponds to the database table staff_attendance_real_rule
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_attendance_real_rule
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
     * This class corresponds to the database table staff_attendance_real_rule
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

        public Criteria andRealRuleStartTimeIsNull() {
            addCriterion("real_rule_start_time is null");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeIsNotNull() {
            addCriterion("real_rule_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeEqualTo(Date value) {
            addCriterion("real_rule_start_time =", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeNotEqualTo(Date value) {
            addCriterion("real_rule_start_time <>", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeGreaterThan(Date value) {
            addCriterion("real_rule_start_time >", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("real_rule_start_time >=", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeLessThan(Date value) {
            addCriterion("real_rule_start_time <", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("real_rule_start_time <=", value, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeIn(List<Date> values) {
            addCriterion("real_rule_start_time in", values, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeNotIn(List<Date> values) {
            addCriterion("real_rule_start_time not in", values, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeBetween(Date value1, Date value2) {
            addCriterion("real_rule_start_time between", value1, value2, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("real_rule_start_time not between", value1, value2, "realRuleStartTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeIsNull() {
            addCriterion("real_rule_end_time is null");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeIsNotNull() {
            addCriterion("real_rule_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeEqualTo(Date value) {
            addCriterion("real_rule_end_time =", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeNotEqualTo(Date value) {
            addCriterion("real_rule_end_time <>", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeGreaterThan(Date value) {
            addCriterion("real_rule_end_time >", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("real_rule_end_time >=", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeLessThan(Date value) {
            addCriterion("real_rule_end_time <", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("real_rule_end_time <=", value, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeIn(List<Date> values) {
            addCriterion("real_rule_end_time in", values, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeNotIn(List<Date> values) {
            addCriterion("real_rule_end_time not in", values, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeBetween(Date value1, Date value2) {
            addCriterion("real_rule_end_time between", value1, value2, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andRealRuleEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("real_rule_end_time not between", value1, value2, "realRuleEndTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeIsNull() {
            addCriterion("is_over_time is null");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeIsNotNull() {
            addCriterion("is_over_time is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeEqualTo(Integer value) {
            addCriterion("is_over_time =", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeNotEqualTo(Integer value) {
            addCriterion("is_over_time <>", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeGreaterThan(Integer value) {
            addCriterion("is_over_time >", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_over_time >=", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeLessThan(Integer value) {
            addCriterion("is_over_time <", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeLessThanOrEqualTo(Integer value) {
            addCriterion("is_over_time <=", value, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeIn(List<Integer> values) {
            addCriterion("is_over_time in", values, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeNotIn(List<Integer> values) {
            addCriterion("is_over_time not in", values, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeBetween(Integer value1, Integer value2) {
            addCriterion("is_over_time between", value1, value2, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsOverTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("is_over_time not between", value1, value2, "isOverTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeIsNull() {
            addCriterion("is_serious_time is null");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeIsNotNull() {
            addCriterion("is_serious_time is not null");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeEqualTo(Integer value) {
            addCriterion("is_serious_time =", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeNotEqualTo(Integer value) {
            addCriterion("is_serious_time <>", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeGreaterThan(Integer value) {
            addCriterion("is_serious_time >", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_serious_time >=", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeLessThan(Integer value) {
            addCriterion("is_serious_time <", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeLessThanOrEqualTo(Integer value) {
            addCriterion("is_serious_time <=", value, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeIn(List<Integer> values) {
            addCriterion("is_serious_time in", values, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeNotIn(List<Integer> values) {
            addCriterion("is_serious_time not in", values, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeBetween(Integer value1, Integer value2) {
            addCriterion("is_serious_time between", value1, value2, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andIsSeriousTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("is_serious_time not between", value1, value2, "isSeriousTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeIsNull() {
            addCriterion("final_time is null");
            return (Criteria) this;
        }

        public Criteria andFinalTimeIsNotNull() {
            addCriterion("final_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinalTimeEqualTo(Date value) {
            addCriterion("final_time =", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeNotEqualTo(Date value) {
            addCriterion("final_time <>", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeGreaterThan(Date value) {
            addCriterion("final_time >", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("final_time >=", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeLessThan(Date value) {
            addCriterion("final_time <", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeLessThanOrEqualTo(Date value) {
            addCriterion("final_time <=", value, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeIn(List<Date> values) {
            addCriterion("final_time in", values, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeNotIn(List<Date> values) {
            addCriterion("final_time not in", values, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeBetween(Date value1, Date value2) {
            addCriterion("final_time between", value1, value2, "finalTime");
            return (Criteria) this;
        }

        public Criteria andFinalTimeNotBetween(Date value1, Date value2) {
            addCriterion("final_time not between", value1, value2, "finalTime");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceIsNull() {
            addCriterion("is_attendance is null");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceIsNotNull() {
            addCriterion("is_attendance is not null");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceEqualTo(Integer value) {
            addCriterion("is_attendance =", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceNotEqualTo(Integer value) {
            addCriterion("is_attendance <>", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceGreaterThan(Integer value) {
            addCriterion("is_attendance >", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_attendance >=", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceLessThan(Integer value) {
            addCriterion("is_attendance <", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceLessThanOrEqualTo(Integer value) {
            addCriterion("is_attendance <=", value, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceIn(List<Integer> values) {
            addCriterion("is_attendance in", values, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceNotIn(List<Integer> values) {
            addCriterion("is_attendance not in", values, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceBetween(Integer value1, Integer value2) {
            addCriterion("is_attendance between", value1, value2, "isAttendance");
            return (Criteria) this;
        }

        public Criteria andIsAttendanceNotBetween(Integer value1, Integer value2) {
            addCriterion("is_attendance not between", value1, value2, "isAttendance");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table staff_attendance_real_rule
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
     * This class corresponds to the database table staff_attendance_real_rule
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
