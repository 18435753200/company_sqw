package com.mall.controller.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.category.po.MoneyUnit;
import com.mall.category.po.TcMeasure;
import com.mall.category.po.TdCatePub;
import com.mall.category.po.TdCatePubAttr;
import com.mall.category.po.TdCatePubAttrval;
import com.mall.controller.base.BaseController;
import com.mall.dealer.product.api.DealerProductTagsService;
import com.mall.dealer.product.dto.DealerPordUpdateTateDto;
import com.mall.dealer.product.dto.DealerProductAttrDTO;
import com.mall.dealer.product.dto.DealerProductObjectDTO;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.dealer.product.dto.DealerProductShowDTO;
import com.mall.dealer.product.po.DealerProductAttach;
import com.mall.dealer.product.po.DealerProductAttrval;
import com.mall.dealer.product.po.DealerProductBuyAttrval;
import com.mall.dealer.product.po.DealerProductWholesaleRange;
import com.mall.dealer.product.po.DictTags;
import com.mall.dealer.product.po.ProductTags;
import com.mall.dealer.product.util.DateUtil;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.supplier.enums.SupplierSouce;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.product.api.SupplierProductService;
import com.mall.supplier.product.dto.SupplierProductAttrDTO;
import com.mall.supplier.product.dto.SupplierProductForOrderDTO;
import com.mall.supplier.product.dto.SupplierProductObjectDTO;
import com.mall.supplier.product.dto.SupplierProductSelectConDTO;
import com.mall.supplier.product.dto.SupplierProductShowDTO;
import com.mall.supplier.product.dto.SupplierProductSkuDTO;
import com.mall.supplier.product.po.SupplierProductAttach;
import com.mall.supplier.product.po.SupplierProductAttrval;
import com.mall.supplier.product.po.SupplierProductBuyAttrval;
import com.mall.supplier.product.po.SupplierProductWholesaleRange;
import com.mall.utils.Constants;
import com.mall.utils.DateTool;
import com.mall.utils.FormatBigDecimal;

import net.sf.json.JSONArray;

/**.
 * 商品类目Controller
 * @author zhouzb
 *
 */
/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/POPproduct")
public class POPProductController extends BaseController{


	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(POPProductController.class);
	@Autowired
	private DealerProductTagsService dealerProductTagsService;
	@Autowired
	private SupplierProductService supplierProductService;
	public final String pop_view_page = "/dealerseller/POPproduct/showProduct";
	public final String pop_view_page_dealer = "/dealerseller/POPproduct/showProductDealer";
	/**.
	 * 加载商品页面
	 * @return ModelAndView
	 */
	@RequestMapping(value="/getPro")
	public String getProductPage(HttpServletRequest request,String isReivew, Integer currentPageId){
		
		if(null != isReivew){
			//request.setAttribute("isReivew",1);
			request.setAttribute("isReivew",isReivew);
			request.setAttribute("currentPageId", currentPageId);
		}
		
		return "/dealerseller/POPproduct/POPproductlist";
		
	}
	
	/**条件加载商品列表
	 * @param cate  String
	 * @param page Integer
	 * @param dealerProductSelectConDTO DealerProductSelectConDTO
	 * @return null1
	 */
	@RequestMapping(value="/getProductByConditions")
	public String getProductByConditions(HttpServletRequest request, Model model, String cate,
			PageBean<DealerProductShowDTO> pageBean,
			DealerProductSelectConDTO dealerProductSelectConDTO,
			String productName, Long subId, Short auditStatus, Integer page,
			String istate,String popshengIsTate, Long productId, String brandId,BigDecimal prodType) {

		// pop商品 审核列表
		if (auditStatus == 2) {
			try {
				PageBean<SupplierProductShowDTO> pageBean2 = new PageBean<SupplierProductShowDTO>();
				SupplierProductSelectConDTO productSelectConVO = new SupplierProductSelectConDTO();
				List<String> list = new ArrayList<String>();
				if (cate != null) {
					list.add(cate);
					productSelectConVO.setCatePubIds(list);
				}
				
				if (null != productId && productId != 0) {
					productSelectConVO.setProductId(productId);
				}

				if (null != dealerProductSelectConDTO.getB2cProductName() && !dealerProductSelectConDTO.getB2cProductName().equals("")) {
					productSelectConVO.setProductName(dealerProductSelectConDTO.getB2cProductName());
				}
				
				if (null != dealerProductSelectConDTO.getProductBarCode() && !dealerProductSelectConDTO.getProductBarCode().equals("")) {
					productSelectConVO.setProductBarCode(dealerProductSelectConDTO.getProductBarCode());
				}
				
				if (null != productName && !productName.equals("")) {
					productSelectConVO.setProductName(productName);
				}

				if (null != brandId && !"".equals(brandId)) {

					productSelectConVO.setBrandId(brandId);
				}
				
				productSelectConVO.setB2cSupply(SupplierSouce.POP.getIndex());
				
				productSelectConVO.setCounterfeittypeid(popshengIsTate);
				productSelectConVO.setProdType(prodType);
				if(dealerProductSelectConDTO.getSuppliername() != null && !"".equals(dealerProductSelectConDTO.getSuppliername())){
					productSelectConVO.setSuppliername(dealerProductSelectConDTO.getSuppliername());
				}

				if (null != page && page != 0) {
					pageBean2.setPage(page);
				} else {
					pageBean2.setPage(1);
				}
				pageBean2.setSortFields("OPERATIONDATE");
				pageBean2.setOrder("DESC");

				PageBean<SupplierProductShowDTO> findProductsByConditions = new PageBean<SupplierProductShowDTO>();

				productSelectConVO.setAuditStatus(auditStatus);
				pageBean2.setPageSize(Constants.PAGESIZE);

				pageBean2.setParameter(productSelectConVO);

				findProductsByConditions = RemoteServiceSingleton.getInstance()
						.getSupplierProductService()
						.findProductsByConditionsbyPOP(pageBean2);

				// 设置图片的地址
				List<SupplierProductShowDTO> result = findProductsByConditions
						.getResult();
				for (int i = 0; i < result.size(); i++) {
					Long productId2 = result.get(i).getProductId();
					List<String> list2=dealerProductTagsService.findTagNameByProId(productId2);
					if (list2!=null&&list2.size()>0) {
						String string = list2.toString();
						result.get(i).setSubsupplierid(string);;
					}
				}
				for (SupplierProductShowDTO supplierProductShowVO : result) {
					String imgURL = supplierProductShowVO.getImgURL();
					if (!imgURL.startsWith("http")
							|| !imgURL.startsWith("Http")) {
						imgURL = Constants.M1 + imgURL;
						supplierProductShowVO.setImgURL(imgURL);
					}
				}
				model.addAttribute("pb", findProductsByConditions);
				return "/dealerseller/POPproduct/modelpage/product_model_pb";
			} catch (Exception e) {
				logger.error(
						"查询商品失败！！！auditStatus:" + auditStatus + e.getMessage(), e);
				return "1";
			}
		} else {
            // 商品列表
			try {
				List<String> cateList = new ArrayList<String>();
				if (cate != null) {
					cateList.add(cate);
					dealerProductSelectConDTO.setCateDispIds(cateList);
				}
				pageBean.setPageSize(Constants.PAGESIZE);
				pageBean.setSortFields("CREATEDDATE");
				pageBean.setOrder("DESC");
				dealerProductSelectConDTO.setB2cSupply(SupplierSouce.POP.getIndex());
				pageBean.setParameter(dealerProductSelectConDTO);
				pageBean = RemoteServiceSingleton.getInstance()
						.getDealerProductService()
						.findProductsByConditionsbyPOP(pageBean);

				List<Long> pids = null;
				List<DealerProductShowDTO> result = pageBean.getResult();
				if (result != null) {
					pids = new ArrayList<Long>();
					FormatBigDecimal formatBigDecimal = new FormatBigDecimal();
					for (int i = 0; i < result.size(); i++) {
						String imgUrl = result.get(i).getImgURL();
						if (!imgUrl.startsWith("http")
								|| !imgUrl.startsWith("Http")) {

							if (result.get(i).getIsB2c()) {
								result.get(i)
										.setB2cImage(Constants.M1 + imgUrl);
							}
							result.get(i).setImgURL(Constants.P1 + imgUrl);
						}
						pids.add(result.get(i).getProductId());
						BigDecimal productPriceMin = result.get(i)
								.getProductPriceMin();
						BigDecimal productPriceMax = result.get(i)
								.getProductPriceMax();

						BigDecimal priceMax = formatBigDecimal
								.priceFormat(productPriceMax);
						BigDecimal priceMin = formatBigDecimal
								.priceFormat(productPriceMin);

						result.get(i).setProductPriceMin(priceMin);
						result.get(i).setProductPriceMax(priceMax);

						if (null != result.get(i).getDescription()) {
							result.get(i).setDescription(
									result.get(i).getDescription()
											.replaceAll("\n", "<br/>"));
						}

						if (dealerProductSelectConDTO.getAuditStatus() == Constants.SHORT6) {
							result.get(i).setCounterfeittypeid(
									"" + Constants.NUM6);
							// result.get(i).getSupply();
						}
						Long productId2 = result.get(i).getProductId();
						List<String> list2=dealerProductTagsService.findTagNameByProId(productId2);
						if (list2!=null&&list2.size()>0) {
							String string = list2.toString();
							result.get(i).setSubdealerid(string);
						}
					}
				}

				if(null != pids && !pids.isEmpty()){
					// 2017-1-7 by lipeng
					// 增加一个查询 审核列表 中数据状态的功能,
					// 用于设置,下架,修改完,之后是待审核状态,不能再上架
					List<com.mall.supplier.product.po.SupplierProduct> supplierProductList = RemoteServiceSingleton
							.getInstance()
							.getSupplierProductService().selectProductAuditStatusByIds(pids);

					for (DealerProductShowDTO dto:result){
						for (com.mall.supplier.product.po.SupplierProduct supplierProduct:supplierProductList){
							if(dto.getProductId().longValue() == supplierProduct.getProductid().longValue()){
								dto.setIsNationalAgency(Short.valueOf(supplierProduct.getCounterfeittypeid()));
								break;
							}
						}
					}
				}

				model.addAttribute("pb2", pageBean);
				return "/dealerseller/POPproduct/modelpage/product_model_pb2";
			} catch (Exception e) {

				LOGGER.error(e.getMessage(), e);

				return "1";

			}
			
		}
		

	}
	
