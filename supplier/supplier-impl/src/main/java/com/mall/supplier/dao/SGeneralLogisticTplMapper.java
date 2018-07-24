package com.mall.supplier.dao;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SGeneralLogisticTpl;
import com.mall.supplier.model.SGeneralLogisticTplExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SGeneralLogisticTplMapper {
    int countByExample(SGeneralLogisticTplExample example);

    int deleteByExample(SGeneralLogisticTplExample example);

    int deleteByPrimaryKey(Long logisticTempId);

    int insert(SGeneralLogisticTpl record);

    int insertSelective(SGeneralLogisticTpl record);

    List<SGeneralLogisticTpl> selectByExample(SGeneralLogisticTplExample example);

    SGeneralLogisticTpl selectByPrimaryKey(Long logisticTempId);

    int updateByExampleSelective(@Param("record") SGeneralLogisticTpl record, @Param("example") SGeneralLogisticTplExample example);

    int updateByExample(@Param("record") SGeneralLogisticTpl record, @Param("example") SGeneralLogisticTplExample example);

    int updateByPrimaryKeySelective(SGeneralLogisticTpl record);

    int updateByPrimaryKey(SGeneralLogisticTpl record);

	List<SGeneralLogisticTpl> selectAllTpl(
			PageBean<SGeneralLogisticTpl> pageBean);
	List<SGeneralLogisticTpl> selectAllTplPageList(
			PageBean<SGeneralLogisticTpl> pageBean);
}