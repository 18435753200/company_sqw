package com.mall.controller.product;

import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.category.po.MoneyUnit;
import com.mall.category.po.TcMeasure;
import com.mall.category.po.TdCatePub;
import com.mall.category.po.TdCatePubAttr;
import com.mall.category.po.TdCatePubAttrval;
import com.mall.customer.order.dto.ThirdPartyMessage;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.dto.DealerPordUpdateTateDto;
import com.mall.dealer.product.dto.DealerProductAttrDTO;
import com.mall.dealer.product.dto.DealerProductObjectDTO;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.dealer.product.dto.DealerProductShowDTO;
import com.mall.dealer.product.dto.DealerProductSkuShowDTO;
import com.mall.dealer.product.dto.ProductSkuStockDto;
import com.mall.dealer.product.dto.PurchasePriceDto;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.dealer.product.po.DealerProductAttach;
import com.mall.dealer.product.po.DealerProductWholesaleRange;
import com.mall.dealer.product.po.PurchasePricePO;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.stock.common.StockConstant;
import com.mall.stock.dto.StockrepairVO;
import com.mall.stock.po.Stock;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.product.dto.SupplierProductForOrderDTO;
import com.mall.supplier.product.dto.SupplierProductObjectDTO;
import com.mall.supplier.product.dto.SupplierProductSkuDTO;
import com.mall.supplier.product.po.SupplierProductWholesaleRange;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.DateTool;
import com.mall.utils.FormatBigDecimal;

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
@RequestMapping(value="/product")
public class ProductController extends BaseController{
	
    
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(ProductController.class);
	
	/**.
	 * 加载商品页面
	 * @param request HttpServletRequest
	 * @param dealerProductSelectConVO DealerProductSelectConDTO
	 * @return ModelAndView
	 */
	@RequestMapping(value="/getPro")
	public String getProductPage(){
		
		return "/dealerseller/product/productlist";
		
	}
	
	/**条件加载商品列表怕
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param cate  String
	 * @param page Integer
	 * @param dealerProductSelectConDTO DealerProductSelectConDTO
	 * @return null
	 */
	@RequestMapping(value="/getProductByConditions")
	public String getProductByConditions(Model model,String cate,String startTime,String endTime,PageBean<DealerProductShowDTO> pageBean,
			DealerProductSelectConDTO  dealerProductSelectConDTO
			){
		try {
			List<String> cateList = new ArrayList<String>();
			if(cate!=null){
				cateList.add(cate);
				dealerProductSelectConDTO.setCateDispIds(cateList);
			}
			if(!Common.isEmpty(startTime)){
				dealerProductSelectConDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
			}
			if(!Common.isEmpty(endTime)){
				dealerProductSelectConDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			pageBean.setSortFields("CREATEDDATE");
			pageBean.setOrder("DESC");
			pageBean.setParameter(dealerProductSelectConDTO);
			
			pageBean = RemoteServiceSingleton.getInstance().
					getDealerProductService().findProductsByConditions(pageBean);
			//张立奎 重复代码 20151130
			/*pageBean = RemoteServiceSingleton.getInstance().
					getDealerProductService().findProductsByConditions(pageBean);*/
		
			List<DealerProductShowDTO> result = pageBean.getResult();
		
			if(result!=null){
				FormatBigDecimal formatBigDecimal = new FormatBigDecimal();
				for( int i = 0 ; i < result.size() ; i++ ){
					String imgUrl = result.get(i).getImgURL();
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
						
						if(result.get(i).getIsB2c()){
							result.get(i).setB2cImage(Constants.M1+imgUrl);
						}
						result.get(i).setImgURL(Constants.P1+imgUrl);
					}
					BigDecimal productPriceMin = result.get(i).getProductPriceMin();
					BigDecimal productPriceMax = result.get(i).getProductPriceMax();
					
					BigDecimal priceMax = formatBigDecimal.priceFormat(productPriceMax);
					BigDecimal priceMin = formatBigDecimal.priceFormat(productPriceMin);
					
					result.get(i).setProductPriceMin(priceMin);
					result.get(i).setProductPriceMax(priceMax);
					
					if(null != result.get(i).getDescription()){
						result.get(i).setDescription(result.get(i).getDescription().replaceAll("\n", "<br/>"));
					}
					
					if(dealerProductSelectConDTO.getAuditStatus()==Constants.SHORT6){
						result.get(i).setCounterfeittypeid(""+Constants.NUM6);
						//result.get(i).getSupply();
					}
					
					/**
					 * 如果商品是POP商品，则删除
					 */
//					if(result.get(i).getSupply() == 51){
//						result.remove(i);
//						pageBean.setTotalCount(pageBean.getTotalCount() - 1);
//					}
				}
			}
			
			model.addAttribute("pb", pageBean);
			
			} catch (Exception e) {
				
				LOGGER.error(e.getMessage(),e);
				
				return "1";
				
			}
			return "/dealerseller/product/modelpage/product_model";
			
		}
	