	/**.
	 * 修改商品页 
	 * @param model Model
	 * @param catePubId String
	 * @param productId Long
	 * @param target 1修改操作  2 查看操作
	 * @param bodytype 1 b2b查看修改商品 2 b2c查看修改商品 用于前台区分显示
	 * @return String URL
	 */
	@RequestMapping(value="/toCreateUI")
	public String editProductInfo(Model model,String catePubId,Long productId,String target,String iseditproperty,String bodytype){
		
		String editStat = Constants.SUCCESS;

		//		图文详情
		String bufImgInfo = "";
		Long minWholesaleQty = 0l;
		
		ProductService serviceProduct = new ProductService();
		
		Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList = 
				new LinkedHashMap<TdCatePubAttr, List<TdCatePubAttrval>>();
		DealerProductObjectDTO proObjVo = new DealerProductObjectDTO();
//		类目
		List<TdCatePub> cateNames = new ArrayList<TdCatePub>();
		List<TcMeasure> allMeasure = new ArrayList<TcMeasure>();
		
		JSONArray jsonProductPic = new JSONArray();
		JSONArray jsonSupplierProductPic = new JSONArray();
		JSONArray jsonCollectionProductPic = new JSONArray();
		JSONArray jsonB2CProductPic = new JSONArray();
		JSONArray showbarCodeJson  = new JSONArray();
		JSONArray returnImgUrlAndName = new JSONArray();
		//图文详情
		List<String> qualification = new ArrayList<String>();
		String b2cDescription = "";
		Long productattachid = 0l;
		//基础数据-币种
		List<MoneyUnit> momeyUnits = new ArrayList<MoneyUnit>();
		//B2C价格 及 可销售数量收集
		List<Object> b2CSkusPic = new ArrayList<Object>();
		
		Map<String, Object> productPic = new LinkedHashMap<String, Object>();
		Map<String, Object> collectionProductPic = new LinkedHashMap<String, Object>();
		Map<String, Object> supplierProductPic = new LinkedHashMap<String, Object>();
		
		List<Long> supplierCountList = new ArrayList<Long>();
		List<Long> dealerCountList = new ArrayList<Long>();
		List<Long> collectioncountList = new ArrayList<Long>();
		List<BigDecimal> supplierPicList = new ArrayList<BigDecimal>();
		List<BigDecimal> dealerPicList = new ArrayList<BigDecimal>();
		List<BigDecimal> conllectionPicList = new ArrayList<BigDecimal>();
		
		try {

			proObjVo = RemoteServiceSingleton.getInstance()
					.getDealerProductService().findProductObjectById(productId, "");

		} catch (Exception e) {

			LOGGER.error("获取商品信息失败  catePubId="+catePubId);
			LOGGER.error("productId="+productId);
			LOGGER.error(e.getMessage(),e);

		}

		try {
			
			editStat = serviceProduct.checkProductIsEdit(target, proObjVo);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		String categoryId = proObjVo.getDealerProduct().getCatePubId();

		try {
			categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryAttrAndValList(categoryId);
		} catch (Exception e) {
			LOGGER.error("获取类目失败  catePubId="+catePubId);
			LOGGER.error("productId="+productId);
			LOGGER.error(e.getMessage(),e);
		}

		try {
			allMeasure = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().findAllMeasure();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		List<DealerProductWholesaleRange> productWholesaleRanges =  proObjVo.getDealerProductWholesaleRanges();

		/**
		 * wofe显示供应价信息
		 */

		try {

			SupplierProductForOrderDTO skuPriceInfo = RemoteServiceSingleton.getInstance().
					getSupplierProductOrderService().findProSkuPriceInfoByPId(productId, "");

			List<SupplierProductWholesaleRange> supplierWholesaleRanges = 
					skuPriceInfo.getsupplierProductWholesaleRanges();

			List<SupplierProductSkuDTO> supplierProductSkuDTOs = skuPriceInfo.getsupplierProductSkuDTOs();

			minWholesaleQty = skuPriceInfo.getSupplierProductSaleSetting().getMinWholesaleQty();

			if (null != supplierWholesaleRanges && supplierWholesaleRanges.size()>0){

				for (SupplierProductWholesaleRange productWholesaleRange : supplierWholesaleRanges) {

					Long startQty = productWholesaleRange.getStartQty();
					supplierCountList.add(startQty);
					BigDecimal discountPic = productWholesaleRange.getDiscount();
					supplierPicList.add(discountPic);

				}

				supplierProductPic.put("type", 0);
				supplierProductPic.put("start", supplierCountList ); 
				supplierProductPic.put("pic", supplierPicList );

			} else {
				//按照sku的价格回显
				List<Object> supplierSkusPic = serviceProduct.showSupplierSkuPic(proObjVo, supplierProductSkuDTOs);
				supplierProductPic.put("type", 1);
				supplierProductPic.put("start", 0); 
				supplierProductPic.put("pic", supplierSkusPic);
			}
		} catch (Exception e) {
			LOGGER.error("wofe显示供应价信息数据异常"+e.getMessage(),e);
		}


		/**
		 * 按照批次回显价格
		 */
		try {

			if (productWholesaleRanges!=null&&productWholesaleRanges.size()>0){

				for (DealerProductWholesaleRange productWholesaleRange : productWholesaleRanges) {

					short stockType = productWholesaleRange.getStockType();

					if(com.mall.dealer.product.util.Constants.PROD_DEALER_RANAG_TYPE==stockType){

						Long startQty = productWholesaleRange.getStartQty();
						dealerCountList.add(startQty);
						BigDecimal discountPic = productWholesaleRange.getDiscount();
						dealerPicList.add(discountPic);

					} else if(com.mall.dealer.product.util.Constants.PROD_SUPPLIER_RANAG_TYPE==stockType){

						Long startQty = productWholesaleRange.getStartQty();
						collectioncountList.add(startQty);
						BigDecimal discountPic = productWholesaleRange.getDiscount();
						conllectionPicList.add(discountPic);

					}

				}
				productPic.put("type", 0);
				productPic.put("start", dealerCountList); 
				productPic.put("pic", dealerPicList );

				collectionProductPic.put("type", 0);
				collectionProductPic.put("start", collectioncountList); 
				collectionProductPic.put("pic", conllectionPicList );

			}else{
				//按照sku的价格回显
				List<Object> skusPic = serviceProduct.showSkuPic(proObjVo);
				productPic.put("type", 1);
				productPic.put("start", 0); 
				productPic.put("pic", skusPic);

				List<Object> collectionSkusPic = serviceProduct.showSkuPicForCollection(proObjVo);
				collectionProductPic.put("type", 1);
				collectionProductPic.put("start", 0); 
				collectionProductPic.put("pic", collectionSkusPic);

			}
		} catch (Exception e) {
			LOGGER.error("价格数据异常"+e.getMessage(),e);
		}

		//B2C价格 及 可销售数量收集
		b2CSkusPic = serviceProduct.showB2CSkuPic(proObjVo);

		//格式化json数据
		try {
			jsonProductPic = JSONArray.fromObject(productPic);
			if(proObjVo != null && proObjVo.getB2cProductDetail() != null){
				if(proObjVo.getB2cProductDetail().getB2cSupply() != null && proObjVo.getB2cProductDetail().getB2cSupply() != (short)31)
					jsonSupplierProductPic = JSONArray.fromObject(supplierProductPic);
			}
			
			jsonCollectionProductPic = JSONArray.fromObject(collectionProductPic);
			jsonB2CProductPic = JSONArray.fromObject(b2CSkusPic);
		} catch (Exception e) {
			LOGGER.error("格式化json数据异常"+e.getMessage(),e);
		}

		//条形码回显
		try {
			List<Object> showbarCode = serviceProduct.showbarCode(proObjVo);
			showbarCodeJson = JSONArray.fromObject(showbarCode);
		} catch (Exception e) {
			LOGGER.error("条形码数据异常"+e.getMessage(),e);
		}

		//属性和属性值回显
		List<DealerProductAttrDTO> pageAttrs = new ArrayList<DealerProductAttrDTO>();
		try {
			pageAttrs = serviceProduct.showAttrAndAttrVal(categoryAttrAndValList, proObjVo);
		} catch (Exception e) {
			LOGGER.error("属性和属性值回显"+e.getMessage(),e);
		}
		proObjVo.setDealerProductAttrDTOs(pageAttrs);

		//图片回显
		List<LinkedHashMap<String,Object>> imgUrl = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			imgUrl = serviceProduct.imgShow(pageAttrs);
		} catch (Exception e) {
			LOGGER.error("图片回显"+e.getMessage(),e);
		}
		returnImgUrlAndName = JSONArray.fromObject(imgUrl);


		//类目
		try {
			cateNames = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		try {

			Date deliverDate = proObjVo.getDealerProductDetail().getDeliverDate();
			if(DateTool.getDateYear(deliverDate)==Constants.YEAR){
				proObjVo.getDealerProductDetail().setDeliverDate(null);
				proObjVo.getDealerProductDetail().setReceiveDate(null);
			}

			String fileurl = "";

			// 图文详情和资质图片
			List<DealerProductAttach> productAttachs = proObjVo.getDealerProductAttachs();

			for (DealerProductAttach supplierProductAttach : productAttachs) {

				if(supplierProductAttach.getType() == Constants.PRODUCT_APTITUDE){
					qualification.add(Constants.P1+supplierProductAttach.getFileurl());
				} else if(Constants.PRODUCT_DESCRIPTIONS == supplierProductAttach.getType()){

					fileurl = supplierProductAttach.getFileurl();
					productattachid = supplierProductAttach.getProductattachid();
					LOGGER.info("图文详情路径:"+fileurl);
					if(target.equals(Constants.SUCCESS)){
						bufImgInfo = Constants.H0 + supplierProductAttach.getFileurl();
					}else{
						if(fileurl!=null&&!fileurl.equals("")){

							bufImgInfo = UploadFileUtil.DownloadFile(fileurl);
							LOGGER.info("图文详情:"+fileurl);

						}
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("获取图文详情数据异常"+e.getMessage(),e);
		}
		
		
		b2cDescription = proObjVo.getB2cProductDetail().getB2cDescription();
		
		if (null != b2cDescription && !b2cDescription.equals("")){
			
			if(target.equals(Constants.SUCCESS)){
				
				b2cDescription = Constants.H0 + b2cDescription;
				
			}else{
				
				if(b2cDescription!=null&&!b2cDescription.equals("")){
	
					b2cDescription = UploadFileUtil.DownloadFile(b2cDescription);
					LOGGER.info("B2C图文详情:"+b2cDescription);
	
				}
			}
		}

		try {

			momeyUnits = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getMomeyUnits();

		} catch (Exception e) {
			LOGGER.error("获取币种基础数据异常"+e.getMessage(),e);
		}

		/*获取 供应商 货源 类型*/
		try {
			
			SupplierProductObjectDTO object = RemoteServiceSingleton.getInstance().getSupplierProductService().findProductObjectById(productId, "");
			
			Supplier supplier1 = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(object.getSupplierid());
			
			if(null==proObjVo.getB2cProductDetail().getB2cSupply()){
				model.addAttribute("souceType", supplier1.getSupplyType());
			}
			
			
		} catch (Exception e) {
			LOGGER.info("供应商 类型获取失败"+e);
		}

		JSONArray qualificationUrl = JSONArray.fromObject(qualification);
		
		model.addAttribute("supplierMinQty", minWholesaleQty);
		model.addAttribute("qualificationUrl", qualificationUrl);
		model.addAttribute("b2cDescription", b2cDescription);
		model.addAttribute("productattachid", productattachid);
		model.addAttribute("cateNames", cateNames);
		model.addAttribute("measure", allMeasure);
		model.addAttribute("attch", bufImgInfo);
		model.addAttribute("proObj", proObjVo);
		model.addAttribute("jsonImg",returnImgUrlAndName);
		model.addAttribute("jsonProductPic", jsonProductPic);
		model.addAttribute("jsonCollectionProductPic", jsonCollectionProductPic);
		model.addAttribute("jsonB2CProductPic", jsonB2CProductPic);
		model.addAttribute("jsonSupplierProductPic", jsonSupplierProductPic);
		model.addAttribute("supplierSkuPriceAndCount", jsonSupplierProductPic);
		model.addAttribute("showbarCodeJson", showbarCodeJson);
		model.addAttribute("momeyUnits", momeyUnits);
		model.addAttribute("editStat", editStat);
		model.addAttribute("target", target);
		model.addAttribute("bodytype", bodytype);
		model.addAttribute("iseditproperty", iseditproperty);


		return "/dealerseller/product/editproduct";

	}

	/**.
	 * 下架操作
	 * @param request HttpServletRequest
	 * @param pid String
	 * @return String
	 */
	@RequestMapping(value="/updateProductTateByIds")
	@ResponseBody
	public String updateProductTateByIds(HttpServletRequest request , String pid ,String stopReason,Short stopType){

		String downprostat = Constants.SUCCESS;
		
		DealerPordUpdateTateDto dealerPordUpdateTateDto = new DealerPordUpdateTateDto();
		
		if(pid.length()>0){
			
			LOGGER.info("下架操作.商品Id:"+pid);
			LOGGER.info("用户ID:"+getCurrentUser().getId());

			try{
				
				String[] cates = pid.split(",");
				Long [] catels = new Long[cates.length];
				
				for(int i = 0 ; i < cates.length ; i++ ){
					
					catels[i] = Long.parseLong(cates[i]);
					
				}
				
				User object =  getCurrentUser();
		
				Long platforId = object.getSequenceId();
				
				dealerPordUpdateTateDto.setProductIds(catels);
				dealerPordUpdateTateDto.setOperatorId(platforId);
				dealerPordUpdateTateDto.setStatus(Constants.SHORT0);
				dealerPordUpdateTateDto.setStopReason(stopReason);
				dealerPordUpdateTateDto.setType(stopType);
				
				RemoteServiceSingleton.getInstance().getDealerProductService().updateProductTateByIds(dealerPordUpdateTateDto);		
				
			}catch (Exception e) {
				
				LOGGER.error(e.getMessage(),e);
				
				downprostat = Constants.ERROR;
				
			}
			
		}else{
			
			downprostat = Constants.ERROR;
			
		}
		
		return downprostat;
		
	}
	
	/**.
	 * 上架操作
	 * AJAX
	 * @param request HttpServletRequest
	 * @param pid String
	 * @return String
	 */
	@RequestMapping(value="/upProductByIds")
	@ResponseBody
	public String hitsShelvesProductByIds(HttpServletRequest request,String pid,Short upType){

		String updata = Constants.SUCCESS;
		
		if(pid.length()>0){

			LOGGER.info("上架操作.商品Id:"+pid);

			try{

				DealerPordUpdateTateDto dealerPordUpdateTateDto = new DealerPordUpdateTateDto();

				String[] cates = pid.split(",");
				Long [] catels = new Long[cates.length];
				
				for(int i = 0 ; i < cates.length ; i++ ){
					
					catels[i] = Long.parseLong(cates[i]);
					
				}

				User object =  getCurrentUser();

				Long platforId = object.getSequenceId();

				dealerPordUpdateTateDto.setProductIds(catels);
				dealerPordUpdateTateDto.setOperatorId(platforId);
				dealerPordUpdateTateDto.setStatus(Constants.SHORT1);
				dealerPordUpdateTateDto.setType(upType);
				
				RemoteServiceSingleton.getInstance().
									getDealerProductService().updateProductTateByIds(dealerPordUpdateTateDto);		
				
			}catch (Exception e) {
				
				LOGGER.error(e.getMessage(),e);
				
				updata = Constants.ERROR;
				
			}
			
		}else{
			
			updata = Constants.ERROR;
			
		}
		
		return updata;
		
	}


	/**
	 * 图片回显.
	 *
	 * @param pageAttrs
	 *            页面显示属性属性值
	 * @return 图片list
	 */
	public List<LinkedHashMap<String, Object>> imgShow(
			List<SupplierProductAttrDTO> pageAttrs) {

		List<LinkedHashMap<String, Object>> imgUrl = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> imgList = null;
		List<String> urlList = null;

		for (int i = 0; i < pageAttrs.size(); i++) {

			List<SupplierProductAttrval> getSupplierProductAttrvals = pageAttrs
					.get(i).getSupplierProductAttrvals();
			if (null != getSupplierProductAttrvals) {
				for (int j = 0; j < getSupplierProductAttrvals.size(); j++) {
					String lineAttrvalNameCn = "";
					if ("/en".equals("/zh")) {
						lineAttrvalNameCn = getSupplierProductAttrvals.get(j)
								.getLineAttrvalName();
					} else {
						lineAttrvalNameCn = getSupplierProductAttrvals.get(j)
								.getLineAttrvalNameCn();
					}
					Map<Integer, List<SupplierProductBuyAttrval>> map2 = pageAttrs
							.get(i).getMap();
					List<SupplierProductBuyAttrval> list = new ArrayList<SupplierProductBuyAttrval>();

					if (map2 != null) {
						list = map2.get(j);

						if (list != null && list.size() > 0) {
							urlList = new ArrayList<String>();
							imgList = new LinkedHashMap<String, Object>();

							for (SupplierProductBuyAttrval supplierProductBuyAttrval : list) {
								String imgurl = Constants.IMAGES_VIEW1
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


	/**.
	 * 商品审核
	 * @param pid String
	 * @return String
	 */
	@RequestMapping(value="/review",method=RequestMethod.POST)
	@ResponseBody
	public String review(String pid ,String rejectReason,int operationType){
		// 返回状态值 1操作成功  0操作错误  2未填拒绝原因
		String downprostat = Constants.ERROR;
		try {
			if(StringUtils.isNotBlank(pid)){
				// 如果是审核失败，必须输入原因
				if(operationType == 1 || operationType == 2){
					if(operationType == 2){
						if(StringUtils.isBlank(rejectReason)){
							return "2";
						}
					}
					String platforId="5000";
					//登陆用户信息
					User object =  getCurrentUser();	
					if(object!=null&&object.getSequenceId()!=null){
						platforId = object.getSequenceId()+"";
					}
					RemoteServiceSingleton.getInstance().getDealerProductAuditService().auditSProductByProductIdForPOP(Long.parseLong(pid), String.valueOf(operationType),platforId, rejectReason, "");
					downprostat = Constants.SUCCESS;

				}
			}
		} catch (Exception e) {
			LOGGER.error("POP商品审核失败==============="+e.getMessage(),e);
		}
		return downprostat;
	}
	/**.
	 * @param pid String
	 * @return String
	 */
	@RequestMapping(value="/reviewsj",method=RequestMethod.GET)
	@ResponseBody
	public String reviewsj(Long pid ){
		SupplierProductObjectDTO findProductObjectById = supplierProductService.findProductObjectById(pid, null);
		Long supplierid = findProductObjectById.getSupplierid();
		return supplierid.toString();
	}



	/**
	 * 审核商品信息界面.
	 *
	 * @param model
	 *            model
	 * @param productId
	 *            productid
	 * @param prodType
	 * 			  prodType
	 * @return String
	 */
	@RequestMapping("/initAudit")
	public String initAudit(Model model, Long productId, Integer currentPageId,Integer prodType) {
		SupplierProductObjectDTO proObjVo = new SupplierProductObjectDTO();

//		Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList = new LinkedHashMap<TdCatePubAttr, List<TdCatePubAttrval>>();

		List<TdCatePub> cateNames = new ArrayList<TdCatePub>();

		try {
			proObjVo = RemoteServiceSingleton.getInstance().getSupplierProductService().findProductObjectById(productId, "");
			BigDecimal cashHqj = proObjVo.getSupplierProductSkuDTOs().get(0).getSupplierProductPriceMap().getCashHqj();
			model.addAttribute("cash", cashHqj);
			String categoryId = proObjVo.getSupplierProduct().getCatePubId();

//			categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
//					.getCategoryAttrAndValList(categoryId);

			cateNames = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId);

		} catch (Exception e) {
			LOGGER.error("获取商品信息失败：" + e.getMessage(),e);
		}

		SupplierProductHandler serviceController = new SupplierProductHandler();

		// //属性和属性值回显
		List<SupplierProductAttrDTO> pageAttrs = proObjVo.getSupplierProductAttrDTOs();
//		List<SupplierProductAttrDTO> pageAttrs = serviceController.showAttrAndAttrVal(categoryAttrAndValList, productAttrVOs);

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
			List<Object> skusPic = serviceController.showSkuPic(proObjVo);
			productPic.put("type", 1);
			productPic.put("start", 0);
			productPic.put("pic", skusPic);
		}
		JSONArray jsonProductPic = JSONArray.fromObject(productPic);

		proObjVo.setSupplierProductAttrDTOs(pageAttrs);


		//将默认日期设置为null
		Date receiveDate = proObjVo.getSupplierProductDetail().getReceiveDate();
		Date deliverDate = proObjVo.getSupplierProductDetail().getDeliverDate();

		if(DateUtil.getDateYear(receiveDate)==1999){
			proObjVo.getSupplierProductDetail().setReceiveDate(null);
		}
		if(DateUtil.getDateYear(deliverDate)==1999){
			proObjVo.getSupplierProductDetail().setDeliverDate(null);
		}


		// 图文详情和资质图片
		String fileurl = "";
		List<SupplierProductAttach> productAttachs = proObjVo.getSupplierProductAttachs();
		String attch = "";
		List<String> qualification = new ArrayList<String>();
		for (SupplierProductAttach supplierProductAttach : productAttachs) {
			if(Constants.PRODUCT_DESCRIPTIONS == supplierProductAttach.getType()){
				fileurl = Constants.IMAGES_VIEW2 + supplierProductAttach.getFileurl();
			}

			if(supplierProductAttach.getType() == Constants.PRODUCT_QUALIFICATION){
				qualification.add(Constants.M1+supplierProductAttach.getFileurl());
			}
		}

		JSONArray qualificationUrl = JSONArray.fromObject(qualification);


		JSONArray skusCode = JSONArray.fromObject(serviceController.showSkuCode(proObjVo));

//		Set<TdCatePubAttr> keySet = categoryAttrAndValList.keySet();
//		List<String> buyAttrName = new ArrayList<String>();
//		List<String> saleAttrName = new ArrayList<String>();
//
//		for (TdCatePubAttr tdCatePubAttr : keySet) {
//			if(tdCatePubAttr.getBuyAttr()==1){
//				String buyAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String buyAttrNameEn = tdCatePubAttr.getLineAttrName();
//				buyAttrName.add(0, buyAttrNameCn);
//				buyAttrName.add(1, buyAttrNameEn);
//			}
//			if(tdCatePubAttr.getSaleAttr()==1){
//				String saleAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String saleAttrNameEn = tdCatePubAttr.getLineAttrName();
//				saleAttrName.add(0, saleAttrNameCn);
//				saleAttrName.add(1, saleAttrNameEn);
//			}
//		}
		List<SupplierProductAttrDTO> dtOs = new ArrayList<SupplierProductAttrDTO>();
		for (SupplierProductAttrDTO supplierProductAttrDTO : proObjVo.getSupplierProductAttrDTOs()) {
			if(supplierProductAttrDTO.getSupplierProductAttr().getBuyAttr()!=1 && supplierProductAttrDTO.getSupplierProductAttr().getSaleAttr()!=1){
				dtOs.add(supplierProductAttrDTO);
			}
		}
		for (int x = proObjVo.getSupplierProductAttrDTOs().size(); x > 0; x--) {
			if (proObjVo.getSupplierProductAttrDTOs().get(x-1).getSupplierProductAttr().getBuyAttr()==(short) 0 &&
					proObjVo.getSupplierProductAttrDTOs().get(x-1).getSupplierProductAttr().getSaleAttr()==(short) 0) {
				proObjVo.getSupplierProductAttrDTOs().remove(x-1);
			}
		}

		// pop商家默认2，贸易商
		model.addAttribute("supplierType", 2);
		model.addAttribute("fileurl", fileurl);
//		model.addAttribute("buyAttrName", buyAttrName);
//		model.addAttribute("saleAttrName", saleAttrName);
		model.addAttribute("qualificationUrl", qualificationUrl);
		model.addAttribute("skusCode", skusCode);
		model.addAttribute("cateNames", cateNames);
		model.addAttribute("attch", attch);
		model.addAttribute("proObj", proObjVo);
		model.addAttribute("jsonImg", returnImgUrlAndName);
		model.addAttribute("skuPriceAndCount", jsonProductPic);
		model.addAttribute("revewFlag", 1);
		model.addAttribute("simpleAttrs", dtOs);
		model.addAttribute("currentPageId", currentPageId);
		model.addAttribute("pdtp",prodType);
		return pop_view_page;
	}



	/**
	 * pop商品查看.
	 *
	 * @param model
	 *            model
	 * @param productId
	 *            productid
	 * @return String
	 */
	@RequestMapping("/initPOPShow")
	public String initPOPShow(Model model, Long productId,@RequestParam(required = false) Integer currentPageId) {
		SupplierProductObjectDTO proObjVo = new SupplierProductObjectDTO();

		//Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList = new LinkedHashMap<TdCatePubAttr, List<TdCatePubAttrval>>();

		List<TdCatePub> cateNames = new ArrayList<TdCatePub>();

		try {
			proObjVo = RemoteServiceSingleton.getInstance().getSupplierProductService().findProductObjectById(productId, "");
			BigDecimal cashHqj = proObjVo.getSupplierProductSkuDTOs().get(0).getSupplierProductPriceMap().getCashHqj();
			model.addAttribute("cash", cashHqj);
			String categoryId = proObjVo.getSupplierProduct().getCatePubId();

//			categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
//					.getCategoryAttrAndValList(categoryId);

			cateNames = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId);

		} catch (Exception e) {
			LOGGER.error("获取商品信息失败：" + e.getMessage(),e);
		}

		SupplierProductHandler serviceController = new SupplierProductHandler();

		// //属性和属性值回显
		List<SupplierProductAttrDTO> pageAttrs = proObjVo.getSupplierProductAttrDTOs();
		//List<SupplierProductAttrDTO> pageAttrs = serviceController.showAttrAndAttrVal(categoryAttrAndValList, productAttrVOs);

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
			List<Object> skusPic = serviceController.showSkuPic(proObjVo);
			productPic.put("type", 1);
			productPic.put("start", 0);
			productPic.put("pic", skusPic);
		}
		JSONArray jsonProductPic = JSONArray.fromObject(productPic);

		proObjVo.setSupplierProductAttrDTOs(pageAttrs);


		//将默认日期设置为null
		Date receiveDate = proObjVo.getSupplierProductDetail().getReceiveDate();
		Date deliverDate = proObjVo.getSupplierProductDetail().getDeliverDate();

		if(DateUtil.getDateYear(receiveDate)==1999){
			proObjVo.getSupplierProductDetail().setReceiveDate(null);
		}
		if(DateUtil.getDateYear(deliverDate)==1999){
			proObjVo.getSupplierProductDetail().setDeliverDate(null);
		}


		// 图文详情和资质图片
		String fileurl = "";
		List<SupplierProductAttach> productAttachs = proObjVo.getSupplierProductAttachs();
		String attch = "";
		List<String> qualification = new ArrayList<String>();
		for (SupplierProductAttach supplierProductAttach : productAttachs) {
			if(Constants.PRODUCT_DESCRIPTIONS == supplierProductAttach.getType()){
				fileurl = Constants.IMAGES_VIEW2 + supplierProductAttach.getFileurl();
			}

			if(supplierProductAttach.getType() == Constants.PRODUCT_QUALIFICATION){
				qualification.add(Constants.M1+supplierProductAttach.getFileurl());
			}
		}

		//JSONArray qualificationUrl = JSONArray.fromObject(qualification);


		JSONArray skusCode = JSONArray.fromObject(serviceController.showSkuCode(proObjVo));

//		Set<TdCatePubAttr> keySet = categoryAttrAndValList.keySet();
//		List<String> buyAttrName = new ArrayList<String>();
//		List<String> saleAttrName = new ArrayList<String>();
//
//		for (TdCatePubAttr tdCatePubAttr : keySet) {
//			if(tdCatePubAttr.getBuyAttr()==1){
//				String buyAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String buyAttrNameEn = tdCatePubAttr.getLineAttrName();
//				buyAttrName.add(0, buyAttrNameCn);
//				buyAttrName.add(1, buyAttrNameEn);
//			}
//			if(tdCatePubAttr.getSaleAttr()==1){
//				String saleAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String saleAttrNameEn = tdCatePubAttr.getLineAttrName();
//				saleAttrName.add(0, saleAttrNameCn);
//				saleAttrName.add(1, saleAttrNameEn);
//			}
//		}
		List<SupplierProductAttrDTO> dtOs = new ArrayList<SupplierProductAttrDTO>();
		for (SupplierProductAttrDTO supplierProductAttrDTO : proObjVo.getSupplierProductAttrDTOs()) {
			if(supplierProductAttrDTO.getSupplierProductAttr().getBuyAttr()!=1 && supplierProductAttrDTO.getSupplierProductAttr().getSaleAttr()!=1){
				dtOs.add(supplierProductAttrDTO);
			}
		}
		for (int x = proObjVo.getSupplierProductAttrDTOs().size(); x > 0; x--) {
			if (proObjVo.getSupplierProductAttrDTOs().get(x-1).getSupplierProductAttr().getBuyAttr()==(short) 0 &&
					proObjVo.getSupplierProductAttrDTOs().get(x-1).getSupplierProductAttr().getSaleAttr()==(short) 0) {
				proObjVo.getSupplierProductAttrDTOs().remove(x-1);
			}
		}
		// pop商家默认2，贸易商
		model.addAttribute("supplierType", 2);
		model.addAttribute("fileurl", fileurl);
//		model.addAttribute("buyAttrName", buyAttrName);
//		model.addAttribute("saleAttrName", saleAttrName);
		model.addAttribute("qualificationUrl", qualification);
		model.addAttribute("skusCode", skusCode);
		model.addAttribute("cateNames", cateNames);
		model.addAttribute("attch", attch);
		model.addAttribute("proObj", proObjVo);
		model.addAttribute("jsonImg", returnImgUrlAndName);
		model.addAttribute("skuPriceAndCount", jsonProductPic);
		model.addAttribute("simpleAttrs", dtOs);
		model.addAttribute("currentPageId",currentPageId);
		return pop_view_page;
	}
	/**
	 * pop商品查看.
	 * DealerProduct
	 * @param model
	 *            model
	 * @param productId
	 *            productid
	 * @return String
	 */
	@RequestMapping("/initPOPShowDealerProduct")
	public String initPOPShowDealerProduct(Model model, Long productId,@RequestParam(required = false) Integer currentPageId) {
		//TODO
		DealerProductObjectDTO proObjVo = new DealerProductObjectDTO();

//		Map<TdCatePubAttr, List<TdCatePubAttrval>> categoryAttrAndValList = new LinkedHashMap<TdCatePubAttr, List<TdCatePubAttrval>>();

		List<TdCatePub> cateNames = new ArrayList<TdCatePub>();

		try {
			proObjVo = RemoteServiceSingleton.getInstance().getDealerProductService().findProductObjectById(productId, "");
			String categoryId = proObjVo.getDealerProduct().getCatePubId();
			BigDecimal cashHqj = proObjVo.getDealerProductSkuDTOs().get(0).getDealerProductPriceMap().getCashHqj();
			model.addAttribute("cash", cashHqj);
//			categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
//					.getCategoryAttrAndValList(categoryId);

			cateNames = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId);

		} catch (Exception e) {
			LOGGER.error("获取商品信息失败：" + e.getMessage(),e);
		}

		SupplierProductHandler serviceController = new SupplierProductHandler();

		// //属性和属性值回显
//		List<DealerProductAttrDTO> productAttrVOs = proObjVo.getDealerProductAttrDTOs();
		List<DealerProductAttrDTO> pageAttrs = proObjVo.getDealerProductAttrDTOs();
		// 图片回显
		List<LinkedHashMap<String, Object>> imgUrl = imgShowDealer(pageAttrs);

		JSONArray returnImgUrlAndName = JSONArray.fromObject(imgUrl);

		// 价格或者sku回显
		List<DealerProductWholesaleRange> dealerProductWholesaleRanges = proObjVo
				.getDealerProductWholesaleRanges();
		Map<String, Object> productPic = new LinkedHashMap<String, Object>();
		List<Long> countList = new ArrayList<Long>();
		List<BigDecimal> picList = new ArrayList<BigDecimal>();

		if (dealerProductWholesaleRanges != null && dealerProductWholesaleRanges.size() > 0) {

			for (DealerProductWholesaleRange supplierProductWholesaleRange : dealerProductWholesaleRanges) {
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
			List<Object> skusPic = serviceController.showSkuPicDealer(proObjVo);
			productPic.put("type", 1);
			productPic.put("start", 0);
			productPic.put("pic", skusPic);
		}
		JSONArray jsonProductPic = JSONArray.fromObject(productPic);

		proObjVo.setDealerProductAttrDTOs(pageAttrs);


		//将默认日期设置为null
		Date receiveDate = proObjVo.getDealerProductDetail().getReceiveDate();
		Date deliverDate = proObjVo.getDealerProductDetail().getDeliverDate();

		if(DateUtil.getDateYear(receiveDate)==1999){
			proObjVo.getDealerProductDetail().setReceiveDate(null);
		}
		if(DateUtil.getDateYear(deliverDate)==1999){
			proObjVo.getDealerProductDetail().setDeliverDate(null);
		}


		// 图文详情和资质图片
		String fileurl = "";
		List<DealerProductAttach> productAttachs = proObjVo.getDealerProductAttachs();
		String attch = "";
		List<String> qualification = new ArrayList<String>();
		for (DealerProductAttach supplierProductAttach : productAttachs) {
			/*if(Constants.PRODUCT_DESCRIPTIONS == supplierProductAttach.getType()){
				fileurl = Constants.IMAGES_VIEW2 + supplierProductAttach.getFileurl();
			}*/



			if(supplierProductAttach.getType() == Constants.PRODUCT_QUALIFICATION){
				qualification.add(Constants.M1+supplierProductAttach.getFileurl());
			}
		}

		// pop取b2c的图文详情
		fileurl = Constants.IMAGES_VIEW2 + proObjVo.getB2cProductDetail().getB2cDescription();

//		JSONArray qualificationUrl = JSONArray.fromObject(qualification);


		JSONArray skusCode = JSONArray.fromObject(serviceController.showSkuCodeDealer(proObjVo));

//		Set<TdCatePubAttr> keySet = categoryAttrAndValList.keySet();
//		List<String> buyAttrName = new ArrayList<String>();
//		List<String> saleAttrName = new ArrayList<String>();
//
//		for (TdCatePubAttr tdCatePubAttr : keySet) {
//			if(tdCatePubAttr.getBuyAttr()==1){
//				String buyAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String buyAttrNameEn = tdCatePubAttr.getLineAttrName();
//				buyAttrName.add(0, buyAttrNameCn);
//				buyAttrName.add(1, buyAttrNameEn);
//			}
//			if(tdCatePubAttr.getSaleAttr()==1){
//				String saleAttrNameCn = tdCatePubAttr.getLineAttrNameCn();
//				String saleAttrNameEn = tdCatePubAttr.getLineAttrName();
//				saleAttrName.add(0, saleAttrNameCn);
//				saleAttrName.add(1, saleAttrNameEn);
//			}
//		}
		List<DealerProductAttrDTO> dtOs = new ArrayList<DealerProductAttrDTO>();
		for (DealerProductAttrDTO dealerProductAttrDTO : proObjVo.getDealerProductAttrDTOs()) {
			if(dealerProductAttrDTO.getDealerProductAttr().getBuyAttr()!=1 && dealerProductAttrDTO.getDealerProductAttr().getSaleAttr()!=1){
				dtOs.add(dealerProductAttrDTO);
			}
		}
		for (int x = proObjVo.getDealerProductAttrDTOs().size(); x > 0; x--) {
			if (proObjVo.getDealerProductAttrDTOs().get(x-1).getDealerProductAttr().getBuyAttr()==(short) 0 &&
					proObjVo.getDealerProductAttrDTOs().get(x-1).getDealerProductAttr().getSaleAttr()==(short) 0) {
				proObjVo.getDealerProductAttrDTOs().remove(x-1);
			}
		}
		// pop商家默认2，贸易商
		model.addAttribute("supplierType", 2);
		model.addAttribute("fileurl", fileurl);
//		model.addAttribute("buyAttrName", buyAttrName);
//		model.addAttribute("saleAttrName", saleAttrName);
		model.addAttribute("qualificationUrl", qualification);
		model.addAttribute("skusCode", skusCode);
		model.addAttribute("cateNames", cateNames);
		model.addAttribute("attch", attch);
		model.addAttribute("proObj", proObjVo);
		model.addAttribute("jsonImg", returnImgUrlAndName);
		model.addAttribute("skuPriceAndCount", jsonProductPic);
		model.addAttribute("simpleAttrs", dtOs);
		model.addAttribute("currentPageId",currentPageId);
		return pop_view_page_dealer;
	}
	/**.
	 * 拼装 图片Json 前台使用
	 * @param pageAttrs List<DealerProductAttrDTO>
	 * @author zhouzb
	 * @return String List<LinkedHashMap<String,Object>>
	 */
	public List<LinkedHashMap<String,Object>> imgShowDealer(List<DealerProductAttrDTO> pageAttrs) {
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
	 * 违规下架操作
	 * @param request HttpServletRequest
	 * @param pid String
	 * @return String
	 */
	@RequestMapping(value="/productUnderByIds")
	@ResponseBody
	public String productUnderByIds(HttpServletRequest request , String pid ,String stopReason,Short stopType){
		String downprostat = Constants.SUCCESS;
		if(pid.length()>0){
			LOGGER.info("违规下架操作.商品Id:"+pid);
			LOGGER.info("用户ID:"+getCurrentUser().getId());
			try{
				//登陆用户信息
				User object =  getCurrentUser();
				String platforId="5000";
				if(object!=null&&object.getSequenceId()!=null){
					platforId = object.getSequenceId()+"";
				}
				RemoteServiceSingleton.getInstance().getDealerProductAuditService().auditSProductByProductIdForPOPUnder(Long.parseLong(pid), String.valueOf(5),platforId, stopReason, "");	
			}catch (Exception e) {				
				LOGGER.error(e.getMessage(),e);				
				downprostat = Constants.ERROR;				
			}			
		}else{	
			downprostat = Constants.ERROR;	
		}
		return downprostat;	
	}
	/**
	 * 查询标签列表
	 */
	@RequestMapping(value="/productxj")
	@ResponseBody
	public List<DictTags>  productxj(){
		List<DictTags> list = dealerProductTagsService.findTagsByType(1);
		return list;
		}
	@RequestMapping(value="/productxjselect")
	@ResponseBody
	public List<ProductTags>  productxjselect(String pid){
		String[] split = pid.split(",");
		if (split.length==1) {
			List<ProductTags> list=dealerProductTagsService.findTagSelected(Long.parseLong(split[0]));
			return list;
		}
		return null;
	}
	/**
	 * 给商品添加标签
	 */
	@RequestMapping(value="/updatetags")
	@ResponseBody
	public String updatetags(String pid,String ids){
		try {
			String[] split = pid.split(",");
			for (String string : split) {
				dealerProductTagsService.deleteByPid(Long.parseLong(string));
				if (ids!=null) {
					String[] strings = ids.split(",");
					for (String string1: strings) {
						dealerProductTagsService.update(Long.parseLong(string),string1);
					}
				}
			}
			
			return Constants.SUCCESS;
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	/**
	 * 查询标签类型2列表
	 */
	@RequestMapping(value="/productxg")
	@ResponseBody
	public List<DictTags> productxg(){
		List<DictTags> list = dealerProductTagsService.findTagsByType(2);
		return list;
	}
	@RequestMapping(value="/productxgselect")
	@ResponseBody
	public List<ProductTags>  productxgselect(String pid){
		String[] split = pid.split(",");
		if (split.length==1) {
			List<ProductTags> list=dealerProductTagsService.findTagSelecteds(Long.parseLong(split[0]));
			return list;
		}
		return null;
	}
	@RequestMapping(value="/producthhselect")
	@ResponseBody
	public String  producthhselect(String pid){
		String[] split = pid.split(",");
		try {
			for (String string : split) {
				dealerProductTagsService.savehh(Long.parseLong(string));
				
			}
		} catch (Exception e) {
			
		}
		return Constants.SUCCESS;
	}
	@RequestMapping(value="/producthhselectd")
	@ResponseBody
	public String  producthhselectd(String pid){
		String[] split = pid.split(",");
		try {
			for (String string : split) {
				dealerProductTagsService.deletehh(Long.parseLong(string));
				
			}
		} catch (Exception e) {
			
		}
		return Constants.SUCCESS;
	}
	@RequestMapping(value="/updatetagsxg")
	@ResponseBody
	public String updatexgtags(String pid,String ids){
		try {
			String[] split = pid.split(",");
			for (String string : split) {
				
				dealerProductTagsService.deleteByPidxg(Long.parseLong(string));
				if (ids!=null) {
					String[] strings = ids.split(",");
					for (String string1 : strings) {
						dealerProductTagsService.update(Long.parseLong(string),string1);
					}
				}
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	@RequestMapping(value="/productzj")
	@ResponseBody
	public List<DictTags> productzj(){
		List<DictTags> list = dealerProductTagsService.findTagsByType(3);
		return list;
	}
	@RequestMapping(value="/productzjselect")
	@ResponseBody
	public List<ProductTags>  productzjselect(String pid){
		String[] split = pid.split(",");
		for (String string : split) {
			if (split.length==1) {
				List<ProductTags> list=dealerProductTagsService.findTagSelectednew(Long.parseLong(string));
				return list;
			}
		}
		return null;
	}
	@RequestMapping(value="/updatetagszj")
	@ResponseBody
	public String updatezjtags(String pid,String ids){
		try {
			String[] split = pid.split(",");
			for (String string : split) {
				dealerProductTagsService.deleteByPidzj(Long.parseLong(string));
				
				if (ids!=null) {
					String[] strings = ids.split(",");
					for (String string1 : strings) {
						dealerProductTagsService.update(Long.parseLong(string),string1);
					}
				}
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	@RequestMapping(value="/addTagsAll")
	@ResponseBody
	public String addTagsAll(){
		try {
			dealerProductTagsService.addTagsAll();
			return "操作成功！";
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	@RequestMapping(value="/productlx")
	@ResponseBody
	public List<DictTags> productlx(){
		List<DictTags> list = dealerProductTagsService.findTagsByType(4);
		return list;
	}
	@RequestMapping(value="/updatetagslx")
	@ResponseBody
	public String updatetagslx(Long pid,String ids){
		try {
			String[] strings = ids.split(",");
			dealerProductTagsService.deleteByPidlx(pid);
			for (String string : strings) {
			if (ids!=null) {
					dealerProductTagsService.update(pid,string);
				}
			}
			
			return Constants.SUCCESS;
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	@RequestMapping(value="/productlxselect")
	@ResponseBody
	public List<ProductTags>  productlxselect(Long pid){
		List<ProductTags> list=dealerProductTagsService.findTagSelectedlx(pid);
		return list;
	}
}