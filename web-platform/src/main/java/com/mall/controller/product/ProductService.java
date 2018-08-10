package com.mall.controller.product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.TdCatePubAttr;
import com.mall.category.po.TdCatePubAttrval;
import com.mall.dealer.product.dto.DealerProductAttrDTO;
import com.mall.dealer.product.dto.DealerProductObjectDTO;
import com.mall.dealer.product.dto.DealerProductSkuDTO;
import com.mall.dealer.product.po.B2cProductSellNum;
import com.mall.dealer.product.po.DealerProductAttr;
import com.mall.dealer.product.po.DealerProductAttrval;
import com.mall.dealer.product.po.DealerProductAudit;
import com.mall.dealer.product.po.DealerProductBuyAttrval;
import com.mall.dealer.product.po.DealerProductSkuAttrval;
import com.mall.supplier.product.dto.SupplierProductSkuDTO;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
/**
 * @author zhouzb
 *
 */
public class ProductService {

	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(ProductService.class);


	/**
	 * 根据sku报价是的数据回显.现货价格
	 * @param proObjVo 商品对象
	 * @return SupplierProductObjectDTO
	 */
	List<Object> showSkuPic(DealerProductObjectDTO proObjVo) {

		LOGGER.info("根据sku报价是的数据回显.");

		List<Object> saveforsku  = null;

		List<Integer> buyIndex = new ArrayList<Integer>();
		List<List<Integer>> saleIndex = new ArrayList<List<Integer>>();
		List<BigDecimal> skuPic = new ArrayList<BigDecimal>();
		//		List<BigDecimal> supplierSkuPic = new ArrayList<BigDecimal>();
		List<BigDecimal> rPrice = new ArrayList<BigDecimal>();
		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
		List<DealerProductSkuDTO> supplierProSkuVOs = proObjVo.getDealerProductSkuDTOs();

		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){

			//				BigDecimal supplierPic = supplierProSkuVOs.get(i).getDealerProductPriceMap().getSupplierprice();
			BigDecimal supplierprice = supplierProSkuVOs.get(i).getDealerProductPriceMap().getDealerprice();
			BigDecimal retailPrice = supplierProSkuVOs.get(i).getDealerProductPriceMap().getRetailPrice();
			List<DealerProductSkuAttrval> supplierProductSkuAttrvals = supplierProSkuVOs.get(i).getDealerProductSkuAttrvals();

			String buyAttrValName = "";

			List<String> saleAttrValNames = new ArrayList<String>();

			for(DealerProductSkuAttrval supplierProdSkuAttrval :supplierProductSkuAttrvals){
				Long attrId = supplierProdSkuAttrval.getAttrId();
				Boolean isName = saleIdMap.get(attrId);

				if(null != isName){
					if(isName){
						saleAttrValNames.add(supplierProdSkuAttrval.getAttrvalNameCn());
					}else if(!isName){
						buyAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
					}
				}
			}

			List<Integer> saleIndexx = new ArrayList<Integer>();
			List<DealerProductAttrDTO> skuAttrs = proObjVo.getDealerProductAttrDTOs();
			for(int j=0;j<skuAttrs.size();j++){
				//展示属性
				if(skuAttrs.get(j).getDealerProductAttr().getBuyAttr()==1){
					List<DealerProductAttrval> productBuyAttrs = skuAttrs.get(j).getDealerProductAttrvals();
					for (int k = 0; k < productBuyAttrs.size(); k++) {

						if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
							buyIndex.add(k);
						}
					}
				} else {
					//规格属性
					if(skuAttrs.get(j).getDealerProductAttr().getSaleAttr()==1){
						List<DealerProductAttrval> productSaleAttrs = 
								skuAttrs.get(j).getDealerProductAttrvals();
						for (int  x= 0; x < productSaleAttrs.size(); x++) {

							for (String saleAttrValName : saleAttrValNames) {
									if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){
										saleIndexx.add(x);
									}
							}
						}
					}
				}
			}
			//				supplierSkuPic.add(supplierPic);
			rPrice.add(retailPrice);
			skuPic.add(supplierprice);
			saleIndex.add(saleIndexx);
		}

		List<Object> skusPic = new ArrayList<Object>();
		for (int i = 0; i < buyIndex.size(); i++) {
			saveforsku = new ArrayList<Object>();
			saveforsku.add(buyIndex.get(i));
			saveforsku.add(saleIndex.get(i));
			saveforsku.add(skuPic.get(i));
			saveforsku.add(rPrice.get(i));
			//			saveforsku.add(supplierSkuPic.get(i));
//			for (int k = 0; k < saleIndex.get(i).size(); k++) {
//   				saveforsku.add(saleIndex.get(i).get(k));
//			}
			skusPic.add(saveforsku);
		}
		return skusPic;
	}
	/**
	 * 根据sku报价是的数据回显.现货价格
	 * @param proObjVo 商品对象
	 * @return SupplierProductObjectDTO
	 */
	List<Object> showSupplierSkuPic(DealerProductObjectDTO proObjVo,List<SupplierProductSkuDTO> supplierProductSkuDTOs) {

		LOGGER.info("根据sku报价是的数据回显.");

		List<Object> saveforsku  = null;

		List<Integer> buyIndex = new ArrayList<Integer>();
		List<List<Integer>> saleIndex = new ArrayList<List<Integer>>();
		//		List<BigDecimal> skuPic = new ArrayList<BigDecimal>();
		List<BigDecimal> supplierSkuPic = new ArrayList<BigDecimal>();
		//		List<BigDecimal> rPrice = new ArrayList<BigDecimal>();
		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
		List<DealerProductSkuDTO> supplierProSkuVOs = proObjVo.getDealerProductSkuDTOs();

		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){

			
			//			BigDecimal supplierprice = supplierProSkuVOs.get(i).getDealerProductPriceMap().getDealerprice();
			//			BigDecimal retailPrice = supplierProSkuVOs.get(i).getDealerProductPriceMap().getRetailPrice();
			List<DealerProductSkuAttrval> supplierProductSkuAttrvals = 
					supplierProSkuVOs.get(i).getDealerProductSkuAttrvals();

			String buyAttrValName = "";

			List<String> saleAttrValNames = new ArrayList<String>();

			for(DealerProductSkuAttrval supplierProdSkuAttrval :supplierProductSkuAttrvals){
				Long attrId = supplierProdSkuAttrval.getAttrId();
				Boolean isName = saleIdMap.get(attrId);
				if(null != isName){
						if(isName){
   						saleAttrValNames.add(supplierProdSkuAttrval.getAttrvalNameCn());
						}else if(!isName){
							buyAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
						}
					}
			}

			List<Integer> saleIndexx = new ArrayList<Integer>();
			List<DealerProductAttrDTO> skuAttrs = proObjVo.getDealerProductAttrDTOs();
			for(int j=0;j<skuAttrs.size();j++){
				//展示属性
				if(skuAttrs.get(j).getDealerProductAttr().getBuyAttr()==1){
					List<DealerProductAttrval> productBuyAttrs = skuAttrs.get(j).getDealerProductAttrvals();
					for (int k = 0; k < productBuyAttrs.size(); k++) {

						if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
							buyIndex.add(k);
						}
					}
				} else {
					//规格属性
					if(skuAttrs.get(j).getDealerProductAttr().getSaleAttr()==1){
						List<DealerProductAttrval> productSaleAttrs = 
								skuAttrs.get(j).getDealerProductAttrvals();
						for (int  x= 0; x < productSaleAttrs.size(); x++) {
							for (String saleAttrValName : saleAttrValNames) {
								if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){
									saleIndexx.add(x);
								}
							}
						}
					}
				}
			}
			saleIndex.add(saleIndexx);
			if(supplierProductSkuDTOs.size()>0){
				BigDecimal supplierPic = supplierProductSkuDTOs.get(i).getSupplierProductPriceMap().getSupplierprice();
				supplierSkuPic.add(supplierPic);
			}else{
				supplierSkuPic.add(new BigDecimal(0));
			}
			//			rPrice.add(retailPrice);
			//			skuPic.add(supplierprice);

		}

		List<Object> skusPic = new ArrayList<Object>();
		for (int i = 0; i < buyIndex.size(); i++) {
			saveforsku = new ArrayList<Object>();
			saveforsku.add(buyIndex.get(i));
			saveforsku.add(saleIndex.get(i));
			//			saveforsku.add(skuPic.get(i));
			//			saveforsku.add(rPrice.get(i));
			saveforsku.add(supplierSkuPic.get(i));
//			for (int k = 0; k < saleIndex.get(i).size(); k++) {
//   				saveforsku.add(saleIndex.get(i).get(k));
//			}
			skusPic.add(saveforsku);
		}
		return skusPic;
	}
	/**
	 * 根据sku报价是的数据回显.期货价格
	 * @param proObjVo 商品对象
	 * @return SupplierProductObjectDTO
	 */
	List<Object> showSkuPicForCollection(DealerProductObjectDTO proObjVo) {
		
		LOGGER.info("根据sku报价是的数据回显.");
		
		List<Object> saveforsku  = null;
		
		List<Integer> buyIndex = new ArrayList<Integer>();
		List<List<Integer>> saleIndex = new ArrayList<List<Integer>>();
		List<BigDecimal> supplierSkuPic = new ArrayList<BigDecimal>();
		List<BigDecimal> rPrice = new ArrayList<BigDecimal>();
		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
		List<DealerProductSkuDTO> supplierProSkuVOs = proObjVo.getDealerProductSkuDTOs();
		
		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){
			
			BigDecimal supplierPic = supplierProSkuVOs.get(i).getDealerProductPriceMap().getSupplierprice();
			BigDecimal retailPrice = supplierProSkuVOs.get(i).getDealerProductPriceMap().getRetailPrice();
			List<DealerProductSkuAttrval> supplierProductSkuAttrvals = 
					supplierProSkuVOs.get(i).getDealerProductSkuAttrvals();
			
			String buyAttrValName = "";
			
			List<String> saleAttrValNames = new ArrayList<String>();
			
			for(DealerProductSkuAttrval supplierProdSkuAttrval :supplierProductSkuAttrvals){
				Long attrId = supplierProdSkuAttrval.getAttrId();
				Boolean isName = saleIdMap.get(attrId);
				if(null != isName){
						if(isName){
   						saleAttrValNames.add(supplierProdSkuAttrval.getAttrvalNameCn());
						}else if(!isName){
							buyAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
						}
					}
			}
			
			List<Integer> saleIndexx = new ArrayList<Integer>();
			List<DealerProductAttrDTO> skuAttrs = proObjVo.getDealerProductAttrDTOs();
			for(int j=0;j<skuAttrs.size();j++){
				//展示属性
				if(skuAttrs.get(j).getDealerProductAttr().getBuyAttr()==1){
					List<DealerProductAttrval> productBuyAttrs = skuAttrs.get(j).getDealerProductAttrvals();
					for (int k = 0; k < productBuyAttrs.size(); k++) {
						
						if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
							buyIndex.add(k);
						}
					}
				} else {
					//规格属性
					if(skuAttrs.get(j).getDealerProductAttr().getSaleAttr()==1){
						List<DealerProductAttrval> productSaleAttrs = 
								skuAttrs.get(j).getDealerProductAttrvals();
						for (int  x= 0; x < productSaleAttrs.size(); x++) {
							for (String saleAttrValName : saleAttrValNames) {
									if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){
										saleIndexx.add(x);
									}
							}
						}
					}
				}
			}
			supplierSkuPic.add(supplierPic);
			rPrice.add(retailPrice);
			saleIndex.add(saleIndexx);
		}
		
		List<Object> skusPic = new ArrayList<Object>();
		for (int i = 0; i < buyIndex.size(); i++) {
			saveforsku = new ArrayList<Object>();
			saveforsku.add(buyIndex.get(i));
			saveforsku.add(saleIndex.get(i));
			saveforsku.add(supplierSkuPic.get(i));
			saveforsku.add(rPrice.get(i));
//			for (int k = 0; k < saleIndex.get(i).size(); k++) {
//   				saveforsku.add(saleIndex.get(i).get(k));
//			}
			skusPic.add(saveforsku);
		}
		return skusPic;
	}
	
	
	/**
	 * B2c价格
	 * @param proObjVo 商品对象
	 * @return SupplierProductObjectDTO
	 */
	List<Object> showB2CSkuPic(DealerProductObjectDTO proObjVo) {

		LOGGER.info("B2c价格数据组装.");

		List<Object> skusPic = new ArrayList<Object>();
		List<Object> saveforsku  = null;
		
		List<Integer> buyIndex = new ArrayList<Integer>();
		List<List<Integer>> saleIndex = new ArrayList<List<Integer>>();
		//可销售数量
		List<Long> sellCounts = new ArrayList<Long>();
		//B2C单价
		List<BigDecimal> unitPrices = new ArrayList<BigDecimal>();
		//B2C国内价
		List<BigDecimal> domesticPrices = new ArrayList<BigDecimal>();
		//B2C翼支付价
		List<BigDecimal> bestoayPrices = new ArrayList<BigDecimal>();
		List<String> customsSkuNumbers = new ArrayList<String>();
		//商品货号
		List<String> productCodeIndex = new ArrayList<String>();		
		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
		List<DealerProductSkuDTO> proSkuVOs = proObjVo.getDealerProductSkuDTOs();

		for ( int i = 0 ; i< proSkuVOs.size() ; i++ ){
			B2cProductSellNum productSellNum = proSkuVOs.get(i).getProductSellNum();
			if (null == productSellNum){
				productSellNum = new B2cProductSellNum();
			}
			BigDecimal unitPrice = proSkuVOs.get(i).getDealerProductPriceMap().getUnitPrice();
			BigDecimal domesticPrice = proSkuVOs.get(i).getDealerProductPriceMap().getDomesticPrice();
			BigDecimal bestoayPrice = proSkuVOs.get(i).getDealerProductPriceMap().getBestoayPrice();
			String customsSkuNumber = proSkuVOs.get(i).getDealerProductSku().getCustomsSkuNumber();
			String productCode = proSkuVOs.get(i).getDealerProductSku().getProductCode();
			Long sellCount = productSellNum.getSellCount();
			
			List<DealerProductSkuAttrval> dealerProductSkuAttrvals = 
					proSkuVOs.get(i).getDealerProductSkuAttrvals();

			String buyAttrValName = "";

			List<String> saleAttrValNames = new ArrayList<String>();

			for(DealerProductSkuAttrval supplierProdSkuAttrval :dealerProductSkuAttrvals){
				Long attrId = supplierProdSkuAttrval.getAttrId();
				Boolean isName = saleIdMap.get(attrId);

				if(null != isName){
						if(isName){
   						saleAttrValNames.add(supplierProdSkuAttrval.getAttrvalNameCn());
						}else if(!isName){
							buyAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
						}
					}
			}

			List<Integer> saleIndexx = new ArrayList<Integer>();
			List<DealerProductAttrDTO> skuAttrs = proObjVo.getDealerProductAttrDTOs();
			for(int j=0;j<skuAttrs.size();j++){
				//展示属性
				if(skuAttrs.get(j).getDealerProductAttr().getBuyAttr()==1){
					List<DealerProductAttrval> productBuyAttrs = skuAttrs.get(j).getDealerProductAttrvals();
					for (int k = 0; k < productBuyAttrs.size(); k++) {

						if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
							buyIndex.add(k);
						}
					}
				} else {
					//规格属性
					if(skuAttrs.get(j).getDealerProductAttr().getSaleAttr()==1){
						List<DealerProductAttrval> productSaleAttrs = 
								skuAttrs.get(j).getDealerProductAttrvals();
						for (int  x= 0; x < productSaleAttrs.size(); x++) {

							for (String saleAttrValName : saleAttrValNames) {
									if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){
										saleIndexx.add(x);
									}
							}
						}
					}
				}
			}
			saleIndex.add(saleIndexx);
			sellCounts.add(sellCount);
			unitPrices.add(unitPrice);
			domesticPrices.add(domesticPrice);
			bestoayPrices.add(bestoayPrice);
			productCodeIndex.add(productCode);//商品货号
			customsSkuNumbers.add(customsSkuNumber);
		}

		
		for (int i = 0; i < buyIndex.size(); i++) {
			saveforsku = new ArrayList<Object>();
			saveforsku.add(buyIndex.get(i));
			saveforsku.add(saleIndex.get(i));
			saveforsku.add(sellCounts.get(i));
			saveforsku.add(unitPrices.get(i));
			saveforsku.add(domesticPrices.get(i));
			saveforsku.add(bestoayPrices.get(i));
			saveforsku.add(customsSkuNumbers.get(i));
			saveforsku.add(productCodeIndex.get(i));
//			for (int k = 0; k < saleIndex.get(i).size(); k++) {
//   				saveforsku.add(saleIndex.get(i).get(k));
//			}
			skusPic.add(saveforsku);
		}
		return skusPic;
	}


	/**
	 * sku条形码数据回显.
	 * @param proObjVo 商品对象
	 * @return SupplierProductObjectDTO
	 */
	List<Object> showbarCode(DealerProductObjectDTO proObjVo) {

		LOGGER.info("sku条形码数据回显.");

		List<Object> saveforsku  = null;

		List<Integer> buyIndex = new ArrayList<Integer>();
		List<List<Integer>> saleIndex = new ArrayList<List<Integer>>();
		List<String> skuBarCode = new ArrayList<String>();
		List<String> barCodeImgs = new ArrayList<String>();
		List<Double> skuWeightList = new ArrayList<Double>();
		List<Double> skuLength = new ArrayList<Double>();
		List<Double> skuWidth = new ArrayList<Double>();
		List<Double> skuHeight = new ArrayList<Double>();

		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);

		List<DealerProductSkuDTO> supplierProSkuVOs = proObjVo.getDealerProductSkuDTOs();
		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){

			String barCode = supplierProSkuVOs.get(i).getDealerProductSku().getSkuCode();
			String barCodeImgUrl = supplierProSkuVOs.get(i).getDealerProductSku().getBarCodeImage();
			double length = supplierProSkuVOs.get(i).getDealerProductSku().getLength();
			double width = supplierProSkuVOs.get(i).getDealerProductSku().getWidth();
			double height = supplierProSkuVOs.get(i).getDealerProductSku().getHeight();
			String showBarCode = "";

			if(!"".equals(barCodeImgUrl)){
				showBarCode = "<img imgtype='temp' width='120px' height='30px' class='preview' src='" + 
						Constants.FILESERVER1+barCodeImgUrl + "'/><input type='hidden' name='barCodeImg' value='" + 
						Constants.FILESERVER1+barCodeImgUrl+ "'>";
			}

			List<DealerProductSkuAttrval> productSkuAttrvals = 
					supplierProSkuVOs.get(i).getDealerProductSkuAttrvals();
			double skuWeight = supplierProSkuVOs.get(i).getDealerProductSku().getSkuWeight();

			String buyAttrValName = "";

			List<String> saleAttrValNames = new ArrayList<String>();

			skuWeightList.add(skuWeight);
			for(DealerProductSkuAttrval prodSkuAttrval :productSkuAttrvals){
				Long attrId = prodSkuAttrval.getAttrId();
				Boolean isName = saleIdMap.get(attrId);

				if(isName){
						saleAttrValNames.add(prodSkuAttrval.getAttrvalNameCn());
					}else if(!isName){
						buyAttrValName = prodSkuAttrval.getAttrvalNameCn();
					}
			}
			List<Integer> saleIndexx = new ArrayList<Integer>();
			List<DealerProductAttrDTO> skuAttrs = proObjVo.getDealerProductAttrDTOs();
			for(int j=0;j<skuAttrs.size();j++){
				//展示属性
				if(skuAttrs.get(j).getDealerProductAttr().getBuyAttr()==1){
					List<DealerProductAttrval> productBuyAttrs = skuAttrs.get(j).getDealerProductAttrvals();
					for (int k = 0; k < productBuyAttrs.size(); k++) {

						if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
							buyIndex.add(k);
						}
					}
				} else {
					//规格属性
					if(skuAttrs.get(j).getDealerProductAttr().getSaleAttr()==1){
						List<DealerProductAttrval> productSaleAttrs = 
								skuAttrs.get(j).getDealerProductAttrvals();
						for (int  x= 0; x < productSaleAttrs.size(); x++) {
							for (String saleAttrValName : saleAttrValNames) {
									if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){
										saleIndexx.add(x);
									}
							}
						}
					}
				}
			}
			barCodeImgs.add(showBarCode);
			skuBarCode.add(barCode);
			skuLength.add(length);
			skuWidth.add(width);
			skuHeight.add(height);
			saleIndex.add(saleIndexx);
		}

		List<Object> skusBarCode = new ArrayList<Object>();
		for (int i = 0; i < buyIndex.size(); i++) {
			saveforsku = new ArrayList<Object>();
			saveforsku.add(buyIndex.get(i));
			saveforsku.add(saleIndex.get(i));
			saveforsku.add(skuBarCode.get(i));
			saveforsku.add(barCodeImgs.get(i));
			saveforsku.add(skuLength.get(i));
			saveforsku.add(skuWidth.get(i));
			saveforsku.add(skuHeight.get(i));
			saveforsku.add(skuWeightList.get(i));
//			for (int k = 0; k < saleIndex.get(i).size(); k++) {
//   				saveforsku.add(saleIndex.get(i).get(k));
//			}
			skusBarCode.add(saveforsku);
		}
		return skusBarCode;
	}
	/**
	 * saleAttrId is true
	 * buyAttrId is false
	 * @param proObjVo
	 * @return
	 */
	private Map<Long,Boolean> getSaleIdMap(DealerProductObjectDTO proObjVo){
		Map<Long, Boolean> saleMap = new LinkedHashMap<Long,Boolean>();
		List<DealerProductAttrDTO> supplierProductAttrDTOs = proObjVo.getDealerProductAttrDTOs();
		for(DealerProductAttrDTO attrDto : supplierProductAttrDTOs){
			DealerProductAttr supplierProductAttr = attrDto.getDealerProductAttr();
			if(supplierProductAttr.getSaleAttr().equals((short)1)){
				saleMap.put(supplierProductAttr.getAttrId(), true);
			}else if(supplierProductAttr.getBuyAttr().equals((short)1)){
				saleMap.put(supplierProductAttr.getAttrId(), false);
			}
		}

		return saleMap;
	}


	/**.
	 * 拼装 图片Json 前台使用
	 * @param pageAttrs List<DealerProductAttrDTO>
	 * @author zhouzb
	 * @return String List<LinkedHashMap<String,Object>>
	 */
	public List<LinkedHashMap<String,Object>> imgShow(List<DealerProductAttrDTO> pageAttrs) {
		List<LinkedHashMap<String,Object>> imgUrl = new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap<String,Object> imglist = null;
		List<String> urllist =  null;
		for(int i = 0 ; i< pageAttrs.size() ; i++){
			List<DealerProductAttrval> getSupplierProductAttrvals = pageAttrs.get(i).getDealerProductAttrvals();
			for(int j = 0 ; j < getSupplierProductAttrvals.size() ;  j++  ){
				String lineAttrvalNameCn = getSupplierProductAttrvals.get(j).getLineAttrvalNameCn();
				Map<Integer, List<DealerProductBuyAttrval>> map2 = pageAttrs.get(i).getMap();
				List<DealerProductBuyAttrval> list  = new ArrayList<DealerProductBuyAttrval>();
				if(map2!=null){
					list = map2.get(j);
					if(list!=null&&list.size()>0){
						urllist =  new ArrayList<String>();
						imglist = new LinkedHashMap<String, Object>();
						for(DealerProductBuyAttrval supplierProductBuyAttrval:list){

							String imgurl	= Constants.P2+supplierProductBuyAttrval.getImgurl();
							urllist.add(imgurl);
						} 
						imglist.put("1", lineAttrvalNameCn);
						imglist.put("2", j+"");
						imglist.put("3", urllist);
						imgUrl.add(imglist);
					}

				}
			}
		}
		return imgUrl;
	}

	/**.
	 * 使用IO 读取 文件  图文详情
	 * @param fileurl 图文详情地址
	 * @author zhouzb
	 * @return String
	 * @throws Exception 
	 */
	public String getBufImgInfo(String fileurl) throws Exception{

		InputStreamReader is = null;
		BufferedReader bis = null;
		StringBuffer  buf = null;

		if(!fileurl.startsWith("Http")){
			fileurl=  Constants.H0+fileurl;
		}
		URL url = new URL(fileurl); 
		buf = new StringBuffer();
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
		uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
		uc.connect();  
		// 创建输入流  
		Thread.sleep(Constants.NUM1000);
		InputStream iputstream = uc.getInputStream();
		is  = new InputStreamReader(iputstream,"utf-8");  
		bis = new BufferedReader(is);  
		// 从输入流读取数据  
		while (bis.ready()) {  
			int c = bis.read();  
			buf.append((char)c);
			System.out.print((char)c);  
		}  
		// 关闭输入流  
		bis.close();  
		is.close();  
		iputstream.close();

		return buf.toString();

	}

	/**.
	 * 组合类目模板和商品属性 对象  前台遍历使用
	 * @param categoryAttrAndValList Map<TdCatePubAttr, List<TdCatePubAttrval>>
	 * @param proObjVo DealerProductObjectDTO
	 * @return List List<DealerProductAttrDTO>
	 */
	public List<DealerProductAttrDTO> showAttrAndAttrVal(
			Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList,
			DealerProductObjectDTO proObjVo) {
		List<DealerProductAttrDTO> productAttrVOs = 
				proObjVo.getDealerProductAttrDTOs();
		List<String> prodAttrNames = new ArrayList<String>();

		//获取所有的属性名集合
		if(productAttrVOs!=null&&productAttrVOs.size()>0){
			for (int i = 0; i < productAttrVOs.size(); i++) {
				String attrNameCn = 
						productAttrVOs.get(i).getDealerProductAttr().getAttrNameCn();
				prodAttrNames.add(attrNameCn);
			}
		}

		//迭代类目中的属性名
		List<DealerProductAttrDTO> pageAttrs = new ArrayList<DealerProductAttrDTO>();
		Set<TdCatePubAttr> cateAttrs = categoryAttrAndValList.keySet();
		Iterator<TdCatePubAttr> iterator = cateAttrs.iterator();
		while (iterator.hasNext()) {

			TdCatePubAttr tdCatePubAttr = iterator.next();

			String lineAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
			String lineAttrNameEn = tdCatePubAttr.getLineAttrName();


			DealerProductAttrDTO productAttrVO = new DealerProductAttrDTO();
			DealerProductAttr productAttr = new DealerProductAttr();

			// 判断商品中是否有对应的属性名
			// 判断商品中是否有对应的属性名
			if (prodAttrNames.contains(lineAttrNameCn)) {

				for (int i = 0; i < productAttrVOs.size(); i++) {

					String productAttrNameCn = productAttrVOs.get(i).getDealerProductAttr().getAttrNameCn();
					if (lineAttrNameCn.equals(productAttrNameCn)) {




						DealerProductAttrval sProdAttrAddAttrVal = null;
						productAttr = productAttrVOs.get(i).getDealerProductAttr();

						List<DealerProductAttrval> productAttrvals = 
								productAttrVOs.get(i).getDealerProductAttrvals();


						List<String> newListAttrval = new ArrayList<String>();
						//获取所有的属性值集合
						for (DealerProductAttrval newProductAttrval : productAttrvals) {
							String lineAttrvalNameCn = newProductAttrval.getLineAttrvalNameCn();
							newListAttrval.add(lineAttrvalNameCn);
						}

						List<TdCatePubAttrval> cateAttrValList = categoryAttrAndValList.get(tdCatePubAttr);

						for (int j = 0; j < cateAttrValList.size(); j++) {
							String lineAttrvalNameCn = cateAttrValList.get(j).getLineAttrvalNameCn();
							String lineAttrvalNameEn = cateAttrValList.get(j).getLineAttrvalName();

							for(int k =0;k  < productAttrvals.size();k++){

								if(lineAttrvalNameCn.equals(
										productAttrvals.get(k).getLineAttrvalNameCn())){
									productAttrvals.get(k).setLineAttrvalName(lineAttrvalNameEn);
									productAttrvals.get(k).setLineAttrvalNameCn(lineAttrvalNameCn);
								}
							}


							if ((!newListAttrval.contains(lineAttrvalNameCn))
									&&(!newListAttrval.contains(lineAttrvalNameEn))) {
								sProdAttrAddAttrVal = new DealerProductAttrval();
								sProdAttrAddAttrVal.setLineAttrvalName(lineAttrvalNameEn);
								sProdAttrAddAttrVal.setLineAttrvalNameCn(lineAttrvalNameCn);
								sProdAttrAddAttrVal.setIsProdAttr(false);

								if(productAttrVOs.get(i).getDealerProductAttr().getType()
										!= Constants.INT3) {
									productAttrvals.add(sProdAttrAddAttrVal);
								}
							}
						}

						if (sProdAttrAddAttrVal != null) {
							if(productAttrVOs.get(i).getDealerProductAttr().getType()
									!= Constants.INT3) {
								productAttrvals.add(sProdAttrAddAttrVal);
							}
						}


						Map<Integer, List<DealerProductBuyAttrval>> map = productAttrVOs.get(i).getMap();
						productAttr.setAttrNameCn(lineAttrNameCn);
						productAttr.setAttrName(lineAttrNameEn);

						productAttrVO.setDealerProductAttr(productAttr);
						productAttrVO.setDealerProductAttrvals(productAttrvals);
						productAttrVO.setMap(map);

					}

					if(!pageAttrs.contains(productAttrVO))
						pageAttrs.add(productAttrVO);


				}


			} else if (prodAttrNames.contains(lineAttrNameEn)) {

				for (int i = 0; i < productAttrVOs.size(); i++) {

					String productAttrNameCn = productAttrVOs.get(i).getDealerProductAttr().getAttrNameCn();

					if (lineAttrNameEn.equals(productAttrNameCn)) {


						DealerProductAttrval sProdAttrAddAttrVal = null;
						productAttr = productAttrVOs.get(i).getDealerProductAttr();

						List<DealerProductAttrval> productAttrvals = 
								productAttrVOs.get(i).getDealerProductAttrvals();


						List<String> newListAttrval = new ArrayList<String>();
						//获取所有的属性值集合
						for (DealerProductAttrval newProductAttrval : productAttrvals) {
							String lineAttrvalNameCn = newProductAttrval.getLineAttrvalNameCn();
							newListAttrval.add(lineAttrvalNameCn);
						}

						List<TdCatePubAttrval> cateAttrValList = categoryAttrAndValList.get(tdCatePubAttr);

						for (int j = 0; j < cateAttrValList.size(); j++) {

							String lineAttrvalNameCn = cateAttrValList.get(j).getLineAttrvalNameCn();
							String lineAttrvalNameEn = cateAttrValList.get(j).getLineAttrvalName();

							for(int k =0;k  < productAttrvals.size();k++){

								if(lineAttrvalNameEn.equals(productAttrvals.get(k).getLineAttrvalNameCn())){
									
									productAttrvals.get(k).setLineAttrvalName(lineAttrvalNameEn);
									productAttrvals.get(k).setLineAttrvalNameCn(lineAttrvalNameCn);
									
								}
							}


							if ((!newListAttrval.contains(lineAttrvalNameCn))&&(!newListAttrval.contains(lineAttrvalNameEn))) {
								
								sProdAttrAddAttrVal = new DealerProductAttrval();

								sProdAttrAddAttrVal.setLineAttrvalName(lineAttrvalNameEn);
								sProdAttrAddAttrVal.setLineAttrvalNameCn(lineAttrvalNameCn);
								sProdAttrAddAttrVal.setIsProdAttr(false);

								if (sProdAttrAddAttrVal != null) {
									
									if(productAttrVOs.get(i).getDealerProductAttr().getType() != Constants.INT3){
										
										productAttrvals.add(sProdAttrAddAttrVal);
										
									}
								}
							}
						}

						if (sProdAttrAddAttrVal != null) {
							
							if(productAttrVOs.get(i).getDealerProductAttr().getType() != Constants.INT3) {
								productAttrvals.add(sProdAttrAddAttrVal);
							}
							
						}

						Map<Integer, List<DealerProductBuyAttrval>> map = productAttrVOs.get(i).getMap();


						productAttr.setAttrNameCn(lineAttrNameCn);
						productAttr.setAttrName(lineAttrNameEn);

						productAttrVO.setDealerProductAttr(productAttr);
						productAttrVO.setDealerProductAttrvals(productAttrvals);
						productAttrVO.setMap(map);

					}
					if(!pageAttrs.contains(productAttrVO))
						pageAttrs.add(productAttrVO);
				}

			}else {

				List<TdCatePubAttrval> list = categoryAttrAndValList.get(tdCatePubAttr);
				DealerProductAttrval supplierProductAttrval = null;
				List<DealerProductAttrval> productAttrvals = new ArrayList<DealerProductAttrval>();

				for(int j=0;j<list.size();j++){
					supplierProductAttrval = new DealerProductAttrval();
					supplierProductAttrval.setLineAttrvalName(list.get(j).getLineAttrvalName());
					supplierProductAttrval.setLineAttrvalNameCn(list.get(j).getLineAttrvalNameCn());
					supplierProductAttrval.setIsProdAttr(false); 
					productAttrvals.add(supplierProductAttrval);
				}

				productAttr.setAttrNameCn(lineAttrNameCn);
				productAttr.setAttrName(lineAttrNameEn);
				productAttr.setIsneed(tdCatePubAttr.getRequired());
				productAttr.setType(tdCatePubAttr.getType());
				productAttr.setStyle(tdCatePubAttr.getStyle());
				productAttr.setBuyAttr(tdCatePubAttr.getBuyAttr());
				productAttr.setSaleAttr( tdCatePubAttr.getSaleAttr());
				productAttr.setIsbrand(tdCatePubAttr.getIsbrand());
				productAttr.setIsneed(tdCatePubAttr.getRequired());
				productAttr.setIsalter( tdCatePubAttr.getIsmodify());

				productAttrVO.setDealerProductAttr(productAttr);
				productAttrVO.setDealerProductAttrvals(productAttrvals);

				pageAttrs.add(productAttrVO);

			}
		}
		if(productAttrVOs!=null&&productAttrVOs.size()>0){
			for(int i = 0 ; i< productAttrVOs.size() ; i++){
				List<DealerProductAttrval> getProductAttrvals = 
						productAttrVOs.get(i).getDealerProductAttrvals();
				LinkedHashSet<DealerProductAttrval> onlyVals  = 
						new LinkedHashSet<DealerProductAttrval>(getProductAttrvals);
				getProductAttrvals.clear();
				getProductAttrvals.addAll(onlyVals);
			}
		}
		return pageAttrs;
	}



	public String checkProductIsEdit(Long productId){

		String editStat = Constants.SUCCESS;

		DealerProductObjectDTO proObjVo = RemoteServiceSingleton.getInstance()
				.getDealerProductService().findProductObjectById(productId, "");

		//商品下架或者删除时
		if(proObjVo.getDealerProduct().getIstate() 
				== com.mall.dealer.product.util.Constants.ISTATE_STATUS_OFF ||	
				proObjVo.getDealerProduct().getIstate() 
				== com.mall.dealer.product.util.Constants.ISTATE_STATUS_DELETE){
			//商品删除时
			if(proObjVo.getDealerProduct().getIstate() 
					== com.mall.dealer.product.util.Constants.ISTATE_STATUS_DELETE){
				editStat = Constants.SAMEISNULL;
			}else{
				//遍历审核信息
				for(DealerProductAudit audit: proObjVo.getDealerProductAudits()){
					//待审核时
					if(audit.getCounterfeittypeid().equals(
							com.mall.dealer.product.util.Constants.AUDIT_STATUS_WAIT)){
						editStat = Constants.SAMEISNULL;
					}
				}
			}

		}

		return editStat;

	}
	
	
	public String checkProductIsEdit(String target ,DealerProductObjectDTO proObjVo){
		
		String editStat = Constants.SUCCESS;
		
		if(!target.equals(Constants.SUCCESS)){

			//商品下架或者删除时
			if (proObjVo.getDealerProduct().getIstate() 
					== com.mall.dealer.product.util.Constants.ISTATE_STATUS_OFF ||	
					proObjVo.getDealerProduct().getIstate() 
					== com.mall.dealer.product.util.Constants.ISTATE_STATUS_DELETE){
				//商品删除时

				if (proObjVo.getDealerProduct().getIstate() 
						== com.mall.dealer.product.util.Constants.ISTATE_STATUS_DELETE){

					editStat = Constants.ERROR;

				}else{
					//遍历审核信息
					for(DealerProductAudit audit: proObjVo.getDealerProductAudits()){
						//待审核时
						if(audit.getCounterfeittypeid().equals(
								com.mall.dealer.product.util.Constants.AUDIT_STATUS_WAIT)){

							editStat = Constants.ERROR;

						}
					}
				}
			}
		}
		return editStat;
	}

}
