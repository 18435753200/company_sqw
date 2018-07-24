package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SuplierAreaTpl;

public interface SupplierAreaTplMapper {
	
	int updateTplName(SuplierAreaTpl tpl);
	
	int deleteMould(@Param("id")Long mid);
	
	int insertMould(SuplierAreaTpl tpl);
	
	List<SuplierAreaTpl> selectMouldsBySupplierIds(List<Long> sids);
	
	int countLimit(@Param("name")String name,@Param("supplierId")Long supplierId);
	
	SuplierAreaTpl selectAreaTmplById(Long id);
}
