package com.mall.supplier.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.dao.SupplierStoreMapper;

import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierStore;
import com.mall.supplier.service.SupplierStoreService;

@Service
@Transactional
public class SupplierStoreServiceImpl implements SupplierStoreService {

	Logger logger = Logger.getLogger(SupplierStoreServiceImpl.class);

	@Autowired
	private SupplierStoreMapper supplierStoreMapper;
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Override
	public int insertStore(SupplierStore supplierStore) {
		supplierStoreMapper.insertSelective(supplierStore);
		return supplierStore.getSupplierId();
	}

	@Override
	public void updateSupplierStorePreview(int spplierId,String storeName,String previewContent) {
		checkSaveData(spplierId,storeName);
		if(StringUtils.isNotBlank(previewContent)){
			supplierStoreMapper.updateSupplierStorePreview(spplierId,storeName,previewContent);
		}
	}

	@Override
	public void publishStoreTemplateData(int spplierId, String storeName,String onlineContent,int updateBy) {
		checkSaveData(spplierId,storeName);
		if(StringUtils.isNotBlank(onlineContent)){
			supplierStoreMapper.updateSupplierStoreOnline(spplierId,storeName,onlineContent,updateBy);
		}
	}

	@Override
	public SupplierStore findSupplierStoreBySupplierId(int spplierId) {
		return supplierStoreMapper.findSupplierStoreBySupplierId(spplierId);
	}

	@Override
	public void updateStoreTemplate(int supplierId, int templateId) {
		supplierStoreMapper.updateStoreTemplate(supplierId,templateId < 1?1:templateId);
	}
	
	public void checkSaveData(int supplierId,String storeName){
		if(supplierId > 0){
			SupplierStore store = supplierStoreMapper.findSupplierStoreBySupplierId(supplierId);
			if(store == null){
				SupplierStore tempStore = new SupplierStore();
				tempStore.setSupplierId(supplierId);
				
				
				// 如果店铺名称为null，需要把供应商的name插进去
				if (StringUtils.isBlank(storeName)) {
					Supplier supplier = supplierMapper.selectByPrimaryKey((long)supplierId);
					if(supplier != null){
						storeName = supplier.getName();
					}
				}
				tempStore.setStoreName(storeName);
				
				insertStore(tempStore);
			}
			
		}
	}
}
