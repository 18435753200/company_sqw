package com.mall.supplier.service;

import java.util.List;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierBrand;
import com.mall.supplier.model.SupplierBrandDTO;
/**
 * 品牌接口
 * @author wangdj
 */
public interface SupplierBrandManagerService{
	
	
	/**
	 * 品牌注册
	 */
	public long insertBrand(SupplierBrand supplierBrand);
	
	/**
	 * 修改品牌
	 */
	public int updateBrand(SupplierBrand supplierBrand);

	/**
	 * 根据id查询某个品牌
	 */
	public SupplierBrandDTO findBrandById(Long brandId);
	
	/**
	 * 根据id删除某个品牌
	 */
	public int delete(Long brandId);
	
	/**
	 * 根据供应商ID 查询品牌列表 
	 * @return List<SupplierBrandDTO>
	 */
	public List<SupplierBrandDTO> findSupplierBrandsBySupplierId(Long supplierId);
	
	/**
	 * 根据子供应商ID 查询品牌列表 
	 * @return List<SupplierBrandDTO>
	 */
	public List<SupplierBrandDTO> findSupplierBrandsBySubSupplierId(Long subSupplierId);

	/**
	 *  查询品牌分页
	 * @return  PageBean<SupplierBrand>
	 */
	public PageBean<SupplierBrandDTO> getBrandsPageList(PageBean<SupplierBrandDTO> pageBean);
	
	
	/**
	 * 是否已经独代
	 * @param brandName
	 * @return
	 */
	public int isExclusiveAgent(String brandName);
	
	/**
	 * 
	 *根据系统ID获取供应商品牌信息 
	 * @param sysBrandId
	 * @return
	 */
	public String findDescriptionBySysBrandId(Long supplierId,String sysBrandId);

    
}
	