	/**.
	 * 修改商品页 
	 * @param model Model
	 * @param catePubId String
	 * @param productId Long
	 * @param target 1修改操作  2 查看操作
	 * @param request HttpServletRequest
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
			allMeasure = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().findAllMeasure();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		//分段折扣
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
//			if(proObjVo != null && proObjVo.getB2cProductDetail() != null){
//				if(proObjVo.getB2cProductDetail().getB2cSupply() != null && proObjVo.getB2cProductDetail().getB2cSupply() != (short)31)
//			}
			jsonSupplierProductPic = JSONArray.fromObject(supplierProductPic);
			
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

		//图片回显
		List<LinkedHashMap<String,Object>> imgUrl = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			imgUrl = serviceProduct.imgShow(proObjVo.getDealerProductAttrDTOs());
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
							if(bufImgInfo!=null&&!bufImgInfo.equals("")){
								LOGGER.info("图文详情:"+bufImgInfo);
								bufImgInfo=bufImgInfo.replaceAll("\\\\", "");
								if(bufImgInfo!=null&&bufImgInfo.indexOf("detail_inner.css")!=-1){
					            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
					            	String endStr="</div>";
					            	bufImgInfo=bufImgInfo.replaceAll(startStr, "").replaceAll(endStr, "");
					            }
							}
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
					if(b2cDescription!=null){
						b2cDescription = b2cDescription.replaceAll("\\\\", "");
						if(b2cDescription!=null&&b2cDescription.indexOf("detail_inner.css")!=-1){
			            	String startStr="<link rel=\"stylesheet\" href=\"http://s.zhongjumall.com/bcw/commons/css/revision20160606/detail_inner.css\" type=\"text/css\"><div class='detail_inner' >";
			            	String endStr="</div>";
			            	b2cDescription=b2cDescription.replaceAll(startStr, "").replaceAll(endStr, "");
			            }
					}
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
//		model.addAttribute("buyListJson", buyListJson);
//		model.addAttribute("saleListJson", saleListJson);
		model.addAttribute("simpleAttrs", dtOs);
		
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
	
	/**.
	 * 删除操作
	 * AJAX
	 * @return String
	 * @param request  HttpServletRequest
	 * @param pid String
	 */
	@ResponseBody
	@RequestMapping(value="/deletePros")
	public String deleteProductByIds(HttpServletRequest request,String pid,String delReason){
		
		String delstat = Constants.SUCCESS;
		
		if (pid.length()>0){

			LOGGER.info(" 删除商品.");
			LOGGER.info("商品Id:"+pid);

			LOGGER.info("用户:"+getCurrentUser().getUsername());
			LOGGER.info("用户ID:"+getCurrentUser().getId());

			try{

				String[] cates = pid.split(",");
				Long [] catels = new Long[cates.length];

				for(int i = 0 ; i < cates.length ; i++ ){

					catels[i] = Long.parseLong(cates[i]);

				}

				User object =  getCurrentUser();

				Long platforId = object.getSequenceId();

				RemoteServiceSingleton.getInstance().
					getDealerProductService().deleteProductById(catels, platforId+"", delReason);		

			}catch (Exception e) {

				LOGGER.error(e.getMessage(),e);

				delstat = Constants.ERROR;

			}
			
		}else{
			
			delstat = Constants.ERROR;
			
		}
		
		return delstat;
		
	}
	
