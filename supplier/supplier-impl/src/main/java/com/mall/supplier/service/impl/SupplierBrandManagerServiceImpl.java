package com.mall.supplier.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.SupplierBrandMapper;
import com.mall.supplier.model.SupplierBrand;
import com.mall.supplier.model.SupplierBrandDTO;
import com.mall.supplier.service.SupplierBrandManagerService;
import com.mall.supplier.util.PKUtils;

@Service
@Transactional
public class SupplierBrandManagerServiceImpl implements SupplierBrandManagerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierBrandManagerServiceImpl.class);

	@Autowired
	private SupplierBrandMapper supplierBrandMapper;

	public long insertBrand(SupplierBrand supplierBrand) {
		if(null!=supplierBrand){
			Long tempId=PKUtils.getLongPrimaryKey();
			supplierBrand.setBrandId(tempId);
			if(null==supplierBrand.getSystemBrandId()){
				supplierBrand.setSystemBrandId(tempId);	
			}
			return supplierBrandMapper.insertSelective(supplierBrand);
		}else{
			return 0;
		}
	}

	public int updateBrand(SupplierBrand supplierBrand) {
		return supplierBrandMapper.updateByPrimaryKeySelective(supplierBrand);
	}

	public int delete(Long brandId) {
		SupplierBrand supplierBrand =new SupplierBrand();
		supplierBrand.setBrandId(brandId);
		supplierBrand.setStatus(3);
		return supplierBrandMapper.updateByPrimaryKeySelective(supplierBrand);
	}

	public List<SupplierBrandDTO> findSupplierBrandsBySupplierId(Long supplierId) {
		LOGGER.info("findSupplierBrandsBySupplierId  supplierId:"+supplierId);
		 List<SupplierBrandDTO>  rs=	supplierBrandMapper.findSupplierBrandsBySupplierId(supplierId);
		LOGGER.info("findSupplierBrandsBySupplierId  rs:"+rs);
		return rs;
	}
	public List<SupplierBrandDTO> findSupplierBrandsBySubSupplierId(Long subSupplierId) {
		LOGGER.info("findSupplierBrandsBySubSupplierId  subSupplierId:"+subSupplierId);
		 List<SupplierBrandDTO>  rs=	supplierBrandMapper.findSupplierBrandsBySubSupplierId(subSupplierId);
		LOGGER.info("findSupplierBrandsBySubSupplierId  rs:"+rs);
		return rs;
	}

	public PageBean<SupplierBrandDTO> getBrandsPageList(
			PageBean<SupplierBrandDTO> pageBean) {
		List<SupplierBrandDTO> list = new ArrayList<SupplierBrandDTO>();
		list =  supplierBrandMapper.getBrandPageList(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}

	public SupplierBrandDTO findBrandById(Long brandId) {
		return supplierBrandMapper.selectByPrimaryKey(brandId);
	}

	@Override
	public int isExclusiveAgent(String brandName) {
		return supplierBrandMapper.isExclusiveAgent(brandName);
	}

	@Override
	public String findDescriptionBySysBrandId(Long supplierId,String sysBrandId) {
		return supplierBrandMapper.findDescriptionBySysBrandId(supplierId,sysBrandId);
	}
	
}
