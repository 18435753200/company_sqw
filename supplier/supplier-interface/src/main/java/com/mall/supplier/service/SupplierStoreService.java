package com.mall.supplier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.supplier.model.LogisticTplWay;
import com.mall.supplier.model.LogisticTplWayfee;
import com.mall.supplier.model.LogisticTplWayfeeAddress;
import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.ProductSalesArea;
import com.mall.supplier.model.SupplierLogisticTpl;
import com.mall.supplier.model.SupplierProductLogistic;
import com.mall.supplier.model.SupplierStore;

/**
 * 商户登录接口
 * @author wangdj
 *
 */
public interface SupplierStoreService{


    /**
	 *
	 * @param supplierStore
	 * @return
     */
	public int insertStore(SupplierStore supplierStore);

	/**
	 * 保存模版预览数据
	 * @param spplierId
	 * @param previewContent
     */
	public void updateSupplierStorePreview(int spplierId,String storeName,String previewContent);


	/**
	 * 发布线上模版数据
	 * @param spplierId
	 * @param onlineContent
     */
	public void publishStoreTemplateData(int spplierId, String storeName,String onlineContent,int updateBy);
	
	/**
	 * 根据supplierId查询店铺对象信息
	 * 
	 */
	public SupplierStore findSupplierStoreBySupplierId(int spplierId);


	/**
	 * 设置店铺模版
	 *   默认店铺模版为1
	 * @param supplierId
	 * @param templateId
     */
	public void updateStoreTemplate(int supplierId, int templateId);

}
	
