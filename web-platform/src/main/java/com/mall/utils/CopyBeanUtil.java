package com.mall.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.mall.dealer.product.dto.DealerProductAttrDTO;
import com.mall.dealer.product.dto.DealerProductObjectDTO;
import com.mall.dealer.product.dto.DealerProductSkuDTO;
import com.mall.dealer.product.po.B2cProductDetail;
import com.mall.dealer.product.po.B2cProductSellNum;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.dealer.product.po.DealerProductAttach;
import com.mall.dealer.product.po.DealerProductAttr;
import com.mall.dealer.product.po.DealerProductAttrval;
import com.mall.dealer.product.po.DealerProductAudit;
import com.mall.dealer.product.po.DealerProductBase;
import com.mall.dealer.product.po.DealerProductBaseEn;
import com.mall.dealer.product.po.DealerProductBuyAttrval;
import com.mall.dealer.product.po.DealerProductDetail;
import com.mall.dealer.product.po.DealerProductInventory;
import com.mall.dealer.product.po.DealerProductPackage;
import com.mall.dealer.product.po.DealerProductPrice;
import com.mall.dealer.product.po.DealerProductSaleSetting;
import com.mall.dealer.product.po.DealerProductSku;
import com.mall.dealer.product.po.DealerProductSkuAttrval;
import com.mall.dealer.product.po.DealerProductWeightRange;
import com.mall.dealer.product.po.DealerProductWholesaleRange;
import com.mall.supplier.product.dto.SupplierProductAttrDTO;
import com.mall.supplier.product.dto.SupplierProductSkuDTO;
import com.mall.supplier.product.po.SupplierProductAttr;
import com.mall.supplier.product.po.SupplierProductAttrval;
import com.mall.supplier.product.po.SupplierProductBuyAttrval;
import com.mall.supplier.product.po.SupplierProductSkuAttrval;

/**
 * @author zhouzb
 *
 */
public class CopyBeanUtil {