	/**
	 * 跳转添加库存页面
	 * @return
	 */
	@RequestMapping(value="/getAddInventoryView")
	public String getAddInventoryView(Model model,HttpServletRequest request,Long productId,String supply){
		LOGGER.info("补录商品的类型:"+supply);
		request.getSession().setAttribute("productId", productId);
		
		List<Warehouse> findWarehouseEnum = new ArrayList<Warehouse>();
		
		try {
			
			findWarehouseEnum = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehouseEnum(StockConstant.getWarehouseType(supply));

		} catch (Exception e) {

			LOGGER.error(e.getMessage(),e);
				
		}

		model.addAttribute("warehouse",findWarehouseEnum);
		
		return "/dealerseller/product/addNewInventory";
	}
	
	/**
	 * 添加库存信息.
	 * @param productId 商品Id，用于查询商品SKU信息.
	 * @return 正常返回sku jsp页面.
	 */
	@RequestMapping(value="/getInventoryListByProductId")
	public String getInventoryListByProductId(HttpServletRequest request, HttpServletResponse response,
			Long productId,Long dealerId){
		LOGGER.info("补录商品的ID:"+productId);
		try {
			
			if (productId!=null){
				
				List<Long> productIds = new ArrayList<Long>();
				
				productIds.add(productId);
				
				List<ProductSkuStockDto> listProductSku = RemoteServiceSingleton.getInstance()
							.getDealerProductService().findProductSkuByProductIdType(productIds);
				
				User object =  getCurrentUser();

				
				Stock stocks = new Stock();
				
				//stocks.setDealerId(dealerId);
				//stocks.setShipperId(dealerId);
				stocks.setOwner(object.getSequenceId());
				stocks.setPid(productId);
				
				Map<Long, List<StockrepairVO>> maps = RemoteServiceSingleton.getInstance()
							.getStockWofeService().findStockByPidAndowenr(stocks);
				
				request.getSession().setAttribute("productskus",listProductSku);
				request.getSession().setAttribute("productskumaps",maps);
				
			}
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		}
		
		return "/dealerseller/product/modelpage/product_addinventory_model";
		
	}
	
