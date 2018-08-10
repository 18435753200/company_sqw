package com.mall.controller.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.TdCatePub;
import com.mall.category.po.TdCatePubAttr;
import com.mall.category.po.TdCatePubAttrval;
import com.mall.supplier.product.dto.SupplierProductAttrDTO;
import com.mall.supplier.product.dto.SupplierProductObjectDTO;
import com.mall.supplier.product.dto.SupplierProductSkuDTO;
import com.mall.supplier.product.po.SupplierProductAttach;
import com.mall.supplier.product.po.SupplierProductAttr;
import com.mall.supplier.product.po.SupplierProductAttrval;
import com.mall.supplier.product.po.SupplierProductBuyAttrval;
import com.mall.supplier.product.po.SupplierProductSkuAttrval;
import com.mall.supplier.product.po.SupplierProductWholesaleRange;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.DateTool;

/**
 * wofe查看供应商修改信息
 * @author xusq
 *
 */

@Controller
public class ProductUpdateViewController {

	
	private final static Log LOGGER = LogFactory.getLogger(ProductUpdateViewController.class);
	
	@RequestMapping("/product/showUpdate")
	public String showSupplierUpdate(Long productId,Model model){
		
		  SupplierProductObjectDTO proObjVo = new SupplierProductObjectDTO();
	        Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList = 
	                new LinkedHashMap<TdCatePubAttr, List<TdCatePubAttrval>>();
	        List<TdCatePub> cateNames = new ArrayList<TdCatePub>();
	        try {
	            proObjVo = RemoteServiceSingleton.getInstance()
	                    .getSupplierProductService().findProductObjectById(productId, "");
	            String categoryId = proObjVo.getSupplierProduct().getCatePubId();
	            categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
	                    .getCategoryAttrAndValList(categoryId);
	            cateNames = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId);
	            
	        } catch (Exception e) {
	            LOGGER.error("获取商品信息失败：" + e.getMessage(),e);
	        }
	        

	        // //属性和属性值回显
	        List<SupplierProductAttrDTO> productAttrVOs = proObjVo.getSupplierProductAttrDTOs();
	        List<SupplierProductAttrDTO> pageAttrs = showAttrAndAttrVal(
	                categoryAttrAndValList, productAttrVOs);

	        // 图片回显
	        List<LinkedHashMap<String, Object>> imgUrl = imgShow(pageAttrs);

	        JSONArray returnImgUrlAndName = JSONArray.fromObject(imgUrl);

	        // 价格或者sku回显
	        List<SupplierProductWholesaleRange> supplierProductWholesaleRanges = proObjVo
	                .getSupplierProductWholesaleRanges();

	        Map<String, Object> productPic = new LinkedHashMap<String, Object>();
	        List<Long> countList = new ArrayList<Long>();
	        List<BigDecimal> picList = new ArrayList<BigDecimal>();

	        if (supplierProductWholesaleRanges != null && supplierProductWholesaleRanges.size() > 0) {
	            
	            for (SupplierProductWholesaleRange supplierProductWholesaleRange : supplierProductWholesaleRanges) {
	                Long startQty = supplierProductWholesaleRange.getStartQty();
	                countList.add(startQty);
	                BigDecimal discountPic = supplierProductWholesaleRange.getDiscount();
	                picList.add(discountPic);
	            }

	            productPic.put("type", 0);
	            productPic.put("start", countList);
	            productPic.put("pic", picList);
	        } else {
	            // 按照sku的价格回显
	            List<Object> skusPic = showSkuPic(proObjVo);
	            productPic.put("type", 1);
	            productPic.put("start", 0);
	            productPic.put("pic", skusPic);
	        }
	        JSONArray jsonProductPic = JSONArray.fromObject(productPic);

	        proObjVo.setSupplierProductAttrDTOs(pageAttrs);

	        
	        //将默认日期设置为null
	        Date receiveDate = proObjVo.getSupplierProductDetail().getReceiveDate();
	        Date deliverDate = proObjVo.getSupplierProductDetail().getDeliverDate();
	        
