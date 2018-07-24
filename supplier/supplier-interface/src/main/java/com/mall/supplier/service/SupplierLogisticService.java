package com.mall.supplier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.LogisticTplWay;
import com.mall.supplier.model.LogisticTplWayfee;
import com.mall.supplier.model.LogisticTplWayfeeAddress;
import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.OrderLogisticsFee;
import com.mall.supplier.model.ProductSalesArea;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SupplierLogisticTpl;
import com.mall.supplier.model.SupplierProductArea;
import com.mall.supplier.model.SupplierProductLogistic;
/**
 * 物流接口
 * @author wangdj
 */
public interface SupplierLogisticService{
	
	public LogisticTplWay findLogisticTplWayByTplId(Long tplId);
	public  SupplierLogisticTpl findLogisticTplByName(String logisticTplName);
	
	public List<SupplierLogisticTpl> findAllLogisticTpl(PageBean<SupplierLogisticTpl> pageBean);

	/**
	 * 根据商品ID和供应商ID查询tmpl类型
	 * @param productId
	 * @param supplierId
	 * @return
	 */
	public SupplierProductArea findSupplierAreaTmplByProductIdAndSupplierId(Long productId,Long supplierId);
	
	public int saveSupplierProductArea(SupplierProductArea record);
	
	public SuplierAreaTpl findSupplierAreaTmplByTmplId(Long id);
	
	
	public int saveSupplierProductLogistic (SupplierProductLogistic record);
	public SupplierProductLogistic findSupplierProductLogisticByPidAndSupplierId (Long productId ,Long supplierId);
	
	public int saveSupplierLogisticTpl (SupplierLogisticTpl record);
	public SupplierLogisticTpl findSupplierLogisticTplById (Long tplId);
	
	public int saveLogisticTplWay (LogisticTplWay record);
	public LogisticTplWay findLogisticTplWayById (Long logisticTplId);
	
	public int saveLogisticTplWayfee (LogisticTplWayfee record);
	public List<LogisticTplWayfee> findLogisticTplWayfeeByLogisticWayId (Long logisticWayId);
	
	public int saveLogisticTplWayfeeAddress (LogisticTplWayfeeAddress record);
	public LogisticTplWayfeeAddress findLogisticTplWayfeeAddressByWaynonefeeId (Long logisticTplWaynonefeeId);
	
	public int saveLogisticTplWaynonefeeAddress (LogisticTplWaynonefeeAddress record);
	public LogisticTplWaynonefeeAddress findLogisticTplWaynonefeeAddressByWaynonefeeId (Long logisticTplWaynonefeeId);
	
	public int saveLogisticTplWaynonefee (LogisticTplWaynonefee record);
    /**
     * 5.根据选择的物流方式获取相应的费用(废)
     * @param logisticWayId
     * @return
     */
	public LogisticTplWaynonefee findLogisticTplWaynonefeeByWaynonefeeId (Long logisticWayId);
	/**
	 * 商家产品地区限售地区
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @param productId
	 * @param supplierId
	 * @return
	 */
	public ProductSalesArea queryAddressWhetherInSalesArea(Long provinceId,Long cityId,Long areaId,Long productId,Long supplierId);
	/**
	 * 1. 产品详情页:查询商品的限购地区(入参:商品id(根据id查询出供应商id,根据供应商id查询出商品的限购地区));
	 * @param productId
	 * @param supplierId
	 * @return
	 */
	public ProductSalesArea getProductDefaultSalesArea3(Long productId,Long supplierId);
	/**
	 * 
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @param map(key:supplierId value:productId 的list)
	 * @return   0:可以去生成订单; 1:收货地址与其中的商品的销售区域不匹配不可以生成订单
	 */
	public ProductSalesArea compareProductSellAndReceiveArea(Long provinceId, Long cityId, Long areaId, Map<Long, List<Long>> map);
	/**
	 * 获取用户订单的物流费用
	 * @param orderLogisticsFee
	 * @return
	 */
	public BigDecimal getCustomerOrderLogisticPrice(OrderLogisticsFee orderLogisticsFee,Long provinceId, Long cityId, Long areaId);
	/**
	 * 发布商品保存productLogistic
	 * @param supplierProductLogistic
	 */
	public void saveLogistic(SupplierProductLogistic supplierProductLogistic);
	/**
	 * 根据supplierId + productId 查物流方式模板
	 * @param productId
	 * @param supplierId
	 * @return
	 */
	public List<SupplierProductLogistic> findSupplierProductLogisticByIds(
			Long productId, Long supplierId);
	/**
	 * 根据productId删除限购模板
	 * @param productId
	 */
	public void deleteAreaByproductId(Long productId);
	/**
	 * 根据productid删除物流配送表
	 * @param productId
	 */
	public void deleteProductLogisticsByprodictId(Long productId);

}
	