	/**
	 * 保存补录信息.
	 * @param request
	 * @param pName
	 * @param dealerId
	 * @param pid
	 * @param skuId
	 * @param batchNo
	 * @param qty
	 * @param createTime
	 * @param endTime
	 * @param skuName
	 * @return
	 */
	@RequestMapping("/stockrepair")
	@ResponseBody
	public String orderDespatch(HttpServletRequest request,String[] pName,Long dealerId,Integer warehouseId,
			Long pid,Long[] skuId,String[] batchNo,Integer[] qty,Date[] createTime,Date[] endTime,String[] skuName){
		LOGGER.info("补录商品库房的ID:"+warehouseId);
		LOGGER.info("补录商品的ID:"+pid);
		String isPass = "0";
		try{
			User object =  getCurrentUser();

			List<StockrepairVO> stockvolists = new ArrayList<StockrepairVO>(); 
			for(int i = 0 ; i < skuId.length ; i++){
				StockrepairVO stockrepairVO = new StockrepairVO();
				stockrepairVO.setPid(pid);
				//stockrepairVO.setDealerId(dealerId);
				//stockrepairVO.setShipperId(dealerId);
				stockrepairVO.setCreateBy(object.getUsername());
				//stockrepairVO.setOwner(object.getPlatformId());
				stockrepairVO.setpName(pName[0]);
				stockrepairVO.setSkuId(skuId[i]);
				stockrepairVO.setSkuName(skuName[i]);
				//stockrepairVO.setBatchNo(batchNo[i]);
				stockrepairVO.setBatchNo(PKUtils.getLongPrimaryKey()+"");
				stockrepairVO.setQty(qty[i]);
				
				if (createTime!=null&&createTime.length>0&&"null".equals(createTime)){
					
					stockrepairVO.setProductTime(createTime[i]);
					stockrepairVO.setLastproductTime(endTime[i]);
					
				}else{
					
					stockrepairVO.setProductTime(new Date());
					stockrepairVO.setLastproductTime(new Date());
					
				}
				stockvolists.add(stockrepairVO);
				
			}
			
			Map<Long,List<StockrepairVO>> mapstockvos = new HashMap<Long, List<StockrepairVO>>();
			
			for(StockrepairVO stockvolist:stockvolists){
				
				List<StockrepairVO> list = new ArrayList<StockrepairVO>();
				
				if (mapstockvos.containsKey(stockvolist.getSkuId())){
					
					list = mapstockvos.get(stockvolist.getSkuId());
					list.add(stockvolist);
					
				}else{
					
					list.add(stockvolist);
					
				}
				
				mapstockvos.put(stockvolist.getSkuId(), list);
				
			}
			
			Set<Long> keys = mapstockvos.keySet();
			
			for (Long skuid:keys){
				
				List<StockrepairVO> list = mapstockvos.get(skuid);
				
				for (StockrepairVO  stockvolist:list){
					
					int i = 0;
					
					for(StockrepairVO  stockvolist1:list){
						
						if (stockvolist.getBatchNo().equals(stockvolist1.getBatchNo())){
							
							i++;
							
						}
						
					}
					
					if (i>1){
						
						isPass = "-2";
						
					}
					
				}
				
			}
			
			if(isPass.equals("0")){
				
				RemoteServiceSingleton.getInstance().getStockWofeService().stockrepair(stockvolists, warehouseId);
				
			}

		} catch (Exception e) {
			
			LOGGER.error("保存补货信息失败");
			LOGGER.error(e.getMessage(),e);
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("保存补货信息失败经销商ID:"+dealerId);
			
			isPass = "-1";
			
		}
		
		return isPass;
				
	}
	
	/**
	 * 复制商品 操作 1 查询可以合并的新增商品
	 * @param model
	 * @param productId
	 * @param productName
	 * @param imgURL
	 * @param businessProdId
	 * @return 可合并新增商品列表页
	 */
	@RequestMapping(value="/getProductByCategory",method=RequestMethod.POST)
	public String getProductByCategory(Model model ,Long productId,String productName,String imgURL,String businessProdId){
		
		PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
		DealerProductSelectConDTO dealerProductSelectConDTO = new DealerProductSelectConDTO();
		
		dealerProductSelectConDTO.setProductId(productId);
		
		pageBean.setPageSize(Constants.MAXPAGESIZE);
		pageBean.setPage(Constants.DEFAULTPAGE);
		pageBean.setParameter(dealerProductSelectConDTO);
		
		try {
			
			 pageBean = RemoteServiceSingleton.getInstance().
				getDealerProductService().findProductListById(pageBean);
			 
		} catch (Exception e) {
			LOGGER.info("复制商品时 查询商品接口出错"+e.getMessage(),e);
		}

		List<DealerProductShowDTO> result = pageBean.getResult();
		
		if(result!=null){
			for( int i = 0 ; i < result.size() ; i++ ){
				String imgUrl = result.get(i).getImgURL();
				if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
					
					if(result.get(i).getIsB2c()){
						result.get(i).setB2cImage(Constants.M1+imgUrl);
					}
					result.get(i).setImgURL(Constants.P1+imgUrl);
				}
			}
		}
		
		dealerProductSelectConDTO.setProductId(productId);
		dealerProductSelectConDTO.setProductName(productName);
		dealerProductSelectConDTO.setBusinessProdId(businessProdId);
		dealerProductSelectConDTO.setSeleByPidStatus(imgURL);//存储图片方便传输
		pageBean.setParameter(dealerProductSelectConDTO);
		
