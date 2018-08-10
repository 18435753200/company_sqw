package com.mall.controller.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.util.UserUtil;
import com.mall.dealer.product.api.DealerProductMixService;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.dealer.product.po.DealerProductCombination;
import com.mall.dealer.product.po.DealerProductScene;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformUser;
import com.mall.platform.service.PlatformUserManagerService;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.ValidateTool;

@Controller
@RequestMapping(value="/productMix")
public class ProductMixController extends BaseController{
	private static final Log LOGGER = LogFactory.getLogger(ProductMixController.class);
	
	@RequestMapping(value="/saveProductMix")
	@ResponseBody
	public String saveProductMix(Model model,DealerProductScene scene,HttpServletRequest request,
	        Long[] skuId,Long[] skuNum,Long[] groupNum,Short[] isDateil){
	    String flag = "0";
		try {
            DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
            scene.setCombinations(this.getCombinations(skuId, skuNum, groupNum, isDateil));
            scene.setCreatOperator(getCurrentUser().getSequenceId());// 获取当前登录用户ID
            dealerProductMixService.creatProductMix(scene);
            flag = "1";
            LOGGER.info("商品情景组合保存成功");
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return flag;
	}
	
	private List<DealerProductCombination> getCombinations(Long[] skuId,Long[] skuNums,Long[] groupNums,Short[] isDateils){
		List<DealerProductCombination> list = new ArrayList<DealerProductCombination>();
		if(skuId != null && skuId.length >0){
			for (int x = 0; x < skuId.length; x++) {
				DealerProductCombination com = new DealerProductCombination();
				com.setSkuId(skuId[x]);// 商品ID
				if(skuNums[x] > 1){// 验证默认商品才可以修改数量
					if(isDateils[x] == 0){
						LOGGER.error("不是默认商品 且修改数量");
						throw new RuntimeException("不是默认商品 且修改数量");
					}
				}
				com.setSkuNum(skuNums[x]);// sku数量
				com.setGroupNum(groupNums[x]);// 组号
				com.setIsDateil(isDateils[x]);// 是否是默认商品
				list.add(com);
			}
		}else{
			LOGGER.error("sku信息异常 无商品选择");
			throw new RuntimeException("sku信息异常 无商品选择");
		}
		return list;
	}
	
	@RequestMapping(value="/getMixList")
	public String getProductPage(){
		return "/dealerseller/productMix/productMixlist";
	}
	
	@RequestMapping(value="/getProductMixByConditions")
	public String getProductByConditions(Model model,PageBean<DealerProductScene> pageBean,
			DealerProductScene  scene,HttpServletRequest request){
		try{
		    // 获取详细时间
		    String startDateV = request.getParameter("startDate");
		    String endDateV = request.getParameter("endDate");
		    // 格式转换
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		    if(StringUtils.isNotBlank(startDateV)){
		        scene.setStartDate(sdf.parse(startDateV));
		    }
		    if(StringUtils.isNotBlank(endDateV)){
		        scene.setEndDate(sdf.parse(endDateV));
            }
		    // 设置参数
		    pageBean.setParameter(scene);
		    pageBean.setOrder("DESC");
		    pageBean.setSortFields("CREATTIME");
		    // 调用服务获取参数
			DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
			pageBean = dealerProductMixService.findProductMixListBy(pageBean);
			if(pageBean != null && pageBean.getResult() != null &&  pageBean.getResult().size() > 0){
			    PlatformUserManagerService platformUserManagerService = RemoteServiceSingleton.getInstance().getPlatformUserManagerService();
			    
			    for(DealerProductScene scene2 : pageBean.getResult()){
			        try {
			        	String name = UserUtil.findUserSqueID(
								String.valueOf(scene2.getCreatOperator()))
								.getUsername();
						if (ValidateTool.isEmpty(name)) {
							PlatformUser platformUser = platformUserManagerService
									.getUserById(scene2.getCreatOperator());
							if (!ValidateTool.isNull(platformUser)) {
								name = platformUser.getName();
							}
						}
	                    scene2.setCreatOperatorName(name);    
                    } catch (Exception e) {
                        LOGGER.info(scene2.getCreatOperator()+" is null ");
                    }
			    }
			}
			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return "1";
		}
		return "/dealerseller/productMix/modelpage/productmix_model";
	}
	/**
	 * add页面list返回
	 * @return
	 */
	@RequestMapping(value="/findMixProductList")
	public String findMixProductList(){
		return "/dealerseller/productMix/productAdd";
		
	}
	/**
	 * add页面条件筛选list返回
	 * @param model
	 * @param pageBean
	 * @param skuCodeDTO
	 * @return
	 */
	@RequestMapping(value="/findMixProductListByConditions")
	public String findMixProductListByConditions(Model model,PageBean<SkuCodeDTO> pageBean,SkuCodeDTO  skuCodeDTO){
		try{
			pageBean.setParameter(skuCodeDTO);
			DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
			pageBean = dealerProductMixService.findSkuDate(pageBean);
			List<SkuCodeDTO> result = pageBean.getResult();
			if(result != null && result.size()>0){
			    for(SkuCodeDTO dto : result){
			        dto.setImageUrl(Constants.P1+dto.getImageUrl());
			    }
			}
			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return "/dealerseller/productMix/modelpage/productadd_model";
		
	}

	@RequestMapping(value="/updateMixTate")
	public String updateProductMixById(Model model,Long mixId){
	    DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
	    dealerProductMixService.updateProductMixTateById(mixId);
	    return "/dealerseller/productMix/productMixlist";
	}
	
	@RequestMapping(value="/showMix")
	public String showProductMixById(Model model,Long mixId){
	    DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
	    DealerProductScene productMix = dealerProductMixService.findProductMixById(mixId);
	    List<DealerProductCombination> combinations = productMix.getCombinations();
	    if(combinations != null && combinations.size()>0){
	        for(DealerProductCombination com : combinations){
	            com.setImageUrl(Constants.P1+com.getImageUrl());
	        }
	    }
	  
	    String name = UserUtil.findUserSqueID(
				String.valueOf(productMix.getCreatOperator()))
				.getUsername();
		if (ValidateTool.isEmpty(name)) {
			PlatformUser platformUser = RemoteServiceSingleton.getInstance().getPlatformUserManagerService()
					.getUserById(productMix.getCreatOperator());
			if (!ValidateTool.isNull(platformUser)) {
				name = platformUser.getName();
			}
		}
	    
	    productMix.setCreatOperatorName(name); 
	    model.addAttribute("barImageUrl",Constants.ND+productMix.getDetailImageUrl());
	    model.addAttribute("productMix", productMix);
	    return "/dealerseller/productMix/productShowMix";
	}
	
	@RequestMapping(value="/toCreat")
	public String toCreatMix(){
	    return "/dealerseller/productMix/productCreatMix";
	}
	
	@RequestMapping(value="/toUpdateMix")
	public String toUpdateMix(Model model,Long mixId){
	    DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
        DealerProductScene productMix = dealerProductMixService.findProductMixById(mixId);
        List<DealerProductCombination> combinations = productMix.getCombinations();
        if(combinations != null && combinations.size()>0){
            for(DealerProductCombination com : combinations){
                com.setImageUrl(Constants.P1+com.getImageUrl());
            }
        }

        String name = UserUtil.findUserSqueID(String.valueOf(productMix.getCreatOperator())).getUsername();
		if (ValidateTool.isEmpty(name)) {
			PlatformUser platformUser = RemoteServiceSingleton
					.getInstance().getPlatformUserManagerService()
					.getUserById(productMix.getCreatOperator());
			if (!ValidateTool.isNull(platformUser)) {
				name = platformUser.getName();
			}
		}
        
        productMix.setCreatOperatorName(name); 
        model.addAttribute("barImageUrl",Constants.ND+productMix.getDetailImageUrl());
        model.addAttribute("productMix", productMix);
	    return "/dealerseller/productMix/productUpdateMix";
	}
	
	@RequestMapping(value="/updateMix")
	@ResponseBody
	public String updateMix(Model model,DealerProductScene scene,HttpServletRequest request,
	        Long[] skuId,Long[] skuNum,Long[] groupNum,Short[] isDateil){
	    String flag = "0";
		try {
            DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
            List<DealerProductCombination> combinations = this.getCombinations(skuId, skuNum, groupNum, isDateil);
            for(DealerProductCombination com : combinations){
            	com.setSceneId( scene.getSceneId());
            }
            scene.setCombinations(combinations);
            scene.setUpdateOperator(getCurrentUser().getSequenceId());// 获取当前登录用户ID
            dealerProductMixService.updateProductMixById(scene);
            flag = "1";
            LOGGER.info("商品情景组合更新成功");
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return flag;
	}
	
    @RequestMapping(value = "/deleteMix")
    @ResponseBody
    public String deleteMixById(Model model, Long mixId) {
        String flag = "0";
        
            try {
                DealerProductMixService dealerProductMixService = RemoteServiceSingleton.getInstance().getDealerProductMixService();
                dealerProductMixService.deleteProductMixById(mixId);
            } catch (Exception e) {
                LOGGER.error(e);
            }
        return flag;
    }
}
