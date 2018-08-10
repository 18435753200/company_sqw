package com.mall.controller.order;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.order.api.rpc.CustomerSecondKillService;
import com.mall.customer.order.dto.SeckKillDTO;
import com.mall.customer.order.po.SeckKill;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
/**.
 * @Title CustomerSecondKillController
 * @Description B2C秒杀商品操作.
 * @author Guofy
 * @DATE 2015年8月18日11:22:21
 *
 */
@Controller
@RequestMapping(value = "/secondKill")
public class CustomerSecondKillController extends BaseController{

	/**.
	 * LOGGER日志打印.
	 */
	private static final Log LOGGER = LogFactory.getLogger(CustomerOrderController.class);


	/**
	 * 初始化秒杀活动商品图片信息.
	 * @return
	 */
	@RequestMapping(value = "/getSecondKillList")
	public String getSecondKillList(){
		LOGGER.info("start to execute method <getSecondKillList>!!!!");
		LOGGER.info("end<> to execute method <getSecondKillList>!!!!");
		return "/dealerseller/B2Cseckill/seckill_list";
	}
	
	
	/**
	 * 进入秒杀添加页面.
	 * @return
	 */
	@RequestMapping(value = "/goAddecondKillProduct")
	public String goAddecondKillProduct(){
		LOGGER.info("start to execute method <goAddecondKillProduct>!!!!");
		LOGGER.info("end<> to execute method <goAddecondKillProduct>!!!!");
		return "/dealerseller/B2Cseckill/seckill_add";
		
	}
	