		model.addAttribute("pageBean", pageBean);
		
		return "/dealerseller/product/modelpage/product_copypaste_model";
	}
	
	
	/**.
	 * 覆盖商品页 
	 * @param model Model
	 * @param catePubId String
	 * @param nProductId Long 被覆盖上商品ID
	 * @param cProductId Long 已复制的商品ID
	 * @param target 1修改操作  2 查看操作
	 * @param request HttpServletRequest
	 * @param bodytype 1 b2b查看修改商品 2 b2c查看修改商品 用于前台区分显示
	 * @return String URL
	 */
	@RequestMapping(value="/coverProduct")
	public String coverProduct(Model model,String catePubId,Long nProductId,Long cProductId,String target,String iseditproperty,String bodytype){
		
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
					.getDealerProductService().findCopyProductDate(nProductId, cProductId,"");

		} catch (Exception e) {

			LOGGER.error(e.getMessage(),e);

		}

		try {
			
			editStat = serviceProduct.checkProductIsEdit(target, proObjVo);
			
		} catch (Exception e) {
			LOGGER.info("nProductId="+nProductId+"\n cProductId="+cProductId);
			LOGGER.error(e.getMessage(),e);
		}

		String categoryId = proObjVo.getDealerProduct().getCatePubId();

		try {
			categoryAttrAndValList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryAttrAndValList(categoryId);
		} catch (Exception e) {
			LOGGER.error("获取类目失败  catePubId="+catePubId);
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
					getSupplierProductOrderService().findProSkuPriceInfoByPId(nProductId, "");

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
			jsonSupplierProductPic = JSONArray.fromObject(supplierProductPic);
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
		
		if (null != b2cDescription&& !b2cDescription.equals("")){
			
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
    /**
     * 转向修改类目页
     * @param model
     * @param productId
     * @return StringUrl
     */
    @RequestMapping("/toEditCategoryUI")
    public String toEditCategoryUI(Model model, Long productId,String showbodytype) {  	
    	logger.info("toEditCategoryUI!!!productId："+productId);
		try {   
            DealerProductObjectDTO productObject = RemoteServiceSingleton.getInstance().getDealerProductService().findProductObjectById(productId, "");    
            String categoryId = productObject.getDealerProduct().getCatePubId(); 
            List<TdCatePub> cateNamesList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getCategoryNameByDigui(categoryId); 
            if(cateNamesList!=null){
            	for (int i = 0; i < cateNamesList.size(); i++) {
                	TdCatePub tdCatePub = cateNamesList.get(i);
                	if(i==0){	
                		model.addAttribute("firCategoryId", tdCatePub.getCatePubId());	
                	}
                	if(i==1){	
                		model.addAttribute("secCategoryId", tdCatePub.getCatePubId());		
                	}
                	if(i==2){	
                		model.addAttribute("thiCategoryId", tdCatePub.getCatePubId());	
                	}
                	if(i==3){	
                		model.addAttribute("fouCategoryId", tdCatePub.getCatePubId());	
                	}
    			}
            }  
    		List<TdCatePub> list = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getFirstCategoryList();			
			if(null != list && list.size() > 0){
				LOGGER.info(JSON.toJSONString(list));
				List<Map<String, String>> categorys = new ArrayList<Map<String,String>>();
				for (TdCatePub tdCatePub : list) {
					Map<String, String> catePub = new LinkedHashMap<String, String>();
					catePub.put("cid", tdCatePub.getCatePubId());
					catePub.put("nameCn", tdCatePub.getPubNameCn());
					catePub.put("name", tdCatePub.getPubName());
					catePub.put("leaf", tdCatePub.getLeaf()+"");
					categorys.add(catePub);
				}
				model.addAttribute("categorys", categorys);
				model.addAttribute("productId", productId);
				model.addAttribute("showbodytype", showbodytype);
			}
        } catch (Exception e) {	
            LOGGER.error("获取商品信息失败productId"+productId + e.getMessage(),e);
            return "/error/notFind";
        }
        return "/dealerseller/product/editCategoryUI";
    }
    /**
     * 修改商品分类
     * @param model
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/toUpdateCategoryId")
    @ResponseBody
    public String toUpdateCategoryId(Model model, Long productId,String cateId) {  	
    	logger.info("toEditCategoryUI!!!productId："+productId);
    	LOGGER.info("用户ID:"+getCurrentUser().getId());
		try {   
			DealerProduct product = new DealerProduct();
			product.setProductid(productId);
			product.setCatePubId(cateId);
			product.setOperatetime(new Date());
			product.setOperator(getCurrentUser().getId());
			RemoteServiceSingleton.getInstance().getDealerProductService().updateProductCategoryId(product);
			return "1";
        } catch (Exception e) {	
            LOGGER.error("获取商品分类信息失败productId"+productId+ e.getMessage(),e);
            return "0";
        }
    }

	/**
	 * 显示子目录.
	 * @param response HttpServletResponse
	 * @param parentCid categoryId
	 */
	@RequestMapping("/childCategory")
	public void childCategory(HttpServletResponse response,String parentCid){
		LOGGER.info("findChildCategory!!!;parentCid:"+parentCid);
		response.setContentType("text/html;charset=UTF-8");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
			try {
				Object json = "[]";
				if(RemoteServiceSingleton.getInstance().getCategoryServiceRpc()!=null){
					//获取所有有效子类目
					childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getFirstChildrenCategoryList(parentCid);
				}
			
				if(null != childrenCategoryList){
					List<Map<String, String>> categorys = new ArrayList<Map<String,String>>();
					for (TdCatePub tdCatePub : childrenCategoryList) {
						Map<String, String> catePub = new LinkedHashMap<String, String>();
						catePub.put("cid", tdCatePub.getCatePubId());
						catePub.put("nameCn", tdCatePub.getPubNameCn());
						catePub.put("name", tdCatePub.getPubName());
						catePub.put("leaf", tdCatePub.getLeaf()+"");
						categorys.add(catePub);
					}					
					json = com.alibaba.fastjson.JSONArray.toJSON(categorys);
					LOGGER.info(json.toString());
					response.getWriter().write(json.toString());
				}
			} catch (Exception e1) {
				LOGGER.error("获取子类目失败"+e1.getMessage(),e1);
			}		
	}
	@RequestMapping(value = "/toImport")
    public String toImport(HttpServletRequest request){
        request.setAttribute("message",request.getParameter("message"));
        return "/dealerseller/product/toImport";
    }
	/**
	 * 获取采购价格表格数据
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/settingPurchasePriceExcel")
	public String settingPurchasePriceExcel(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, Model model){
 
		String fileName = file.getOriginalFilename();
		if(StringUtils.isBlank(fileName) || !(fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件格式不正确");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/product/toImport";
        }
		
		DealerProductSkuService dealerProductSkuService = RemoteServiceSingleton.getInstance().getDealerProductSkuService();
		try {
				Workbook wb = null;
				if (fileName.endsWith("xls") ) {
					wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream()));
				} else if(fileName.endsWith("xlsx") ){
					wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
				} else{
	                ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件不能解析");
	                model.addAttribute("message", JSONArray.fromObject(m).toString());
	                return "redirect:/product/toImport";
				}
			  Sheet sheet = wb.getSheetAt(0); // 创建对工作表的引用
              int rows = sheet.getPhysicalNumberOfRows();//获取表格的行
              for (int r = 1; r < rows; r++) {//循环遍历表格的行
                  Row row = sheet.getRow(r);//获取单元格中指定的行对象
                  if (row != null) {                	  
                	  PurchasePriceDto dto = new PurchasePriceDto();

                       int cells = row.getPhysicalNumberOfCells();//获取单元格中指定列对象
                       for (short c = 0; c < cells; c++) {      //循环遍历单元格中的列                  
                          Cell cell = row.getCell((short) c); //获取指定单元格中的列                      
                          if (cell != null) {
                              if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {//判断单元格的值是否为字符串类型
                            	  //供应商Id
                            	  if(c == 0){
                            		  dto.setSupplierId(cell.getStringCellValue().trim());
                            	  }
                            	  //供应商名称
                            	  if(c == 1){
                            		  dto.setSupplierName(cell.getStringCellValue().trim());
                            	  }
                            	  //skuId
                            	  if(c == 2){
                            		  dto.setSkuId(cell.getStringCellValue().trim());
                            	  }
                            	  //成本价
                            	  if(c == 3){
                            		  dto.setPurchasePrice(cell.getStringCellValue().trim());
                            	  }
                              } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {//判断单元格的值是否为数字类型            
                            	  if(c == 0){
                            		  dto.setSupplierId(cell.getNumericCellValue()+"");
                            	  }  
                            	  if(c == 1){
                            		  dto.setSupplierName(cell.getNumericCellValue()+"");
                            	  }
                            	  if(c == 2){
                            		  dto.setSkuId(cell.getNumericCellValue()+"");
                            	  }                            	  
                            	  if(c == 3){
                            		  dto.setPurchasePrice(cell.getNumericCellValue()+"");
                            	  }                            	  
                              }
                          }
                      }
                      if(dto.getSkuId()!=null&&dto.getSupplierId()!=null&&dto.getPurchasePrice()!=null){
                    	  Map<String, String> map = new HashMap<String, String>();		                      
                          map.put("skuId", dto.getSkuId());
                          map.put("supplierId", dto.getSupplierId());
                          DealerProductSkuShowDTO dealerProductShow = dealerProductSkuService.selectproductSkuShowBySkuId(map);
                          if(null != dealerProductShow){	
                              PurchasePricePO overPurchasePrice = new PurchasePricePO();
                              overPurchasePrice.setSupplierId(dto.getSupplierId());
                              overPurchasePrice.setSupplierName(dto.getSupplierName());
                              overPurchasePrice.setSkuId(dto.getSkuId());
                              overPurchasePrice.setPurchasePrice(new BigDecimal(dto.getPurchasePrice()));
                              overPurchasePrice.setBeginTime(new Date());
                              overPurchasePrice.setEndTime(new Date());
                              overPurchasePrice.setOperation(getCurrentUser().getUsername());
                              overPurchasePrice.setOperationTime(new Date());
    	                      overPurchasePrice.setPid(dealerProductShow.getProductid());
    	                      overPurchasePrice.setProductName(dealerProductShow.getProductName()); 
    	                      boolean status2 = false;           
    	                      try {
    	                    	    status2 = dealerProductSkuService.insertProductPurchasePrice(overPurchasePrice);
    		                   		ThirdPartyMessage m = new ThirdPartyMessage("success","","导入成功！");
    		                        model.addAttribute("message", JSONArray.fromObject(m).toString());
    						  } catch (Exception e) {
    							 if(status2 == false){
    	               			  	LOGGER.error("采购价格表添加失败！！！" + e.getMessage(), e);
    							 }
    							 ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,插入数据库异常");
    				             model.addAttribute("message", JSONArray.fromObject(m).toString());
    				             return "redirect:/product/toImport";
    						  }
                          }else{
                        	  ThirdPartyMessage m = new ThirdPartyMessage("fail","","excel中SkuID与供应商ID不符");
      				          model.addAttribute("message", JSONArray.fromObject(m).toString());
      				          return "redirect:/product/toImport";
                          }
                      }else{
                    	  ThirdPartyMessage m = new ThirdPartyMessage("fail","","excel中SkuID:"+dto.getSkuId()+"的供应商ID,成本价不能为空");
  				          model.addAttribute("message", JSONArray.fromObject(m).toString());
  				          return "redirect:/product/toImport";
                      }
                }
            }              
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("解析execl错误",e);
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,解析execl错误");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/product/toImport";
        }
		return "redirect:/product/toImport";        
    }
}	