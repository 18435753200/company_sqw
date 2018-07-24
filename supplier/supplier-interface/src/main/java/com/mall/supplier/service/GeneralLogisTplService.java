package com.mall.supplier.service;

import java.util.List;
import java.util.Map;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SGeneralLogisticTpl;


public interface GeneralLogisTplService {
	void addGeneralLogisTpl(SGeneralLogisticTpl sGeneralLogisticTpl);

	List<SGeneralLogisticTpl> findAllGeneralLogisTpl(PageBean<SGeneralLogisticTpl> pageBean);
	PageBean<SGeneralLogisticTpl> findAllGeneralLogisTpl(SGeneralLogisticTpl sg,Integer page,Integer pageSize);

	void deleteGeneralLogisTpl(Long logisticTempId);

	void updateGeneralLogisTpl(SGeneralLogisticTpl sGeneralLogisticTpl);

	SGeneralLogisticTpl findGeneralLogisticTplById(Long logisticTempId);

	List<SGeneralLogisticTpl> findGeneralLogisTplByAjax(Long supplierId);
}
