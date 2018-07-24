package com.mall.supplier.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCounty;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.LogisticTplWayMapper;
import com.mall.supplier.dao.LogisticTplWayfeeAddressMapper;
import com.mall.supplier.dao.LogisticTplWayfeeMapper;
import com.mall.supplier.dao.LogisticTplWaynonefeeAddressMapper;
import com.mall.supplier.dao.LogisticTplWaynonefeeMapper;
import com.mall.supplier.dao.SupplierAreaRegionMapper;
import com.mall.supplier.dao.SupplierAreaTplMapper;
import com.mall.supplier.dao.SupplierLogisticTplMapper;
import com.mall.supplier.dao.SupplierProductAreaMapper;
import com.mall.supplier.dao.SupplierProductLogisticMapper;
import com.mall.supplier.dao.SupplierProductMapper;
import com.mall.supplier.model.LogisticTplWay;
import com.mall.supplier.model.LogisticTplWayfee;
import com.mall.supplier.model.LogisticTplWayfeeAddress;
import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.OrderItemLogistics;
import com.mall.supplier.model.OrderLogisticsFee;
import com.mall.supplier.model.ProductSalesArea;
import com.mall.supplier.model.SGeneralLogisticTpl;
import com.mall.supplier.model.SuplierAreaRegion;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SupplierLogisticTpl;
import com.mall.supplier.model.SupplierProduct;
import com.mall.supplier.model.SupplierProductArea;
import com.mall.supplier.model.SupplierProductLogistic;
import com.mall.supplier.service.GeneralLogisTplService;
import com.mall.supplier.service.SupplierLogisticService;
import com.mall.supplier.service.SupplierRoleManagerService;

