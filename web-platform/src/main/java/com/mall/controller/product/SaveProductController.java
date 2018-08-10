package com.mall.controller.product;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.Brand;
import com.mall.category.po.SubBrand;
import com.mall.dealer.product.dto.DealerProductAttrDTO;
import com.mall.dealer.product.dto.DealerProductObjectDTO;
import com.mall.dealer.product.dto.DealerProductSkuDTO;
import com.mall.dealer.product.po.B2cProductDetail;
import com.mall.dealer.product.po.B2cProductSellNum;
import com.mall.dealer.product.po.DealerProductAttach;
import com.mall.dealer.product.po.DealerProductAttr;
import com.mall.dealer.product.po.DealerProductAttrval;
import com.mall.dealer.product.po.DealerProductBuyAttrval;
import com.mall.dealer.product.po.DealerProductPrice;
import com.mall.dealer.product.po.DealerProductSaleSetting;
import com.mall.dealer.product.po.DealerProductSku;
import com.mall.dealer.product.po.DealerProductSkuAttrval;
import com.mall.dealer.product.po.DealerProductWholesaleRange;
import com.mall.supplier.product.dto.SupplierProductAttrDTO;
import com.mall.supplier.product.po.SupplierProductAttr;
import com.mall.supplier.product.po.SupplierProductAttrval;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.CopyBeanUtil;
import com.mall.utils.MD5;


/**
 * 保存商品.
 * @author zhouz.
 *
 */
@Controller
public class SaveProductController extends BaseController{
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(SaveProductController.class);

