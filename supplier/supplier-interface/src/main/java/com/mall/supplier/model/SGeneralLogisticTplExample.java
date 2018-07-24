package com.mall.supplier.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SGeneralLogisticTplExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SGeneralLogisticTplExample() {
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

        public Criteria andLogisticTempIdIsNull() {
            addCriterion("logistic_temp_id is null");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdIsNotNull() {
            addCriterion("logistic_temp_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdEqualTo(Long value) {
            addCriterion("logistic_temp_id =", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdNotEqualTo(Long value) {
            addCriterion("logistic_temp_id <>", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdGreaterThan(Long value) {
            addCriterion("logistic_temp_id >", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdGreaterThanOrEqualTo(Long value) {
            addCriterion("logistic_temp_id >=", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdLessThan(Long value) {
            addCriterion("logistic_temp_id <", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdLessThanOrEqualTo(Long value) {
            addCriterion("logistic_temp_id <=", value, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdIn(List<Long> values) {
            addCriterion("logistic_temp_id in", values, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdNotIn(List<Long> values) {
            addCriterion("logistic_temp_id not in", values, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdBetween(Long value1, Long value2) {
            addCriterion("logistic_temp_id between", value1, value2, "logisticTempId");
            return (Criteria) this;
        }

        public Criteria andLogisticTempIdNotBetween(Long value1, Long value2) {
            addCriterion("logistic_temp_id not between", value1, value2, "logisticTempId");
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

        public Criteria andLogisticTempNameIsNull() {
            addCriterion("logistic_temp_name is null");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameIsNotNull() {
            addCriterion("logistic_temp_name is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameEqualTo(String value) {
            addCriterion("logistic_temp_name =", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameNotEqualTo(String value) {
            addCriterion("logistic_temp_name <>", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameGreaterThan(String value) {
            addCriterion("logistic_temp_name >", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameGreaterThanOrEqualTo(String value) {
            addCriterion("logistic_temp_name >=", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameLessThan(String value) {
            addCriterion("logistic_temp_name <", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameLessThanOrEqualTo(String value) {
            addCriterion("logistic_temp_name <=", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameLike(String value) {
            addCriterion("logistic_temp_name like", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameNotLike(String value) {
            addCriterion("logistic_temp_name not like", value, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameIn(List<String> values) {
            addCriterion("logistic_temp_name in", values, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameNotIn(List<String> values) {
            addCriterion("logistic_temp_name not in", values, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameBetween(String value1, String value2) {
            addCriterion("logistic_temp_name between", value1, value2, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andLogisticTempNameNotBetween(String value1, String value2) {
            addCriterion("logistic_temp_name not between", value1, value2, "logisticTempName");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdIsNull() {
            addCriterion("province_start_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdIsNotNull() {
            addCriterion("province_start_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdEqualTo(Integer value) {
            addCriterion("province_start_id =", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdNotEqualTo(Integer value) {
            addCriterion("province_start_id <>", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdGreaterThan(Integer value) {
            addCriterion("province_start_id >", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("province_start_id >=", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdLessThan(Integer value) {
            addCriterion("province_start_id <", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdLessThanOrEqualTo(Integer value) {
            addCriterion("province_start_id <=", value, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdIn(List<Integer> values) {
            addCriterion("province_start_id in", values, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdNotIn(List<Integer> values) {
            addCriterion("province_start_id not in", values, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdBetween(Integer value1, Integer value2) {
            addCriterion("province_start_id between", value1, value2, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andProvinceStartIdNotBetween(Integer value1, Integer value2) {
            addCriterion("province_start_id not between", value1, value2, "provinceStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdIsNull() {
            addCriterion("city_start_id is null");
            return (Criteria) this;
        }

        public Criteria andCityStartIdIsNotNull() {
            addCriterion("city_start_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityStartIdEqualTo(Integer value) {
            addCriterion("city_start_id =", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdNotEqualTo(Integer value) {
            addCriterion("city_start_id <>", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdGreaterThan(Integer value) {
            addCriterion("city_start_id >", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_start_id >=", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdLessThan(Integer value) {
            addCriterion("city_start_id <", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_start_id <=", value, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdIn(List<Integer> values) {
            addCriterion("city_start_id in", values, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdNotIn(List<Integer> values) {
            addCriterion("city_start_id not in", values, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdBetween(Integer value1, Integer value2) {
            addCriterion("city_start_id between", value1, value2, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andCityStartIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_start_id not between", value1, value2, "cityStartId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBaseQtIsNull() {
            addCriterion("base_qt is null");
            return (Criteria) this;
        }

        public Criteria andBaseQtIsNotNull() {
            addCriterion("base_qt is not null");
            return (Criteria) this;
        }

        public Criteria andBaseQtEqualTo(Integer value) {
            addCriterion("base_qt =", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtNotEqualTo(Integer value) {
            addCriterion("base_qt <>", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtGreaterThan(Integer value) {
            addCriterion("base_qt >", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtGreaterThanOrEqualTo(Integer value) {
            addCriterion("base_qt >=", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtLessThan(Integer value) {
            addCriterion("base_qt <", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtLessThanOrEqualTo(Integer value) {
            addCriterion("base_qt <=", value, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtIn(List<Integer> values) {
            addCriterion("base_qt in", values, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtNotIn(List<Integer> values) {
            addCriterion("base_qt not in", values, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtBetween(Integer value1, Integer value2) {
            addCriterion("base_qt between", value1, value2, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseQtNotBetween(Integer value1, Integer value2) {
            addCriterion("base_qt not between", value1, value2, "baseQt");
            return (Criteria) this;
        }

        public Criteria andBaseFeeIsNull() {
            addCriterion("base_fee is null");
            return (Criteria) this;
        }

        public Criteria andBaseFeeIsNotNull() {
            addCriterion("base_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBaseFeeEqualTo(BigDecimal value) {
            addCriterion("base_fee =", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeNotEqualTo(BigDecimal value) {
            addCriterion("base_fee <>", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeGreaterThan(BigDecimal value) {
            addCriterion("base_fee >", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("base_fee >=", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeLessThan(BigDecimal value) {
            addCriterion("base_fee <", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("base_fee <=", value, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeIn(List<BigDecimal> values) {
            addCriterion("base_fee in", values, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeNotIn(List<BigDecimal> values) {
            addCriterion("base_fee not in", values, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("base_fee between", value1, value2, "baseFee");
            return (Criteria) this;
        }

        public Criteria andBaseFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("base_fee not between", value1, value2, "baseFee");
            return (Criteria) this;
        }

        public Criteria andStepQtIsNull() {
            addCriterion("step_qt is null");
            return (Criteria) this;
        }

        public Criteria andStepQtIsNotNull() {
            addCriterion("step_qt is not null");
            return (Criteria) this;
        }

        public Criteria andStepQtEqualTo(Integer value) {
            addCriterion("step_qt =", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtNotEqualTo(Integer value) {
            addCriterion("step_qt <>", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtGreaterThan(Integer value) {
            addCriterion("step_qt >", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_qt >=", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtLessThan(Integer value) {
            addCriterion("step_qt <", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtLessThanOrEqualTo(Integer value) {
            addCriterion("step_qt <=", value, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtIn(List<Integer> values) {
            addCriterion("step_qt in", values, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtNotIn(List<Integer> values) {
            addCriterion("step_qt not in", values, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtBetween(Integer value1, Integer value2) {
            addCriterion("step_qt between", value1, value2, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepQtNotBetween(Integer value1, Integer value2) {
            addCriterion("step_qt not between", value1, value2, "stepQt");
            return (Criteria) this;
        }

        public Criteria andStepFeeIsNull() {
            addCriterion("step_fee is null");
            return (Criteria) this;
        }

        public Criteria andStepFeeIsNotNull() {
            addCriterion("step_fee is not null");
            return (Criteria) this;
        }

        public Criteria andStepFeeEqualTo(BigDecimal value) {
            addCriterion("step_fee =", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeNotEqualTo(BigDecimal value) {
            addCriterion("step_fee <>", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeGreaterThan(BigDecimal value) {
            addCriterion("step_fee >", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("step_fee >=", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeLessThan(BigDecimal value) {
            addCriterion("step_fee <", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("step_fee <=", value, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeIn(List<BigDecimal> values) {
            addCriterion("step_fee in", values, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeNotIn(List<BigDecimal> values) {
            addCriterion("step_fee not in", values, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("step_fee between", value1, value2, "stepFee");
            return (Criteria) this;
        }

        public Criteria andStepFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("step_fee not between", value1, value2, "stepFee");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceIsNull() {
            addCriterion("nonefee_price is null");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceIsNotNull() {
            addCriterion("nonefee_price is not null");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceEqualTo(BigDecimal value) {
            addCriterion("nonefee_price =", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceNotEqualTo(BigDecimal value) {
            addCriterion("nonefee_price <>", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceGreaterThan(BigDecimal value) {
            addCriterion("nonefee_price >", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nonefee_price >=", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceLessThan(BigDecimal value) {
            addCriterion("nonefee_price <", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nonefee_price <=", value, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceIn(List<BigDecimal> values) {
            addCriterion("nonefee_price in", values, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceNotIn(List<BigDecimal> values) {
            addCriterion("nonefee_price not in", values, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nonefee_price between", value1, value2, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nonefee_price not between", value1, value2, "nonefeePrice");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumIsNull() {
            addCriterion("nonefee_num is null");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumIsNotNull() {
            addCriterion("nonefee_num is not null");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumEqualTo(Integer value) {
            addCriterion("nonefee_num =", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumNotEqualTo(Integer value) {
            addCriterion("nonefee_num <>", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumGreaterThan(Integer value) {
            addCriterion("nonefee_num >", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("nonefee_num >=", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumLessThan(Integer value) {
            addCriterion("nonefee_num <", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumLessThanOrEqualTo(Integer value) {
            addCriterion("nonefee_num <=", value, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumIn(List<Integer> values) {
            addCriterion("nonefee_num in", values, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumNotIn(List<Integer> values) {
            addCriterion("nonefee_num not in", values, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumBetween(Integer value1, Integer value2) {
            addCriterion("nonefee_num between", value1, value2, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("nonefee_num not between", value1, value2, "nonefeeNum");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdIsNull() {
            addCriterion("nonefee_province_id is null");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdIsNotNull() {
            addCriterion("nonefee_province_id is not null");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdEqualTo(String value) {
            addCriterion("nonefee_province_id =", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdNotEqualTo(String value) {
            addCriterion("nonefee_province_id <>", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdGreaterThan(String value) {
            addCriterion("nonefee_province_id >", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdGreaterThanOrEqualTo(String value) {
            addCriterion("nonefee_province_id >=", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdLessThan(String value) {
            addCriterion("nonefee_province_id <", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdLessThanOrEqualTo(String value) {
            addCriterion("nonefee_province_id <=", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdLike(String value) {
            addCriterion("nonefee_province_id like", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdNotLike(String value) {
            addCriterion("nonefee_province_id not like", value, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdIn(List<String> values) {
            addCriterion("nonefee_province_id in", values, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdNotIn(List<String> values) {
            addCriterion("nonefee_province_id not in", values, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdBetween(String value1, String value2) {
            addCriterion("nonefee_province_id between", value1, value2, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andNonefeeProvinceIdNotBetween(String value1, String value2) {
            addCriterion("nonefee_province_id not between", value1, value2, "nonefeeProvinceId");
            return (Criteria) this;
        }

        public Criteria andTplTypeIsNull() {
            addCriterion("tpl_type is null");
            return (Criteria) this;
        }

        public Criteria andTplTypeIsNotNull() {
            addCriterion("tpl_type is not null");
            return (Criteria) this;
        }

        public Criteria andTplTypeEqualTo(Integer value) {
            addCriterion("tpl_type =", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeNotEqualTo(Integer value) {
            addCriterion("tpl_type <>", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeGreaterThan(Integer value) {
            addCriterion("tpl_type >", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tpl_type >=", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeLessThan(Integer value) {
            addCriterion("tpl_type <", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeLessThanOrEqualTo(Integer value) {
            addCriterion("tpl_type <=", value, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeIn(List<Integer> values) {
            addCriterion("tpl_type in", values, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeNotIn(List<Integer> values) {
            addCriterion("tpl_type not in", values, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeBetween(Integer value1, Integer value2) {
            addCriterion("tpl_type between", value1, value2, "tplType");
            return (Criteria) this;
        }

        public Criteria andTplTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("tpl_type not between", value1, value2, "tplType");
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