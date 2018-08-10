package com.mall.controller.cps;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.constant.CommonConstant;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CpsData;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.utils.ConstProperteUtil;
import com.mall.utils.HttpClientUtil;
import com.mall.utils.JsonUtil;
import com.mall.vo.CpsOrder;
import com.mall.vo.CpsOrderItem;
import com.mall.vo.CpsOrders;

/**
 * 
 * @author zhanglk
 * cps 控制类
 */
@Controller
@RequestMapping("/view/cps")
public class CpsController {
	
	private static final Logger logger = Logger.getLogger(CpsController.class);
	
	@Autowired
	private CustomerOrderService customerOrderService;
	
	/**
	 * 领克特cps着陆，从cps联盟请求过来，记录cookie值
	 * @param request
	 * @param response
	 * @param a_id 网站下联盟会员ID
	 * @param m_id 广告主在LINKTECH网站的ID
	 * @param c_id 广告点击数
	 * @param l_id 广告序号
	 * @param l_type1 广告类型 
	 * @param rd COOKIE生存周期
	 * @param url 广告主banner目标地址
	 * @return
	 */
	@RequestMapping(value="/fromLktInfo",method=RequestMethod.GET)
	public void cpsLktLoadPage(HttpServletRequest request, HttpServletResponse response){
		logger.info("-----------领克特cps 着陆 ---------");
		String a_id  = request.getParameter("a_id");    //我们网站下联盟会员ID。
		String m_id  = request.getParameter("m_id");    //广告主在LINKTECH网站的ID
		String c_id  = request.getParameter("c_id");   //广告点击数。
		String l_id  = request.getParameter("l_id");   //广告序号
		String l_type1 = request.getParameter("l_type1"); //广告类型，
		String rd    = request.getParameter("rd");      //COOKIE生存周期
		String url   = request.getParameter("url");     //广告主banner目标地址 
		logger.info("着陆参数值：a_id："+a_id+",m_id:"+m_id+",c_id:"+c_id+",l_id:"+l_id+",l_type1："+l_type1+",rd:"+rd+",url:"+url);
		
		String merchant_domain = ConstProperteUtil.getInstance().findCpsDomain();
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(!ConstProperteUtil.getInstance().findCpsRdLkt().equals(rd)){
				logger.info("COOKIE生存周期不一致:传如参数RD与服务器RD不一致：传入参数RD："+rd+",服务器参数RD:"+ConstProperteUtil.getInstance().findCpsRdLkt());
		   		out.print("<html><head><script language=\"javascript\"> alert('LPMS:RD Parameter Error'); history.go(-1);</script></head></html>");
			}else if(a_id==null || m_id==null || c_id==null || l_id==null || l_type1==null || url==null){
				logger.info("参数值为空，未写cookie：a_id："+a_id+",m_id:"+m_id+",c_id:"+c_id+",l_id:"+l_id+",l_type1："+l_type1+",rd:"+rd+",url:"+url);
		   		out.print("<html><head><script language=\"javascript\"> alert('LPMS: Parameter Error'); history.go(-1);</script></head></html>");
			}else{//写独立的日志文件 逐行写
				response.setHeader("P3P", "CP=\"NOI DEVa TAIa OUR BUS UNI\""); 
				Cookie ltinfo = new Cookie(CommonConstant.CPS_COOKIE_KEY, ConstProperteUtil.getInstance().findSrcLkt()+"|"+a_id + "|" + c_id + "|" + l_id + "|" + l_type1 + "|");
				logger.info("cookie值："+CommonConstant.CPS_COOKIE_KEY+":"+ConstProperteUtil.getInstance().findSrcLkt()+"|"+a_id + "|" + c_id + "|" + l_id + "|" + l_type1 + "|");
				ltinfo.setPath("/");
				ltinfo.setDomain(merchant_domain);//添加广告主域名
				if(Integer.parseInt(ConstProperteUtil.getInstance().findCpsRdLkt())!=0) ltinfo.setMaxAge(60*60*24*Integer.parseInt(ConstProperteUtil.getInstance().findCpsRdLkt()));
				response.addCookie(ltinfo);
				logger.info("写cookie成功："+CommonConstant.CPS_COOKIE_KEY+":"+ConstProperteUtil.getInstance().findSrcLkt()+",domain:"+merchant_domain+",a_id："+a_id+",m_id:"+m_id+",c_id:"+c_id+",l_id:"+l_id+",l_type1："+l_type1+",rd:"+ConstProperteUtil.getInstance().findCpsRdLkt()+",url:"+url);
			}
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("领克特写cookie错误:" + e,e.fillInStackTrace());
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 亿起发cps着陆，从cps联盟请求过来，记录cookie值
	 * @param request
	 * @param response
	 * @param src 广告主为亿起发指定的标识 来源
	 * @param channel 渠道 wap推广 固定值 wap
	 * @param cid 广告主在亿起发推广的标识 
	 *            广告主在亿起发平台的推广可有多个标识，此参数可用来区分不同的推广方式，此参数需要回传给亿起发，
	 *            方便以后如果增加推广方式则接口很容易扩展
	 * @param wi 亿起发下级网站信息 :此参数的值经过base64编码，广告主无需转码，须原样回传给亿起发，作为亿起发结算的依据
	 * @param url 广告主banner目标地址 为广告主网站的任一url，包括目录页、单品页、专题页。
	 * @return
	 */
	@RequestMapping(value="/fromYqfInfo",method=RequestMethod.GET)
	public void cpsYqfLoadPage(HttpServletRequest request, HttpServletResponse response){
		logger.info("-----------亿起发cps 着陆 ---------");
		String src  = request.getParameter("src");    //来源标示 
		String channel  = request.getParameter("channel");   //渠道
		String cid  = request.getParameter("cid");   //数字 活动id
		String wi = request.getParameter("wi"); //亿起发下级网站信息
		String url   = request.getParameter("url");     //广告主banner目标地址 
		logger.info("着陆参数值：src："+src+",channel:"+channel+",cid:"+cid+",wi:"+wi+",url:"+url);
		
		String merchant_domain = ConstProperteUtil.getInstance().findCpsDomain();
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			if(src==null || channel==null || cid==null || wi==null || url==null || !ConstProperteUtil.getInstance().findCpsSrcYqf().equals(src)){
				logger.info("参数值为空，未写cookie：src："+src+",channel:"+channel+",cid:"+cid+",wi:"+wi+",url："+url);
		   		out.print("<html><head><script language=\"javascript\"> alert('LPMS: Parameter Error'); history.go(-1);</script></head></html>");
			}else{//写独立的日志文件 逐行写
				response.setHeader("P3P", "CP=\"NOI DEVa TAIa OUR BUS UNI\""); 
				Cookie ltinfo = new Cookie(CommonConstant.CPS_COOKIE_KEY, ConstProperteUtil.getInstance().findCpsSrcYqf()+"|"+wi + "|" + cid + "|" + channel + "|");
				logger.info("cookie值："+CommonConstant.CPS_COOKIE_KEY+":"+ConstProperteUtil.getInstance().findCpsSrcYqf()+"|"+wi + "|" + cid + "|" + channel + "|");
				ltinfo.setPath("/");
				ltinfo.setDomain(merchant_domain);//添加广告主域名
				//设置广告有效期
				if(Integer.parseInt(ConstProperteUtil.getInstance().findCpsRdYqf())!=0) ltinfo.setMaxAge(60*60*24*Integer.parseInt(ConstProperteUtil.getInstance().findCpsRdYqf()));
				response.addCookie(ltinfo);
				logger.info("写cookie成功："+CommonConstant.CPS_COOKIE_KEY+":"+ConstProperteUtil.getInstance().findCpsSrcYqf()+",domain:"+merchant_domain+",channel："+channel+",cid:"+cid+",wi:"+wi+",url:"+url);
			}
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("亿起发写cookie错误:" + e,e.fillInStackTrace());
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * cps联盟实时数据传送
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toOrderInfo",method=RequestMethod.POST)
	public void toOrderInfo(HttpServletRequest request, HttpServletResponse response){
		logger.info("-----------回传开始 ---------");
		String cpsCookieValue  = request.getParameter("cpsCookieValue");//cps cookie 值
		String cpsOrdersInfo  = request.getParameter("cpsOrdersInfo"); //订单数据信息
		logger.info("回传参数值：cpsCookieValue："+cpsCookieValue+",cpsOrdersInfo:"+cpsOrdersInfo);
		
		//PrintWriter out = null;
		try {
			//out = response.getWriter();
			
			String returnStr ="";			
			//领克特传送
			if(cpsCookieValue.startsWith(ConstProperteUtil.getInstance().findSrcLkt())){
				logger.info("----------领克特回传开始--------");
				//json 转换 cpsorders
				CpsOrders cpsOrders = JsonUtil.createBeanfromJsonString(CpsOrders.class,cpsOrdersInfo);
				List<CpsOrder> orders = cpsOrders.getOrders();
				List<CpsOrderItem> orderItems = null;
				logger.info("领克特回传订单数："+orders.size());
				
				//returnStr = "<script src='" + ConstProperteUtil.getInstance().findCpsHcurlLkt() + "?a_id=" + cpsCookieValue.substring(ConstProperteUtil.getInstance().findSrcLkt().length()+1);//拼接cookie值
				//returnStr = ConstProperteUtil.getInstance().findCpsHcurlLkt() + "?a_id=" + cpsCookieValue.substring(ConstProperteUtil.getInstance().findSrcLkt().length()+1);//拼接cookie值
				returnStr = ConstProperteUtil.getInstance().findCpsHcurlLkt() + "?a_id=" + cpsCookieValue.split("\\|")[1];//拼接cookie值 会员id
				returnStr = returnStr + "&m_id=" + ConstProperteUtil.getInstance().findCpsMerchantIdLkt() + "&mbr_id=" + ConstProperteUtil.getInstance().findCpsMbrIdLkt();//广告主在linktech的id 购买人账号(手机号)
				int index=0;//记录传送订单数索引
				for(CpsOrder order:orders){
					String returnStrT = "";	
					String arr[] = {"","","", "",""};//定义数组 拼接参数
					index = index +1;
					
					arr[0] = order.getOrderNo();
					orderItems = order.getOrderItems();
					for(int i = 0; i < orderItems.size(); i++){
						if(i == 0){
							arr[1] = orderItems.get(i).getCategory(); //分类编号
							arr[2] = orderItems.get(i).getPrice();    //商品价格
							arr[3] = "1";   //商品数量按1
							arr[4] = orderItems.get(i).getProductNo();//商品编号
						}else{
							arr[1] = arr[1] + "||" + orderItems.get(i).getCategory();
							arr[2] = arr[2] + "||" + orderItems.get(i).getPrice();
							arr[3] = arr[3] + "||" + "1";   //商品数量 按1
							arr[4] = arr[4] + "||" + orderItems.get(i).getProductNo();//商品编号
						}					
					}
					
					//完成一个订单拼接
					returnStrT = returnStr + "&o_cd=" + arr[0];
					returnStrT = returnStrT + "&p_cd=" + arr[4];
					returnStrT = returnStrT + "&price=" + arr[2];
					returnStrT = returnStrT + "&it_cnt=" + arr[3];
					returnStrT = returnStrT + "&c_cd=xsj";// + arr[1];
					//returnStrT = returnStrT + "'></script>";
					
					logger.info("领克特回传订单："+index + ",拼接串" + returnStrT);
					String strCon = HttpClientUtil.getHttp(returnStrT);
					//out.write(returnStrT);
					logger.info("领克特回传订单："+index + "完成，接收接口返回值："+strCon);
				}
			}else if(cpsCookieValue.startsWith(ConstProperteUtil.getInstance().findCpsSrcYqf())){//亿起发
				logger.info("----------亿起发回传开始--------");
				//json 转换 cpsorders
				CpsOrders cpsOrders = JsonUtil.createBeanfromJsonString(CpsOrders.class,cpsOrdersInfo);
				List<CpsOrder> orders = cpsOrders.getOrders();
				List<CpsOrderItem> orderItems = null;
				logger.info("亿起发回传订单数："+orders.size());
				//亿起发 ta 订单数量固定为1  
				//dt移动互联网手机活动，固定值为：m
				//returnStr = "<script type='text/javascript' src='" + ConstProperteUtil.getInstance().findCpsHcurlYqf() + "?ta=1&dt=m&os=&ps=&pw=&far=&fav=&fac=";
				returnStr = ConstProperteUtil.getInstance().findCpsHcurlYqf() + "?dt=m&os=&ps=&pw=&far=&fav=&fac=";
				int index=0;//记录传送订单数索引
				java.text.DecimalFormat   df =new java.text.DecimalFormat("#.00"); //保留两位小数 
				for(CpsOrder order:orders){
					String returnStrT = "";	
					String arr[] = {"","","","","","","",""};//定义数组 拼接参数
					index = index +1;
					arr[0] = order.getCampaignId();//活动id cid
					arr[1] = order.getFeedback();//wi
					arr[2] = order.getOrderNo();//订单编号
					arr[4] = order.getOrderTime();//下单时间
					orderItems = order.getOrderItems();
					for(int i = 0; i < orderItems.size(); i++){
						if(i == 0){
							arr[3] = df.format(Double.parseDouble(orderItems.get(i).getPrice()));//实际支付金额
							arr[5] = orderItems.get(i).getName(); //商品名称
							arr[6] = orderItems.get(i).getProductNo(); //商品编号
							arr[7] = orderItems.get(i).getAmount(); //商品数量
							
						}else{
							arr[3] = arr[3] + "%7C" + df.format(Double.parseDouble(orderItems.get(i).getPrice()));//实际支付金额 商品支付金额 小计-优惠金额
							arr[5] = arr[5] + "|" + orderItems.get(i).getName(); //商品名称
							arr[6] = arr[6] + "%7C" + orderItems.get(i).getProductNo(); //商品编号
							arr[7] = arr[7] + "%7C" + orderItems.get(i).getAmount(); //商品数量
						}					
					}
					
					//完成一个订单拼接
					returnStrT = returnStr + "&cid=" + arr[0];
					returnStrT = returnStrT + "&wi=" + arr[1];
					returnStrT = returnStrT + "&on=" + arr[2];
					returnStrT = returnStrT + "&ta=" + arr[7];
					returnStrT = returnStrT + "&pp=" + arr[3];
					returnStrT = returnStrT + "&sd=" + URLEncoder.encode(arr[4],"utf-8");
					returnStrT = returnStrT + "&pna=" + URLEncoder.encode(arr[5],"utf-8");
					returnStrT = returnStrT + "&pn=" + arr[6];
					returnStrT = returnStrT + "&encoding=utf-8";
					//returnStrT = returnStrT + "'></script>";
					
					logger.info("亿起发回传订单："+index + ",拼接串" + returnStrT);
					String strCon = HttpClientUtil.getHttp(returnStrT);
					//out.write(returnStrT);
					if("0".equals(strCon)){
			        	logger.info("回传订单："+index + "成功！");
			        }else if("1".equals(strCon)){
			        	logger.info("回传订单："+index +"缺少必要参数！");
			        }else if("2".equals(strCon)){
			        	logger.info("回传订单："+index +"参数格式错误！");
			        }
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("cps回传错误:" + e,e.fillInStackTrace());
		}
	}
	
	/**
	 * 领克特查询数据接口 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/findLktCpsList",method=RequestMethod.GET)
	public void findLktCpsList(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String LTID  = ConstProperteUtil.getInstance().findSrcLkt();//request.getParameter("LTID");         //来源标示 
		String yyyymmdd  = request.getParameter("yyyymmdd"); //查询日期
		String key  = request.getParameter("key");   //查询key
		
		logger.info("查询参数:LTID:" + LTID+",yyyymmdd:"+yyyymmdd+",key:"+key);
		
		//核对查询参数
		if(yyyymmdd==null || key == null){
			logger.info("查询参数为空，请核对查询参数。");
			return;
		}
		//核对查询key值
		if(!ConstProperteUtil.getInstance().findCpsFindKeyLkt().equals(key)){
			logger.info("查询key不正确，正确key值为："+ConstProperteUtil.getInstance().findCpsFindKeyLkt());
			return;
		}
		
		//获取查询日期并格式化
		SimpleDateFormat sdfFind=new SimpleDateFormat("yyyyMMdd");
		Date date1 = sdfFind.parse(yyyymmdd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		cal.add(Calendar.DATE, 1);
		String findDate = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
		//当前日期
		Date curTime=new Date(); 
		SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");  
		String curDate=sdfDate.format(curTime); 
		//核对查询日期
		if(!curDate.equals(findDate)){
			logger.info("当前查询日期为："+ yyyymmdd +",当日只能查询上一日的数据。");
			return;
		}
		
		logger.info("每天允许查询时间段为："+ ConstProperteUtil.getInstance().findCpsStartTimeLkt() +"-" + ConstProperteUtil.getInstance().findCpsEndTimeLkt());
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date startTime = sdf.parse(curDate + " " + ConstProperteUtil.getInstance().findCpsStartTimeLkt());//允许查询开始时间
		Date endTime = sdf.parse(curDate + " " + ConstProperteUtil.getInstance().findCpsEndTimeLkt());    //允许查询结束时间
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(curTime.getTime() >= startTime.getTime() && curTime.getTime()<=endTime.getTime()){//查询 key值相同
				Map<String,String> map = new HashMap<String,String>();
				 map.put("createTime",yyyymmdd);
		    	 map.put("orderSource",LTID);
		    	 
		    	String CODE = "2";//cps 为2
		    	SimpleDateFormat sdf1=new SimpleDateFormat("HHmmss"); 
		    	
				List<CpsData> cpsData = customerOrderService.getCpsOrderDTOs(map);
				if(cpsData != null && cpsData.size()>0){
					int index = 0;
					for(CpsData cpObj:cpsData){
						if(index > 0){
							sb.append("\n");
						}
						sb.append(CODE+ "\t");	
						sb.append(sdf1.format(cpObj.getCreateTime()) + "\t");//下单时间		
						sb.append(cpObj.getCpsMemId() + "\t");//领克特的联盟会员id
						sb.append(cpObj.getOrderId() + "\t"); //订单号	
						sb.append(cpObj.getPid() + "\t");  //商品编号
						sb.append("111111" + "\t");//用户id
						sb.append("1" + "\t");//数量按照1返回	
			     	    sb.append(cpObj.getSubtotalPirce().subtract(cpObj.getDiscountPrice()).doubleValue() + "\t");
						//sb.append(cpObj.getCategoryId() + "\t");
			     	   sb.append("xsj" + "\t");
						sb.append("100" + "\t");
						index++;
					}
				}
			}else{
				sb.append("查询参数错误或者时间段不正确！每天允许查询时间段为："+ ConstProperteUtil.getInstance().findCpsStartTimeLkt() +"-" + ConstProperteUtil.getInstance().findCpsEndTimeLkt());
				logger.info("查询参数错误或者时间段不正确:LTID:" + LTID+",yyyymmdd:"+yyyymmdd+",key:"+key);
			}
			logger.info("查询结果:" + sb.toString());
			response.setContentType("text/html;charset=utf-8");
			out.write(sb.toString());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("领克特查询接口错误:" + e,e.fillInStackTrace());
		}finally{
			if(out != null){
				//out.flush();
				out.close();
			}
		}
		
	}
	
	/**
	 * 亿起发查询数据接口 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/findYqfCpsList",method=RequestMethod.GET)
	public void findYqfCpsList(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String cid  = request.getParameter("cid");         //来源标示 
		String yyyymmdd  = request.getParameter("yyyymmdd"); //查询日期
		String key  = request.getParameter("key");   //查询key
		
		logger.info("查询参数:cid:" + cid+",yyyymmdd:"+yyyymmdd+",key:"+key);
		//核对查询参数
		if(cid == null || yyyymmdd==null || key == null){
			logger.info("查询参数为空，请核对查询参数。");
			return;
		}		
		//核对查询key值
		if(!ConstProperteUtil.getInstance().findCpsFindKeyYqf().equals(key)){
			logger.info("查询key不正确，正确key值为："+ConstProperteUtil.getInstance().findCpsFindKeyYqf());
			return;
		}
		
		//获取查询日期并格式化
		SimpleDateFormat sdfFind=new SimpleDateFormat("yyyyMMdd");
		Date date1 = sdfFind.parse(yyyymmdd);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		cal.add(Calendar.DATE, 1);
		String findDate = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
		//当前日期
		Date curTime=new Date(); 
		SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");  
		String curDate=sdfDate.format(curTime); 
		//核对查询日期
		if(!curDate.equals(findDate)){
			logger.info("当前查询日期为："+ yyyymmdd +"当日只能查询上一日的数据。");
			return;
		}
		
		String LTID  = ConstProperteUtil.getInstance().findCpsSrcYqf(); //来源标示 
		java.text.DecimalFormat   df =new java.text.DecimalFormat("#.00"); //保留两位小数 
		String startTimeStr = ConstProperteUtil.getInstance().findCpsStartTimeYqf();//查询时间段 开始时间
		String endTimeStr = ConstProperteUtil.getInstance().findCpsEndTimeYqf();//查询时间段 结束时间
		
		logger.info("每天允许查询时间段为："+ startTimeStr +"-" + endTimeStr);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date startTime = sdf.parse(curDate + " " + startTimeStr);//允许查询开始时间
		Date endTime = sdf.parse(curDate + " " + endTimeStr);    //允许查询结束时间
		PrintWriter out = null;
		try {
			out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			String cpsOrdersStr = "";
			if(curTime.getTime() >= startTime.getTime() && curTime.getTime()<=endTime.getTime()
				&& cid!=null && yyyymmdd !=null){//查询 key值相同
				Map<String,String> map = new HashMap<String,String>();
				 map.put("createTime",yyyymmdd);//查询日期
		    	 map.put("orderSource",LTID);//来源
		    	 map.put("lType",cid);
		    	
		    	List<OrderDTO> orderDTOs = customerOrderService.getCpsOrderDTO(map);
		    	//循环取出cps需要的订单信息数据 zhanglk 2015-11-04
				if(orderDTOs != null && orderDTOs.size() > 0){
					logger.info("订单提交成功，拼接cps订单数据信息开始，共计："+orderDTOs.size()+"个订单。");
					List<CpsOrder> cpsOrderList = new ArrayList<CpsOrder>();//订单列表
					List<CpsOrderItem> cpsOrderItems = null;//订单明细列表
					CpsOrder cpsOrder = null;//cps 订单
					CpsOrderItem cpsOrderItem = null;//订单明细
					CpsOrders cpsOrders = new CpsOrders();//cps 所有订单
					SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					for(int i=0; i<orderDTOs.size(); i++){
						cpsOrderItems = new ArrayList<CpsOrderItem>();
						cpsOrder = new CpsOrder();
						cpsOrder.setCampaignId(orderDTOs.get(i).getCpsOrderDTO().getlType());
						cpsOrder.setFare("");//该字段未取实际值，赋予空值
						cpsOrder.setFavorable("");//该字段未取实际值，赋予空值
						cpsOrder.setFavorableCode("");//该字段未取实际值，赋予空值
						cpsOrder.setFeedback(orderDTOs.get(i).getCpsOrderDTO().getCpsMemId());//亿起发对应wi
						cpsOrder.setOrderNo(orderDTOs.get(i).getId()+"");//订单号
						cpsOrder.setOrderstatus(orderDTOs.get(i).getStatus()+"");//订单状态
						cpsOrder.setOrderTime(time.format(orderDTOs.get(i).getCreateTime()));//下单时间
						cpsOrder.setPaymentStatus(orderDTOs.get(i).getStatus()+"");//支付状态,与订单状态公用
						cpsOrder.setPaymentType("");//支付方式 空值
						cpsOrder.setUpdateTime(time.format(orderDTOs.get(i).getLastEditTime()));//更新时间
						cpsOrder.setTotalPrice(orderDTOs.get(i).getPrice()+"");//订单应付金额
						
						for(OrderItemDTO item:orderDTOs.get(i).getOrderItemDTOs()){
							cpsOrderItem = new CpsOrderItem();
							cpsOrderItem.setAmount(item.getSkuQty()+"");//商品数量 单价按折扣后总金额算
							cpsOrderItem.setCategory(item.getCategoryId());//商品分类
							cpsOrderItem.setCommissionType("basic");//佣金类型 统一为basic 固定值
							cpsOrderItem.setProductNo(item.getPid()+"");
							cpsOrderItem.setName(item.getpName());//商品名称
							cpsOrderItem.setOrderNo(item.getOrderId()+"");//订单ID
							//计算商品 价格 一种商品金额小计 减去 优惠金额 除以数量 为商品单价
							cpsOrderItem.setPrice(item.getPrice().subtract(item.getDiscountPrice().divide(new BigDecimal(item.getSkuQty()),2,BigDecimal.ROUND_HALF_UP)).doubleValue()+"");
							logger.info("**************订单号"+orderDTOs.get(i).getId()+"的item.getPrice()="+item.getPrice()+";item.getDiscountPrice()="+item.getDiscountPrice()+";item.getSkuQty()="+item.getSkuQty()+"*****************");
							if(!item.getSubtotalPirce().equals(new BigDecimal(0))){
								logger.info("==================非赠品订单"+orderDTOs.get(i).getId()+"====================");
								cpsOrderItems.add(cpsOrderItem);
							}
						}
						cpsOrder.setOrderItems(cpsOrderItems);
						
						cpsOrderList.add(cpsOrder);
					}
					cpsOrders.setOrders(cpsOrderList);
					 cpsOrdersStr = JsonUtil.serializeBeanToJsonString(cpsOrders);
					logger.info("拼接cps订单数据信息完成："+cpsOrdersStr);
				}
			}else{
				sb.append("查询参数错误或者时间段不正确！每天允许查询时间段为："+ ConstProperteUtil.getInstance().findCpsStartTimeYqf() +"-" + ConstProperteUtil.getInstance().findCpsEndTimeYqf());
				cpsOrdersStr = sb.toString();
				logger.info("查询参数错误或者时间段不正确:LTID:" + LTID+",yyyymmdd:"+yyyymmdd+",key:"+key+",cid:"+cid);
			}
			logger.info("查询结果:" + cpsOrdersStr);
			response.setContentType("text/html;charset=utf-8");
			out.write(cpsOrdersStr);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("亿起发查询接口错误:" + e,e.fillInStackTrace());
		}finally{
			if(out != null){
				//out.flush();
				out.close();
			}
		}
		
	}
	
	/**
	 * cps联盟实时数据传送
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/testHcurl",method=RequestMethod.GET)
	public void testHcurl(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			String lt_log ="";
			
			/*lt_log = "http://service.linktech.cn/purchase_cps.php?a_id=a_id";
			  lt_log = lt_log + "&m_id=merchant_id" + "&mbr_id=lt_user_id";
			  lt_log = lt_log + "&o_cd=lt_o_cd" + "&p_cd=lt_p_cd" ;
			  lt_log = lt_log + "&price=lt_price";
			  lt_log = lt_log + "&it_cnt=lt_it_cnt";
			  lt_log = lt_log + "&c_cd=lt_c_cd" ;
			
			out.write(lt_log);*/
			out.write("http://o.yiqifa.com/servlet/handleCpsIn?ta=1&dt=m&os=&ps=&pw=&far=&fav=&fac=&cid=101&wi=67yundl&on=504686356571161210&pp=98.00&sd=2015-11-07+10%3A32%3A45&pna=%E5%BE%B7%E5%9B%BD+%E6%96%BD%E5%B7%B4%E5%A9%B4%E5%84%BF%E6%B3%A1%E6%B3%A1%E6%B5%B4%E9%9C%B2+200ml&pn=2356641566954198&encoding=utf-8");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("cps回传错误:" + e,e.fillInStackTrace());
		}finally{
			if(out != null){
				//out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String cpsCookieValue = "lind|A100217250|10|5|wap|";
		//System.out.println(cpsCookieValue.split("\\|")[1]);
		/*String aa =  CommonConstant.CPS_SRC_LKT+"|123|3456|sdsd|23rwe|";
		String arr[] = aa.split("\\|");
		System.out.println(aa.startsWith(CommonConstant.CPS_SRC_LKT+"dd"));
		System.out.println(arr.length);
		System.out.println(aa.substring(CommonConstant.CPS_SRC_LKT.length()+1));
		
		List<CpsOrder> aaa = new ArrayList<CpsOrder>();
		
		List<CpsOrderItem> bbb = new ArrayList<CpsOrderItem>();
		CpsOrderItem b1 = new CpsOrderItem();
		b1.setAmount("11");
		b1.setCategory("test1");
		bbb.add(b1);
		b1 = new CpsOrderItem();
		b1.setAmount("22");
		b1.setCategory("test2");
		bbb.add(b1);
		
		CpsOrder a = new CpsOrder();
		a.setCampaignId("101");
		a.setFare("ddd");
		a.setOrderItems(bbb);
		
		aaa.add(a);
		
		CpsOrders c = new CpsOrders();
		c.setOrders(aaa);*/
		
	//	System.out.println(JSONArray.fromObject(a).toString());
		/*System.out.println(JSONObject.fromObject(c).toString());
		System.out.println(JsonUtil.serializeBeanToJsonString(c));*/
	/*	BigDecimal b11 = new BigDecimal("100");
        BigDecimal b2 = new BigDecimal("30");
		BigDecimal b3 = b11.divide(b2,0,BigDecimal.ROUND_HALF_EVEN);
        System.out.println(b3);
        
        BigDecimal b4 = new BigDecimal("1000.89");
        BigDecimal b5 = new BigDecimal("800.78");
        System.out.println(b4.subtract(b5).doubleValue());*/
	    /*String cc2 = "{\"orders\":[{\"orderNo\":\"504668801587670924\",\"updateTime\":\"2015-11-05 09:46:55\",\"paymentType\":\"\",\"orderItems\":[{\"name\":\"意大利ORO DI SPELLO 超级保温霜 适合极干部位 150ml\",\"category\":\"69010000000\",\"price\":\"250.0\",\"orderNo\":\"504668801587670924\",\"amount\":\"1\",\"commissionType\":\"basic\"}],\"campaignId\":\"wap\",\"fare\":\"\",\"favorable\":\"\",\"favorableCode\":\"\",\"feedback\":\"1221231\",\"orderTime\":\"2015-11-05 09:46:55\",\"orderstatus\":\"1\",\"paymentStatus\":\"1\"}]}";
		CpsOrders cpsOrders = JsonUtil.createBeanfromJsonString(CpsOrders.class,cc2);
		System.out.println("dddd");*/
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
		java.util.Date date=new java.util.Date();  
		//String str=sdf.format(date); 
		Date date1;
		try {
			date1 = sdf.parse("20151109");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			cal.add(Calendar.DATE, 1);
			
			System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