	/**.
	 * @Description WOFE查询所有秒杀活动的商品.
	 * @Title getSecondKillListByContion
	 * @DATE 2015年8月18日17:43:24
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/getSecondKillListByContion")
	public String getSecondKillListByContion(Model model,Long pid,Long skuId,String startTime,String endTime,Integer page){
		LOGGER.info("start to execute method <getSecondKillListByContion>!!!!");
		SeckKillDTO seckKill = new SeckKillDTO();
		PageBean<SeckKillDTO> pageBean = new PageBean<SeckKillDTO>();
		if(null != pid){
			seckKill.setPid(pid);
		}
		if(null != skuId){
			seckKill.setSkuid(skuId);
		}
		if(!Common.isEmpty(endTime)){
			seckKill.setEndTime(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(startTime)){
			seckKill.setStartTime(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(null != page){
			pageBean.setPage(page);
		}else{
			pageBean.setPage(1);
		}
		pageBean.setParameter(seckKill);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerSecondKillService customerSecondKillService = RemoteServiceSingleton.getInstance().getcustomerSecondKillService();
			pageBean = customerSecondKillService.findSeckKillAllProductPageBean(pageBean);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("<查询全部秒杀的商品>customerSecondKillService.findSeckKillProduct() is failed!!"+e.getMessage(),e);
		}
		model.addAttribute("pageBean",pageBean);
		LOGGER.info("end<> to execute method <getSecondKillListByContion>!!!!");
		return "/dealerseller/B2Cseckill/seckill_list_model";
	}

	
	/**.
	 * @Description WOFE添加秒杀活动的商品.
	 * @Title saveSecondKillProduct
	 * @DATE 2015年8月18日14:20:43
	 * @param seckKill
	 * @return String
	 */
	@RequestMapping(value = "/saveSecondKillProduct")
	public String saveSecondKillProduct(MultipartFile imageProductUrl,SeckKill seckKill,String startDate,String endDate){
		LOGGER.info("start to execute method <saveSecondKillProduct添加秒杀活动>!!!!");
		LOGGER.info("SKUID:"+seckKill.getSkuid());
		LOGGER.info("PID:"+seckKill.getPid());
		LOGGER.info("活动开始时间:"+startDate+"   活动结束时间:"+endDate);
		seckKill.setCreateBy(getCurrentUser().getUsername());
		seckKill.setStatus(1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		if(!Common.isEmpty(startDate)){
			try {
				seckKill.setStartTime(simpleDateFormat.parse(startDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!Common.isEmpty(endDate)){
			try {
				seckKill.setEndTime(simpleDateFormat.parse(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(null != imageProductUrl && imageProductUrl.getSize()>0){
			String myfileUrl = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(imageProductUrl.getInputStream(), Common.getFileExt2(imageProductUrl.getOriginalFilename()), null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error("UploadFileUtil服务异常。。。。"+e.getMessage(), e);
			} 
			seckKill.setImageUrl(Constants.FILESERVER1+myfileUrl);
		}
		try {
			CustomerSecondKillService customerSecondKillService = RemoteServiceSingleton.getInstance().getcustomerSecondKillService();
			customerSecondKillService.insertSeckKill(seckKill);
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info("end<> to execute method <saveSecondKillProduct添加秒杀活动>!!!!");
		return "redirect:/secondKill/getSecondKillList";
	}
	
	
	
	/**
	 * @Description WOFE根据ID秒杀活动的商品.
	 * @Title showSecKillById
	 * @date 2015年8月19日10:17:44
	 * @param Model model
	 * @param Long id
	 * @return String
	 */
	@RequestMapping(value = "/showSecKillById")
	public String showSecKillById(Model model,Long id){
		LOGGER.info("start to execute method <showSecKillById根据ID秒杀活动>!!!!");
		LOGGER.info("活动ID:"+id);
		SeckKill secKill = new  SeckKill();
		try {
			CustomerSecondKillService customerSecondKillService = RemoteServiceSingleton.getInstance().getcustomerSecondKillService();
			secKill = customerSecondKillService.selectOneSeckillById(id);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("<查询一个秒杀的商品>customerSecondKillService.selectBySkuId(id) is failed!!"+e.getMessage(),e);
		}
		model.addAttribute("secKill",secKill );
		LOGGER.info("end<> to execute method <showSecKillById根据ID秒杀活动>!!!!");
		return "/dealerseller/B2Cseckill/seckill_update";
	}
	
	
	
	
	
	
	/**.
	 * @Description WOFE修改秒杀活动的商品.
	 * @Title updateSeckKillById
	 * @DATE 2015年8月18日18:55:23
	 * @param seckKill
	 * @param imageUrl
	 * @return String
	 */
	@RequestMapping("/updateSeckKillById")
	public String updateSeckKillById(MultipartFile imageProductUrl, SeckKill seckill,String startDate,String endDate){
		LOGGER.info("start to execute method <updateSeckKillById修改秒杀活动>!!!!");
		LOGGER.info("SKUID:"+seckill.getSkuid());
		LOGGER.info("PID:"+seckill.getPid());
		LOGGER.info("活动开始时间:"+startDate+"   活动结束时间:"+endDate);
		String result = "";
		seckill.setStatus(1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		if(!Common.isEmpty(startDate)){
			try {
				seckill.setStartTime(simpleDateFormat.parse(startDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!Common.isEmpty(endDate)){
			try {
				seckill.setEndTime(simpleDateFormat.parse(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(null != imageProductUrl && imageProductUrl.getSize()>0){
			String myfileUrl = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(imageProductUrl.getInputStream(), Common.getFileExt2(imageProductUrl.getOriginalFilename()), null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error("UploadFileUtil服务异常。。。。"+e.getMessage(), e);
			} 
			seckill.setImageUrl(Constants.FILESERVER1+myfileUrl);
			
		}
		boolean falg = false;
		try {
			CustomerSecondKillService customerSecondKillService = RemoteServiceSingleton.getInstance().getcustomerSecondKillService();
			falg = customerSecondKillService.updateSeckKillById(seckill);
		} catch (Exception e) {
			// TODO: handle exception
			result = "修改失败!";
			LOGGER.error("customerSecondKillService.updateSeckKillById(seckill)<根据ID修改秒杀商品信息!!>"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <updateSeckKillById修改秒杀活动>!!!!");
		if(falg){
			return "redirect:/secondKill/getSecondKillList";
		}else{
			return "redirect:/secondKill/showSecKillById";
		}
	}	
	
	
	/**.
	 * @Description WOFE删除秒杀活动的商品.
	 * @Title deleteSeckillById
	 * @DATE 2015年8月19日11:37:42
	 * @param Long id id
	 * @return String
	 */
	@RequestMapping(value = "/deleteSeckillById")
	@ResponseBody
	public String deleteSeckillById(Long id){
		LOGGER.info("start to execute method <deleteSeckillById删除秒杀活动>!!!!");
		LOGGER.info("活动ID:"+id);
		String result = "";
		boolean flag = false;
		try {
			CustomerSecondKillService customerSecondKillService = RemoteServiceSingleton.getInstance().getcustomerSecondKillService();
			flag = customerSecondKillService.deleteSeckillById(id);
			if(flag){
				result = "此活动删除成功!";
			}else{
				result = "此活动删除失败!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "此活动删除失败!";
			LOGGER.error("customerSecondKillService.deleteSeckillById(id)<根据ID删除秒杀商品信息!!>"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <deleteSeckillById删除秒杀活动>!!!!");
		return result;
	}
}