	        if(DateTool.getDateYear(receiveDate)==1999){
	        	proObjVo.getSupplierProductDetail().setReceiveDate(null);
	        }
	        if(DateTool.getDateYear(deliverDate)==1999){
	        	proObjVo.getSupplierProductDetail().setDeliverDate(null);
	        }
	        
	        
	        // 图文详情和资质图片
	        String fileurl = "";
	        List<SupplierProductAttach> productAttachs = proObjVo.getSupplierProductAttachs();
	        String attch = "";
	        List<String> qualification = new ArrayList<String>();
	        for (SupplierProductAttach supplierProductAttach : productAttachs) {
	        	if(Constants.PRODUCT_DESCRIPTIONS == supplierProductAttach.getType()){
	        		fileurl = Constants.P1 + supplierProductAttach.getFileurl();
	        		//attch = getAttrach(fileurl);
	        	}
	        	
	        	if(supplierProductAttach.getType() == Constants.PRODUCT_QUALIFICATION){
	        		qualification.add(Constants.P1+supplierProductAttach.getFileurl());
	        	}
			}
	        
	        JSONArray qualificationUrl = JSONArray.fromObject(qualification);
	        

	        JSONArray skusCode = JSONArray.fromObject(showSkuCode(proObjVo));
	        
	        Set<TdCatePubAttr> keySet = categoryAttrAndValList.keySet();
	        List<String> buyAttrName = new ArrayList<String>();
			List<String> saleAttrName = new ArrayList<String>();
			
			for (TdCatePubAttr tdCatePubAttr : keySet) {
				if(tdCatePubAttr.getBuyAttr()==1){
					String buyAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
					String buyAttrNameEn = tdCatePubAttr.getLineAttrName();
					buyAttrName.add(0, buyAttrNameCn);
					buyAttrName.add(1, buyAttrNameEn);
				}
				if(tdCatePubAttr.getSaleAttr()==1){
					String saleAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
					String saleAttrNameEn = tdCatePubAttr.getLineAttrName();
					saleAttrName.add(0, saleAttrNameCn);
					saleAttrName.add(1, saleAttrNameEn);
					
				}
				
			}
			
			
			
			
			
			
			
			model.addAttribute("fileurl", fileurl);
			model.addAttribute("buyAttrName", buyAttrName);
			model.addAttribute("saleAttrName", saleAttrName);
	        model.addAttribute("qualificationUrl", qualificationUrl);
	        model.addAttribute("skusCode", skusCode);
	        model.addAttribute("cateNames", cateNames);
	        model.addAttribute("attch", attch);
	        model.addAttribute("proObj", proObjVo);
	        model.addAttribute("jsonImg", returnImgUrlAndName);
	        model.addAttribute("skuPriceAndCount", jsonProductPic);
	        