	/**
	 * 提交审核商品
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 * @param cost Integer
	 * @param productId Long
	 * @param start Long
	 * @param pic Long
	 * @param buyIndex Long
	 * @param saleIndex Long
	 * @param productPic Long
	 * @param onlyPrice Long
	 * @param saleVal Long
	 * @param minNum minNum
	 * @param buyVal Long
	 * @param pname String
	 * @param brandId String
	 * @param subBrandId String 
	 * @param imgUrl String
	 * @param buyName String
	 * @param saleName String
	 * @param rPrice String
	 * @param editorValue Long
	 * @param skuCode skuCode
	 * @return String 返回状态
	 */
	@RequestMapping("/product/editProduct")
	@ResponseBody
	public String editProduct(HttpServletRequest request,
			String[] attrRows,String[] saleAttrRows,String[] attrOrd,String[] attrName,
			DealerProductObjectDTO objectVO,Integer cost, Long productId ,
			Long buyIndex, Long[] saleIndex,Long[] saleVal, Long[] saleVal2, Long[] saleVal3, Long[] saleVal4, Long[] buyVal,
			Long[] start,BigDecimal[] pic,Long[] supplierStart,BigDecimal[] supplierPrice,BigDecimal[] supplierPic, BigDecimal[] productPic,BigDecimal onlyPrice,
			String[] buyName,String[] saleName,String[] saleName2,String[] saleName3,String[] saleName4,
			Double[] length,Double[] width,Double[] height,Double[] weight,String pname,String brandId,String subBrandId,Long minNum,Long supplierMinNum,
			String[] imgUrl,BigDecimal[] rPrice,String editorValue,String editor1,String[] skuCode,Long[] sellCount,
			BigDecimal[] unitPrice,BigDecimal[] domesticPrice,BigDecimal[] bestoayPrice,String[] customsSkuNumber,String[] productCode){
		//Long[] buyAttrvalId,Long[] saleAttrvalId,String[] buyLineAttrvalNameCn,String[] saleLineAttrvalNameCn
		
		String isSaveParameter = "1";
		
		LOGGER.info("修改商品信息.");
		
		LOGGER.info("商品Id:"+productId);
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		//是否是国内经销商
		Short prodType = objectVO.getDealerProduct().getProdType();
		String categoryId = objectVO.getDealerProduct().getCatePubId();
		boolean isDealer = false;
		if(null != prodType){
			isDealer = prodType == 1 ? true : false;
		} 
		
		/**
    	 * 设置属性和属性值
    	 */
    	List<DealerProductAttrDTO> attrDTOs = new ArrayList<DealerProductAttrDTO>();
    	//普通属性
    	if(attrRows!=null && attrRows.length>0){
    		for(int i=0;i<attrRows.length;i++){
        		DealerProductAttrDTO attrDto = new DealerProductAttrDTO();
        		DealerProductAttr attr = new DealerProductAttr();
        		List<DealerProductAttrval> listAttrval = new ArrayList<DealerProductAttrval>();
        		String type = request.getParameter("type"+attrRows[i]);
        		attr.setType(Short.valueOf(type));
        		attr.setAttrNameCn(attrName[i]);
        		attr.setAttrName(attrName[i]);
        		attr.setBuyAttr(Short.valueOf("0"));
        		attr.setSaleAttr(Short.valueOf("0"));
        		attr.setIsneed(Short.valueOf("-1"));
        		boolean b = isInteger(attrOrd[i]);
        		if(b){
            		attr.setSortval(Integer.parseInt(attrOrd[i]));
        		}
        		String[] attrvals = request.getParameterValues("attrval"+attrRows[i]);
        		for (String val : attrvals) {
        			DealerProductAttrval attrval = new DealerProductAttrval();
            		attrval.setAttrNameCn(attrName[i]);
        			attrval.setAttrName(attrName[i]);
        			attrval.setLineAttrvalName(val);
        			attrval.setLineAttrvalNameCn(val);
        			attrval.setIstate(Short.valueOf("1"));
        			listAttrval.add(attrval);
    			}
        		attrDto.setDealerProductAttr(attr);
        		attrDto.setDealerProductAttrvals(listAttrval);
        		attrDTOs.add(attrDto);
        	}
    	}
		
		List<String> buyAttrValUrl = new ArrayList<String>();
		
		for (int x = 0; x < imgUrl.length; x++) {
			
			String[] prodImg = imgUrl[x].split("_");
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < prodImg.length; i++) {
				if (i > 1) {
					sb.append("_");
				}
				sb.append(prodImg[i]);
			}
			
			if(!"00".equals(prodImg[0])){
				buyAttrValUrl.add(imgUrl[x]);
			}
			
		}
		
		
		/** 
		 * 设置展示属性的图片url
		 */
		List<Integer> buyMap = new ArrayList<Integer>();
		Map<Integer, List<DealerProductBuyAttrval>> map = new LinkedHashMap<Integer, List<DealerProductBuyAttrval>>();
		DealerProductObjectDTO newProductObjectVO = new DealerProductObjectDTO();
		try{
			
			Short auditReason = objectVO.getDealerProduct().getAuditReason();
			
			
			if(buyAttrValUrl!=null&&buyAttrValUrl.size()>0){
				
				for (int x = 0; x < buyAttrValUrl.size(); x++) {
					
					String[] split = buyAttrValUrl.get(x).split("_");
					Integer index = Integer.parseInt(split[0]);
					
					buyMap.add(index);
					
					List<DealerProductBuyAttrval> list = map.get(index);
					
					if (list != null && list.size() > 0) {
						
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						
						for (int i = 1; i < split.length; i++) {
							
							if (i > 1) {
								sb.append("_");
							}
							
							sb.append(split[i]);
							
						}
						
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
						
					} else {
						
						list = new ArrayList<DealerProductBuyAttrval>();
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						
						for (int i = 1; i < split.length; i++) {
							
							if (i > 1) {
								sb.append("_");
							}
							
							sb.append(split[i]);
						}
						
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
						
						map.put(index, list);
					}
				}
			}
			
			LOGGER.info("设置展示属性的图片url完成!");
			
			/**
			 * 商品基本信息
			 */
			objectVO.getDealerProductBase().setProductname(pname);
			objectVO.getDealerProductBaseEn().setProductname(pname);
			LOGGER.info("保存商品名称完成!");
			
			
			/**
			 * sku信息
			 */
			LOGGER.info("设置sku信息及价格开始!");
			List<DealerProductSkuDTO> productSkuVOs = new ArrayList<DealerProductSkuDTO>();
			
			DealerProductSaleSetting productSaleSetting = new DealerProductSaleSetting();
			List<DealerProductWholesaleRange> wholesaleRanges = new ArrayList<DealerProductWholesaleRange>();
			
			List<DealerProductSkuDTO> supplierProductSkuVOs = objectVO.getDealerProductSkuDTOs();
			
			if (cost == 2) {
				
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(productPic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(productPic));
				productSaleSetting.setMinWholesaleQty(minNum);
				
				
				
				if(isDealer){
					productSaleSetting.setMinSupplierQty(minNum);
				} else {
					productSaleSetting.setMinSupplierQty(supplierMinNum);
				}
				
				for (int i = 0; i < buyVal.length; i++) {
					
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					
					productSku.setCreateDate(new Date());
					if(skuCode!=null && skuCode.length == buyVal.length){
						productSku.setSkuCode(skuCode[i]);
					}
					if(weight!=null && weight.length == buyVal.length){
						productSku.setSkuWeight(weight[i]);
					}
					
					//TODO
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setSaleStatus((short) 1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}
					skuVO.setDealerProductSku(productSku);
					
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);
					
					for(int j = 0; j < saleIndex.length; j++){
						if (saleVal!=null&&saleVal.length > 0) {
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}
					
					
					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs ) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())
								){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}
					
					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					} else {
						price = new DealerProductPrice();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						
						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					}
					
					
					//TODO
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);
					
					
					skuVO.setDealerProductPriceMap(price);
					
					productSkuVOs.add(skuVO);
				}
			}
			
			if (cost == 1) {
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(pic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(pic));
				productSaleSetting.setMinWholesaleQty(start[0]);
				
				if(isDealer){
					
					productSaleSetting.setMinSupplierQty(start[0]);
				} else {
					
					productSaleSetting.setMinSupplierQty(supplierStart[0]);
				}
				for (int i = 0; i < buyVal.length; i++) {
					
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					if(skuCode == null){
						productSku.setSkuCode("");
					}else{
						productSku.setSkuCode(skuCode[i]);
					}
					
					productSku.setSkuWeight(weight[i]);
					
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					//TODO
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setCreateDate(new Date());
					productSku.setSaleStatus((short) 1);
					productSku.setIstate((short)1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}
					skuVO.setDealerProductSku(productSku);
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setDealerid(1l);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);
					
//					if (saleVal!=null&&saleVal.length > 0) {
//						DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
//						saleAttrval.setAttrId(Long.parseLong("" + saleIndex));
//						saleAttrval.setAttrValId(saleVal[i]);
//						saleAttrval.setSubdealerid("");
//						saleAttrval.setAttrvalNameCn(saleName[i]);
//						supplierProductSkuAttrvals.add(saleAttrval);
//					}
					for(int j = 0; j < saleVal.length; j++){
						if (saleVal!=null&&saleVal.length > 0) {
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							saleAttrval.setSubdealerid("");
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}
					
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);
					
					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					
					
					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}
					
					
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(Common.getMax2(pic));
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setRetailPrice(onlyPrice);
					} else {
						price = new DealerProductPrice();
						price.setRetailPrice(onlyPrice);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setDealerprice(Common.getMax2(pic));
					}
					skuVO.setDealerProductPriceMap(price);
					productSkuVOs.add(skuVO);
				}
				for (int i = 0; i < pic.length; i++) {
					DealerProductWholesaleRange range = new DealerProductWholesaleRange();
					Long qty = 0l;
					range.setStartQty(start[i]);
					if(i + 1 < pic.length){
						qty = start[i + 1]-1;
					}
					
					if(i==pic.length-1){
						qty =0l;
					}
					range.setStockType(com.mall.dealer.product.util.Constants.PROD_DEALER_RANAG_TYPE);
					range.setEndQty(qty);
					range.setDiscountType("0");
					range.setDiscount(pic[i]);
					wholesaleRanges.add(range);
				}
				
				
				
				if(isDealer){
					
					
					for (int i = 0; i < pic.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(start[i]);
						if(i + 1 < pic.length){
							qty = start[i + 1]-1;
						}
						
						if(i==pic.length-1){
							qty =0l;
						}
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(pic[i]);
						wholesaleRanges.add(range);
					}
				} else {
					for (int i = 0; i < supplierPrice.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(supplierStart[i]);
						if(i + 1 < supplierPrice.length){
							qty = supplierStart[i + 1]-1;
						}
						
						if(i==supplierPrice.length-1){
							qty =0l;
						}
						
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(supplierPrice[i]);
						wholesaleRanges.add(range);
					}
				}
				
			}
			
			for (int x = 0; x < objectVO.getDealerProductAttrDTOs().size(); x++) {
				if (objectVO.getDealerProductAttrDTOs().get(x)
						.getDealerProductAttr().getBuyAttr() == 1) {
					objectVO.getDealerProductAttrDTOs().get(x).setMap(map);
				}
			}
			for (int x = objectVO.getDealerProductAttrDTOs().size(); x > 0; x--) {
				if (objectVO.getDealerProductAttrDTOs().get(x-1).getDealerProductAttr()==null) {
					objectVO.getDealerProductAttrDTOs().remove(x-1);
				}
			}
			Long[] attrIndex = new Long[productSkuVOs.get(0).getDealerProductSkuAttrvals().size()];
			for(int x = 0; x < attrIndex.length; x++){
				if(x < saleIndex.length){
					attrIndex[x]=saleIndex[x];
				}else{
					attrIndex[x]=buyIndex;
				}
			}
			Arrays.sort(attrIndex);
			for (DealerProductSkuDTO skuDto1 : productSkuVOs) {
				List<DealerProductSkuAttrval> skuAttrvals = skuDto1.getDealerProductSkuAttrvals();
				for (DealerProductSkuAttrval dealerProductSkuAttrval : skuAttrvals) {
					for(int x = 0; x < attrIndex.length; x++){
						if(dealerProductSkuAttrval.getAttrId().equals(attrIndex[x])){
							dealerProductSkuAttrval.setAttrId(Long.parseLong("" + x));
						}
					}
				}
			}
			objectVO.setDealerProductSkuDTOs(productSkuVOs);
			for (DealerProductAttrDTO attrDto1 : attrDTOs) {
				objectVO.getDealerProductAttrDTOs().add(attrDto1);
			}
			productSaleSetting.setRetaiPrePayPercent(objectVO.getDealerProductSaleSetting().getRetaiPrePayPercent());
			objectVO.setDealerProductSaleSetting(productSaleSetting);
			objectVO.setDealerProductWholesaleRanges(wholesaleRanges);
			LOGGER.info("设置sku信息及价格完成!");
			
			
			/**
			 * 商品核心信息
			 */
			LOGGER.info("设置商品核心信息!");
			objectVO.getDealerProduct().setAuditReason(Constants.SHORT1);
			objectVO.getDealerProduct().setSuppliername(null);
			if(subBrandId!=null&&!"".equals(subBrandId)){
				objectVO.getDealerProduct().setBrandId(subBrandId);
			} else {
				objectVO.getDealerProduct().setBrandId(brandId);
			}
			objectVO.getDealerProduct().setSourcetype("1");
			objectVO.getDealerProduct().setNamemd5(MD5.encrypt(pname));
			
			//设置主图
			String imageUrl = "";
			StringBuffer buffer = new StringBuffer();
			if(imgUrl!=null){
				String[] strArr = imgUrl[0].split("_");
				new StringBuffer();
				for (int i = 1; i < strArr.length; i++) {
					if(i>1){
						buffer.append("_");
					}
					buffer.append(strArr[i]);
				}
			}
			imageUrl = buffer.toString();
			imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
			objectVO.getDealerProduct().setImageurl(imageUrl);
			LOGGER.info("设置商品主图完成!");
			/**
			 * 图文详情
			 */
			if(editorValue!=null){
				
//				editorValue = editorValue.replaceAll("href=", "");
				List<DealerProductAttach> attachs = new ArrayList<DealerProductAttach>();
				DealerProductAttach attach = objectVO.getDealerProductAttachs().get(0);
				if(editorValue!=null&&!"".equals(editorValue)&&editorValue.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editorValue=startStr+editorValue+endStr;
	            }
				byte[] picByte = editorValue.getBytes();
				String fileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				fileUrl = UploadFileUtil.uploadFile(stream, null, null);
				
				attach.setType(Constants.PRODUCT_DESCRIPTIONS);
				attach.setFileurl(fileUrl);
				attachs.add(attach);
				objectVO.setDealerProductAttachs(attachs);
			}
			if(editor1!=null){
				
				B2cProductDetail b2cProductDetail = objectVO.getB2cProductDetail();
				
//				editor1 = editor1.replaceAll("href=", "");
				if(editor1!=null&&!"".equals(editor1)&&editor1.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editor1=startStr+editor1+endStr;
	            }
				byte[] picByte = editor1.getBytes();
				String b2cFileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				b2cFileUrl = UploadFileUtil.uploadFile(stream, null, null);
				

				b2cProductDetail.setB2cDescription(b2cFileUrl);
				objectVO.setB2cProductDetail(b2cProductDetail );
				
			}
			LOGGER.info("设置图文详情完成!");
			
			newProductObjectVO = CopyBeanUtil.copy(objectVO, buyMap);
			newProductObjectVO.getDealerProduct().setMnemonicCode("1");
			newProductObjectVO.setProductId(productId);
			newProductObjectVO.getDealerProduct().setAuditReason(auditReason);
			newProductObjectVO.getDealerProduct().setOperator(""+getCurrentUser().getSequenceId());
			RemoteServiceSingleton.getInstance().getDealerProductService().updateProductObject(newProductObjectVO);
			
			LOGGER.info("商品保存成功!");
		}catch (Exception e) {
			LOGGER.error("保存商品错误！  msg:"+e.getMessage()+"商品Id = "+productId,e);
			
			LOGGER.error("商户Id:"+productId);
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
		
		try {
			
			memCachedClient.delete(newProductObjectVO.getProductId()+"");
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		} 
		
		return isSaveParameter;
		
	}
	/**
	 * 提交审核商品
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 * @param cost Integer
	 * @param productId Long
	 * @param start Long
	 * @param pic Long
	 * @param buyIndex Long
	 * @param saleIndex Long
	 * @param productPic Long
	 * @param onlyPrice Long
	 * @param saleVal Long
	 * @param minNum minNum
	 * @param buyVal Long
	 * @param pname String
	 * @param brandId String
	 * @param subBrandId String 
	 * @param imgUrl String
	 * @param buyName String
	 * @param saleName String
	 * @param rPrice String
	 * @param editorValue Long
	 * @param skuCode skuCode
	 * @return String 返回状态
	 */
	@RequestMapping("/product/editProperty")
	@ResponseBody
	public String editProperty(HttpServletRequest request,
			String[] attrRows,String[] saleAttrRows,String[] attrOrd,String[] attrName,
			DealerProductObjectDTO objectVO,Integer cost, Long productId ,
			Long buyIndex, Long[] saleIndex,Long[] saleVal, Long[] saleVal2, Long[] saleVal3, Long[] saleVal4, Long[] buyVal,
			String[] imgUrl,String[] buyName,String[] saleName,String[] saleName2,String[] saleName3,String[] saleName4,
			Long[] start,BigDecimal[] pic,Long[] supplierStart,BigDecimal[] supplierPrice,BigDecimal[] supplierPic, BigDecimal[] productPic,BigDecimal onlyPrice,
			Double[] length,Double[] width,Double[] height,Double[] weight,String pname,String brandId,String subBrandId,Long minNum,Long supplierMinNum,
			BigDecimal[] rPrice,String editorValue,String editor1 ,String[] skuCode,Long[] sellCount,
			BigDecimal[] unitPrice,BigDecimal[] domesticPrice,BigDecimal[] bestoayPrice,String[] customsSkuNumber,String[] productCode,
			Long[] buyAttrvalId,Long[] saleAttrvalId,String[] buyLineAttrvalNameCn,String[] saleLineAttrvalNameCn){
		
		
		String isSaveParameter = "1";
		
		LOGGER.info("修改商品信息.");
		
		LOGGER.info("商品Id:"+productId);
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		//是否是国内经销商
		Short prodType = objectVO.getDealerProduct().getProdType();
		String categoryId = objectVO.getDealerProduct().getCatePubId();
		boolean isDealer = false;
		if(null != prodType){
			isDealer = prodType == 1 ? true : false;
		} 
		
		/**
    	 * 设置属性和属性值
    	 */
    	List<DealerProductAttrDTO> attrDTOs = new ArrayList<DealerProductAttrDTO>();
    	//普通属性
    	
    	if (null!=attrRows){
	    	for(int i=0;i<attrRows.length;i++){
	    		DealerProductAttrDTO attrDto = new DealerProductAttrDTO();
	    		DealerProductAttr attr = new DealerProductAttr();
	    		List<DealerProductAttrval> listAttrval = new ArrayList<DealerProductAttrval>();
	    		String type = request.getParameter("type"+attrRows[i]);
	    		attr.setType(Short.valueOf(type));
	    		attr.setAttrNameCn(attrName[i]);
	    		attr.setAttrName(attrName[i]);
	    		attr.setBuyAttr(Short.valueOf("0"));
	    		attr.setSaleAttr(Short.valueOf("0"));
	    		attr.setIsneed(Short.valueOf("-1"));
	    		boolean b = isInteger(attrOrd[i]);
	    		if(b){
	        		attr.setSortval(Integer.parseInt(attrOrd[i]));
	    		}
	    		String[] attrvals = request.getParameterValues("attrval"+attrRows[i]);
	    		if(attrvals!=null&&attrvals.length>0){
	    			for (String val : attrvals) {
	        			DealerProductAttrval attrval = new DealerProductAttrval();
	            		attrval.setAttrNameCn(attrName[i]);
	        			attrval.setAttrName(attrName[i]);
	        			attrval.setLineAttrvalName(val);
	        			attrval.setLineAttrvalNameCn(val);
	        			attrval.setIstate(Short.valueOf("1"));
	        			listAttrval.add(attrval);
	    			}
	    		}
	    		
	    		attrDto.setDealerProductAttr(attr);
	    		attrDto.setDealerProductAttrvals(listAttrval);
	    		attrDTOs.add(attrDto);
	    	}   
    	}
		
		List<String> buyAttrValUrl = new ArrayList<String>();
		
		for (int x = 0; x < imgUrl.length; x++) {
			
			String[] prodImg = imgUrl[x].split("_");
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < prodImg.length; i++) {
				if (i > 1) {
					sb.append("_");
				}
				sb.append(prodImg[i]);
			}
			
			if(!"00".equals(prodImg[0])){
				buyAttrValUrl.add(imgUrl[x]);
			}
			
		}
		
		
		/** 
		 * 设置展示属性的图片url
		 */
		List<Integer> buyMap = new ArrayList<Integer>();
		Map<Integer, List<DealerProductBuyAttrval>> map = new LinkedHashMap<Integer, List<DealerProductBuyAttrval>>();
		DealerProductObjectDTO newProductObjectVO = new DealerProductObjectDTO();
		try{
			
			Short auditReason = objectVO.getDealerProduct().getAuditReason();
			
			
			if(buyAttrValUrl!=null&&buyAttrValUrl.size()>0){
				
				for (int x = 0; x < buyAttrValUrl.size(); x++) {
					
					String[] split = buyAttrValUrl.get(x).split("_");
					Integer index = Integer.parseInt(split[0]);
					
					buyMap.add(index);
					
					List<DealerProductBuyAttrval> list = map.get(index);
					
					if (list != null && list.size() > 0) {
						
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						
						for (int i = 1; i < split.length; i++) {
							
							if (i > 1) {
								sb.append("_");
							}
							
							sb.append(split[i]);
							
						}
						
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
						
					} else {
						
						list = new ArrayList<DealerProductBuyAttrval>();
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						
						for (int i = 1; i < split.length; i++) {
							
							if (i > 1) {
								sb.append("_");
							}
							
							sb.append(split[i]);
						}
						
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
						
						map.put(index, list);
					}
				}
			}
			
			LOGGER.info("设置展示属性的图片url完成!");
			
			/**
			 * 商品基本信息
			 */
			objectVO.getDealerProductBase().setProductname(pname);
			objectVO.getDealerProductBaseEn().setProductname(pname);
			LOGGER.info("保存商品名称完成!");
			
			
			/**
			 * sku信息
			 */
			LOGGER.info("设置sku信息及价格开始!");
			List<DealerProductSkuDTO> productSkuVOs = new ArrayList<DealerProductSkuDTO>();
			
			DealerProductSaleSetting productSaleSetting = new DealerProductSaleSetting();
			List<DealerProductWholesaleRange> wholesaleRanges = new ArrayList<DealerProductWholesaleRange>();
			
			List<DealerProductSkuDTO> supplierProductSkuVOs = objectVO.getDealerProductSkuDTOs();
			
			if (cost == 2) {
				
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(productPic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(productPic));
				productSaleSetting.setMinWholesaleQty(minNum);
				
				
				
				if(isDealer){
					productSaleSetting.setMinSupplierQty(minNum);
				} else {
					productSaleSetting.setMinSupplierQty(supplierMinNum);
				}
				
				for (int i = 0; i < buyVal.length; i++) {
					
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					
					productSku.setCreateDate(new Date());
					if(skuCode!=null && skuCode.length == buyVal.length){
						productSku.setSkuCode(skuCode[i]);
					}
					if(weight!=null && weight.length == buyVal.length){
						productSku.setSkuWeight(weight[i]);
					}
					
					//TODO
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setSaleStatus((short) 1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}
					skuVO.setDealerProductSku(productSku);
					
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);
					
					for(int j = 0; j < saleIndex.length; j++){
						if (saleVal!=null&&saleVal.length > 0) {
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}
					
					
					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs ) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())
								){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}
					
					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					} else {
						price = new DealerProductPrice();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					}
					
					
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);
					
					skuVO.setDealerProductPriceMap(price);
					
					productSkuVOs.add(skuVO);
				}
			}
			
			if (cost == 1) {
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(pic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(pic));
				productSaleSetting.setMinWholesaleQty(start[0]);
				
				if(isDealer){
					
					productSaleSetting.setMinSupplierQty(start[0]);
				} else {
					
					productSaleSetting.setMinSupplierQty(supplierStart[0]);
				}
				for (int i = 0; i < buyVal.length; i++) {
					
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					if(skuCode == null) {
						productSku.setSkuCode("");
					}else{
						productSku.setSkuCode(skuCode[i]);
					}
					
					productSku.setSkuWeight(weight[i]);
					
					
					//TODO
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setCreateDate(new Date());
					productSku.setSaleStatus((short) 1);
					productSku.setIstate((short)1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}
					skuVO.setDealerProductSku(productSku);
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setDealerid(1l);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);
					
					if (saleVal!=null&&saleVal.length > 0) {
						for(int j = 0; j < saleVal.length; j++){
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							saleAttrval.setSubdealerid("");
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}
					
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);
					
					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					
					
					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}
					
					
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(Common.getMax2(pic));
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setRetailPrice(onlyPrice);
					} else {
						price = new DealerProductPrice();
						price.setRetailPrice(onlyPrice);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]);
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setDealerprice(Common.getMax2(pic));
					}
					skuVO.setDealerProductPriceMap(price);
					productSkuVOs.add(skuVO);
				}
				for (int i = 0; i < pic.length; i++) {
					DealerProductWholesaleRange range = new DealerProductWholesaleRange();
					Long qty = 0l;
					range.setStartQty(start[i]);
					if(i + 1 < pic.length){
						qty = start[i + 1]-1;
					}
					
					if(i==pic.length-1){
						qty =0l;
					}
					range.setStockType(com.mall.dealer.product.util.Constants.PROD_DEALER_RANAG_TYPE);
					range.setEndQty(qty);
					range.setDiscountType("0");
					range.setDiscount(pic[i]);
					wholesaleRanges.add(range);
				}
				
				
				
				if(isDealer){
					
					
					for (int i = 0; i < pic.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(start[i]);
						if(i + 1 < pic.length){
							qty = start[i + 1]-1;
						}
						
						if(i==pic.length-1){
							qty =0l;
						}
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(pic[i]);
						wholesaleRanges.add(range);
					}
				} else {
					for (int i = 0; i < supplierPrice.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(supplierStart[i]);
						if(i + 1 < supplierPrice.length){
							qty = supplierStart[i + 1]-1;
						}
						
						if(i==supplierPrice.length-1){
							qty =0l;
						}
						
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(supplierPrice[i]);
						wholesaleRanges.add(range);
					}
				}
				
			}
			
			for (int x = 0; x < objectVO.getDealerProductAttrDTOs().size(); x++) {
				if (objectVO.getDealerProductAttrDTOs().get(x)
						.getDealerProductAttr().getBuyAttr() == 1) {
					objectVO.getDealerProductAttrDTOs().get(x).setMap(map);
				}
			}
			for (int x = objectVO.getDealerProductAttrDTOs().size(); x > 0; x--) {
				if (objectVO.getDealerProductAttrDTOs().get(x-1).getDealerProductAttr()==null) {
					objectVO.getDealerProductAttrDTOs().remove(x-1);
				}
			}
			Long[] attrIndex = new Long[productSkuVOs.get(0).getDealerProductSkuAttrvals().size()];
			for(int x = 0; x < attrIndex.length; x++){
				if(x < saleIndex.length){
					attrIndex[x]=saleIndex[x];
				}else{
					attrIndex[x]=buyIndex;
				}
			}
			Arrays.sort(attrIndex);
			for (DealerProductSkuDTO skuDto1 : productSkuVOs) {
				List<DealerProductSkuAttrval> skuAttrvals = skuDto1.getDealerProductSkuAttrvals();
				for (DealerProductSkuAttrval dealerProductSkuAttrval : skuAttrvals) {
					for(int x = 0; x < attrIndex.length; x++){
						if(dealerProductSkuAttrval.getAttrId().equals(attrIndex[x])){
							dealerProductSkuAttrval.setAttrId(Long.parseLong("" + x));
						}
					}
				}
			}
			objectVO.setDealerProductSkuDTOs(productSkuVOs);
			for (DealerProductAttrDTO attrDto1 : attrDTOs) {
				objectVO.getDealerProductAttrDTOs().add(attrDto1);
			}
			productSaleSetting.setRetaiPrePayPercent(objectVO.getDealerProductSaleSetting().getRetaiPrePayPercent());
			objectVO.setDealerProductSaleSetting(productSaleSetting);
			objectVO.setDealerProductWholesaleRanges(wholesaleRanges);
			LOGGER.info("设置sku信息及价格完成!");
			
			
			/**
			 * 商品核心信息
			 */
			LOGGER.info("设置商品核心信息!");
			objectVO.getDealerProduct().setAuditReason(Constants.SHORT1);
			objectVO.getDealerProduct().setSuppliername(null);
			if(subBrandId!=null&&!"".equals(subBrandId)){
				objectVO.getDealerProduct().setBrandId(subBrandId);
			} else {
				objectVO.getDealerProduct().setBrandId(brandId);
			}
			objectVO.getDealerProduct().setSourcetype("1");
			objectVO.getDealerProduct().setNamemd5(MD5.encrypt(pname));
			
			//设置主图
			String imageUrl = "";
			StringBuffer buffer = new StringBuffer();
			if(imgUrl!=null){
				String[] strArr = imgUrl[0].split("_");
				new StringBuffer();
				for (int i = 1; i < strArr.length; i++) {
					if(i>1){
						buffer.append("_");
					}
					buffer.append(strArr[i]);
				}
			}
			imageUrl = buffer.toString();
			imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
			objectVO.getDealerProduct().setImageurl(imageUrl);
			LOGGER.info("设置商品主图完成!");
			/**
			 * 图文详情
			 */
			if(editorValue!=null){
				
//				editorValue = editorValue.replaceAll("href=", "");
				List<DealerProductAttach> attachs = new ArrayList<DealerProductAttach>();
				DealerProductAttach attach = objectVO.getDealerProductAttachs().get(0);
				if(editorValue!=null&&!"".equals(editorValue)&&editorValue.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editorValue=startStr+editorValue+endStr;
	            }
				byte[] picByte = editorValue.getBytes();
				String fileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				fileUrl = UploadFileUtil.uploadFile(stream, null, null);
				
				attach.setType(Constants.PRODUCT_DESCRIPTIONS);
				attach.setFileurl(fileUrl);
				attachs.add(attach);
				objectVO.setDealerProductAttachs(attachs);
			}
			
			if(editor1!=null){
				
				B2cProductDetail b2cProductDetail = objectVO.getB2cProductDetail();
				
//				editor1 = editor1.replaceAll("href=", "");
				if(editor1!=null&&!"".equals(editor1)&&editor1.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editor1=startStr+editor1+endStr;
	            }
				byte[] picByte = editor1.getBytes();
				String b2cFileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				b2cFileUrl = UploadFileUtil.uploadFile(stream, null, null);
				

				b2cProductDetail.setB2cDescription(b2cFileUrl);
				objectVO.setB2cProductDetail(b2cProductDetail );
				
			}
			LOGGER.info("设置图文详情完成!");
			
			newProductObjectVO = CopyBeanUtil.copy(objectVO, buyMap);
			newProductObjectVO.getDealerProduct().setMnemonicCode("1");
			newProductObjectVO.setProductId(productId);
			newProductObjectVO.getDealerProduct().setAuditReason(auditReason);
			newProductObjectVO.getDealerProduct().setOperator(""+getCurrentUser().getSequenceId());
			
			newProductObjectVO.getDealerProduct().setProdType((short) 3);
			
			RemoteServiceSingleton.getInstance().getDealerProductService().updateProductObject(newProductObjectVO);
			LOGGER.info("商品保存成功!");
		}catch (Exception e) {
			LOGGER.error("保存商品错误！  msg:"+e.getMessage()+"商品Id = "+productId,e);

			LOGGER.error("商户Id:"+productId);

			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
		
		try {
			
			memCachedClient.delete(newProductObjectVO.getProductId()+"");
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);

		} 
		
		return isSaveParameter;

	}



	/**
	 * 保存商品
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 * @param cost Integer
	 * @param productId Long
	 * @param start Long
	 * @param pic Long
	 * @param buyIndex Long
	 * @param saleIndex Long
	 * @param productPic Long
	 * @param onlyPrice Long
	 * @param saleVal Long
	 * @param minNum minNum
	 * @param buyVal Long
	 * @param pname String
	 * @param brandId String
	 * @param subBrandId String 
	 * @param imgUrl String
	 * @param buyName String
	 * @param saleName String
	 * @param rPrice String
	 * @param editorValue Long
	 * @param skuCode skuCode
	 * @return String 返回状态
	 */
	@RequestMapping("/product/saveProduct")
	@ResponseBody
	public String saveProduct(HttpServletRequest request,
			String[] attrRows,String[] saleAttrRows,String[] attrOrd,String[] attrName,
			DealerProductObjectDTO objectVO,Integer cost, Long productId ,
			Long[] start,BigDecimal[] pic,Long[] supplierStart,BigDecimal[] supplierPrice,
			Long buyIndex, Long[] saleIndex,Long[] saleVal, Long[] saleVal2, Long[] saleVal3, Long[] saleVal4, Long[] buyVal,
			String[] imgUrl,String[] buyName,String[] saleName,String[] saleName2,String[] saleName3,String[] saleName4,
			BigDecimal[] supplierPic, BigDecimal[] productPic,BigDecimal onlyPrice,
			Double[] length,Double[] width,Double[] height,Double[] weight,String pname,String brandId,String subBrandId,Long minNum,Long supplierMinNum,
			BigDecimal[] rPrice,String editorValue,String editor1 ,String[] skuCode,Long[] sellCount,BigDecimal[] unitPrice,
			BigDecimal[] domesticPrice,BigDecimal[] bestoayPrice,String[] customsSkuNumber,String[] productCode,
			Long[] buyAttrvalId,Long[] saleAttrvalId,String[] buyLineAttrvalNameCn,String[] saleLineAttrvalNameCn){

		String isSaveParameter = "1";

		LOGGER.info("修改商品信息.");

		LOGGER.info("商品Id:"+productId);

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());

		//是否是国内经销商
		Short prodType = objectVO.getDealerProduct().getProdType();
		String categoryId = objectVO.getDealerProduct().getCatePubId();
		boolean isDealer = false;
		if(null != prodType){
			isDealer = prodType == 1 ? true : false;
		}
    	
		/**
    	 * 设置属性和属性值
    	 */
    	List<DealerProductAttrDTO> attrDTOs = new ArrayList<DealerProductAttrDTO>();
    	//普通属性
    	if (null!=attrRows){
	    	for(int i=0;i<attrRows.length;i++){
	    		DealerProductAttrDTO attrDto = new DealerProductAttrDTO();
	    		DealerProductAttr attr = new DealerProductAttr();
	    		List<DealerProductAttrval> listAttrval = new ArrayList<DealerProductAttrval>();
	    		String type = request.getParameter("type"+attrRows[i]);
	    		attr.setType(Short.valueOf(type));
	    		attr.setAttrNameCn(attrName[i]);
	    		attr.setAttrName(attrName[i]);
	    		attr.setBuyAttr(Short.valueOf("0"));
	    		attr.setSaleAttr(Short.valueOf("0"));
	    		attr.setIsneed(Short.valueOf("-1"));
	    		boolean b = isInteger(attrOrd[i]);
	    		if(b){
	        		attr.setSortval(Integer.parseInt(attrOrd[i]));
	    		}
	    		String[] attrvals = request.getParameterValues("attrval"+attrRows[i]);
	    		if(attrvals!=null&&attrvals.length>0){
	    			for (String val : attrvals) {
	        			DealerProductAttrval attrval = new DealerProductAttrval();
	            		attrval.setAttrNameCn(attrName[i]);
	        			attrval.setAttrName(attrName[i]);
	        			attrval.setLineAttrvalName(val);
	        			attrval.setLineAttrvalNameCn(val);
	        			attrval.setIstate(Short.valueOf("1"));
	        			listAttrval.add(attrval);
	    			}
	    		}
	    		
	    		attrDto.setDealerProductAttr(attr);
	    		attrDto.setDealerProductAttrvals(listAttrval);
	    		attrDTOs.add(attrDto);
	    	} 
    	}
		

		List<String> buyAttrValUrl = new ArrayList<String>();

		for (int x = 0; x < imgUrl.length; x++) {

			String[] prodImg = imgUrl[x].split("_");
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < prodImg.length; i++) {
				if (i > 1) {
					sb.append("_");
				}
				sb.append(prodImg[i]);
			}

			if(!"00".equals(prodImg[0])){
				buyAttrValUrl.add(imgUrl[x]);
			}

		}


		/** 
		 * 设置展示属性的图片url
		 */
		List<Integer> buyMap = new ArrayList<Integer>();
		Map<Integer, List<DealerProductBuyAttrval>> map = new LinkedHashMap<Integer, List<DealerProductBuyAttrval>>();
		DealerProductObjectDTO newProductObjectVO = new DealerProductObjectDTO();
		
		try{

			Short auditReason = objectVO.getDealerProduct().getAuditReason();


			if(buyAttrValUrl!=null&&buyAttrValUrl.size()>0){

				for (int x = 0; x < buyAttrValUrl.size(); x++) {

					String[] split = buyAttrValUrl.get(x).split("_");
					Integer index = Integer.parseInt(split[0]);

					buyMap.add(index);

					List<DealerProductBuyAttrval> list = map.get(index);

					if (list != null && list.size() > 0) {

						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();

						for (int i = 1; i < split.length; i++) {

							if (i > 1) {
								sb.append("_");
							}

							sb.append(split[i]);

						}

						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);

					} else {

						list = new ArrayList<DealerProductBuyAttrval>();
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();

						for (int i = 1; i < split.length; i++) {

							if (i > 1) {
								sb.append("_");
							}

							sb.append(split[i]);
						}

						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);

						map.put(index, list);
					}
				}
			}

			LOGGER.info("设置展示属性的图片url完成!");

			/**
			 * 商品基本信息
			 */
			objectVO.getDealerProductBase().setProductname(pname);
			objectVO.getDealerProductBaseEn().setProductname(pname);
			LOGGER.info("保存商品名称完成!");


			/**
			 * sku信息
			 */
			LOGGER.info("设置sku信息及价格开始!");
			List<DealerProductSkuDTO> productSkuVOs = new ArrayList<DealerProductSkuDTO>();

			DealerProductSaleSetting productSaleSetting = new DealerProductSaleSetting();
			List<DealerProductWholesaleRange> wholesaleRanges = new ArrayList<DealerProductWholesaleRange>();

			List<DealerProductSkuDTO> supplierProductSkuVOs = objectVO.getDealerProductSkuDTOs();

			if (cost == 2) {

				productSaleSetting.setMaxBuyerPrice(Common.getMax2(productPic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(productPic));
				productSaleSetting.setMinWholesaleQty(minNum);
				
				
				
				if(isDealer){
					productSaleSetting.setMinSupplierQty(minNum);
				} else {
					productSaleSetting.setMinSupplierQty(supplierMinNum);
				}

				for (int i = 0; i < buyVal.length; i++) {

					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();

					productSku.setCreateDate(new Date());
					if(skuCode!=null && skuCode.length == buyVal.length){
						productSku.setSkuCode(skuCode[i]);
					}
					if(weight!=null && weight.length == buyVal.length){
						productSku.setSkuWeight(weight[i]);
					}
					
					//TODO
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setSaleStatus((short) 1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}

					skuVO.setDealerProductSku(productSku);

					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();

					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);

					for(int j = 0; j < saleIndex.length; j++){
						if (saleVal!=null&&saleVal.length > 0) {
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}

					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs ) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())
								){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}

					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]); //翼支付价格
						price.setUnitPrice(unitPrice[i]);
						

						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					} else {
						price = new DealerProductPrice();
						price.setDealerprice(productPic[i]);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]); //翼支付价格
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(productPic[i]);
						} else {
							price.setSupplierprice(supplierPic[i]);
						}
						
						price.setRetailPrice(rPrice[i]);
					}
					
					
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);
					skuVO.setDealerProductPriceMap(price);

					productSkuVOs.add(skuVO);
				}
			}

			if (cost == 1) {
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(pic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(pic));
				productSaleSetting.setMinWholesaleQty(start[0]);
				
				if(isDealer){
					
					productSaleSetting.setMinSupplierQty(start[0]);
				} else {
					
					productSaleSetting.setMinSupplierQty(supplierStart[0]);
				}
				for (int i = 0; i < buyVal.length; i++) {
					
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					productSku.setSkuCode(skuCode[i]);
					productSku.setSkuWeight(weight[i]);
					
					
					//TODO
					productSku.setCustomsSkuNumber(customsSkuNumber[i]);
					productSku.setLength(length[i]);
					productSku.setWidth(width[i]);
					productSku.setHeight(height[i]);
					
					productSku.setCreateDate(new Date());
					productSku.setSaleStatus((short) 1);
					productSku.setIstate((short)1);
					String saleNm2 = "";
					String saleNm3 = "";
					String saleNm4 = "";
					if(saleName2 != null&&saleName2.length>0){
						saleNm2=saleName2[i];
					}
					if(saleName3 != null&&saleName3.length>0){
						saleNm3=saleName3[i];
					}
					if(saleName4 != null&&saleName4.length>0){
						saleNm4=saleName4[i];
					}
					productSku.setSkuNameCn(buyName[i]+saleName[i]+saleNm2+saleNm3+saleNm4);
					//商品货号
					if(productCode[i]!=null){
						productSku.setProductCode(productCode[i]);
					}
					skuVO.setDealerProductSku(productSku);
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setDealerid(1l);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);

					for(int j = 0; j < saleVal.length; j++){
						if (saleVal!=null&&saleVal.length > 0) {
							DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
							saleAttrval.setAttrId(Long.parseLong("" + saleIndex[j]));

							if(j==0){
								saleAttrval.setAttrValId(saleVal[i]);
								saleAttrval.setAttrvalNameCn(saleName[i]);
							}
							if(j==1){
								saleAttrval.setAttrValId(saleVal2[i]);
								saleAttrval.setAttrvalNameCn(saleName2[i]);
							}
							if(j==2){
								saleAttrval.setAttrValId(saleVal3[i]);
								saleAttrval.setAttrvalNameCn(saleName3[i]);
							}
							if(j==3){
								saleAttrval.setAttrValId(saleVal4[i]);
								saleAttrval.setAttrvalNameCn(saleName4[i]);
							}
							saleAttrval.setSubdealerid("");
							supplierProductSkuAttrvals.add(saleAttrval);
						}
					}
					
					
					B2cProductSellNum productSellNum = new B2cProductSellNum();
					productSellNum.setSellCount(sellCount[i]);
					skuVO.setProductSellNum(productSellNum);

					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);


					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs) {
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())||
								productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameEn())){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
					}


					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(Common.getMax2(pic));
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]); //翼支付价格
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setRetailPrice(onlyPrice);
					} else {
						price = new DealerProductPrice();
						price.setRetailPrice(onlyPrice);
						price.setDomesticPrice(domesticPrice[i]);
						price.setBestoayPrice(bestoayPrice[i]); //翼支付价格
						price.setUnitPrice(unitPrice[i]);
						
						
						if(isDealer){
							price.setSupplierprice(Common.getMax2(pic));
						} else {
							price.setSupplierprice(Common.getMax2(supplierPrice));
						}
						price.setDealerprice(Common.getMax2(pic));
					}
					skuVO.setDealerProductPriceMap(price);
					productSkuVOs.add(skuVO);
				}
				for (int i = 0; i < pic.length; i++) {
					DealerProductWholesaleRange range = new DealerProductWholesaleRange();
					Long qty = 0l;
					range.setStartQty(start[i]);
					if(i + 1 < pic.length){
						qty = start[i + 1]-1;
					}

					if(i==pic.length-1){
						qty =0l;
					}
					range.setStockType(com.mall.dealer.product.util.Constants.PROD_DEALER_RANAG_TYPE);
					range.setEndQty(qty);
					range.setDiscountType("0");
					range.setDiscount(pic[i]);
					wholesaleRanges.add(range);
				}

				
				
				if(isDealer){
					
					
					for (int i = 0; i < pic.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(start[i]);
						if(i + 1 < pic.length){
							qty = start[i + 1]-1;
						}

						if(i==pic.length-1){
							qty =0l;
						}
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(pic[i]);
						wholesaleRanges.add(range);
					}
				} else {
					for (int i = 0; i < supplierPrice.length; i++) {
						DealerProductWholesaleRange range = new DealerProductWholesaleRange();
						Long qty = 0l;
						range.setStartQty(supplierStart[i]);
						if(i + 1 < supplierPrice.length){
							qty = supplierStart[i + 1]-1;
						}
	
						if(i==supplierPrice.length-1){
							qty =0l;
						}
	
						range.setStockType(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE);
						range.setEndQty(qty);
						range.setDiscountType("0");
						range.setDiscount(supplierPrice[i]);
						wholesaleRanges.add(range);
					}
				}

			}

			for (int x = 0; x < objectVO.getDealerProductAttrDTOs().size(); x++) {
				if (objectVO.getDealerProductAttrDTOs().get(x)
						.getDealerProductAttr()!=null && objectVO.getDealerProductAttrDTOs().get(x)
						.getDealerProductAttr().getBuyAttr() == 1) {
					objectVO.getDealerProductAttrDTOs().get(x).setMap(map);
				}
			}
			for (int x = objectVO.getDealerProductAttrDTOs().size(); x > 0; x--) {
				if (objectVO.getDealerProductAttrDTOs().get(x-1).getDealerProductAttr()==null) {
					objectVO.getDealerProductAttrDTOs().remove(x-1);
				}
			}
			Long[] attrIndex = new Long[productSkuVOs.get(0).getDealerProductSkuAttrvals().size()];
			for(int x = 0; x < attrIndex.length; x++){
				if(x < saleIndex.length){
					attrIndex[x]=saleIndex[x];
				}else{
					attrIndex[x]=buyIndex;
				}
			}
			Arrays.sort(attrIndex);
			for (DealerProductSkuDTO skuDto1 : productSkuVOs) {
				List<DealerProductSkuAttrval> skuAttrvals = skuDto1.getDealerProductSkuAttrvals();
				for (DealerProductSkuAttrval dealerProductSkuAttrval : skuAttrvals) {
					for(int x = 0; x < attrIndex.length; x++){
						if(dealerProductSkuAttrval.getAttrId().equals(attrIndex[x])){
							dealerProductSkuAttrval.setAttrId(Long.parseLong("" + x));
						}
					}
				}
			}
			objectVO.setDealerProductSkuDTOs(productSkuVOs);
			for (DealerProductAttrDTO attrDto1 : attrDTOs) {
				objectVO.getDealerProductAttrDTOs().add(attrDto1);
			}
			productSaleSetting.setRetaiPrePayPercent(objectVO.getDealerProductSaleSetting().getRetaiPrePayPercent());
			objectVO.setDealerProductSaleSetting(productSaleSetting);
			objectVO.setDealerProductWholesaleRanges(wholesaleRanges);
			LOGGER.info("设置sku信息及价格完成!");


			/**
			 * 商品核心信息
			 */
			LOGGER.info("设置商品核心信息!");
			objectVO.getDealerProduct().setAuditReason(Constants.SHORT1);
			objectVO.getDealerProduct().setSuppliername(null);
			if(subBrandId!=null&&!"".equals(subBrandId)){
				objectVO.getDealerProduct().setBrandId(subBrandId);
			} else {
				objectVO.getDealerProduct().setBrandId(brandId);
			}
			objectVO.getDealerProduct().setSourcetype("1");
			objectVO.getDealerProduct().setNamemd5(MD5.encrypt(pname));

			//设置主图
			String imageUrl = "";
			StringBuffer buffer = new StringBuffer();
			if(imgUrl!=null){
				String[] strArr = imgUrl[0].split("_");
				new StringBuffer();
				for (int i = 1; i < strArr.length; i++) {
					if(i>1){
						buffer.append("_");
					}
					buffer.append(strArr[i]);
				}
			}
			imageUrl = buffer.toString();
			imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
			objectVO.getDealerProduct().setImageurl(imageUrl);
			LOGGER.info("设置商品主图完成!");
			/**
			 * 图文详情
			 */
			if(editorValue!=null){

//				editorValue = editorValue.replaceAll("href=", "");
				List<DealerProductAttach> attachs = new ArrayList<DealerProductAttach>();
				DealerProductAttach attach = objectVO.getDealerProductAttachs().get(0);
				if(editorValue!=null&&!"".equals(editorValue)&&editorValue.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editorValue=startStr+editorValue+endStr;
	            }
				byte[] picByte = editorValue.getBytes();
				String fileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				fileUrl = UploadFileUtil.uploadFile(stream, null, null);

				attach.setType(Constants.PRODUCT_DESCRIPTIONS);
				attach.setFileurl(fileUrl);
				attachs.add(attach);
				objectVO.setDealerProductAttachs(attachs);
			}
			
			if(editor1!=null){
				
				B2cProductDetail b2cProductDetail = objectVO.getB2cProductDetail();
				
//				editor1 = editor1.replaceAll("href=", "");
				if(editor1!=null&&!"".equals(editor1)&&editor1.indexOf("detail_inner.css")==-1){
	            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
	            	String endStr="</div>";
	            	editor1=startStr+editor1+endStr;
	            }
				byte[] picByte = editor1.getBytes();
				String b2cFileUrl = "";
				ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
				b2cFileUrl = UploadFileUtil.uploadFile(stream, null, null);
				

				b2cProductDetail.setB2cDescription(b2cFileUrl);
				objectVO.setB2cProductDetail(b2cProductDetail );
				
			}
			LOGGER.info("设置图文详情完成!");

			newProductObjectVO = CopyBeanUtil.copy(objectVO, buyMap);
			newProductObjectVO.getDealerProduct().setMnemonicCode("1");
			newProductObjectVO.setProductId(productId);
			newProductObjectVO.getDealerProduct().setAuditReason(auditReason);
			newProductObjectVO.getDealerProduct().setOperator(""+getCurrentUser().getSequenceId());
			RemoteServiceSingleton.getInstance().getDealerProductService().updateProductObject(newProductObjectVO);
			LOGGER.info("商品保存成功!");
		}catch (Exception e) {
			LOGGER.error("保存商品错误！  msg:"+e.getMessage()+"商品Id = "+productId,e);

			LOGGER.error("商户Id:"+productId);

			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
		
		try {
		
			memCachedClient.delete(newProductObjectVO.getProductId()+"");
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		} 
		
		return isSaveParameter;

	}









	/**
	 * 新增单品
	 * @param request
	 * @param objectVO
	 * @param cost
	 * @param productId
	 * @param start
	 * @param pic
	 * @param buyIndex
	 * @param saleIndex
	 * @param productPic
	 * @param onlyPrice
	 * @param saleVal
	 * @param buyVal
	 * @param pname
	 * @param brandId
	 * @param subBrandId
	 * @param minNum
	 * @param imgUrl
	 * @param buyName
	 * @param saleName
	 * @param rPrice
	 * @param editorValue
	 * @param skuCode
	 * @return editProdNewSku
	 */
	@RequestMapping("/product/editProdNewSku")
	@ResponseBody
	public String editProductNewSKU(HttpServletRequest request,
			DealerProductObjectDTO objectVO,Integer cost, Long productId ,
			Long[] start,BigDecimal[] pic,Long buyIndex, Long saleIndex, BigDecimal[] productPic,BigDecimal onlyPrice,Double[] weight,
			Long[] saleVal, Long[] buyVal,Long minNum,String[] imgUrl,String[] buyName,String[] saleName,BigDecimal[] rPrice,
			BigDecimal[] supplierPic,Long[] collectionStart,BigDecimal[] collectionPic

			){



		String isSaveParameter = "1";
		/** 
		 * 设置展示属性的图片url
		 */
		List<Integer> buyMap = new ArrayList<Integer>();
		Map<Integer, List<DealerProductBuyAttrval>> map = new LinkedHashMap<Integer, List<DealerProductBuyAttrval>>();
		try{
			if(imgUrl!=null&&imgUrl.length>0){
				for (int x = 0; x < imgUrl.length; x++) {
					String[] split = imgUrl[x].split("_");
					Integer index = Integer.parseInt(split[0]);
					buyMap.add(index);
					List<DealerProductBuyAttrval> list = map.get(index);
					if (list != null && list.size() > 0) {
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						for (int i = 1; i < split.length; i++) {
							if (i > 1) {
								sb.append("_");
							}
							sb.append(split[i]);
						}
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
					} else {
						list = new ArrayList<DealerProductBuyAttrval>();
						DealerProductBuyAttrval buyAttrval = new DealerProductBuyAttrval();
						StringBuffer sb = new StringBuffer();
						for (int i = 1; i < split.length; i++) {
							if (i > 1) {
								sb.append("_");
							}
							sb.append(split[i]);
						}
						String imgurl = sb.toString();
						imgurl = imgurl.substring(imgurl.indexOf("group"));
						buyAttrval.setIstate((short)1);
						buyAttrval.setImgurl(imgurl);
						list.add(buyAttrval);
						map.put(index, list);
					}
				}
			}
			LOGGER.info("setting up attr main image is success!");



			/**
			 * sku信息
			 */
			List<DealerProductSkuDTO> productSkuVOs = new ArrayList<DealerProductSkuDTO>();

			DealerProductSaleSetting productSaleSetting = new DealerProductSaleSetting();
			List<DealerProductWholesaleRange> wholesaleRanges = new ArrayList<DealerProductWholesaleRange>();
			List<DealerProductSkuDTO> supplierProductSkuVOs = objectVO.getDealerProductSkuDTOs();
			if (cost == 2) {
				productSaleSetting.setMaxBuyerPrice(Common.getMax2(productPic));
				productSaleSetting.setMinBuyerPrice(Common.getMin2(productPic));
				productSaleSetting.setMinWholesaleQty(minNum);
				productSaleSetting.setIsUptae(true);
				for (int i = 0; i < buyVal.length; i++) {
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					productSku.setCreateDate(new Date());
					productSku.setSaleStatus((short) 1);
					productSku.setSkuWeight(weight[i]);
					productSku.setSkuNameCn(buyName[i]+saleName[i]);
					skuVO.setDealerProductSku(productSku);
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);


					if (saleVal!=null&&saleVal.length > 0) {
						DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
						saleAttrval.setAttrId(Long.parseLong("" + saleIndex));
						saleAttrval.setAttrValId(saleVal[i]);
						saleAttrval.setAttrvalNameCn(saleName[i]);
						supplierProductSkuAttrvals.add(saleAttrval);
					}



					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs ) {
						//						if(skuAttr.getDealerProductSku().getIstate()==2){
						if(skuAttr.getDealerProductSku().getSkuNameCn().equals(productSku.getSkuNameCn())){

							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							productSku.setIstate(skuAttr.getDealerProductSku().getIstate());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
						/*} else {
							productSku = null; 
							break;
						}*/
					}

					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);
					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(productPic[i]);
						price.setSupplierprice(supplierPic[i]);
						price.setRetailPrice(rPrice[i]);
						
					} else {
						price = new DealerProductPrice();
						price.setDealerprice(productPic[i]);
						price.setSupplierprice(supplierPic[i]);
						price.setRetailPrice(rPrice[i]);
						
					}
					skuVO.setDealerProductPriceMap(price);

					productSkuVOs.add(skuVO);
				}
			}

			if (cost == 1) {
				for (int i = 0; i < buyVal.length; i++) {
					DealerProductSkuDTO skuVO = new DealerProductSkuDTO();
					DealerProductSku productSku = new DealerProductSku();
					productSku.setCreateDate(new Date());
					productSku.setSaleStatus((short) 1);
					productSku.setIstate((short)1);
					productSku.setSkuWeight(weight[i]);
					productSku.setSkuNameCn(buyName[i]+saleName[i]);
					skuVO.setDealerProductSku(productSku);
					List<DealerProductSkuAttrval> supplierProductSkuAttrvals = new ArrayList<DealerProductSkuAttrval>();
					DealerProductSkuAttrval buyAttrval = new DealerProductSkuAttrval();
					buyAttrval.setAttrId(Long.parseLong("" + buyIndex));
					buyAttrval.setAttrValId(buyVal[i]);
					buyAttrval.setDealerid(1l);
					buyAttrval.setAttrvalNameCn(buyName[i]);
					supplierProductSkuAttrvals.add(buyAttrval);

					if (saleVal!=null&&saleVal.length > 0) {
						DealerProductSkuAttrval saleAttrval = new DealerProductSkuAttrval();
						saleAttrval.setAttrId(Long.parseLong("" + saleIndex));
						saleAttrval.setAttrValId(saleVal[i]);
						saleAttrval.setSubdealerid("");
						saleAttrval.setAttrvalNameCn(saleName[i]);
						supplierProductSkuAttrvals.add(saleAttrval);
					}

					skuVO.setDealerProductSkuAttrvals(supplierProductSkuAttrvals);


					//设置skuid及价格
					for (DealerProductSkuDTO skuAttr : supplierProductSkuVOs) {
						//						if(skuAttr.getDealerProductSku().getIstate()==2){
						if(productSku.getSkuNameCn().equals(skuAttr.getDealerProductSku().getSkuNameCn())){
							productSku.setProductSkuId(skuAttr.getDealerProductSku().getProductSkuId());
							productSku.setSkuId(skuAttr.getDealerProductSku().getSkuId());
							productSku.setIstate(skuAttr.getDealerProductSku().getIstate());
							skuVO.setDealerProductPriceMap(skuAttr.getDealerProductPriceMap());
						}
						//							} 
					}


					DealerProductPrice price = null;
					if(skuVO.getDealerProductPriceMap()!=null){
						price = skuVO.getDealerProductPriceMap();
						price.setDealerprice(Common.getMax2(pic));
						
						price.setRetailPrice(onlyPrice);
					} else {
						price = new DealerProductPrice();
						price.setRetailPrice(onlyPrice);
						
						price.setDealerprice(Common.getMax2(pic));
					}



					skuVO.setDealerProductPriceMap(price);
					productSkuVOs.add(skuVO);
				}
				for (int i = 0; i < pic.length; i++) {
					DealerProductWholesaleRange range = new DealerProductWholesaleRange();
					Long qty = 0l;
					range.setStartQty(start[i]);
					if(i + 1 < pic.length){
						qty = start[i + 1]-1;
					}

					if(i==pic.length-1){
						qty =0l;
					}
					range.setEndQty(qty);
					range.setDiscountType("0");
					range.setDiscount(pic[i]);
					wholesaleRanges.add(range);
				}
				for (int i = 0; i < collectionPic.length; i++) {
					DealerProductWholesaleRange range = new DealerProductWholesaleRange();
					Long qty = 0l;
					range.setStartQty(collectionStart[i]);
					if(i + 1 < collectionPic.length){
						qty = collectionStart[i + 1]-1;
					}

					if(i==pic.length-1){
						qty =0l;
					}
					range.setEndQty(qty);
					range.setDiscountType("0");
					range.setDiscount(collectionPic[i]);
					wholesaleRanges.add(range);


				}

			}


			List<DealerProductSkuDTO> dealerProductSkuVOs = new ArrayList<DealerProductSkuDTO>();

			for (int x = 0 ; x <  productSkuVOs.size();x++) {
				if(productSkuVOs.get(x).getDealerProductSku().getIstate()==2){
					dealerProductSkuVOs.add(productSkuVOs.get(x));
				}
			}


			objectVO.setDealerProductSkuDTOs(dealerProductSkuVOs);

			//DealerProductObjectDTO copy = CopyBeanUtil.copy(objectVO, buyMap);

			for (int x = 0; x < objectVO.getDealerProductAttrDTOs().size(); x++) {
				if (objectVO.getDealerProductAttrDTOs().get(x).getDealerProductAttr().getBuyAttr() == 1) {
					objectVO.getDealerProductAttrDTOs().get(x).setMap(map);
				}
			}



			LOGGER.info("setting up productName and enProductName is success!");
			objectVO.setDealerProductSkuDTOs(productSkuVOs);
			objectVO.setDealerProductSaleSetting(productSaleSetting);
			objectVO.setDealerProductWholesaleRanges(wholesaleRanges);
			RemoteServiceSingleton.getInstance()
			.getDealerProductService().updateNewProductSkuByProductObjectDto(objectVO);

		}catch (Exception e) {
			LOGGER.error("保存商品错误！  msg:"+e.getMessage()+"商品Id = "+productId,e);
			isSaveParameter="0";
		}
		return isSaveParameter;
	}
	
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 获取所有品牌
	 * @param cid
	 * @return
	 */
	@RequestMapping("/product/getBrands")
	@ResponseBody
	public String getTopBrands(){
		List<Brand> onlyBrand=new ArrayList<Brand>();
		try {
			onlyBrand = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getBrandList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.toJSONString(onlyBrand);
	}
	/**
	 * @param brandId brandId.
	 * @return String
	 */
	@RequestMapping("/product/getOtherBrand")
	@ResponseBody
	public String getOtherBrand(String brandId){
		List<SubBrand> subBrand = new ArrayList<SubBrand>();
		try {
			subBrand = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getSubBrandListByBrandId(brandId);
		} catch (Exception e) {
			LOGGER.error("获取子品牌失败！！！"+e.getMessage(),e);
		}
		return  JSONArray.toJSONString(subBrand);
	}

}