@Service
@Transactional
public class SupplierLogisticServiceImpl implements SupplierLogisticService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SupplierLogisticServiceImpl.class);

	@Autowired
	private SupplierLogisticTplMapper supplierLogisticTplMapper;
	@Autowired
	private SupplierLogisticService supplierLogisticService;
	@Autowired
	private SupplierRoleManagerService supplierRoleManagerService;
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;

	@Autowired
	private LogisticTplWayfeeAddressMapper logisticTplWayfeeAddressMapper;
	@Autowired
	private LogisticTplWayfeeMapper logisticTplWayfeeMapper;
	@Autowired
	private LogisticTplWayMapper logisticTplWayMapper;
	@Autowired
	private LogisticTplWaynonefeeAddressMapper logisticTplWaynonefeeAddressMapper;
	@Autowired
	private LogisticTplWaynonefeeMapper logisticTplWaynonefeeMapper;

	@Autowired
	private SupplierProductLogisticMapper supplierProductLogisticMapper;

	@Autowired
	SupplierAreaTplMapper supplierAreaTplMapper;

	@Autowired
	SupplierAreaRegionMapper supplierAreaRegionMapper;
	@Autowired
	SupplierProductAreaMapper supplierProductAreaMapper;
	@Autowired
	SupplierProductMapper supplierProductMapper;

	@Override
	public List<SupplierLogisticTpl> findAllLogisticTpl(
			PageBean<SupplierLogisticTpl> pageBean) {

		return supplierLogisticTplMapper.selectAllTpl(pageBean);
	}

	@Override
	public int saveSupplierProductLogistic(SupplierProductLogistic record) {
		return supplierProductLogisticMapper.insert(record);
	}

	@Override
	public SupplierProductLogistic findSupplierProductLogisticByPidAndSupplierId(
			Long productId, Long supplierId) {
		return supplierProductLogisticMapper
				.selectSupplierProductLogisticByPidAndSid(productId, supplierId);
	}

	@Override
	public int saveSupplierLogisticTpl(SupplierLogisticTpl record) {
		return supplierLogisticTplMapper.insert(record);
	}

	@Override
	public SupplierLogisticTpl findSupplierLogisticTplById(Long tplId) {
		return supplierLogisticTplMapper.selectSupplierLogisticTplById(tplId);
	}

	@Override
	public int saveLogisticTplWay(LogisticTplWay record) {
		return logisticTplWayMapper.insert(record);
	}

	@Override
	public LogisticTplWay findLogisticTplWayById(Long logisticTplId) {
		return logisticTplWayMapper.selectLogisticTplWayByTId(logisticTplId);
	}

	@Override
	public int saveLogisticTplWayfee(LogisticTplWayfee record) {
		return logisticTplWayfeeMapper.insert(record);
	}

	@Override
	public List<LogisticTplWayfee> findLogisticTplWayfeeByLogisticWayId(
			Long logisticWayId) {
		return logisticTplWayfeeMapper
				.selectLogisticTplWayfeeById(logisticWayId);
	}

	@Override
	public int saveLogisticTplWayfeeAddress(LogisticTplWayfeeAddress record) {
		return logisticTplWayfeeAddressMapper.insert(record);
	}

	@Override
	public LogisticTplWayfeeAddress findLogisticTplWayfeeAddressByWaynonefeeId(
			Long logisticTplWaynonefeeId) {
		return logisticTplWayfeeAddressMapper
				.selectLogisticTplWayfeeAddressById(logisticTplWaynonefeeId);
	}

	@Override
	public int saveLogisticTplWaynonefeeAddress(
			LogisticTplWaynonefeeAddress record) {
		return logisticTplWaynonefeeAddressMapper.insert(record);
	}

	@Override
	public LogisticTplWaynonefeeAddress findLogisticTplWaynonefeeAddressByWaynonefeeId(
			Long logisticTplWaynonefeeId) {
		return logisticTplWaynonefeeAddressMapper
				.selectLogisticTplWaynonefeeAddressById(logisticTplWaynonefeeId);
	}

	@Override
	public int saveLogisticTplWaynonefee(LogisticTplWaynonefee record) {
		return logisticTplWaynonefeeMapper.insert(record);
	}

	@Override
	public LogisticTplWaynonefee findLogisticTplWaynonefeeByWaynonefeeId(
			Long logisticWayId) {
		return logisticTplWaynonefeeMapper
				.selectLogisticTplWaynonefeeByWid(logisticWayId);
	}

	@Override
	public SupplierProductArea findSupplierAreaTmplByProductIdAndSupplierId(
			Long productId, Long supplierId) {
		SupplierProductArea area = new SupplierProductArea();
		area = supplierProductAreaMapper.selectBySupperilerIdAndProductId(
				productId, supplierId);
		return area;
	}

	@Override
	public int saveSupplierProductArea(SupplierProductArea record) {

		return supplierProductAreaMapper.insert(record);
	}

	@Override
	public SuplierAreaTpl findSupplierAreaTmplByTmplId(Long id) {

		return supplierAreaTplMapper.selectAreaTmplById(id);
	}

	@Override
	public ProductSalesArea queryAddressWhetherInSalesArea(Long provinceId,
			Long cityId, Long areaId, Long productId, Long supplierId) {
		ProductSalesArea productSalesArea1 = new ProductSalesArea();
		Map<String, Object> salesAreaMap = getProductSalesAreaMap(productId,
				supplierId);
		int isSetAreaTmpl = (Integer) salesAreaMap.get("isSetAreaTmpl");
		Boolean flag = false;
		if (isSetAreaTmpl == 2) {
			productSalesArea1.setStatus(2);
			return productSalesArea1;
		} else if (isSetAreaTmpl == 1) {
			productSalesArea1.setMessage("");
			productSalesArea1.setStatus(1);
			return productSalesArea1;
		} else if (isSetAreaTmpl == 0) {
			productSalesArea1.setStatus(0);
			int limitType = (Integer) salesAreaMap.get("limitType");

			List<Long> proviceIdsList = (List<Long>) salesAreaMap
					.get("proviceIdsList");
			List<Long> cityIdsList = (List<Long>) salesAreaMap
					.get("cityIdsList");
			List<Long> areaIdsList = (List<Long>) salesAreaMap
					.get("areaIdsList");
			if (limitType == 1) {
				productSalesArea1.setTmplType(1);
				if (proviceIdsList.contains(provinceId)) {
					flag = true;
				}

				if (!proviceIdsList.contains(provinceId)
						&& cityIdsList.contains(cityId)) {
					flag = true;
				}
				if (!proviceIdsList.contains(provinceId)
						&& !cityIdsList.contains(cityId)
						&& areaIdsList.contains(areaId)) {
					flag = true;
				}

			}

			if (limitType == 2) {
				productSalesArea1.setTmplType(2);
				if (proviceIdsList.contains(provinceId)
						|| cityIdsList.contains(cityId)
						|| areaIdsList.contains(areaId)) {
					flag = false;
				}

			}

			if (!flag) {
				productSalesArea1.setMessage("此地区不配送");
				productSalesArea1.setStatus(1);
			}

			if (flag) {
				productSalesArea1.setMessage("此地区配送");
				productSalesArea1.setStatus(0);
			}
			productSalesArea1.setProvinceId(provinceId);
			productSalesArea1.setCityId(cityId);
			productSalesArea1.setCountyId(areaId);
			String provinceCityCountyName = baseDataServiceRpc
					.getProvinceCityCountyName(provinceId, cityId, areaId);
			productSalesArea1.setName(provinceCityCountyName);
		}

		return productSalesArea1;
	}

	public Map<String, Object> getProductSalesAreaMap(Long productId,
			Long supplierId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> proviceIdsList = new ArrayList();
		List<Long> cityIdsList = new ArrayList();
		List<Long> areaIdsList = new ArrayList();
		List<Long> limitTypeList = new ArrayList();
		List<Long> supplierAreaTmplList = new ArrayList();
		SupplierProductArea supplierAreaTmpl1 = new SupplierProductArea();
		Boolean flag = false;
		ProductSalesArea productSalesArea = new ProductSalesArea();
		SupplierProductArea supplierProductArea = supplierLogisticService
				.findSupplierAreaTmplByProductIdAndSupplierId(productId,
						supplierId);
		if (null != supplierProductArea) {
			SuplierAreaTpl supplierAreaTmplByTmplId = supplierLogisticService
					.findSupplierAreaTmplByTmplId(supplierProductArea
							.getAreaTplId());

			if (null != supplierAreaTmplByTmplId) {
				// limitType: 1 销售区域， 2限售区域
				int limitType = supplierAreaTmplByTmplId.getType();
				List<SuplierAreaRegion> agentCounty = supplierRoleManagerService
						.findAgentCountyByMould(supplierAreaTmplByTmplId
								.getId());
				if (null != agentCounty && agentCounty.size() > 0) {
					// limitType: 1 销售区域， 2限售区域
					for (SuplierAreaRegion sAreaRegion : agentCounty) {
						if (3 == sAreaRegion.getType()
								&& null != Integer.valueOf(sAreaRegion
										.getProvinceId())) {
							if (!proviceIdsList.contains(sAreaRegion
									.getProvinceId())) {
								proviceIdsList.add((long) sAreaRegion
										.getProvinceId());
							}
						}
						if (2 == sAreaRegion.getType()
								&& null != Integer.valueOf(sAreaRegion
										.getCityId())) {
							if (!cityIdsList.contains(sAreaRegion.getCityId())) {
								cityIdsList.add((long) sAreaRegion.getCityId());
							}
						}
						if (1 == sAreaRegion.getType()
								&& null != Integer.valueOf(sAreaRegion
										.getCountyId())) {
							if (!areaIdsList
									.contains(sAreaRegion.getCountyId())) {
								areaIdsList.add((long) sAreaRegion
										.getCountyId());
							}
						}
					}

					Collections.sort(proviceIdsList);
					Collections.sort(cityIdsList);
					Collections.sort(areaIdsList);
					map.put("proviceIdsList", proviceIdsList);
					map.put("cityIdsList", cityIdsList);
					map.put("areaIdsList", areaIdsList);
					map.put("limitType", limitType);
					map.put("isSetAreaTmpl", 0);

				}
			}

		}
		if (null == supplierProductArea) {
			// supplierAreaTmplList.add((long) 3);
			map.put("isSetAreaTmpl", 1);
		}
		return map;
	}

	// 空provinceid,cityId,areaId
	public ProductSalesArea getProductDefaultSalesArea3(Long productId,
			Long supplierId) {
		ProductSalesArea productSalesArea = new ProductSalesArea();
		Map<String, Object> salesAreaMap = getProductSalesAreaMap(productId,
				supplierId);
		int isSetAreaTmpl = (Integer) salesAreaMap.get("isSetAreaTmpl");
		Boolean flag = false;
		AgentCounty agentCounty2 = new AgentCounty();
		if (isSetAreaTmpl == 1) {
			productSalesArea.setStatus(1);
			return productSalesArea;
		} else if (isSetAreaTmpl == 0) {
			productSalesArea.setStatus(1);

			int limitType = (Integer) salesAreaMap.get("limitType");
			if (limitType == 1) {
				List<AgentCounty> getproductSalesCountysBySalesArea = baseDataServiceRpc
						.getproductSalesCountysBySalesArea(salesAreaMap);
				agentCounty2 = getproductSalesCountysBySalesArea.get(0);
			} else if (limitType == 2) {
				List<AgentCounty> getproductSalesCountysBySalesArea = baseDataServiceRpc
						.getProductSalesCountysByRestrictedArea(salesAreaMap);
				agentCounty2 = getproductSalesCountysBySalesArea.get(0);
			}

			Long provinceid2 = (long) agentCounty2.getProvinceid();
			Long cityid2 = (long) agentCounty2.getCityid();
			Long countyid2 = (long) agentCounty2.getCountyid();
			productSalesArea.setProvinceId(provinceid2);
			productSalesArea.setCityId(cityid2);
			productSalesArea.setCountyId(countyid2);
			productSalesArea.setMessage("此地区配送");
			productSalesArea.setTmplType(1);
			String name = null;
			String provinceCityCountyName = baseDataServiceRpc
					.getProvinceCityCountyName(provinceid2, cityid2, countyid2);
			productSalesArea.setName(provinceCityCountyName);
			productSalesArea.setStatus(0);
		}

		return productSalesArea;
	}

	/*
	 * @Override public BigDecimal
	 * getCustomerOrderLogisticPrice(OrderLogisticsFee orderLogisticsFee) {
	 * BigDecimal orderLogisticprice = new BigDecimal("0");
	 * List<OrderItemLogistics> orderItems = orderLogisticsFee.getOrderItems();
	 * Map<Long, List<OrderItemLogistics>> map=new HashMap<Long,
	 * List<OrderItemLogistics>>(); for (OrderItemLogistics orderItemLogistic :
	 * orderItems) { Long productId = orderItemLogistic.getProductId(); Long
	 * supplierId = orderItemLogistic.getSupplierId(); SupplierProductLogistic
	 * supplierProductLogistic =
	 * supplierLogisticService.findSupplierProductLogisticByPidAndSupplierId
	 * (productId, supplierId); SupplierProductArea supplierAreaTmpl =
	 * supplierLogisticService
	 * .findSupplierAreaTmplByProductIdAndSupplierId(productId, supplierId); //
	 * productLogisticType: 0，需要物流配送； 1，不要物流配送 if (null!=supplierProductLogistic
	 * && supplierProductLogistic.getProductLogisticType()==0) {
	 * 
	 * if (null!=supplierProductLogistic ) { if
	 * (map.containsKey(supplierProductLogistic.getLogisticTplId())) {
	 * List<OrderItemLogistics> list =
	 * map.get(supplierProductLogistic.getLogisticTplId());
	 * list.add(orderItemLogistic); }else{ List<OrderItemLogistics> tmpllist
	 * =new ArrayList(); tmpllist.add(orderItemLogistic);
	 * map.put(supplierProductLogistic.getLogisticTplId(), tmpllist); } } }
	 * 
	 * } Set<Entry<Long, List<OrderItemLogistics>>> entrySet = map.entrySet();
	 * for (Entry<Long, List<OrderItemLogistics>> entry : entrySet) { Long
	 * tmplId = entry.getKey(); List<OrderItemLogistics> value =
	 * entry.getValue(); SupplierLogisticTpl supplierLogisticTpl =
	 * findSupplierLogisticTplById(tmplId); if (null!=supplierLogisticTpl) {
	 * 
	 * for (OrderItemLogistics orderItemLogistics : value) { Long productId =
	 * orderItemLogistics.getProductId(); Long supplierId =
	 * orderItemLogistics.getSupplierId(); BigDecimal quantity =
	 * orderItemLogistics.getQuantity(); BigDecimal itemLogisticPrice= new
	 * BigDecimal("0"); BigDecimal continuedWeightPrice= new BigDecimal("0");
	 * //邮费 1,自定义 2, 卖家付邮费 if (null!=supplierLogisticTpl &&
	 * supplierLogisticTpl.getState()==1) { if
	 * (supplierLogisticTpl.getLogisticFeeType()==1) { //按重量计价 if
	 * (supplierLogisticTpl.getLogisticJijia()==2) { LogisticTplWay
	 * logisticTplWay = findLogisticTplWayById(supplierLogisticTpl.getId());
	 * 
	 * if (null!=logisticTplWay && logisticTplWay.getState()==1) {
	 * 
	 * List<LogisticTplWayfee> logisticTplWayfeeByLogisticWayId =
	 * findLogisticTplWayfeeByLogisticWayId(logisticTplWay.getId());
	 * LogisticTplWayfee logisticTplWayfee = new LogisticTplWayfee();
	 * 
	 * if (null !=logisticTplWayfeeByLogisticWayId) {
	 * logisticTplWayfee=logisticTplWayfeeByLogisticWayId.get(0); //} if
	 * (null!=logisticTplWayfee) { BigDecimal addNum = new
	 * BigDecimal(logisticTplWayfee.getAddNum()); BigDecimal startPrice =
	 * logisticTplWayfee.getStartPrice(); BigDecimal addPrice =
	 * logisticTplWayfee.getAddPrice(); BigDecimal startNum = new
	 * BigDecimal(logisticTplWayfee.getStartNum()); if
	 * (quantity.compareTo(startNum)==1) { continuedWeightPrice =
	 * addPrice.multiply(quantity.subtract(startNum)); }if
	 * (quantity.compareTo(startNum)<1) { continuedWeightPrice = new
	 * BigDecimal("0");
	 * 
	 * }
	 * 
	 * itemLogisticPrice = startPrice.add(continuedWeightPrice);
	 * 
	 * LogisticTplWayfeeAddress logisticTplWayfeeAddress =
	 * findLogisticTplWayfeeAddressByWaynonefeeId(logisticTplWayfee.getId()); if
	 * (null!=logisticTplWayfeeAddress &&
	 * logisticTplWayfeeAddress.getState()==1) {
	 * 
	 * } } } } } } }
	 * 
	 * orderLogisticprice = orderLogisticprice.add(itemLogisticPrice);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return orderLogisticprice; }
	 */

	@Override
	public ProductSalesArea compareProductSellAndReceiveArea(Long provinceId,
			Long cityId, Long areaId, Map<Long, List<Long>> map) {
		Boolean flag = true;
		ProductSalesArea productSalesArea = new ProductSalesArea();
		Map<Object, Object> mp = new HashMap<Object, Object>();
		List<Long> productsList = new ArrayList<Long>();
		List<SupplierProduct> productList = new ArrayList<SupplierProduct>();
		if (!map.isEmpty()) {
			for (Entry<Long, List<Long>> element : map.entrySet()) {
				Long supplierId = element.getKey();
				List<Long> productIdList = element.getValue();
				for (Long productId : productIdList) {
					ProductSalesArea productSalesArea1 = queryAddressWhetherInSalesArea(
							provinceId, cityId, areaId, productId, supplierId);
					if (null != productSalesArea1
							&& productSalesArea1.getStatus() == 1) {
						flag = false;
						SupplierProduct supplierProduct = new SupplierProduct();
						supplierProduct.setProductId(productId);
						supplierProduct.setSupplierId(supplierId);
						productList.add(supplierProduct);

					}

				}
			}

			productSalesArea.setSupplierProducts(productList);
		}
		if (flag) {
			productSalesArea.setStatus(0);
			return productSalesArea;
		} else {
			productSalesArea.setStatus(1);
			return productSalesArea;
		}
	}

	@Override
	public SupplierLogisticTpl findLogisticTplByName(String logisticTplName) {

		return supplierLogisticTplMapper.findLogisticTplByName(logisticTplName);
	}

	@Override
	public LogisticTplWay findLogisticTplWayByTplId(Long tplId) {

		return logisticTplWayMapper.selectLogisticTplWayByTId(tplId);
	}

	@Autowired
	private GeneralLogisTplService generalLogisTplService;

	@Override
	public BigDecimal getCustomerOrderLogisticPrice(
			OrderLogisticsFee orderLogisticsFee, Long provinceId, Long cityId, Long areaId) {
		BigDecimal orderLogisticprice = new BigDecimal(0);
		List<OrderItemLogistics> orderItems = orderLogisticsFee.getOrderItems();
		for (OrderItemLogistics orderItemLogistics : orderItems) {
			Long productId = orderItemLogistics.getProductId();
			Long supplierId = orderItemLogistics.getSupplierId();
			List<SupplierProductLogistic> supplierProductLogistic = supplierProductLogisticMapper.selectSupplierProductLogisticByIds(productId,supplierId);
			for (SupplierProductLogistic spl : supplierProductLogistic) {
				Long type = spl.getProductLogisticType();
				if (type == null || type == 0 || type == 3) {
					// 包邮
					continue;
				}

				Long logisticTplId = spl.getLogisticTplId();
				// 使用的物流模板
				SGeneralLogisticTpl generalLogisticTpl = generalLogisTplService
						.findGeneralLogisticTplById(logisticTplId);
				BigDecimal nonefeePrice = generalLogisticTpl.getNonefeePrice();
				Integer nonefeeNum = generalLogisticTpl.getNonefeeNum();
				String nonefeeProvinceIds = generalLogisticTpl
						.getNonefeeProvinceId();
				String[] split = nonefeeProvinceIds.split(",");
				for (int i = 0; i < split.length; i++) {
					Long nonefeeProvinceId = Long.valueOf(split[i]);
					if (nonefeeProvinceId.equals(provinceId)) {
						// 包邮
						continue;
					}
				}

				BigDecimal quantity = orderItemLogistics.getQuantity();
				// 总数量
				Integer number = quantity.intValue();
				if (number >= nonefeeNum) {
					// 包邮
					continue;
				}

				int price = orderItemLogistics.getPrice().intValue();
				int totalPrice = price * number;
				int prices = nonefeePrice.intValue();
				if (totalPrice >= prices) {
					// 包邮
					continue;
				}
				// 不包邮
				Integer baseQt = generalLogisticTpl.getBaseQt();
				BigDecimal baseFee = generalLogisticTpl.getBaseFee();
				if (baseQt >= number) {
					orderLogisticprice = orderLogisticprice.add(baseFee);
				} else {
					// 增加的件数
					int more = number - baseQt;
					int stepQt = generalLogisticTpl.getStepQt().intValue();
					if ((more / stepQt) >= 1) {
						int stepFee = generalLogisticTpl.getStepFee().intValue();
						int logisticPriceInt = (more / stepQt) * stepFee
								+ baseFee.intValue();
						BigDecimal logisticPrice = new BigDecimal(logisticPriceInt);
						orderLogisticprice = orderLogisticprice.add(logisticPrice);
					}
				}
			}
	
		}

		return orderLogisticprice;
	}

	@Override
	public void saveLogistic(SupplierProductLogistic supplierProductLogistic) {
		// TODO Auto-generated method stub
		supplierProductLogisticMapper.insert(supplierProductLogistic);
	}

	@Override
	public List<SupplierProductLogistic> findSupplierProductLogisticByIds(
			Long productId, Long supplierId) {
		// TODO Auto-generated method stub
		return supplierProductLogisticMapper.selectSupplierProductLogisticByIds(productId, supplierId);
	}

	@Override
	public void deleteAreaByproductId(Long productId) {
		// TODO Auto-generated method stub
		supplierProductAreaMapper.deleteAreaByproductId(productId);
	}

	@Override
	public void deleteProductLogisticsByprodictId(Long productId) {
		// TODO Auto-generated method stub
		supplierProductLogisticMapper.deleteProductLogisticsByprodictId(productId);
	}

	
}
