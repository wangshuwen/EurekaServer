package com.cst.xinhe.persistence.model.coordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinateExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public CoordinateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
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
     * This method corresponds to the database table coordinate
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
     * This method corresponds to the database table coordinate
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coordinate
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
     * This class corresponds to the database table coordinate
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

        public Criteria andCoordinateIdIsNull() {
            addCriterion("coordinate_id is null");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdIsNotNull() {
            addCriterion("coordinate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdEqualTo(Integer value) {
            addCriterion("coordinate_id =", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdNotEqualTo(Integer value) {
            addCriterion("coordinate_id <>", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdGreaterThan(Integer value) {
            addCriterion("coordinate_id >", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("coordinate_id >=", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdLessThan(Integer value) {
            addCriterion("coordinate_id <", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdLessThanOrEqualTo(Integer value) {
            addCriterion("coordinate_id <=", value, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdIn(List<Integer> values) {
            addCriterion("coordinate_id in", values, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdNotIn(List<Integer> values) {
            addCriterion("coordinate_id not in", values, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdBetween(Integer value1, Integer value2) {
            addCriterion("coordinate_id between", value1, value2, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("coordinate_id not between", value1, value2, "coordinateId");
            return (Criteria) this;
        }

        public Criteria andCoordinateXIsNull() {
            addCriterion("coordinate_x is null");
            return (Criteria) this;
        }

        public Criteria andCoordinateXIsNotNull() {
            addCriterion("coordinate_x is not null");
            return (Criteria) this;
        }

        public Criteria andCoordinateXEqualTo(Double value) {
            addCriterion("coordinate_x =", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXNotEqualTo(Double value) {
            addCriterion("coordinate_x <>", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXGreaterThan(Double value) {
            addCriterion("coordinate_x >", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXGreaterThanOrEqualTo(Double value) {
            addCriterion("coordinate_x >=", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXLessThan(Double value) {
            addCriterion("coordinate_x <", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXLessThanOrEqualTo(Double value) {
            addCriterion("coordinate_x <=", value, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXIn(List<Double> values) {
            addCriterion("coordinate_x in", values, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXNotIn(List<Double> values) {
            addCriterion("coordinate_x not in", values, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXBetween(Double value1, Double value2) {
            addCriterion("coordinate_x between", value1, value2, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateXNotBetween(Double value1, Double value2) {
            addCriterion("coordinate_x not between", value1, value2, "coordinateX");
            return (Criteria) this;
        }

        public Criteria andCoordinateYIsNull() {
            addCriterion("coordinate_y is null");
            return (Criteria) this;
        }

        public Criteria andCoordinateYIsNotNull() {
            addCriterion("coordinate_y is not null");
            return (Criteria) this;
        }

        public Criteria andCoordinateYEqualTo(Double value) {
            addCriterion("coordinate_y =", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYNotEqualTo(Double value) {
            addCriterion("coordinate_y <>", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYGreaterThan(Double value) {
            addCriterion("coordinate_y >", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYGreaterThanOrEqualTo(Double value) {
            addCriterion("coordinate_y >=", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYLessThan(Double value) {
            addCriterion("coordinate_y <", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYLessThanOrEqualTo(Double value) {
            addCriterion("coordinate_y <=", value, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYIn(List<Double> values) {
            addCriterion("coordinate_y in", values, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYNotIn(List<Double> values) {
            addCriterion("coordinate_y not in", values, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYBetween(Double value1, Double value2) {
            addCriterion("coordinate_y between", value1, value2, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateYNotBetween(Double value1, Double value2) {
            addCriterion("coordinate_y not between", value1, value2, "coordinateY");
            return (Criteria) this;
        }

        public Criteria andCoordinateZIsNull() {
            addCriterion("coordinate_z is null");
            return (Criteria) this;
        }

        public Criteria andCoordinateZIsNotNull() {
            addCriterion("coordinate_z is not null");
            return (Criteria) this;
        }

        public Criteria andCoordinateZEqualTo(Double value) {
            addCriterion("coordinate_z =", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZNotEqualTo(Double value) {
            addCriterion("coordinate_z <>", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZGreaterThan(Double value) {
            addCriterion("coordinate_z >", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZGreaterThanOrEqualTo(Double value) {
            addCriterion("coordinate_z >=", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZLessThan(Double value) {
            addCriterion("coordinate_z <", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZLessThanOrEqualTo(Double value) {
            addCriterion("coordinate_z <=", value, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZIn(List<Double> values) {
            addCriterion("coordinate_z in", values, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZNotIn(List<Double> values) {
            addCriterion("coordinate_z not in", values, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZBetween(Double value1, Double value2) {
            addCriterion("coordinate_z between", value1, value2, "coordinateZ");
            return (Criteria) this;
        }

        public Criteria andCoordinateZNotBetween(Double value1, Double value2) {
            addCriterion("coordinate_z not between", value1, value2, "coordinateZ");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table coordinate
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
     * This class corresponds to the database table coordinate
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
