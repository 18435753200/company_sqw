package com.mall.controller.prize;


import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.RPCPrizeService;
import com.mall.customer.order.dto.prize.PrizeDto;
import com.mall.customer.order.dto.prize.PrizeRecordDto;
import com.mall.customer.order.dto.prize.PrizeUserDto;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.utils.JsonUtil;
import com.mall.vo.prizeDataVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Prize 抽奖相关方法
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping(value = "/prize")
public class PrizeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PrizeController.class);
	@Autowired
	private RPCPrizeService rpcPrizeService;
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;
	@Autowired
	private prizeDataVO skuVO;
	/**
	 * 跳转到获奖列表页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/goPrizeList" ,method = RequestMethod.GET)
	public String goPrizeList(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		return "prize/prizeList";
	}

	/**
	 * 跳转到领奖页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/goWinPrize" ,method = RequestMethod.GET)
	public String goWinPrize(HttpServletRequest request,HttpServletResponse response,Model model,String recordId){
		this.getCurrentUser(request);
		log.info("领取的奖品记录ID为-----------------"+recordId);
		model.addAttribute("recordId",recordId);
		return "prize/prizeAddress";
	}

	/**
	 * 查看领奖showPrize
	 * @param request
	 * @param response
	 * @param model
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "/showPrize" ,method = RequestMethod.GET)
	public String showPrize(HttpServletRequest request,HttpServletResponse response,Model model,String recordId){
		log.info("查看领奖信息-----------------" +recordId);
		PrizeRecordDto  dto=rpcPrizeService.getPrizeRecord(Long.valueOf(recordId));
		if(!dto.getStatus().equals("0") && !dto.getStatus().equals("3")){
			String[] adrs=dto.getAderess().split(" ");
			model.addAttribute("proviName",adrs[0]);
			model.addAttribute("cityName",adrs[1]);
			model.addAttribute("areaName",adrs[2]);
		}else if(dto.getStatus().equals("3")){
			model.addAttribute("proviName","请选择");
			model.addAttribute("cityName","请选择");
			model.addAttribute("areaName","请选择");
		}
		model.addAttribute("prizeRecorddto",dto);
		return "prize/showPrize";
	}
	/**
	 *  获取个人中奖纪录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrizeListForPersonal" ,method = RequestMethod.POST)
	public String getPrizeListForPersonal(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		log.info("进入获取个人中奖纪录的信息方法-----------------");
		//String userId=request.getParameter("userId");
		User user = getCurrentUser(request);
		List<PrizeRecordDto> prizeList=rpcPrizeService.getUserPrizeRecords(String.valueOf(user.getUserId()));
		List<com.mall.vo.PrizeRecordDto> dtoList=new ArrayList<com.mall.vo.PrizeRecordDto>();
		if(null!=prizeList && prizeList.size()>0){
			for(PrizeRecordDto d:prizeList){
				com.mall.vo.PrizeRecordDto dto=new com.mall.vo.PrizeRecordDto();
				dto.setRecordId(String.valueOf(d.getRecordId()));
				dto.setUserId(d.getUserId());
				dto.setUserPhone(d.getUserPhone());
				dto.setPrizeId(String.valueOf(d.getPrizeId()));
				dto.setPrizeTitle(d.getPrizeTitle());
				dto.setIsPrize(d.getIsPrize());
				dto.setStatus(d.getStatus());
				dto.setProvince(String.valueOf(d.getProvince()));
				dto.setCity(String.valueOf(d.getCity()));
				dto.setArea(String.valueOf(d.getArea()));
				dto.setAderess(d.getAderess());
				dto.setOwnName(d.getOwnName());
				dto.setOwnPhone(d.getOwnPhone());
				dto.setPrizeImg(d.getPrizeImg());
				dto.setCreateDate(d.getCreateDate());
				dto.setType(d.getType());
				dto.setPrizeDate(d.getPrizeDate());
				dto.setGrantPrizeDate(d.getGrantPrizeDate());
				dto.setSource(d.getSource());
				dtoList.add(dto);
			}
		}
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(Long.class, new JsonValueProcessor() {
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {

				return value;
			}
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value+"";
			}

		});
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {

				return value;
			}
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				if(null!=value){
					value =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				}

				return value;
			}

		});
		log.info("获取个人中奖纪录的信息-----------------如下："+ JSONArray.fromObject(dtoList, jsonConfig).toString());
		return JSONArray.fromObject(dtoList, jsonConfig).toString();
	}


	/**
	 * 领奖,保存用户地址信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/savePrizeAddress" ,method = RequestMethod.POST)
	@ResponseBody
	public String savePrizeAddress(HttpServletRequest request,HttpServletResponse response,Model model,String recordId,String province,
								   String city,String area,String address,String ownName,String ownPhone){
		log.info("进领奖,保存用户地址信息.............recordId:"+recordId);
		PrizeRecordDto prizeRecordDto= null;
		try {
			prizeRecordDto = rpcPrizeService.saveAddressDto(Long.valueOf(recordId),Integer.valueOf(province),Integer.valueOf(city),Integer.valueOf(area),address,ownName,ownPhone);
			if (null!=prizeRecordDto){
				return prizeRecordDto.getStatus();
			}else{
				log.info("领奖,保存用户地址信息失败.............");
				return "501";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.info("领奖,保存用户地址信息失败数据异常.............");
			return "500";
		}
	}

	/**
	 * 抽奖
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getPrize")
	@ResponseBody
	public void getPrize(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		//传入用户ID和来源source=0 wap winPrize
		log.info("进入转盘抽奖.............userId:");
		long userId=0;
		String source=request.getParameter("source");
		if(source.equals("0")){
			//0代表 wap 1代表APP
			User user = getCurrentUser(request);
			userId=user.getUserId();
		}else{
			//app的话需要前台传参过来userid
			userId=Long.valueOf(request.getParameter("userId"));
		}
		log.info("进入转盘抽奖.............userId:"+userId);
		//判断活动时间是否合法
		SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");





		try {
			Date starttime=sDateFormat.parse(skuVO.getStartTime());
			Date endtime=sDateFormat.parse(skuVO.getEndTime());
			Date nowTime=new Date();
			if(nowTime.getTime()>starttime.getTime() && nowTime.getTime()<endtime.getTime()){
				//如果当前时间大于开始时间 小于结束时间，表示活动正常
				PrizeRecordDto d=rpcPrizeService.winPrize(userId, source);
				com.mall.vo.PrizeRecordDto dto=new com.mall.vo.PrizeRecordDto();
				if(null!=d){
					dto.setRecordId(String.valueOf(d.getRecordId()));
					dto.setUserId(d.getUserId());
					dto.setUserPhone(d.getUserPhone());
					dto.setPrizeId(String.valueOf(d.getPrizeId()));
					dto.setPrizeTitle(d.getPrizeTitle());
					dto.setIsPrize(d.getIsPrize());
					dto.setStatus(d.getStatus());
					dto.setProvince(String.valueOf(d.getProvince()));
					dto.setCity(String.valueOf(d.getCity()));
					dto.setArea(String.valueOf(d.getArea()));
					dto.setAderess(d.getAderess());
					dto.setOwnName(d.getOwnName());
					dto.setOwnPhone(d.getOwnPhone());
					dto.setPrizeImg(d.getPrizeImg());
					dto.setCreateDate(d.getCreateDate());
					dto.setType(d.getType());
					dto.setPrizeDate(d.getPrizeDate());
					dto.setGrantPrizeDate(d.getGrantPrizeDate());
					dto.setSource(d.getSource());
				}
				log.info("转盘抽奖数据返回......:"+JsonUtil.serializeBeanToJsonString(dto));
				try {
					response.getWriter().write( request.getParameter("callback") + "(" + JsonUtil.serializeBeanToJsonString(dto)+ ")");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					log.info("转盘抽奖活动未到规定时间......");
					response.getWriter().write( request.getParameter("callback") + "(" + JsonUtil.serializeBeanToJsonString(null)+ ")");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ParseException e) {
			log.info("转盘抽奖活动格式化对比开始结束时间时异常......" + e);
		}
	}

	/**
	 * 获取剩余抽奖次数
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getPrizeSum")
	@ResponseBody
	public void getPrizeSum(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		//传入用户ID和来源source=0 wap winPrize

		//long userId=Long.valueOf(request.getParameter("userId"));
		long userId=0;
		String source=request.getParameter("source");
		if(source.equals("0")){
			//0代表 wap 1代表APP
			User user = getCurrentUser(request);
			userId=user.getUserId();
		}else{
			//app的话需要前台传参过来userid
			String uid=request.getParameter("userId");
			System.out.println("获取的REQUEST 用户ID为：------"+uid);
			userId=Long.valueOf(uid);
		}
		log.info("获取剩余抽奖次数.............userId:"+userId);
		PrizeUserDto prizeUserDto=rpcPrizeService.getPrizeLotteryNum(userId);
		com.mall.vo.PrizeUserDto prizeUserDto1=new com.mall.vo.PrizeUserDto();
		if(null!=prizeUserDto){
			prizeUserDto1.setPrizeUserId(String.valueOf(prizeUserDto.getPrizeUserId()));
			prizeUserDto1.setUserId(prizeUserDto.getUserId());
			prizeUserDto1.setName(prizeUserDto.getName());
			prizeUserDto1.setPhone(prizeUserDto.getPhone());
			prizeUserDto1.setNumber(prizeUserDto.getNumber());
			prizeUserDto1.setStatus(prizeUserDto.getStatus());
			log.info("剩余抽奖次数.............userId:" + prizeUserDto.getNumber());
		}
		try {
			response.getWriter().write( request.getParameter("callback") + "(" + JsonUtil.serializeBeanToJsonString(prizeUserDto1)+ ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *查询中奖top10
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/getPrizeTop")
	@ResponseBody
	public void getPrizeTop(HttpServletRequest request,HttpServletResponse response,Model mode){
		log.info("获取TOP10抽奖信息.............");
		List<PrizeRecordDto> list =rpcPrizeService.getTop10();
		List<com.mall.vo.PrizeRecordDto> dtoList=new ArrayList<com.mall.vo.PrizeRecordDto>();
		if(null!=list && list.size()>0){
			for(PrizeRecordDto d:list){
				com.mall.vo.PrizeRecordDto dto=new com.mall.vo.PrizeRecordDto();
				dto.setRecordId(String.valueOf(d.getRecordId()));
				dto.setUserId(d.getUserId());
				if(null!=d.getUserPhone() && d.getUserPhone()!=""){
					dto.setUserPhone(d.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}else {
					dto.setUserPhone("");
				}
				dto.setPrizeId(String.valueOf(d.getPrizeId()));
				dto.setPrizeTitle(d.getPrizeTitle());
				dto.setIsPrize(d.getIsPrize());
				dto.setStatus(d.getStatus());
				dto.setProvince(String.valueOf(d.getProvince()));
				dto.setCity(String.valueOf(d.getCity()));
				dto.setArea(String.valueOf(d.getArea()));
				dto.setAderess(d.getAderess());
				dto.setOwnName(d.getOwnName());
				if(null!=d.getOwnPhone() && d.getOwnPhone()!=""){
					dto.setOwnPhone(d.getOwnPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}else{
					dto.setOwnPhone("");
				}
				dto.setPrizeImg(d.getPrizeImg());
				dto.setCreateDate(d.getCreateDate());
				dto.setType(d.getType());
				dto.setPrizeDate(d.getPrizeDate());
				dto.setGrantPrizeDate(d.getGrantPrizeDate());
				dto.setSource(d.getSource());
				dtoList.add(dto);
			}
		}
		log.info("获取TOP10抽奖信息............."+JsonUtil.serializeBeanToJsonString(dtoList));
		try {
			response.getWriter().write( request.getParameter("callback") + "(" + JsonUtil.serializeBeanToJsonString(dtoList)+ ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所以奖品信息
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/getPrizes")
	@ResponseBody
	public void getPrizes(HttpServletRequest request,HttpServletResponse response,Model mode){
		log.info("获取所以可用奖品信息.............");
		List<PrizeDto> prizeList=rpcPrizeService.getPrizes();
		List<com.mall.vo.PrizeDto> pList=new ArrayList<com.mall.vo.PrizeDto>();
		if(null!=prizeList && prizeList.size()>0){
			for(PrizeDto d:prizeList){
				com.mall.vo.PrizeDto dto=new com.mall.vo.PrizeDto();
				dto.setPrizeId(String.valueOf(d.getPrizeId()));
				dto.setLevel(String.valueOf(d.getLevel()));
				dto.setTitle(d.getTitle());
				dto.setContent(d.getContent());
				dto.setNumber(0l);
				dto.setStatus("");
				dto.setPrizeImg(d.getPrizeImg());
				dto.setType(d.getType());
				pList.add(dto);
			}

		}
		log.info("获取所以可用奖品信息.............返回数据："+JsonUtil.serializeBeanToJsonString(pList));
		try {
			response.getWriter().write( request.getParameter("callback") + "(" + JsonUtil.serializeBeanToJsonString(pList)+ ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取城市
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_PROVINCE)
	@ResponseBody
	public String getAllProvince( Model model,
								  HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllProvince execute....");

		List<AgentProvince> prov =this.getProvince();
		String pro = JsonUtil.serializeBeanToJsonString(prov);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(pro);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取城市
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_CITY)
	@ResponseBody
	public String getAllCity(Integer proId,Model model,
							 HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllCity execute....");
		if(proId == null) {
			log.error("proId is null");
			throw new BadRequestException("getAllCity fail");
		}

		List<AgentCity> city = this.getCity(proId);
		String ci = JsonUtil.serializeBeanToJsonString(city);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(ci);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 获取乡镇
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_COUNTRY)
	@ResponseBody
	public String getAllCountry(Integer ciId,Model model,
								HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllCountry execute....");

		if(ciId == null) {
			log.error("ciId is null");
			throw new BadRequestException("getAllCountry fail");
		}

		List<AgentCounty> couns =this.getCountry(ciId);
		String cou = JsonUtil.serializeBeanToJsonString(couns);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(cou);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取省份
	 * @return
	 */
	public List<AgentProvince> getProvince() {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentProvince> allProvices=null;
		try {
			allProvices =  baseDataServiceRpc.getAllProvices();
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getAllProvices error ....");
			throw new BusinessException("  call service error");
		}
		return allProvices;
	}

	/**
	 * 获取类目服务
	 * @return
	 */
	private BaseDataServiceRpc getBaseDataServiceRpc() {
		return RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi();
	}


	/**
	 * 获取城市列表
	 * @param proId
	 * @return
	 */
	public List<AgentCity> getCity(Integer proId) {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentCity> citiesByProvinceId=null;
		try {
			citiesByProvinceId = baseDataServiceRpc.getCitiesByProvinceId(proId);
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getCitiesByProvinceId error ....");
			throw new BusinessException("  call service error");
		}
		return citiesByProvinceId;
	}

	/**
	 * \获取城镇列表
	 * @param ciId
	 * @return
	 */
	public List<AgentCounty> getCountry(Integer ciId) {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentCounty> citiesByProvinceId=null;
		try {
			citiesByProvinceId = baseDataServiceRpc.getCountiesByCityId(ciId);
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getCountiesByCityId error ....");
			throw new BusinessException("  call service error");
		}
		return citiesByProvinceId;
	}

}
