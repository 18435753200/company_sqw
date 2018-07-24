package com.mall.supplier.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierApplyYhqExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierApplyYhqExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(Long value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(Long value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(Long value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(Long value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(Long value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<Long> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<Long> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(Long value1, Long value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(Long value1, Long value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andApplyValIsNull() {
            addCriterion("apply_val is null");
            return (Criteria) this;
        }

        public Criteria andApplyValIsNotNull() {
            addCriterion("apply_val is not null");
            return (Criteria) this;
        }

        public Criteria andApplyValEqualTo(BigDecimal value) {
            addCriterion("apply_val =", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValNotEqualTo(BigDecimal value) {
            addCriterion("apply_val <>", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValGreaterThan(BigDecimal value) {
            addCriterion("apply_val >", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("apply_val >=", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValLessThan(BigDecimal value) {
            addCriterion("apply_val <", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValLessThanOrEqualTo(BigDecimal value) {
            addCriterion("apply_val <=", value, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValIn(List<BigDecimal> values) {
            addCriterion("apply_val in", values, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValNotIn(List<BigDecimal> values) {
            addCriterion("apply_val not in", values, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apply_val between", value1, value2, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyValNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apply_val not between", value1, value2, "applyVal");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeIsNull() {
            addCriterion("apply_datetime is null");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeIsNotNull() {
            addCriterion("apply_datetime is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeEqualTo(Date value) {
            addCriterion("apply_datetime =", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeNotEqualTo(Date value) {
            addCriterion("apply_datetime <>", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeGreaterThan(Date value) {
            addCriterion("apply_datetime >", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_datetime >=", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeLessThan(Date value) {
            addCriterion("apply_datetime <", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_datetime <=", value, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeIn(List<Date> values) {
            addCriterion("apply_datetime in", values, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeNotIn(List<Date> values) {
            addCriterion("apply_datetime not in", values, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeBetween(Date value1, Date value2) {
            addCriterion("apply_datetime between", value1, value2, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andApplyDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_datetime not between", value1, value2, "applyDatetime");
            return (Criteria) this;
        }

        public Criteria andValidValIsNull() {
            addCriterion("valid_val is null");
            return (Criteria) this;
        }

        public Criteria andValidValIsNotNull() {
            addCriterion("valid_val is not null");
            return (Criteria) this;
        }

        public Criteria andValidValEqualTo(BigDecimal value) {
            addCriterion("valid_val =", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValNotEqualTo(BigDecimal value) {
            addCriterion("valid_val <>", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValGreaterThan(BigDecimal value) {
            addCriterion("valid_val >", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("valid_val >=", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValLessThan(BigDecimal value) {
            addCriterion("valid_val <", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValLessThanOrEqualTo(BigDecimal value) {
            addCriterion("valid_val <=", value, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValIn(List<BigDecimal> values) {
            addCriterion("valid_val in", values, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValNotIn(List<BigDecimal> values) {
            addCriterion("valid_val not in", values, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valid_val between", value1, value2, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidValNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valid_val not between", value1, value2, "validVal");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeIsNull() {
            addCriterion("valid_datetime is null");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeIsNotNull() {
            addCriterion("valid_datetime is not null");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeEqualTo(Date value) {
            addCriterion("valid_datetime =", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeNotEqualTo(Date value) {
            addCriterion("valid_datetime <>", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeGreaterThan(Date value) {
            addCriterion("valid_datetime >", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("valid_datetime >=", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeLessThan(Date value) {
            addCriterion("valid_datetime <", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("valid_datetime <=", value, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeIn(List<Date> values) {
            addCriterion("valid_datetime in", values, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeNotIn(List<Date> values) {
            addCriterion("valid_datetime not in", values, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeBetween(Date value1, Date value2) {
            addCriterion("valid_datetime between", value1, value2, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andValidDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("valid_datetime not between", value1, value2, "validDatetime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNull() {
            addCriterion("biz_type is null");
            return (Criteria) this;
        }

        public Criteria andBizTypeIsNotNull() {
            addCriterion("biz_type is not null");
            return (Criteria) this;
        }

        public Criteria andBizTypeEqualTo(Integer value) {
            addCriterion("biz_type =", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotEqualTo(Integer value) {
            addCriterion("biz_type <>", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThan(Integer value) {
            addCriterion("biz_type >", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("biz_type >=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThan(Integer value) {
            addCriterion("biz_type <", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeLessThanOrEqualTo(Integer value) {
            addCriterion("biz_type <=", value, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeIn(List<Integer> values) {
            addCriterion("biz_type in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotIn(List<Integer> values) {
            addCriterion("biz_type not in", values, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeBetween(Integer value1, Integer value2) {
            addCriterion("biz_type between", value1, value2, "bizType");
            return (Criteria) this;
        }

        public Criteria andBizTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("biz_type not between", value1, value2, "bizType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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