	        return "/dealerseller/product/showProducUpdate";
	        
	}
	
	
	
	
	 /**
     * 回显属性和属性名.
     * 
     * @param categoryAttrAndValList
     *            类目属性属性值
     * @param productAttrVOs
     *            商品属性属性值
     * @return List<SupplierProductAttrDTO>
     */
    private List<SupplierProductAttrDTO> showAttrAndAttrVal(
            Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList,
            List<SupplierProductAttrDTO> productAttrVOs) {

        List<String> prodAttrNames = new ArrayList<String>();

        // 获取所有的属性名集合
        if (productAttrVOs != null && productAttrVOs.size() > 0) {
            for (int i = 0; i < productAttrVOs.size(); i++) {
                String attrNameCn = productAttrVOs.get(i)
                        .getSupplierProductAttr().getAttrNameCn();
                prodAttrNames.add(attrNameCn);
            }
        }

        // 迭代类目中的属性名
        List<SupplierProductAttrDTO> pageAttrs = new ArrayList<SupplierProductAttrDTO>();
        Set<TdCatePubAttr> cateAttrs = categoryAttrAndValList.keySet();
        Iterator<TdCatePubAttr> iterator = cateAttrs.iterator();
        while (iterator.hasNext()) {

            TdCatePubAttr tdCatePubAttr = iterator.next();

            String lineAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
            String lineAttrNameEn = tdCatePubAttr.getLineAttrName();
            String attrDescribe = tdCatePubAttr.getAttrDescribe();

            SupplierProductAttrDTO productAttrVO = new SupplierProductAttrDTO();
            SupplierProductAttr productAttr = new SupplierProductAttr();

            // 判断商品中是否有对应的属性名
            if (prodAttrNames.contains(lineAttrNameCn)) {

                for (int i = 0; i < productAttrVOs.size(); i++) {

                    String productAttrNameCn = productAttrVOs.get(i)
                            .getSupplierProductAttr().getAttrNameCn();
                    if (lineAttrNameCn.equals(productAttrNameCn)) {

                        SupplierProductAttrval sProdAttrAddAttrVal = null;
                        productAttr = productAttrVOs.get(i)
                                .getSupplierProductAttr();

                        List<SupplierProductAttrval> productAttrvals =
                                productAttrVOs.get(i).getSupplierProductAttrvals();

                        List<String> newListAttrval = new ArrayList<String>();
                        // 获取所有的属性值集合
                        for (SupplierProductAttrval newProductAttrval : productAttrvals) {
                            String lineAttrvalNameCn = newProductAttrval
                                    .getLineAttrvalNameCn();
                            newListAttrval.add(lineAttrvalNameCn);
                        }

                        List<TdCatePubAttrval> cateAttrValList =
                                categoryAttrAndValList.get(tdCatePubAttr);

                        for (int j = 0; j < cateAttrValList.size(); j++) {
                            String lineAttrvalNameCn = cateAttrValList.get(j)
                                    .getLineAttrvalNameCn();
                            String lineAttrvalNameEn = cateAttrValList.get(j)
                                    .getLineAttrvalName();

                            for (int k = 0; k < productAttrvals.size(); k++) {

                                if (lineAttrvalNameCn.equals(productAttrvals
                                        .get(k).getLineAttrvalNameCn())) {
                                    productAttrvals.get(k).setLineAttrvalName(
                                            lineAttrvalNameEn);
                                    productAttrvals.get(k)
                                            .setLineAttrvalNameCn(
                                                    lineAttrvalNameCn);
                                }
                            }

                            if ((!newListAttrval.contains(lineAttrvalNameCn))
                                    && (!newListAttrval
                                            .contains(lineAttrvalNameEn))) {
                                sProdAttrAddAttrVal = new SupplierProductAttrval();
                                sProdAttrAddAttrVal.setLineAttrvalName(lineAttrvalNameEn);
                                sProdAttrAddAttrVal.setLineAttrvalNameCn(lineAttrvalNameCn);
                                sProdAttrAddAttrVal.setIsProdAttr(false);

                                if (productAttrVOs.get(i).getSupplierProductAttr().getType() != 3) {
                                    productAttrvals.add(sProdAttrAddAttrVal);
                                }
                            }
                        }

                        if (sProdAttrAddAttrVal != null) {
                            if (productAttrVOs.get(i).getSupplierProductAttr().getType() != 3) {
                                productAttrvals.add(sProdAttrAddAttrVal);
                            }
                        }

                        Map<Integer, List<SupplierProductBuyAttrval>> map = productAttrVOs.get(i).getMap();
                        productAttr.setAttrNameCn(lineAttrNameCn);
                        productAttr.setAttrName(lineAttrNameEn);
                        //TODO
                        productAttr.setIsmeasure(attrDescribe);

                        productAttrVO.setSupplierProductAttr(productAttr);
                        productAttrVO.setSupplierProductAttrvals(productAttrvals);
                        productAttrVO.setMap(map);

                    }

                    if (!pageAttrs.contains(productAttrVO)) {
                        pageAttrs.add(productAttrVO);
                    }

                }

            } else if (prodAttrNames.contains(lineAttrNameEn)) {

                for (int i = 0; i < productAttrVOs.size(); i++) {

                    String productAttrNameCn = productAttrVOs.get(i)
                            .getSupplierProductAttr().getAttrNameCn();

                    if (lineAttrNameEn.equals(productAttrNameCn)) {

                        SupplierProductAttrval sProdAttrAddAttrVal = null;
                        productAttr = productAttrVOs.get(i)
                                .getSupplierProductAttr();

                        List<SupplierProductAttrval> productAttrvals =
                                productAttrVOs.get(i).getSupplierProductAttrvals();

                        List<String> newListAttrval = new ArrayList<String>();
                        // 获取所有的属性值集合
                        for (SupplierProductAttrval newProductAttrval : productAttrvals) {
                            String lineAttrvalNameCn = newProductAttrval
                                    .getLineAttrvalNameCn();
                            newListAttrval.add(lineAttrvalNameCn);
                        }

                        List<TdCatePubAttrval> cateAttrValList = categoryAttrAndValList.get(tdCatePubAttr);

                        for (int j = 0; j < cateAttrValList.size(); j++) {

                            String lineAttrvalNameCn = cateAttrValList.get(j)
                                    .getLineAttrvalNameCn();
                            String lineAttrvalNameEn = cateAttrValList.get(j)
                                    .getLineAttrvalName();

                            for (int k = 0; k < productAttrvals.size(); k++) {

                                if (lineAttrvalNameEn.equals(productAttrvals.get(k).getLineAttrvalNameCn())) {
                                    productAttrvals.get(k).setLineAttrvalName(lineAttrvalNameEn);
                                    productAttrvals.get(k).setLineAttrvalNameCn(lineAttrvalNameCn);
                                }
                            }

                            if ((!newListAttrval.contains(lineAttrvalNameCn))
                                    && (!newListAttrval.contains(lineAttrvalNameEn))) {
                                sProdAttrAddAttrVal = new SupplierProductAttrval();

                                sProdAttrAddAttrVal.setLineAttrvalName(lineAttrvalNameEn);
                                sProdAttrAddAttrVal.setLineAttrvalNameCn(lineAttrvalNameCn);
                                sProdAttrAddAttrVal.setIsProdAttr(false);

                                if (sProdAttrAddAttrVal != null) {
                                    if (productAttrVOs.get(i).getSupplierProductAttr().getType() != 3) {
                                        productAttrvals.add(sProdAttrAddAttrVal);
                                    }
                                }
                            }
                        }

                        if (sProdAttrAddAttrVal != null) {
                            if (productAttrVOs.get(i).getSupplierProductAttr().getType() != 3) {
                                productAttrvals.add(sProdAttrAddAttrVal);
                            }
                        }

                        Map<Integer, List<SupplierProductBuyAttrval>> map = productAttrVOs.get(i).getMap();

                        productAttr.setAttrNameCn(lineAttrNameCn);
                        productAttr.setAttrName(lineAttrNameEn);
                        productAttr.setIsmeasure(attrDescribe);

                        
                        
                        productAttrVO.setSupplierProductAttr(productAttr);
                        productAttrVO
                                .setSupplierProductAttrvals(productAttrvals);
                        productAttrVO.setMap(map);

                    }
                    if (!pageAttrs.contains(productAttrVO)) {
                        pageAttrs.add(productAttrVO);
                    }
                }

            } else {

                List<TdCatePubAttrval> list = categoryAttrAndValList
                        .get(tdCatePubAttr);
                SupplierProductAttrval supplierProductAttrval = null;
                List<SupplierProductAttrval> productAttrvals = new ArrayList<SupplierProductAttrval>();

                for (int j = 0; j < list.size(); j++) {
                    supplierProductAttrval = new SupplierProductAttrval();
                    supplierProductAttrval.setLineAttrvalName(list.get(j)
                            .getLineAttrvalName());
                    supplierProductAttrval.setLineAttrvalNameCn(list.get(j)
                            .getLineAttrvalNameCn());
                    supplierProductAttrval.setIsProdAttr(false);
                    productAttrvals.add(supplierProductAttrval);
                }

                productAttr.setAttrNameCn(lineAttrNameCn);
                productAttr.setAttrName(lineAttrNameEn);
                productAttr.setIsneed(tdCatePubAttr.getRequired());
                productAttr.setType(tdCatePubAttr.getType());
                productAttr.setStyle(tdCatePubAttr.getStyle());
                productAttr.setBuyAttr(tdCatePubAttr.getBuyAttr());
                productAttr.setSaleAttr(tdCatePubAttr.getSaleAttr());
                productAttr.setIsbrand(tdCatePubAttr.getIsbrand());
                productAttr.setIsneed(tdCatePubAttr.getRequired());
                productAttr.setIsalter(tdCatePubAttr.getIsmodify());
                productAttr.setIsmeasure(attrDescribe);

                
                
                productAttrVO.setSupplierProductAttr(productAttr);
                productAttrVO.setSupplierProductAttrvals(productAttrvals);

                pageAttrs.add(productAttrVO);

            }
        }
        if (productAttrVOs != null && productAttrVOs.size() > 0) {
            for (int i = 0; i < productAttrVOs.size(); i++) {
                List<SupplierProductAttrval> getProductAttrvals =
                        productAttrVOs.get(i).getSupplierProductAttrvals();
                LinkedHashSet<SupplierProductAttrval> onlyVals = 
                        new LinkedHashSet<SupplierProductAttrval>(getProductAttrvals);
                getProductAttrvals.clear();
                getProductAttrvals.addAll(onlyVals);
            }
        }
        return pageAttrs;
    }
    
    
    
    /**
     * 图片回显.
     * 
     * @param pageAttrs
     *            页面显示属性属性值
     * @return 图片list
     */
    private List<LinkedHashMap<String, Object>> imgShow(
            List<SupplierProductAttrDTO> pageAttrs) {

        List<LinkedHashMap<String, Object>> imgUrl = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> imgList = null;
        List<String> urlList = null;

        for (int i = 0; i < pageAttrs.size(); i++) {

            List<SupplierProductAttrval> getSupplierProductAttrvals = pageAttrs
                    .get(i).getSupplierProductAttrvals();
            if (null != getSupplierProductAttrvals) {
                for (int j = 0; j < getSupplierProductAttrvals.size(); j++) {
                    String lineAttrvalNameCn = getSupplierProductAttrvals.get(j)
                                .getLineAttrvalNameCn();
                    Map<Integer, List<SupplierProductBuyAttrval>> map2 = pageAttrs
                            .get(i).getMap();
                    List<SupplierProductBuyAttrval> list = new ArrayList<SupplierProductBuyAttrval>();

                    if (map2 != null) {
                        list = map2.get(j);

                        if (list != null && list.size() > 0) {
                            urlList = new ArrayList<String>();
                            imgList = new LinkedHashMap<String, Object>();

                            for (SupplierProductBuyAttrval supplierProductBuyAttrval : list) {
                                String imgurl = Constants.P0
                                        + supplierProductBuyAttrval.getImgurl();

                                urlList.add(imgurl);
                            }

                            imgList.put("1", lineAttrvalNameCn);
                            imgList.put("2", j + "");
                            imgList.put("3", urlList);
                            imgUrl.add(imgList);
                        }

                    }
                }
            }
        }
        return imgUrl;
    }
    
    
    
    /**
   	 * 根据sku报价是的数据回显.
   	 * @param proObjVo 商品对象
   	 * @return SupplierProductObjectDTO
   	 */
   	List<Object> showSkuPic(SupplierProductObjectDTO proObjVo) {
   		List<Object> saveforsku  = null;
   		
   		List<Integer> buyIndex = new ArrayList<Integer>();
   		List<Integer> saleIndex = new ArrayList<Integer>();
   		List<BigDecimal> skuPic = new ArrayList<BigDecimal>();
   		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
   		List<SupplierProductSkuDTO> supplierProSkuVOs = proObjVo.getSupplierProductSkuDTOs();
   		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){

    			BigDecimal supplierprice = supplierProSkuVOs.get(i).getSupplierProductPriceMap().getSupplierprice();
   				List<SupplierProductSkuAttrval> supplierProductSkuAttrvals = supplierProSkuVOs.get(i).getSupplierProductSkuAttrvals();
   				
   				String buyAttrValName = "";
   				
   				String saleAttrValName = "";
   				
   				for(SupplierProductSkuAttrval supplierProdSkuAttrval :supplierProductSkuAttrvals){
   					Long attrId = supplierProdSkuAttrval.getAttrId();
   					Boolean isName = saleIdMap.get(attrId);
   					
   					if(null != isName){
   						if(isName){
   							saleAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
   						}else if(!isName){
   							buyAttrValName = supplierProdSkuAttrval.getAttrvalNameCn();
   						}
   					}
   				}
   				
   				
   				List<SupplierProductAttrDTO> skuAttrs = proObjVo.getSupplierProductAttrDTOs();
   				for(int j=0;j<skuAttrs.size();j++){
   					//展示属性
   					if(skuAttrs.get(j).getSupplierProductAttr().getBuyAttr()==1){
   						List<SupplierProductAttrval> productBuyAttrs = skuAttrs.get(j).getSupplierProductAttrvals();
   						for (int k = 0; k < productBuyAttrs.size(); k++) {
   							
   							if(buyAttrValName==productBuyAttrs.get(k).getLineAttrvalNameCn()||
   									buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
   								buyIndex.add(k);
   							}
   						}
   					} else {
   						//规格属性
   						if(skuAttrs.get(j).getSupplierProductAttr().getSaleAttr()==1){
   							List<SupplierProductAttrval> productSaleAttrs = 
   									skuAttrs.get(j).getSupplierProductAttrvals();
   							for (int  x= 0; x < productSaleAttrs.size(); x++) {

   								if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){

   									saleIndex.add(x);
   								}
   							}
   						}
   					}
   				}
   				skuPic.add(supplierprice);
   				
   		}
   		
   		List<Object> skusPic = new ArrayList<Object>();
   		for (int i = 0; i < buyIndex.size(); i++) {
   			saveforsku = new ArrayList<Object>();
   			saveforsku.add(buyIndex.get(i));
   			saveforsku.add(saleIndex.get(i));
   			saveforsku.add(skuPic.get(i));
   			skusPic.add(saveforsku);
   		}
   		return skusPic;
   	}
   	
   	
    /**
   	 * sku条形码数据回显.
   	 * @param proObjVo 商品对象
   	 * @return SupplierProductObjectDTO
   	 */
   	List<Object> showSkuCode(SupplierProductObjectDTO proObjVo) {
   		
   		List<Object> saveforsku  = null;
   		List<String> barCodeImgs = new ArrayList<String>();
   		List<Integer> buyIndex = new ArrayList<Integer>();
   		List<Integer> saleIndex = new ArrayList<Integer>();
   		List<String> skuBarCode = new ArrayList<String>();
   		
   		
   		Map<Long,Boolean> saleIdMap = this.getSaleIdMap(proObjVo);
   		
   		List<SupplierProductSkuDTO> supplierProSkuVOs = proObjVo.getSupplierProductSkuDTOs();
   		for ( int i = 0 ; i< supplierProSkuVOs.size() ; i++ ){

   			
   			 String supplierSkuCode = supplierProSkuVOs.get(i).getSupplierProductSku().getSkuCode();
   	            String barCodeImgUrl = supplierProSkuVOs.get(i).getSupplierProductSku().getBarCodeImage();
   	            String showBarCode = "";
   	            
   	            if(!"".equals(barCodeImgUrl)){
   	            	showBarCode = "<img imgtype='temp' width='120px' height='30px' class='preview'" 
   	    							+ "src='" + Constants.FILESERVER1+barCodeImgUrl + "'/>"
   	    									+ "<input type='hidden' name='barCodeImg' value='" + Constants.FILESERVER1+barCodeImgUrl+ "'>";
   	            }
   				List<SupplierProductSkuAttrval> productSkuAttrvals = supplierProSkuVOs.get(i).getSupplierProductSkuAttrvals();
   				
   				String buyAttrValName = "";
   				
   				String saleAttrValName = "";
   				
   				for(SupplierProductSkuAttrval prodSkuAttrval :productSkuAttrvals){
   					Long attrId = prodSkuAttrval.getAttrId();
   					Boolean isName = saleIdMap.get(attrId);
   					
   					if(null != isName){
   						if(isName){
   							saleAttrValName = prodSkuAttrval.getAttrvalNameCn();
   						}else if(!isName){
   							buyAttrValName = prodSkuAttrval.getAttrvalNameCn();
   						}
   					}
   				}
   				
   				
   				List<SupplierProductAttrDTO> skuAttrs = proObjVo.getSupplierProductAttrDTOs();
   				for(int j=0;j<skuAttrs.size();j++){
   					//展示属性
   					if(skuAttrs.get(j).getSupplierProductAttr().getBuyAttr()==1){
   						List<SupplierProductAttrval> productBuyAttrs = skuAttrs.get(j).getSupplierProductAttrvals();
   						for (int k = 0; k < productBuyAttrs.size(); k++) {
   							
   							if(buyAttrValName.equals(productBuyAttrs.get(k).getLineAttrvalNameCn())){
   								buyIndex.add(k);
   							}
   						}
   					} else {
   						//规格属性
   						if(skuAttrs.get(j).getSupplierProductAttr().getSaleAttr()==1){
   							List<SupplierProductAttrval> productSaleAttrs = skuAttrs.get(j).getSupplierProductAttrvals();
   							for (int  x= 0; x < productSaleAttrs.size(); x++) {

   								if(saleAttrValName.equals(productSaleAttrs.get(x).getLineAttrvalNameCn())){

   									saleIndex.add(x);
   								}
   							}
   						}
   					}
   				}
   				skuBarCode.add(supplierSkuCode);
   				barCodeImgs.add(showBarCode);
   		}
   		
   		List<Object> skusBarCode = new ArrayList<Object>();
   		for (int i = 0; i < buyIndex.size(); i++) {
   			saveforsku = new ArrayList<Object>();
   			saveforsku.add(buyIndex.get(i));
   			saveforsku.add(saleIndex.get(i));
   			saveforsku.add(skuBarCode.get(i));
   			saveforsku.add(barCodeImgs.get(i));
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
   	private Map<Long,Boolean> getSaleIdMap(SupplierProductObjectDTO proObjVo){
   		Map<Long, Boolean> saleMap = new LinkedHashMap<Long,Boolean>();
   		List<SupplierProductAttrDTO> supplierProductAttrDTOs = proObjVo.getSupplierProductAttrDTOs();
   		for(SupplierProductAttrDTO attrDto : supplierProductAttrDTOs){
   			SupplierProductAttr supplierProductAttr = attrDto.getSupplierProductAttr();
   			if(supplierProductAttr.getSaleAttr().equals((short)1)){
   				saleMap.put(supplierProductAttr.getAttrId(), true);
   			}else if(supplierProductAttr.getBuyAttr().equals((short)1)){
   				saleMap.put(supplierProductAttr.getAttrId(), false);
   			}
   		}
   		
   		return saleMap;
   	}
}
