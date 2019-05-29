package com.cst.xinhe.persistence.model.warning_area;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarningAreaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public WarningAreaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
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
     * This method corresponds to the database table warning_area
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
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
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
     * This class corresponds to the database table warning_area
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

        public Criteria andWarningAreaNameIsNull() {
            addCriterion("warning_area_name is null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameIsNotNull() {
            addCriterion("warning_area_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameEqualTo(String value) {
            addCriterion("warning_area_name =", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameNotEqualTo(String value) {
            addCriterion("warning_area_name <>", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameGreaterThan(String value) {
            addCriterion("warning_area_name >", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("warning_area_name >=", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameLessThan(String value) {
            addCriterion("warning_area_name <", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameLessThanOrEqualTo(String value) {
            addCriterion("warning_area_name <=", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameLike(String value) {
            addCriterion("warning_area_name like", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameNotLike(String value) {
            addCriterion("warning_area_name not like", value, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameIn(List<String> values) {
            addCriterion("warning_area_name in", values, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameNotIn(List<String> values) {
            addCriterion("warning_area_name not in", values, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameBetween(String value1, String value2) {
            addCriterion("warning_area_name between", value1, value2, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaNameNotBetween(String value1, String value2) {
            addCriterion("warning_area_name not between", value1, value2, "warningAreaName");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescIsNull() {
            addCriterion("warning_area_desc is null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescIsNotNull() {
            addCriterion("warning_area_desc is not null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescEqualTo(String value) {
            addCriterion("warning_area_desc =", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescNotEqualTo(String value) {
            addCriterion("warning_area_desc <>", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescGreaterThan(String value) {
            addCriterion("warning_area_desc >", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescGreaterThanOrEqualTo(String value) {
            addCriterion("warning_area_desc >=", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescLessThan(String value) {
            addCriterion("warning_area_desc <", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescLessThanOrEqualTo(String value) {
            addCriterion("warning_area_desc <=", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescLike(String value) {
            addCriterion("warning_area_desc like", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescNotLike(String value) {
            addCriterion("warning_area_desc not like", value, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescIn(List<String> values) {
            addCriterion("warning_area_desc in", values, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescNotIn(List<String> values) {
            addCriterion("warning_area_desc not in", values, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescBetween(String value1, String value2) {
            addCriterion("warning_area_desc between", value1, value2, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andWarningAreaDescNotBetween(String value1, String value2) {
            addCriterion("warning_area_desc not between", value1, value2, "warningAreaDesc");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeIsNull() {
            addCriterion("warning_area_type is null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeIsNotNull() {
            addCriterion("warning_area_type is not null");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeEqualTo(Integer value) {
            addCriterion("warning_area_type =", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeNotEqualTo(Integer value) {
            addCriterion("warning_area_type <>", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeGreaterThan(Integer value) {
            addCriterion("warning_area_type >", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("warning_area_type >=", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeLessThan(Integer value) {
            addCriterion("warning_area_type <", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeLessThanOrEqualTo(Integer value) {
            addCriterion("warning_area_type <=", value, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeIn(List<Integer> values) {
            addCriterion("warning_area_type in", values, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeNotIn(List<Integer> values) {
            addCriterion("warning_area_type not in", values, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeBetween(Integer value1, Integer value2) {
            addCriterion("warning_area_type between", value1, value2, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andWarningAreaTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("warning_area_type not between", value1, value2, "warningAreaType");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeIsNull() {
            addCriterion("residence_time is null");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeIsNotNull() {
            addCriterion("residence_time is not null");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeEqualTo(String value) {
            addCriterion("residence_time =", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeNotEqualTo(String value) {
            addCriterion("residence_time <>", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeGreaterThan(String value) {
            addCriterion("residence_time >", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeGreaterThanOrEqualTo(String value) {
            addCriterion("residence_time >=", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeLessThan(String value) {
            addCriterion("residence_time <", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeLessThanOrEqualTo(String value) {
            addCriterion("residence_time <=", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeLike(String value) {
            addCriterion("residence_time like", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeNotLike(String value) {
            addCriterion("residence_time not like", value, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeIn(List<String> values) {
            addCriterion("residence_time in", values, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeNotIn(List<String> values) {
            addCriterion("residence_time not in", values, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeBetween(String value1, String value2) {
            addCriterion("residence_time between", value1, value2, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andResidenceTimeNotBetween(String value1, String value2) {
            addCriterion("residence_time not between", value1, value2, "residenceTime");
            return (Criteria) this;
        }

        public Criteria andContainNumberIsNull() {
            addCriterion("contain_number is null");
            return (Criteria) this;
        }

        public Criteria andContainNumberIsNotNull() {
            addCriterion("contain_number is not null");
            return (Criteria) this;
        }

        public Criteria andContainNumberEqualTo(Integer value) {
            addCriterion("contain_number =", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberNotEqualTo(Integer value) {
            addCriterion("contain_number <>", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberGreaterThan(Integer value) {
            addCriterion("contain_number >", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("contain_number >=", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberLessThan(Integer value) {
            addCriterion("contain_number <", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberLessThanOrEqualTo(Integer value) {
            addCriterion("contain_number <=", value, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberIn(List<Integer> values) {
            addCriterion("contain_number in", values, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberNotIn(List<Integer> values) {
            addCriterion("contain_number not in", values, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberBetween(Integer value1, Integer value2) {
            addCriterion("contain_number between", value1, value2, "containNumber");
            return (Criteria) this;
        }

        public Criteria andContainNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("contain_number not between", value1, value2, "containNumber");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table warning_area
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
     * This class corresponds to the database table warning_area
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