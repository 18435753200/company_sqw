package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SuplierAreaRegion;

public interface SupplierAreaRegionMapper {
	
	int countDelMould(@Param(value="tplId") long tplId);
	
	int deleteByMouldId(@Param(value="tplId") long tplId);
	
	int insertMouldRegion(List<SuplierAreaRegion> list);
	
	List<SuplierAreaRegion> findRegionByMenuId(Long tplId);

}