	/**.
	 * @param oldProductVO DealerProductObjectDTO
	 * @param buyMap ,List<Integer>
	 * @return DealerProductObjectDTO
	 */
	public static DealerProductObjectDTO copy(DealerProductObjectDTO oldProductVO,List<Integer> buyMap){
			DealerProductObjectDTO productVO = new DealerProductObjectDTO();
			productVO.setDealerid(oldProductVO.getDealerid());
			//商品核心信息
			DealerProduct product = new DealerProduct();
			BeanUtils.copyProperties(oldProductVO.getDealerProduct(), product);
			productVO.setDealerProduct(product);
			
			
			//b2c商品信息
			B2cProductDetail b2cProductDetail = new B2cProductDetail();
			BeanUtils.copyProperties(oldProductVO.getB2cProductDetail(), b2cProductDetail);
			productVO.setB2cProductDetail(b2cProductDetail);
			
			
			//附件信息
			List<DealerProductAttach> attachs = new ArrayList<DealerProductAttach>();
			for (DealerProductAttach productAttach : oldProductVO.getDealerProductAttachs()) {
				DealerProductAttach attach = new DealerProductAttach();
				BeanUtils.copyProperties(productAttach, attach);
				attachs.add(attach);
			}
			if(attachs!=null&& attachs.size()>0){
				productVO.setDealerProductAttachs(attachs);
			}
			//商品审核信息
			List<DealerProductAudit> audits = new ArrayList<DealerProductAudit>();
			for(DealerProductAudit productAudit:audits){
				DealerProductAudit audit = new DealerProductAudit();
				BeanUtils.copyProperties(productAudit, audit);
				audits.add(audit);
			}
			if(audits!=null&& audits.size()>0){
				productVO.setDealerProductAudits(audits);
			}
			
			// 产品 基本&国际化信息
			DealerProductBase base = new DealerProductBase();
			BeanUtils.copyProperties(oldProductVO.getDealerProductBase(), base);
			productVO.setDealerProductBase(base);
			DealerProductBaseEn baseEn = new DealerProductBaseEn();
			BeanUtils.copyProperties(oldProductVO.getDealerProductBaseEn(), baseEn);
			productVO.setDealerProductBaseEn(baseEn);
			
			// 商品备货信息
			DealerProductInventory inventory = new DealerProductInventory();
			BeanUtils.copyProperties(oldProductVO.getDealerProductInventorie(), inventory);
			productVO.setDealerProductInventorie(inventory);
			
			// 商品包装信息
			DealerProductPackage productPackage = new DealerProductPackage();
			BeanUtils.copyProperties(oldProductVO.getDealerProductPackage(), productPackage);
			productVO.setDealerProductPackage(productPackage);
			
			// 商品销售信息表
			DealerProductSaleSetting saleSetting = new DealerProductSaleSetting();
			BeanUtils.copyProperties(oldProductVO.getDealerProductSaleSetting(), saleSetting);
			productVO.setDealerProductSaleSetting(saleSetting);
			
			// 商品阶梯重量表
			DealerProductWeightRange weightRange = new DealerProductWeightRange();
			BeanUtils.copyProperties(oldProductVO.getDealerProductWeightRange(), weightRange);
			productVO.setDealerProductWeightRange(weightRange);
			
			// 商品批发折扣表-阶梯价格
			List<DealerProductWholesaleRange> ranges = new ArrayList<DealerProductWholesaleRange>();
			for(DealerProductWholesaleRange range : oldProductVO.getDealerProductWholesaleRanges()){
				DealerProductWholesaleRange wholesaleRange = new DealerProductWholesaleRange();
				BeanUtils.copyProperties(range, wholesaleRange);
				ranges.add(wholesaleRange);
			}
			if(ranges.size()>0&&ranges!=null){
				productVO.setDealerProductWholesaleRanges(ranges);
			}
			
			// 商品明细表
			DealerProductDetail detail = new DealerProductDetail();
			BeanUtils.copyProperties(oldProductVO.getDealerProductDetail(), detail);
			productVO.setDealerProductDetail(detail);
			
			//商品属性属性值信息
			Map<Integer,Integer> butValueAttrIndex = new HashMap<Integer, Integer>();
			Integer newAttrIndex = null;
			
			//商品属性属性值信息
			List<DealerProductAttrDTO> attrVOs = new ArrayList<DealerProductAttrDTO>();
			for(int i = 0;i < oldProductVO.getDealerProductAttrDTOs().size();i++){
				DealerProductAttrDTO supplierProductAttrVO = new DealerProductAttrDTO(); 
				DealerProductAttrDTO supplierProductAttrDTO = oldProductVO.getDealerProductAttrDTOs().get(i);
				BeanUtils.copyProperties(supplierProductAttrDTO, supplierProductAttrVO);
				SupplierProductAttr attr = new SupplierProductAttr();
				BeanUtils.copyProperties(oldProductVO.getDealerProductAttrDTOs().get(i).getDealerProductAttr(), attr);
				List<DealerProductAttrval> supplierProductAttrvals = new ArrayList<DealerProductAttrval>();
				
				for(int x =0;supplierProductAttrDTO.getDealerProductAttrvals()!=null
						&&x<supplierProductAttrDTO.getDealerProductAttrvals().size();x++){
					DealerProductAttrval attrval=oldProductVO
							.getDealerProductAttrDTOs().get(i).getDealerProductAttrvals().get(x);
					DealerProductAttrval supplierProductAttrval = new DealerProductAttrval();
					BeanUtils.copyProperties(attrval, supplierProductAttrval);
					if(supplierProductAttrval.getLineAttrvalNameCn()!=null 
							&& !"".equals(supplierProductAttrval.getLineAttrvalNameCn())){
						supplierProductAttrval.setAttrNameCn(attr.getAttrNameCn());
						supplierProductAttrvals.add(supplierProductAttrval);
						if(supplierProductAttrDTO.getDealerProductAttr().getBuyAttr()==1)
							butValueAttrIndex.put(x, supplierProductAttrvals.size()-1);
						
						for(DealerProductSkuDTO skuVo : oldProductVO.getDealerProductSkuDTOs()){
							for(DealerProductSkuAttrval skuVal: skuVo.getDealerProductSkuAttrvals()){
								if(skuVal.getAttrId() == i){
									if(skuVal.getAttrValId() == x){
										skuVal.setAttrId(Long.parseLong(attrVOs.size()+""));
										skuVal.setAttrValId(supplierProductAttrvals.size()-1l);
									}
								}
							}
						}
					}
					
				}
				
				if(supplierProductAttrvals.size()>0){
					supplierProductAttrVO.setDealerProductAttrvals(supplierProductAttrvals);
					attrVOs.add(supplierProductAttrVO);
					if(supplierProductAttrVO.getDealerProductAttr().getBuyAttr()==1)
						newAttrIndex = attrVOs.size()-1;
					
					for(DealerProductSkuDTO skuVo : oldProductVO.getDealerProductSkuDTOs()){
						for(DealerProductSkuAttrval skuVal: skuVo.getDealerProductSkuAttrvals()){
							if(skuVal.getAttrId() == i){
								skuVal.setAttrId(attrVOs.size()-1l);
							}
						}
					}
				}
				
				if(oldProductVO.getDealerProductAttrDTOs().get(i).getMap()!=null
						&&oldProductVO.getDealerProductAttrDTOs().get(i).getMap().size()>0){
					Map<Integer, List<DealerProductBuyAttrval>> map = 
							oldProductVO.getDealerProductAttrDTOs().get(i).getMap();
					Map<Integer, List<DealerProductBuyAttrval>> map2 = 
							new LinkedHashMap<Integer, List<DealerProductBuyAttrval>>();
					if(supplierProductAttrvals.size()>0){
						for(int x = 0;x<buyMap.size();x++){
							List<DealerProductBuyAttrval> list = map.get(buyMap.get(x));
							List<DealerProductBuyAttrval> buyVals = new ArrayList<DealerProductBuyAttrval>();
							Integer integer  = 0;
							if(list!=null&&list.size()>0){
								for(DealerProductBuyAttrval buyattr:list){
									DealerProductBuyAttrval buyattrval = new DealerProductBuyAttrval();
									BeanUtils.copyProperties(buyattr, buyattrval);
									buyattrval.setAttrId(newAttrIndex+0l);
									integer = butValueAttrIndex.get(buyMap.get(x));
									buyattrval.setAttrValId(integer.longValue());
									buyVals.add(buyattrval);
								}
							}
							if(buyVals.size()>0){
								map2.put(integer, buyVals);
							}
						}
						supplierProductAttrVO.setMap(map2);
					}
				}
			}
			productVO.setDealerProductAttrDTOs(attrVOs);
			
			//商品sku信息
			List<DealerProductSkuDTO> skuVOs = new ArrayList<DealerProductSkuDTO>();
			for (int i = 0; i < oldProductVO.getDealerProductSkuDTOs().size(); i++) {
				DealerProductSkuDTO productSkuVO = new DealerProductSkuDTO();
				DealerProductSku sku = new DealerProductSku();
				B2cProductSellNum b2cProductSellNum = new B2cProductSellNum();
				DealerProductPrice price = new DealerProductPrice();
				BeanUtils.copyProperties(oldProductVO.getDealerProductSkuDTOs().get(i).getDealerProductSku(), sku);
				productSkuVO.setDealerProductSku(sku);
				BeanUtils.copyProperties(oldProductVO.getDealerProductSkuDTOs()
						.get(i).getDealerProductPriceMap(), price);
				
				
				BeanUtils.copyProperties( oldProductVO.getDealerProductSkuDTOs().get(i).getProductSellNum(),b2cProductSellNum);
				
				productSkuVO.setProductSellNum(b2cProductSellNum);
				productSkuVO.setDealerProductPriceMap(price);
				List<DealerProductSkuAttrval> attrvals = new ArrayList<DealerProductSkuAttrval>();
				//TODO
				if(oldProductVO.getDealerProductSkuDTOs().get(i).getDealerProductSkuAttrvals()
						!=null&&oldProductVO.getDealerProductSkuDTOs().get(i).getDealerProductSkuAttrvals().size()>0){
					for(DealerProductSkuAttrval attrval : oldProductVO
							.getDealerProductSkuDTOs().get(i).getDealerProductSkuAttrvals()){
						DealerProductSkuAttrval skuAttrval = new DealerProductSkuAttrval();
						BeanUtils.copyProperties(attrval, skuAttrval);
						attrvals.add(skuAttrval);
					}
				}
				/*if(oldProductVO.getdealerProductAttrVOs().get(i)!=null){
					BeanUtils.copyProperties(oldProductVO.getdealerProductAttrVOs().get(i), productSkuVO);
				}*/
				
				if(attrvals.size()>0){
					productSkuVO.setDealerProductSkuAttrvals(attrvals);
					skuVOs.add(productSkuVO);
				}
				
			}
			
			productVO.setDealerProductSkuDTOs(skuVOs);
			return productVO; 
		}
	}
