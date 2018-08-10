package com.mall.controller.order;


import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.order.api.rpc.ThirdPartyOrderService;
import com.mall.customer.order.dto.ThirdPartyMessage;
import com.mall.customer.order.dto.ThirdParyDTO;
import com.mall.customer.order.dto.ThirdParyItemDTO;
import com.mall.customer.order.dto.ThirdTypeEnum;
import com.mall.customer.order.po.Dictionarys;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.customer.api.CustomerOrderApi;
import com.mall.dealer.product.dto.B2cProductOrderShowDTO;
import com.mall.dto.ThirdPartyImportDto;
import com.mall.utils.ValidateTool;

/**
 * Created by liyang-macbook on 16/1/13.
 */
@Controller
@RequestMapping(value="/thirdPartyImport")
public class ThirdPartyImportController {

    private static final Log logger = LogFactory.getLogger(ThirdPartyImportController.class);

    @Resource
    private ThirdPartyOrderService thirdPartyOrderService;
    
    @Resource
    private DealerProductSkuService dealerProductSkuService;
    
    @Resource
    private CustomerOrderApi customerOrderApi;

   // public static final String userId = "246045";

    public static Map<String,Integer>  typemap = new HashMap<String,Integer>();

    static{
        EnumSet<ThirdTypeEnum> _set = ThirdTypeEnum.getList();
        if(_set!=null && !_set.isEmpty()){
            for(ThirdTypeEnum e:_set){
                typemap.put(e.getStr(),e.getNum());
            }
        }
    }

    @RequestMapping(value = "/toImport")
    public String toImport(HttpServletRequest request){
        request.setAttribute("message",request.getParameter("message"));
        return "/thirdPartyImport/toImport";
    }
    
    
    
    
   
    
    /**
     * 
     * 第三方订单导入
     * @param file 文件
     * @param request
     * @param model
     * @return
     * @author YANHONGYUAN 2016年8月12日17:37:23
     */
    @RequestMapping(value = "/importFile")
    public String importFile(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, Model model){
    	//获取核心线程数量
    	//Runtime.getRuntime().availableProcessors();
    	//总耗时
    	long beginTimeAll = System.currentTimeMillis();
    	long endTimeAll = System.currentTimeMillis();
    	//开始与结束时间标记
    	long beginTime = 0L;
    	long endTime = 0L;
    	//工作薄
    	Workbook wb = null;
    	//正式数据开始行,=2表示从第二行开始
    	int dataBeginRowNum = 2;
    	//允许一次性导入最大行
    	int allowMaxRow = 1000;
    	//
        try {
        	//第1步:读取文件并校验文件属性----------------------------------------------------
        	//初始化开始时间
        	beginTime = System.currentTimeMillis();
        	String fileName = file.getOriginalFilename();
             
        	if(StringUtils.isBlank(fileName) || !(fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
        		ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件格式不正确");
        		model.addAttribute("message", JSONArray.fromObject(m).toString());
        		return "redirect:/thirdPartyImport/toImport";
        	}
        	//初始化结束时间
        	endTime = System.currentTimeMillis();
            logger.info(">>>.读取文件并校验文件属性 耗时:"+ (endTime - beginTime) +":ms");
    		
            //第2步:读取文件并校验行属性----------------------------------------------------
            if (fileName.endsWith("xls") ) {
                wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream())); 
            } else if(fileName.endsWith("xlsx") ){
                wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
            } else{
                ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件不能解析");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }
            
            endTime = System.currentTimeMillis();
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.验证文件格式 耗时:"+ (endTime - beginTime) +":ms");
    	 } catch (Exception e) {
             logger.error("解析execl错误,文件格式错误或文件类型异常!",e);
             ThirdPartyMessage m = new ThirdPartyMessage("fail","","解析execl错误,文件格式错误或文件类型异常!");
             model.addAttribute("message", JSONArray.fromObject(m).toString());
             return "redirect:/thirdPartyImport/toImport";
    	 }
        
        //记录存在任何问题的订单号
        final Map<String,String> map_orderid_errernum = new HashMap<String, String>();
        //错误消息  ,初始化集合50,每次扩容10
        final Vector<ThirdPartyMessage> errorMassageV = new Vector<ThirdPartyMessage>(50,10);
        int errormassageV_max_size = 20;
        //记录第三方订单号,去重复+总金额是否一致
        Map<String,Double> map_orderid_obj = new HashMap<String, Double>();
        
        //<定单号ID,<skuId,数量>>orderId_<skuId,String[]>
        Map<String,Map<Long,Integer>> map_orderidnew_skus = new HashMap<String, Map<Long,Integer>>();
        //<订单号,对应集合下标>
        Map<String,Integer> map_orderidnew_index = new HashMap<String, Integer>();
        //订单集合
        List<ThirdParyDTO> list_orders = new ArrayList<ThirdParyDTO>();
        //新订单与旧的订单的关系:<新订单号,导入订单号>
        Map<String,String> map_orderidnew_orderid = new HashMap<String, String>();
        
        //<订单号,通过sku重新计算后 的总价格>
        Map<String,BigDecimal> map_orderid_newsumskuprice = new HashMap<String, BigDecimal>();
        //<新的订单号,通过sku重新计算后 的总价格>
        Map<String,BigDecimal> map_orderidnew_newsumskuprice = new HashMap<String, BigDecimal>();
        //标记订单是否拆分:<导入订单id,对应的才分订单idmap对象>
        Map<String,Map<String,Integer>> map_orderid_orderidnewmap = new HashMap<String, Map<String,Integer>>();
        
        //<skuId,供应商ID>
        Map<Long,Short> map_skuid_supplytype = new HashMap<Long, Short>();
        //<skuId,供应商对象>
        Map<Long,Map<String,Object>> map_skuid_supplyObj = new HashMap<Long, Map<String,Object>>();
        //供应商ID名称,供应商名称对应名称
        String supplyIdName = "supplierid";
        String supplyNameName = "suppliername";
        //
        String orderId = null;
        String skuIdStr = null;
        Long skuId = null;
        //行数据对象
        List<ThirdPartyImportDto> list_rows = new ArrayList<ThirdPartyImportDto>();
        //skuidList
        List<Long> list_skuid = new ArrayList<Long>();
        //<skuid,sku对象>
        Map<Long, B2cProductOrderShowDTO> map_skuid_skuObj = null;
        
        try	{
        	beginTime = System.currentTimeMillis();
        	 
    		//获取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取最大行数
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            //有效汗!
            int nowRow = (numberOfRows-(dataBeginRowNum-1));
            //有效数据行数不能大于allowMaxRow
            if(allowMaxRow < nowRow){
            	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,导入订单上限为"+allowMaxRow+"行,当前有效数据已达到:"+nowRow+"行!");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }

            //读取数据放入
            Row row = null;
            int rowNum = 0;
            
            //读取数据,并进行基本格式校验,基本
            //开始位置i=有效行-1,结束位置<最大行数
            for (int i = (dataBeginRowNum-1); i < numberOfRows; i++) {
            	row = sheet.getRow(i);
            	if(row == null){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":不可以为空!"));
            		continue;
            	}
            	rowNum = (1+i);
            	//订单ID
            	orderId = (null == row.getCell(0)?null:row.getCell(0).toString());
            	//skuIdStr
            	skuIdStr =   (null == row.getCell(1)?null:row.getCell(1).toString());
            	//orderId
            	if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderId.trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",订单号不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}else{
            		//处理:第三方订单号替换双引号，单引号和空格
                	orderId = orderId.replaceAll("\"", "").replaceAll("\'", "").trim();
            	}
            	//sku
            	if(StringUtils.isEmpty(skuIdStr) || StringUtils.isEmpty(skuIdStr.trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",sku不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}else{
                	skuIdStr = skuIdStr.replaceAll("\"", "").replaceAll("\'", "").trim();
                	//转换类型
                	skuId = Long.valueOf(skuIdStr);
            	}
            	
            	//0 第三方订单ID>
            	//1 skuid>2 sku数量> 3 sku价格（N）>
            	//4 订单实付总价> 5第三方来源>
            	//6 收货人> 7 联系电话> 8 收货地址> 9 备注（N）> 10 运费（N）> 11 是否开发票> 12 发票抬头> 13发票内容
            	ThirdPartyImportDto importDto = new ThirdPartyImportDto(
            			orderId,
            			skuId, 
            			(null == row.getCell(2)?null:row.getCell(2).toString().trim()),
            			(null == row.getCell(3)?null:row.getCell(3).toString().trim()),
            			(null == row.getCell(4)?null:row.getCell(4).toString().trim()),
            			(null == row.getCell(5)?null:row.getCell(5).toString().trim()),
            			(null == row.getCell(6)?null:row.getCell(6).toString().trim()),
            			(null == row.getCell(7)?null:row.getCell(7).toString().trim()),
            			(null == row.getCell(8)?null:row.getCell(8).toString().trim()),
            			(null == row.getCell(9)?null:row.getCell(9).toString().trim()),
            			(null == row.getCell(10)?null:row.getCell(10).toString().trim()),
            			(null == row.getCell(11)?null:row.getCell(11).toString().trim()),
            			(null == row.getCell(12)?null:row.getCell(12).toString().trim()),
            			(null == row.getCell(13)?null:row.getCell(13).toString().trim())
            			);
            	//-------------------------------------其他属性验证
            	//sku数量
            	if(StringUtils.isEmpty(importDto.getSkuNum()) ||
            			!Pattern.compile("^([1-9]{1}\\d*)(\\.0{0,})?$").matcher(importDto.getSkuNum()).matches()){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",sku数量必须为正整数!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//订单总价
            	if(StringUtils.isEmpty(importDto.getPrice()) ||
            			!Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,})?$").matcher(importDto.getPrice()).matches()){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",订单总价必须为金额类型!"));
            		map_orderid_errernum.put(orderId, null);
            	}else if(0>=Double.valueOf(importDto.getPrice())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",订单总价必须为>0.00 !"));
            		map_orderid_errernum.put(orderId, null);
            	}else if(map_orderid_obj.containsKey(orderId) 
            			&& !map_orderid_obj.get(orderId).equals(Double.valueOf(importDto.getPrice()))){
            		//验证总金额是否一致
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",所有订单总额必须一致 !"));
            		map_orderid_errernum.put(orderId, null);
            	}else{
            		//记录第三方导入的订单号------------------------------------------------------------------
            		map_orderid_obj.put(orderId, Double.valueOf(importDto.getPrice()));
            	}
            	
            	
            	//第三方订单来源
            	if(StringUtils.isEmpty(importDto.getOrderTypeName()) || StringUtils.isEmpty(importDto.getOrderTypeName().trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",第三方来源不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//电话
            	if(StringUtils.isEmpty(importDto.getPeceive_mobile_phone()) || StringUtils.isEmpty(importDto.getPeceive_mobile_phone().trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",电话不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//收货人
            	if(StringUtils.isEmpty(importDto.getPeceive_name()) || StringUtils.isEmpty(importDto.getPeceive_name().trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",收货人不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//收货地址
            	if(StringUtils.isEmpty(importDto.getPeceive_address()) || StringUtils.isEmpty(importDto.getPeceive_address().trim())){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",收货地址不可以为空!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//运费
            	if(!StringUtils.isEmpty(importDto.getTransfer_price()) &&
            			!Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,})?$").matcher(importDto.getTransfer_price()).matches()){
            		errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",如果设置运费值,则运费必须为金额类型!"));
            		map_orderid_errernum.put(orderId, null);
            	}
            	
            	//如果当前订单出现任何错误则忽略当前定记录
            	if(map_orderid_errernum.containsKey(orderId)){
            		continue;
            	}
            	
            	//记录skutype,利用map去重复,同时放入到list_skuid
            	if(!map_skuid_supplytype.containsKey(skuId)){
            		map_skuid_supplytype.put(skuId, null);
            		list_skuid.add(skuId);
            	}
            	
            	//放入集合中
            	list_rows.add(importDto);
            }
            endTime = System.currentTimeMillis();
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.读取excel 耗时:"+ (endTime - beginTime) +":ms");
            
		    //错误拦截--------------------------------------------------begin
            if(0<map_orderid_errernum.size()){
            	StringBuffer errerMassage = new StringBuffer();
            	errerMassage.append("<div>");
         	    errerMassage.append("表格数据基本格式存在错误,不允许导入!  ;<br/>");
            	errerMassage.append("<ul>");
            	Iterator<ThirdPartyMessage> iterator_emv =  errorMassageV.iterator();
            	int index = 0;
            	while (iterator_emv.hasNext() && index<errormassageV_max_size) {
            		errerMassage.append("<li>").append(iterator_emv.next().getMsg()).append("</li>");
            		index ++;
				}
            	errerMassage.append("</ul>");
            	errerMassage.append("</div>");
            	 
            	ThirdPartyMessage m = new ThirdPartyMessage("fail","",errerMassage.toString());
                model.addAttribute("message", JSONArray.fromObject(m).toString());
        	    return "redirect:/thirdPartyImport/toImport";
            }
    	    //错误拦截--------------------------------------------------end
            
            
        } catch (Exception e) {
            logger.error("导入失败,解析execl错误,基本数据格式验证出现异常!",e);
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,解析execl错误,基本数据格式验证出现异常!");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";
   	 	}
        
        //
        
        

       try{     
    	   //查询skuid对应的供应商信息--------------------------------------------------------
    	   beginTime = System.currentTimeMillis();
    	   Iterator<Long> skuids = map_skuid_supplytype.keySet().iterator();
    	   while (skuids.hasNext()) {
    		   //查询对象,并设置值
			   skuId = skuids.next();
			   Short supplyType = dealerProductSkuService.getB2cProductSupplyBySkuId(skuId);
			   Map<String,Object> supplyObj = dealerProductSkuService.getSupplierInfoBySkuId(skuId);
			   map_skuid_supplytype.put(skuId, supplyType);
			   map_skuid_supplyObj.put(skuId, supplyObj);
    	   }
		   endTime = System.currentTimeMillis();
		   logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.查询skuid对应的供应商信息 耗时:"+ (endTime - beginTime) +":ms");
			
			
		   //查询skuid对应的价格信息--------------------------------------------------------
		   beginTime = System.currentTimeMillis();
		   map_skuid_skuObj = customerOrderApi.findSkuShowDtoBySkuIds(list_skuid);
		   endTime = System.currentTimeMillis();
		   logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.查询skuid对应的价格信息  耗时:"+ (endTime - beginTime) +":ms");
       } catch (Exception e) {
           logger.error("导入失败,查询sku信息出现异常!",e);
           ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,查询sku信息出现异常!");
           model.addAttribute("message", JSONArray.fromObject(m).toString());
           return "redirect:/thirdPartyImport/toImport";
       }
       
       
       
       
       try {
    	   beginTime = System.currentTimeMillis();
    	   
    	   //订单处理:包括拆分订单功能 --------------------------------------------------------
    	   ThirdPartyImportDto rowData = null;
    	   Short supplyType = null;
    	   String skuSupplierId = null;
    	   String skuSupplierName = null;
    	   Integer orderType = null;
    	   String needInvoice = null;
    	   //新的订单号
    	   String orderIdNew = null;
    	   //同一个导入订单:通过sku计算的价格合计
    	   BigDecimal order_newsumskuprice = BigDecimal.ZERO;
    	 //同一个新的订单:通过sku计算的价格合计
    	   BigDecimal ordernew_newsumskuprice = BigDecimal.ZERO;
			int rowsSize = list_rows.size();
			int rowNum = 0;
			//开始位置i=有效行-1,结束位置<最大行数
			for (int i = 0; i < rowsSize; i++) {
				//当前行号:i+开始行,i初始化值=0,第一行有效数据--------------------------------------
				rowNum = i+dataBeginRowNum; 
				rowData = list_rows.get(i);
            	//第三方订单ID>
            	//skuid>sku数量>sku价格（N）>
            	//订单实付总价> 第三方来源>
            	//收货人>联系电话>收货地址>备注（N）> 运费（N）> 是否开发票>发票抬头>发票内容
				orderId = rowData.getOrderId();
				
				//再次检查,当前订单是否出现过任何错误信息,如果出现整个订单都不处理
				if(map_orderid_errernum.containsKey(orderId)){
					logger.warning("订单号:"+orderId+",被标记过存在错误信息,则当前整个订单任何sku数据都不处理!");
					continue;
				}
				
				skuId = rowData.getSkuId();
				orderType = typemap.get(rowData.getOrderTypeName());//第三方来源
				
				//必须校验sku的真实性
				if(!map_skuid_supplyObj.containsKey(skuId) || null == map_skuid_supplyObj.get(skuId)){
					errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",无效!"));
            		map_orderid_errernum.put(orderId, null);
				}else{
					//供应商信息:供应商类型|供应商ID|供应商名称
					supplyType = map_skuid_supplytype.get(skuId);
					skuSupplierId = String.valueOf(map_skuid_supplyObj.get(skuId).get(supplyIdName));
					skuSupplierName = String.valueOf(map_skuid_supplyObj.get(skuId).get(supplyNameName));
				}
				
				//验证其他属性----------------------开始
				//订单类型
				if(null == orderType){
					errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",第三方来源不支持!"));
            		map_orderid_errernum.put(orderId, null);
				}
				//供应商类型
				if(null == supplyType){
					errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",无法获取当前sku对应供应商类型!"));
            		map_orderid_errernum.put(orderId, null);
				}
				//如果是保税区商品，身份证号不能为空 并验证 格式 是否正确
				if(supplyType != null && 12 == supplyType){
					if(StringUtils.isEmpty(rowData.getRemark()) ){
						errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",保税区订单身份证号为必填项!"));
	            		map_orderid_errernum.put(orderId, null);
					}else if(!ValidateTool.regularIdcard(rowData.getRemark().trim())){
						errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",身份证号:"+rowData.getRemark()+",位数不正确!"));
	            		map_orderid_errernum.put(orderId, null);
					}else{//身份证号大写
						rowData.setRemark(rowData.getRemark().trim().toUpperCase());
					}
					//收货地址不为空
					if(StringUtils.isNotBlank(rowData.getPeceive_address())){
						String receiveAddr = rowData.getPeceive_address().trim();
						if(receiveAddr.split(" ").length < 3 ){
							errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",保税区订单地址信息格式不正确，省 市 区 详细 按空格分隔:"+rowData.getPeceive_address()));
		            		map_orderid_errernum.put(orderId, null);
						}
					}				
				}
				//供应商ID
				if(StringUtils.isEmpty(skuSupplierId)){
					errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",sku:"+skuIdStr+",无法获取当前sku对应供应商ID!"));
            		map_orderid_errernum.put(orderId, null);
				}
            	
				//再次检查,当前订单是否出现过任何错误信息,如果出现整个订单都不处理
				if(map_orderid_errernum.containsKey(orderId)){
					logger.warning("订单号:"+orderId+",被标记过存在错误信息,则当前整个订单任何sku数据都不处理!");
					continue;
				}
            	//验证其他属性----------------------结束
            	
				
				//创建新的订单号------------------------------------------------第三方订单号+供应商类型
				orderIdNew = orderId + "_" + supplyType;
				
				//保存新旧订单号的关系
				map_orderidnew_orderid.put(orderIdNew, orderId);
				
            	//声明订单对象
            	ThirdParyDTO order = null;
            	List<ThirdParyItemDTO> list_skus =  null;
            	//如果对象存在,直接取出,使用新的订单号-----------------****orderIdNew
            	Integer index = map_orderidnew_index.get(orderIdNew);
            	if(null == index){
            		//实例订单对象,并设置sku集合对象
            		order = new ThirdParyDTO();
            		list_skus = new ArrayList<ThirdParyItemDTO>();
            		order.setThirdParyItemDTOs(list_skus);
            		//记录相关属性,使用新的订单号-----------------****orderIdNew
                	order.setOrderId(orderIdNew);
                	order.setOrderType(orderType);
                	order.setSupplyType(supplyType);
                	//判断供应商类型为12（保税区）订单时，相关字段赋值
                	if(supplyType == 12){
                		order.setCustomsCode("3888");
                		order.setPortType("102");//导入订单赋值
                		order.setMerchantid("12");//导入订单赋值
                		order.setSendCode(Short.valueOf("0"));//默认值
                		order.setAccountType("2");
                	}
                	//
                	order.setPrice(Double.valueOf(rowData.getPrice()));
                	order.setPeceive_name(rowData.getPeceive_name());
                	//new DecimalFormat("#").format("")
                	order.setPeceive_mobile_phone(rowData.getPeceive_mobile_phone());
                	order.setPeceive_address(rowData.getPeceive_address());
                	order.setRemark(rowData.getRemark());
                	//
                	order.setNeedInvoice(("是".equals(rowData.getNeedInvoice())?"1":"0"));
                	order.setInvoiceTitle(rowData.getInvoiceTitle());
                	order.setInvoiceDetail(rowData.getInvoiceDetail());
                	//运费 null =0;
                	order.setTransfer_price(Double.valueOf( ((null == rowData.getTransfer_price() || "".equals(rowData.getTransfer_price()))?"0":rowData.getTransfer_price()) ));
                	
            		//获取下标,放入集合,设置map,使用新的订单号-----------------****orderIdNew
            		index = list_orders.size();
            		list_orders.add(index,order);
            		map_orderidnew_index.put(orderIdNew,index);
            		//设置订单号下sku重复标记容器,使用新的订单号-----------------****orderIdNew
            		Map<Long,Integer> map_sukid_index = new HashMap<Long, Integer>();
            		map_orderidnew_skus.put(orderIdNew,map_sukid_index);
            	}else{
            		//获取对象
            		order = list_orders.get(index);
            		//获取sku集合
            		list_skus = order.getThirdParyItemDTOs();
            	}
            	
            	//检查sku是否重复,使用新的订单号-----------------****orderIdNew
            	Integer index_sku = map_orderidnew_skus.get(orderIdNew).get(skuId);
            	if(null != index_sku){
            		logger.warning("行"+rowNum+":订单号:"+orderId+",suk:"+skuId+"重复!  ;<br/>");
					errorMassageV.add(new ThirdPartyMessage("fail","","行"+rowNum+":订单号:"+orderId+",suk:"+skuId+"重复!"));
					map_orderid_errernum.put(orderId, null);
            	}else{
            		//实例sku对象
            		ThirdParyItemDTO sku = new ThirdParyItemDTO();
            		sku.setSkuId(skuId);
            		sku.setNum(Double.valueOf(rowData.getSkuNum()).intValue());
            		//重新设置sku合计价格:sku数量*sku单价
            		sku.setPrice(BigDecimal.valueOf(sku.getNum()).multiply(map_skuid_skuObj.get(skuId).getPrice()).doubleValue());
            		sku.setSupplierId(StringUtils.isEmpty(skuSupplierId)?null:Long.valueOf(skuSupplierId));
            		sku.setSupplierName(skuSupplierName);
            		
            		//记录原始订单号sku合计价格,放入map,使用导入订单号-----------------****orderId
            		order_newsumskuprice = (null== map_orderid_newsumskuprice.get(orderId) ? BigDecimal.ZERO:map_orderid_newsumskuprice.get(orderId));
            		order_newsumskuprice = order_newsumskuprice.add(BigDecimal.valueOf(sku.getPrice())); //加上新的sku价格
            		map_orderid_newsumskuprice.put(orderId, order_newsumskuprice);
            		
            		//记录拆分订单号sku合计价格,放入map,使用新的订单号-----------------****orderIdNew
            		ordernew_newsumskuprice = (null== map_orderidnew_newsumskuprice.get(orderIdNew) ? BigDecimal.ZERO:map_orderidnew_newsumskuprice.get(orderIdNew));
            		ordernew_newsumskuprice = ordernew_newsumskuprice.add(BigDecimal.valueOf(sku.getPrice())); //加上新的sku价格
            		map_orderidnew_newsumskuprice.put(orderIdNew, ordernew_newsumskuprice);
            		
            		//放入集合,记录到map
            		index_sku = list_skus.size();
            		list_skus.add(index_sku, sku);
            		map_orderidnew_skus.get(orderIdNew).put(skuId, index_sku);
            	}
            	
            	//标记订单拆分过程,使用key=导入订单号,value=导入订单号分解的新订单号-----------------****orderId -------------- begin
            	Map<String,Integer> map_orderidnew_null =map_orderid_orderidnewmap.get(orderId); 
            	if(null == map_orderidnew_null){
            		map_orderidnew_null = new HashMap<String, Integer>();
            	}
            	map_orderidnew_null.put(orderIdNew, null);
            	map_orderid_orderidnewmap.put(orderId, map_orderidnew_null);
            	//标记订单拆分过程,使用key=导入订单号,value=导入订单号分解的新订单号-----------------****orderId -------------- end
			}
			
			endTime = System.currentTimeMillis();
		    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.解析订单 耗时:"+ (endTime - beginTime) +":ms");
		    
		    // 获取运费信息 保税区 计算运费运费值 目前79 免运费 订单金额小于79 增加 10元运费 
	        BigDecimal dictValue12 = new BigDecimal("79");
	        BigDecimal transferPrice12 = new BigDecimal("10");//订单金额小于79 运费10元
		    
		    //处理拆分订单价格------------------------------
			beginTime = System.currentTimeMillis();
		    Set<String> set_orderid = map_orderid_orderidnewmap.keySet();
		    for (String key : set_orderid) {
		    	//获取子订单数量,如果大于1则进行价格处理
		    	int orderid_chind_size =  map_orderid_orderidnewmap.get(key).size();
		    	if(1<orderid_chind_size){
		    		Set<String> set_orderidnew = map_orderid_orderidnewmap.get(key).keySet();
		    		for (String key_new : set_orderidnew) {
		    			logger.info(">>>>拆分的订单:原始orderid:"+key +"拆分之订单:"+key_new);
		    			//获取订单对象
		    			ThirdParyDTO order = list_orders.get(map_orderidnew_index.get(key_new));
		    			logger.info(order.getOrderId()+">\t计算[(新订单sku金额合计值/原始订单所有sku金额合计)*导入订单原始金额]:"+map_orderidnew_newsumskuprice.get(key_new)+"/"+map_orderid_newsumskuprice.get(key)+"*"+BigDecimal.valueOf(order.getPrice()));
		    			logger.info(order.getOrderId()+">\t精度问题转换=[导入订单原始金额*新订单sku金额合计值/原始订单所有sku金额合计]:"+BigDecimal.valueOf(order.getPrice())+"*"+map_orderidnew_newsumskuprice.get(key_new)+"/"+map_orderid_newsumskuprice.get(key));
		    			// （新订单sku金额合计值/原始订单所有sku金额合计)*导入订单原始金额,保留2位小数
		    			/*order.setPrice(
		    					map_orderidnew_newsumskuprice.get(key_new)
		    					.divide(map_orderid_newsumskuprice.get(key),4)
		    					.multiply(BigDecimal.valueOf(order.getPrice()))
		    					.setScale(2).doubleValue());*/
		    			order.setPrice(
		    					BigDecimal.valueOf(order.getPrice())
		    					.multiply(map_orderidnew_newsumskuprice.get(key_new))
		    					.divide(map_orderid_newsumskuprice.get(key),BigDecimal.ROUND_UNNECESSARY-1)
		    					.setScale(2).doubleValue());
		    			logger.info(order.getOrderId()+">\t计算结果:"+ order.getPrice());
		    			
		    			/* 保税区订单 税费、运费计算 其他类型订单不检查 */
		    			if(order.getSupplyType() == 12){
		    				logger.info(">>>>保税区订单检验拆分的订单总价，大于2000不允许提交:原始orderid:"+key +"拆分之订单:"+key_new+",拆分后订单价格："+order.getPrice());
		    				if(order.getPrice().compareTo(new Double(2000)) > 0){//保税区订单总价超过2000 不允许提交
		    					errorMassageV.add(new ThirdPartyMessage("fail","",":订单号:"+key+",保税区商品总价超过2000，不允许提交!"));
								map_orderid_errernum.put(key, null);
		    				}else{
		    					BigDecimal totalPrice = new BigDecimal(0);	// 订单总计
		    					
		    					BigDecimal totalDiscountPrice = new BigDecimal(0);	// 总折扣价
		    					BigDecimal totalTax = new BigDecimal(0);	// 购物车总税费
		    					BigDecimal transferPriceCur = new BigDecimal(0); //当前订单运费
		    					//BigDecimal dictValue12 = new BigDecimal("79");
		    				    //BigDecimal transferPrice12

		    					for(ThirdParyItemDTO orderI:order.getThirdParyItemDTOs()){
		    						totalPrice = totalPrice.add(new BigDecimal(orderI.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
		    					}
		    					//计算运费税费的Map
		    					if((totalPrice.doubleValue() - dictValue12.doubleValue()) < 0 ){//订单价格大于等于免邮价时,设置邮费
	    							transferPriceCur = transferPriceCur.add(transferPrice12);
	    						}
		    					order.setPrice(order.getPrice() - transferPriceCur.doubleValue());
		    					for(ThirdParyItemDTO orderI:order.getThirdParyItemDTOs()){
		    						//税
									BigDecimal exchangeRange = new BigDecimal(map_skuid_skuObj.get(orderI.getSkuId()).getExchangeRate().doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
									//sku税率
									orderI.setTaxRate(map_skuid_skuObj.get(orderI.getSkuId()).getExchangeRate().doubleValue());
									
									// 单个sku的总价格
									BigDecimal skuTotalPrice = new BigDecimal(orderI.getPrice());
									
									//sku总价格乘以税率 = 税额 
									BigDecimal skuSumTax = skuTotalPrice.multiply(exchangeRange).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
									
									// 单个sku价格的占比
									BigDecimal goodPriceRate = skuTotalPrice.divide(totalPrice, 2, RoundingMode.HALF_UP);
									//折扣金额
									BigDecimal discountPrice = skuTotalPrice.subtract(new BigDecimal(order.getPrice()).multiply(goodPriceRate));
									//明细折扣
									orderI.setDiscountPrice(discountPrice.doubleValue());
									//总折扣
									totalDiscountPrice = totalDiscountPrice.add(discountPrice);
									
									//计算单个sku的税  = 运费税 + 单sku税
									orderI.setTax(transferPriceCur.multiply(goodPriceRate).multiply(exchangeRange).divide(BigDecimal.valueOf(100L)).add(skuSumTax).doubleValue());
									
									totalTax = totalTax.add(new BigDecimal(orderI.getTax()).setScale(2,BigDecimal.ROUND_HALF_UP));//单sku运费税
		    					}
		    					order.setDiscountPrice(totalDiscountPrice.doubleValue());
		    					order.setTotalTax(totalTax.toString());
		    					order.setRealTotalTax(totalTax.toString());
		    					order.setTransfer_price(transferPriceCur.doubleValue());
		    					order.setReal_transfer_price(transferPriceCur.doubleValue());
		    				}
		    			}
					}
		    	}else{
			    		Set<String> set_orderidnew = map_orderid_orderidnewmap.get(key).keySet();
			    		for (String key_new : set_orderidnew) {
			    			logger.info(">>>>拆分的订单:原始orderid:"+key +"拆分之订单:"+key_new);
			    			//获取订单对象
			    			ThirdParyDTO order = list_orders.get(map_orderidnew_index.get(key_new));
			    			logger.info(order.getOrderId()+">\t计算[(新订单sku金额合计值/原始订单所有sku金额合计)*导入订单原始金额]:"+map_orderidnew_newsumskuprice.get(key_new)+"/"+map_orderid_newsumskuprice.get(key)+"*"+BigDecimal.valueOf(order.getPrice()));
			    			logger.info(order.getOrderId()+">\t精度问题转换=[导入订单原始金额*新订单sku金额合计值/原始订单所有sku金额合计]:"+BigDecimal.valueOf(order.getPrice())+"*"+map_orderidnew_newsumskuprice.get(key_new)+"/"+map_orderid_newsumskuprice.get(key));
			    			
			    			/* 保税区订单 税费、运费计算 其他类型订单不检查 */
			    			if(order.getSupplyType() == 12){
			    				logger.info(">>>>保税区订单检验拆分的订单总价，大于2000不允许提交:原始orderid:"+key +"拆分之订单:"+key_new+",拆分后订单价格："+order.getPrice());
			    				if(order.getPrice().compareTo(new Double(2000)) > 0){//保税区订单总价超过2000 不允许提交
			    					errorMassageV.add(new ThirdPartyMessage("fail","",":订单号:"+key+",保税区商品总价超过2000，不允许提交!"));
									map_orderid_errernum.put(key, null);
			    				}else{
			    					BigDecimal totalPrice = new BigDecimal(0);	// 订单总计
			    					
			    					BigDecimal totalDiscountPrice = new BigDecimal(0);	// 总折扣价
			    					BigDecimal totalTax = new BigDecimal(0);	// 购物车总税费
			    					BigDecimal transferPriceCur = new BigDecimal(0); //当前订单运费
			    					//BigDecimal dictValue12 = new BigDecimal("79");
			    				    //BigDecimal transferPrice12
			    					
			    					for(ThirdParyItemDTO orderI:order.getThirdParyItemDTOs()){
			    						totalPrice = totalPrice.add(new BigDecimal(orderI.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
			    					}
			    					//计算运费税费的Map
			    					if((totalPrice.doubleValue() - dictValue12.doubleValue()) < 0 ){//订单价格大于等于免邮价时,设置邮费
		    							transferPriceCur = transferPriceCur.add(transferPrice12);
		    						}
			    					order.setPrice(order.getPrice() - transferPriceCur.doubleValue());
			    					for(ThirdParyItemDTO orderI:order.getThirdParyItemDTOs()){
			    						//税
										BigDecimal exchangeRange = new BigDecimal(map_skuid_skuObj.get(orderI.getSkuId()).getExchangeRate().doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
										//sku税率
										orderI.setTaxRate(map_skuid_skuObj.get(orderI.getSkuId()).getExchangeRate().doubleValue());
										
										// 单个sku的总价格
										BigDecimal skuTotalPrice = new BigDecimal(orderI.getPrice());
										
										//sku总价格乘以税率 = 税额 
										BigDecimal skuSumTax = skuTotalPrice.multiply(exchangeRange).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
										
										// 单个sku价格的占比
										BigDecimal goodPriceRate = skuTotalPrice.divide(totalPrice, 2, RoundingMode.HALF_UP);
										//折扣金额
										BigDecimal discountPrice = skuTotalPrice.subtract(new BigDecimal(order.getPrice()).multiply(goodPriceRate));
										//明细折扣
										orderI.setDiscountPrice(discountPrice.doubleValue());
										//总折扣
										totalDiscountPrice = totalDiscountPrice.add(discountPrice);
										
										//计算单个sku的税  = 运费税 + 单sku税
										orderI.setTax(transferPriceCur.multiply(goodPriceRate).multiply(exchangeRange).divide(BigDecimal.valueOf(100L)).add(skuSumTax).doubleValue());
										
										totalTax = totalTax.add(new BigDecimal(orderI.getTax()).setScale(2,BigDecimal.ROUND_HALF_UP));//单sku运费税
			    					}
			    					order.setDiscountPrice(totalDiscountPrice.doubleValue());
			    					order.setTotalTax(totalTax.toString());
			    					order.setRealTotalTax(totalTax.toString());
			    					order.setTransfer_price(transferPriceCur.doubleValue());
			    					order.setReal_transfer_price(transferPriceCur.doubleValue());
			    				}
			    			}
			    	}
				}
		    }
		    
			endTime = System.currentTimeMillis();
		    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.拆分订单价格处理 耗时:"+ (endTime - beginTime) +":ms");
		    
		    
		    //错误拦截--------------------------------------------------begin
            if(0<map_orderid_errernum.size()){
            	StringBuffer errerMassage = new StringBuffer();
            	errerMassage.append("<div>");
         	    errerMassage.append("订单中某些数据不合法,不允许导入!  <br/>");
            	errerMassage.append("<ul>");
            	Iterator<ThirdPartyMessage> iterator_emv =  errorMassageV.iterator();
            	int index = 0;
            	while (iterator_emv.hasNext() && index<errormassageV_max_size) {
            		errerMassage.append("<li>").append(iterator_emv.next().getMsg()).append("</li>");
            		index ++;
				}
            	errerMassage.append("</ul>");
            	 errerMassage.append("</div>");
            	 
            	ThirdPartyMessage m = new ThirdPartyMessage("fail","",errerMassage.toString());
                model.addAttribute("message", JSONArray.fromObject(m).toString());
        	    return "redirect:/thirdPartyImport/toImport";
            }
    	    //错误拦截--------------------------------------------------end
            
        } catch (Exception e) {
            logger.error("导入失败,订单业务数据处理或订单拆分异常!",e);
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,订单业务数据处理或订单拆分异常!");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";
        }
       
       //订单入库:---------------------此处可优化为线程池并行处理------------------------------------------------------
       
       beginTime = System.currentTimeMillis();
       int nThreads = Runtime.getRuntime().availableProcessors();
       logger.info(">>>>>>>>获取CPU个数:"+nThreads);
       ExecutorService pool = Executors.newFixedThreadPool(2*nThreads);
       
       try {
    	    for (final ThirdParyDTO order: list_orders) {
    	    	//通过新订单号,找到旧订单号,如果旧订单出现过任何错误,则当前新订单不做入库处理
    	    	orderId = map_orderidnew_orderid.get(order.getOrderId());
				if(map_orderid_errernum.containsKey(orderId)){
					//
					logger.warning("订单号:"+orderId+",被标记过存在错误信息,则当前整个订单任何sku数据都不处理!");
					continue;
				}else{
					final String orderId2 = orderId;
					pool.execute(new Runnable() {
						@Override
						public void run() {
							ThirdPartyMessage m =  null;
							try {
								logger.info("线程:"+Thread.currentThread().getName()+"\t>>>> :"+ orderId2 +";order:" + ordrerToString(order));
								String userId =ThirdTypeEnum.getUserId(order.getOrderType()).toString();
								m =  thirdPartyOrderService.saveOrder(order,userId);
								if(null!=m && !m.getStatus().equals("success")){
									errorMassageV.add(m);
									 map_orderid_errernum.put(orderId2, null);
								}
							} catch (Exception e) {
								//logger.error("调用 thirdPartyOrderService.saveOrder方法,进行订单保存 失败,可能原因库存不足,第三方订单ID="+orderId2);
								errorMassageV.add(new ThirdPartyMessage("fail","","调用 订单保存服务 失败,可能原因库存不足,第三方订单ID="+orderId2+";"));
								map_orderid_errernum.put(orderId2, null);
							}
						}
			       });
					
				}
			}
    	    //
    	    pool.shutdown();
    	    while (true) {
    	    	//等等线程池处理完成
	            if (pool.isTerminated()) {
	            	//
	    			endTime = System.currentTimeMillis();
	    		    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.保存订单  耗时:"+ (endTime - beginTime) +":ms");
	                break;
	            }else{
	            	//logger.info("线程池处理中!----------------------------------------");
	            }
	            Thread.sleep(200);  
	        }
    	    
    	    logger.info("errorMassageV.size():"+errorMassageV.size());
    	    
    	    endTimeAll = System.currentTimeMillis();
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.整个处理过程  耗时:"+ (endTimeAll - beginTimeAll) +":毫秒!");
    	    
            
            //返回消息-----------------------------------------
    	    int failnum = map_orderid_errernum.size();
    	    int successnum =  map_orderid_obj.size() - failnum;
    	    
		    
    	    StringBuffer errerMassage = new StringBuffer();
    	    errerMassage.append("<div>");
    	    errerMassage.append("导入成功"+successnum+"单,失败"+failnum+"单"+" ;<br/>  ");
    	    errerMassage.append("累计耗时:"+(endTimeAll - beginTimeAll)/1000 +":秒!;<br/>");
    	    //错误拦截--------------------------------------------------begin
            if(0<map_orderid_errernum.size()){
            	errerMassage.append("<ul>");
            	Iterator<ThirdPartyMessage> iterator_emv =  errorMassageV.iterator();
            	int index = 0;
            	while (iterator_emv.hasNext() && index<errormassageV_max_size) {
            		errerMassage.append("<li>").append(iterator_emv.next().getMsg()).append("</li>");
            		index ++;
				}
            	errerMassage.append("</ul>");
            }
            errerMassage.append("</div>");
            //错误拦截--------------------------------------------------end
            ThirdPartyMessage m = new ThirdPartyMessage("success","",errerMassage.toString());
            model.addAttribute("message", JSONArray.fromObject(m).toString());
    	    return "redirect:/thirdPartyImport/toImport";
       } catch (Exception e) {
           logger.error("保存订单出现异常!",e);
           ThirdPartyMessage m = new ThirdPartyMessage("fail","","保存订单出现异常!");
           model.addAttribute("message", JSONArray.fromObject(m).toString());
           return "redirect:/thirdPartyImport/toImport";
       }
       
      
    }
    
    /***
     * 输出订单对象
     * @param order
     * @return
     */
	private String ordrerToString(ThirdParyDTO order) {
		if(null== order){
			return "ThirdParyDTO is NULL ----------------------";
		}else{
			return "ThirdParyDTO [orderId=" + order.getOrderId() + ", supplyType="
					+ order.getSupplyType() + ", orderType=" + order.getOrderType() + ", price=" + order.getPrice()
					+ ", peceive_name=" + order.getPeceive_name() + ", peceive_mobile_phone="
					+ order.getPeceive_mobile_phone() + ", peceive_address=" + order.getPeceive_address()
					+ ", transfer_price=" + order.getTransfer_price() + ", remark=" + order.getRemark()
					+ ", needInvoice=" + order.getNeedInvoice() + ", invoiceTitle="
					+ order.getInvoiceTitle()+ ", invoiceDetail=" + order.getInvoiceDetail()
					+ ", thirdParyItemDTO size=" +(null == order.getThirdParyItemDTOs()?0:order.getThirdParyItemDTOs().size())  + "]";
		}

	}
    
    
    
    
    @RequestMapping(value = "/importFile_old")
    public String importFile_old(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, Model model){
        String fileName = file.getOriginalFilename();

        if(StringUtils.isBlank(fileName) || !(fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件格式不正确");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";
        }

        Map<String,ThirdParyDTO> map = new HashMap<String,ThirdParyDTO>();
        try {
            Workbook wb = null;
            if (fileName.endsWith("xls") ) {
                wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream())); 
            } else if(fileName.endsWith("xlsx") ){
                wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
            } else{
                ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件不能解析");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }
            Sheet sheet = wb.getSheetAt(0);
            Row row;
            String cell;


            ThirdParyDTO dto;
            List<ThirdParyItemDTO> list;
            ThirdParyItemDTO itemDTO;
            if(sheet.getPhysicalNumberOfRows()>1000){
            	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,导入订单上限为1000行");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(row==null){
                	continue;
                }
                if(row.getCell(0)==null){
                	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方订单号不能为空");
                    model.addAttribute("message", JSONArray.fromObject(m).toString());
                    return "redirect:/thirdPartyImport/toImport";
                }
                //第三方订单号替换双引号，单引号和空格
                String thirdOrderId = row.getCell(0).toString().replaceAll("\"", "").replaceAll("\'", "").trim();//第三方订单号替换双引号，单引号和空格
                if(thirdOrderId.equals("")){
                	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方订单号不能为空");
                    model.addAttribute("message", JSONArray.fromObject(m).toString());
                    return "redirect:/thirdPartyImport/toImport";
                }
                if(map.containsKey(thirdOrderId)){
                    list = map.get(thirdOrderId).getThirdParyItemDTOs();
                    itemDTO = new ThirdParyItemDTO();
                    if(row.getCell(1)==null||"".equals(row.getCell(1).toString().trim())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,sku_id不能为空,第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    itemDTO.setSkuId(Long.valueOf(row.getCell(1).toString().trim()));//商品的sku_id
                    
                    if(row.getCell(2)==null||"".equals(row.getCell(2).toString().trim())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,sku_id:"+row.getCell(2).toString().trim()+",购买数量不能为空,第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }

                    itemDTO.setNum(Double.valueOf(row.getCell(2).toString().trim()).intValue());//商品的购买数量

                    if(row.getCell(3)!=null && !"null".equals(row.getCell(3).toString()) && !"".equals(row.getCell(3).toString())){
                        itemDTO.setPrice(Double.valueOf(row.getCell(3).toString()));
                    }
                    list.add(itemDTO);
                }else{
                    dto = new ThirdParyDTO();
                    dto.setOrderId(thirdOrderId);
                    if(row.getCell(4)==null||"".equals(row.getCell(4).toString())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,订单总价不能为空，第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    dto.setPrice(Double.valueOf(row.getCell(4).toString()));//订单总价
                    if(row.getCell(5)==null||"".equals(row.getCell(5).toString().trim())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方来源不能为空");
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    Integer _type = ThirdPartyImportController.typemap.get(row.getCell(5).toString().trim());//第三方来源
                    //
                    if(null!=_type){
                        dto.setOrderType(ThirdPartyImportController.typemap.get(row.getCell(5).toString().trim()));//第三方来源
                    }else{
                        ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方来源不支持");
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    if(row.getCell(6)==null||"".equals(row.getCell(6).toString())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,收货人姓名不能为空，第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    dto.setPeceive_name(row.getCell(6).toString());//收货人姓名

                    if(row.getCell(7)==null||"".equals(row.getCell(7).toString())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,收货人电话不能为空，第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    
                    //收货人电话
                    if(row.getCell(7).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                        dto.setPeceive_mobile_phone(new DecimalFormat("#").format(row.getCell(7).getNumericCellValue()));
                    }else{
                        dto.setPeceive_mobile_phone(row.getCell(7).toString().replaceAll("\"", "").replaceAll("\'", "").trim());
                    }
                    
                    if(row.getCell(8)==null||"".equals(row.getCell(8).toString())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,收货人地址不能为空，第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    //收货人地址
                    dto.setPeceive_address(row.getCell(8).toString());
                    //备注信息
                    if(null!=row.getCell(9) && !"null".equals(row.getCell(9).toString()) && !"".equals(row.getCell(9).toString())){
                        dto.setRemark(row.getCell(9).toString());
                    }

                    //运费
                    if(null!=row.getCell(10) && !"null".equals(row.getCell(10).toString()) && !"".equals(row.getCell(10).toString())){
                        dto.setTransfer_price(Double.valueOf(row.getCell(10).toString()));
                    }else{
                        dto.setTransfer_price(0d);
                    }
                    //是否开发票
                    if(null!=row.getCell(11) && "是".equals(row.getCell(11).toString().trim()) ){
                        dto.setNeedInvoice("1");
                        if(null!=row.getCell(12)) {//发票抬头
                            dto.setInvoiceTitle(row.getCell(12).toString().trim());
                        }
                        if(null!=row.getCell(13)) {//发票内容

                            dto.setInvoiceDetail(row.getCell(13).toString().trim());
                        }
                    }else{
                        dto.setNeedInvoice("0");
                    }

                    list = new ArrayList<ThirdParyItemDTO>();
                    itemDTO = new ThirdParyItemDTO();
                    
                    if(row.getCell(1)==null||"".equals(row.getCell(1).toString())){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,sku_id不能为空,第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    itemDTO.setSkuId(Long.valueOf(row.getCell(1).toString().trim()));//sku_id 
                    
                    if(row.getCell(2)==null||"".equals(row.getCell(2))){
                    	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,sku_id:"+row.getCell(1).toString()+",购买数量不能为空,第三方订单号："+thirdOrderId);
                        model.addAttribute("message", JSONArray.fromObject(m).toString());
                        return "redirect:/thirdPartyImport/toImport";
                    }
                    itemDTO.setNum(Double.valueOf(row.getCell(2).toString().trim()).intValue());//购买数量

                    if(row.getCell(3)!=null && !"null".equals(row.getCell(3).toString()) && !"".equals(row.getCell(3).toString())){
                        itemDTO.setPrice(Double.valueOf(row.getCell(3).toString()));//单价
                    }
                    list.add(itemDTO);
                    dto.setThirdParyItemDTOs(list);
                    map.put(thirdOrderId,dto);
                }

            }

        } catch (Exception e) {
            logger.error("解析execl错误",e);
            e.printStackTrace();
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,解析execl错误");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";

        }


        List<ThirdPartyMessage> tmlist = new ArrayList<ThirdPartyMessage>();

        for(String str :map.keySet()){
            ThirdPartyMessage m = null;
            try{
                String userId =ThirdTypeEnum.getUserId(map.get(str).getOrderType()).toString();
                m =  thirdPartyOrderService.saveOrder(map.get(str),userId);
            }catch (Exception e){
                m = new ThirdPartyMessage("fail","","导入失败,可能原因库存不足,第三方订单ID="+str);
            }
            tmlist.add(m);
        }
        int successnum = 0;
        int failnum = 0;
        String str = "";
        for(ThirdPartyMessage tm:tmlist){
            if(tm.getStatus().equals("success")){
                successnum++;
                logger.info("成功,"+tm.getMsg());
            }else{
                failnum++;
                logger.info("失败,"+tm.getMsg());
                str+=tm.getMsg()+";<br/>";
            }
        }
        ThirdPartyMessage m = new ThirdPartyMessage("success","","导入成功"+successnum+"单,失败"+failnum+"单"+"<br/>   "+str);
        model.addAttribute("message", JSONArray.fromObject(m).toString());

        return "redirect:/thirdPartyImport/toImport";
    }
    
    
    @RequestMapping(value = "/importFile_old2")
    public String importFile_old2(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, Model model){
        String fileName = file.getOriginalFilename();

        if(StringUtils.isBlank(fileName) || !(fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件格式不正确");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";
        }

        Map<String,ThirdParyDTO> map = new HashMap<String,ThirdParyDTO>();
        List<ThirdPartyMessage> thirdMsglist = new ArrayList<ThirdPartyMessage>();
        
        Map<String, List<String>> multiChannelMap = new HashMap<String, List<String>>();
        
        try {
            Workbook wb = null;
            if (fileName.endsWith("xls") ) {
                wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream())); 
            } else if(fileName.endsWith("xlsx") ){
                wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
            } else{
                ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,文件不能解析");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }
            Sheet sheet = wb.getSheetAt(0);
            Row row;
            String cell;
            
            ThirdParyDTO dto;
            List<ThirdParyItemDTO> list;
            ThirdParyItemDTO itemDTO;
            if(sheet.getPhysicalNumberOfRows()>1001){
            	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,导入订单上限为1000行");
                model.addAttribute("message", JSONArray.fromObject(m).toString());
                return "redirect:/thirdPartyImport/toImport";
            }
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(row==null){
                	continue;
                }
                if(row.getCell(0)==null){
                	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方订单号不能为空");
                    model.addAttribute("message", JSONArray.fromObject(m).toString());
                    return "redirect:/thirdPartyImport/toImport";
                }
                //第三方订单号替换双引号，单引号和空格
                String thirdOrderId = row.getCell(0).toString().replaceAll("\"", "").replaceAll("\'", "").trim();//第三方订单号替换双引号，单引号和空格
                if(thirdOrderId.equals("")){
                	ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,第三方订单号不能为空");
                    model.addAttribute("message", JSONArray.fromObject(m).toString());
                    return "redirect:/thirdPartyImport/toImport";
                }
                
                String key = thirdOrderId + "";
                Short supplyType ;
                Map<String, Object> supplyMap = null;
                
                if(checkCell(row,1,"导入失败,sku_id不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){//判断sku_id是否为空
                	continue;
                }else{                   	
                	Long skuId = Long.valueOf(row.getCell(1).toString().trim());
                	
                	supplyType = dealerProductSkuService.getB2cProductSupplyBySkuId(skuId);
                	
                	supplyMap = dealerProductSkuService.getSupplierInfoBySkuId(skuId);
                	
                	
                	key += "_" + supplyType;
                }
                
            	if (multiChannelMap.containsKey(thirdOrderId)) {
            		List<String> multiKeylist = multiChannelMap.get(thirdOrderId);
            		boolean isExist = Boolean.FALSE;
            		for (String ttt : multiKeylist) {
            			if (ttt.equals(key)) {
            				isExist = Boolean.TRUE;
            				break;
            			}
            		}
            		if (!isExist) {
            			multiKeylist.add(key);
            		}
                }
                else {
                	List<String> multiKeylist = new ArrayList<String>();
        			multiKeylist.add(key);
        			multiChannelMap.put(thirdOrderId, multiKeylist);
                }
                
                if(map.containsKey(key)){
                	if(map.get(key)==null){
                		continue;
                	}
                	ThirdParyDTO tmpThirdParyDTO = map.get(key);
                    list = tmpThirdParyDTO.getThirdParyItemDTOs();
                    itemDTO = new ThirdParyItemDTO();
                    if(checkCell(row,1,"导入失败,sku_id不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){//判断sku_id是否为空
                    	map.put(key,null);
                    	continue;
                    }else{                   	
                    	itemDTO.setSkuId(Long.valueOf(row.getCell(1).toString().trim()));//商品的sku_id
                    }
                    
                    if(checkCell(row,2,"导入失败,sku_id:"+row.getCell(2).toString().trim()+",购买数量不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){//判断购买数量是否为空
                    	map.put(key,null);
                    	continue;
                    }else{                   	
                    	itemDTO.setNum(Double.valueOf(row.getCell(2).toString().trim()).intValue());//商品的购买数量
                    }
                    if(row.getCell(3)!=null && !"null".equals(row.getCell(3).toString()) && !"".equals(row.getCell(3).toString())){
                        itemDTO.setPrice(Double.valueOf(row.getCell(3).toString()));
                    }
                    
                    if (null != supplyMap) {
                    	itemDTO.setSupplierId((null != supplyMap.get("supplierid") && StringUtils.isNotEmpty(supplyMap.get("supplierid").toString())) ? Long.valueOf(supplyMap.get("supplierid").toString()) : null);
                    	itemDTO.setSupplierName(null != supplyMap.get("suppliername") ? supplyMap.get("suppliername").toString() : "");
                    }
                    
                    list.add(itemDTO);
                }else{
                    dto = new ThirdParyDTO();
                    dto.setOrderId(thirdOrderId);
                    dto.setSupplyType(supplyType);
                    String sku_Id="";
                    if(checkCell(row,1,"导入失败,sku_id不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{                	
                    	sku_Id=row.getCell(1).toString().trim();//sku_id 
                    }
                    if(checkCell(row,4,"导入失败,sku_id"+sku_Id+",订单总价不能为空，第三方订单号："+thirdOrderId,thirdMsglist)){//订单总价是否为空
                    	continue;
                    }else{                 	
                    	dto.setPrice(Double.valueOf(row.getCell(4).toString()));//订单总价
                    }
                    if(checkCell(row,5,"导入失败,sku_id"+sku_Id+",第三方来源不能为空，第三方订单号："+thirdOrderId,thirdMsglist)){//第三方来源是否为空
                    	continue;
                    }else{                   	
                    	Integer _type = ThirdPartyImportController.typemap.get(row.getCell(5).toString().trim());//第三方来源
                    	if(null!=_type){
                    		dto.setOrderType(ThirdPartyImportController.typemap.get(row.getCell(5).toString().trim()));//第三方来源
                    	}else{
                    		ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,sku_id"+sku_Id+",第三方来源不支持，第三方订单号："+thirdOrderId);
                    		thirdMsglist.add(m);
                    		continue;
                    	}
                    }
                    if(checkCell(row,6,"导入失败,sku_id"+sku_Id+",收货人姓名不能为空，第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{                 	
                    	dto.setPeceive_name(row.getCell(6).toString());//收货人姓名
                    }

                    if(checkCell(row,7,"导入失败,sku_id"+sku_Id+",联系电话不能为空，第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{
                    	//收货人电话
                        if(row.getCell(7).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            dto.setPeceive_mobile_phone(new DecimalFormat("#").format(row.getCell(7).getNumericCellValue()));
                        }else{
                            dto.setPeceive_mobile_phone(row.getCell(7).toString().replaceAll("\"", "").replaceAll("\'", "").trim());
                        }
                    }
                    //收货人地址
                    if(checkCell(row,8,"导入失败,sku_id"+sku_Id+",收货地址不能为空，第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{                    	
                    	dto.setPeceive_address(row.getCell(8).toString());
                    }
                    //备注信息
                    if(null!=row.getCell(9) && !"null".equals(row.getCell(9).toString()) && !"".equals(row.getCell(9).toString())){
                        dto.setRemark(row.getCell(9).toString());
                    }

                    //运费
                    if(null!=row.getCell(10) && !"null".equals(row.getCell(10).toString()) && !"".equals(row.getCell(10).toString())){
                        dto.setTransfer_price(Double.valueOf(row.getCell(10).toString()));
                    }else{
                        dto.setTransfer_price(0d);
                    }
                    //是否开发票
                    if(null!=row.getCell(11) && "是".equals(row.getCell(11).toString().trim()) ){
                        dto.setNeedInvoice("1");
                        if(null!=row.getCell(12)) {//发票抬头
                            dto.setInvoiceTitle(row.getCell(12).toString().trim());
                        }
                        if(null!=row.getCell(13)) {//发票内容

                            dto.setInvoiceDetail(row.getCell(13).toString().trim());
                        }
                    }else{
                        dto.setNeedInvoice("0");
                    }

                    list = new ArrayList<ThirdParyItemDTO>();
                    itemDTO = new ThirdParyItemDTO();
                    
                    if(checkCell(row,1,"导入失败,sku_id不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{                	
                    	itemDTO.setSkuId(Long.valueOf(row.getCell(1).toString().trim()));//sku_id 
                    }
                    
                    if(checkCell(row,2,"导入失败,sku_id"+sku_Id+",购买数量不能为空,第三方订单号："+thirdOrderId,thirdMsglist)){
                    	continue;
                    }else{                    	
                    	itemDTO.setNum(Double.valueOf(row.getCell(2).toString().trim()).intValue());//购买数量
                    }

                    if(row.getCell(3)!=null && !"null".equals(row.getCell(3).toString()) && !"".equals(row.getCell(3).toString())){
                        itemDTO.setPrice(Double.valueOf(row.getCell(3).toString()));//单价
                    }
                    if (null != supplyMap) {
                    	itemDTO.setSupplierId((null != supplyMap.get("supplierid") && StringUtils.isNotEmpty(supplyMap.get("supplierid").toString())) ? Long.valueOf(supplyMap.get("supplierid").toString()) : null);
                    	itemDTO.setSupplierName(null != supplyMap.get("suppliername") ? supplyMap.get("suppliername").toString() : "");
                    }
                    list.add(itemDTO);
                    dto.setThirdParyItemDTOs(list);
                    map.put(key,dto);
                }

            }

        } catch (Exception e) {
            logger.error("解析execl错误",e);
            e.printStackTrace();
            ThirdPartyMessage m = new ThirdPartyMessage("fail","","导入失败,解析execl错误");
            model.addAttribute("message", JSONArray.fromObject(m).toString());
            return "redirect:/thirdPartyImport/toImport";

        }
        dealMultiOrderData(map, multiChannelMap);

        List<ThirdPartyMessage> tmlist = new ArrayList<ThirdPartyMessage>();
        tmlist.addAll(thirdMsglist);
        for(String str :map.keySet()){
            ThirdPartyMessage m = null;
            if(map.get(str)!=null){
	            try{
	            	if(map.get(str)!=null){		
	            		String userId =ThirdTypeEnum.getUserId(map.get(str).getOrderType()).toString();
	            		m =  thirdPartyOrderService.saveOrder(map.get(str),userId);
	            	}
	            }catch (Exception e){
	                m = new ThirdPartyMessage("fail","","导入失败,可能原因库存不足,第三方订单ID="+str.substring(0, str.indexOf("_")));
	            }
	            tmlist.add(m);
            }
        }
        int successnum = 0;
        int failnum = 0;
        String str = "";
        for(ThirdPartyMessage tm:tmlist){
            if(tm.getStatus().equals("success")){
                successnum++;
                logger.info("成功,"+tm.getMsg());
            }else{
                failnum++;
                logger.info("失败,"+tm.getMsg());
                str+=tm.getMsg()+";<br/>";
            }
        }
        ThirdPartyMessage m = new ThirdPartyMessage("success","","导入成功"+successnum+"单,失败"+failnum+"单"+"<br/>   "+str);
        model.addAttribute("message", JSONArray.fromObject(m).toString());

        return "redirect:/thirdPartyImport/toImport";
    }
	private void dealMultiOrderData(Map<String, ThirdParyDTO> ThirdParyDTOMap,
			Map<String, List<String>> multiChannelMap) {
		Iterator<String> iterator = multiChannelMap.keySet().iterator();
        while(iterator.hasNext()) {
        	String multiKey = iterator.next();
        	List<String> thirdChannelIdList = multiChannelMap.get(multiKey);
        	if (null == thirdChannelIdList || thirdChannelIdList.isEmpty() || thirdChannelIdList.size() <= 1) {
        		continue;
        	}
        	List<Long> skuIds = new ArrayList<Long>();
        	for (String tstr : thirdChannelIdList) {
        		ThirdParyDTO dto = ThirdParyDTOMap.get(tstr);
        		List<ThirdParyItemDTO> thirdParyItemDTOs = dto.getThirdParyItemDTOs();
        		for(ThirdParyItemDTO thirdParyItemDTO : thirdParyItemDTOs) {
        			skuIds.add(thirdParyItemDTO.getSkuId());
        		}
        	}
        	
        	Map<Long, B2cProductOrderShowDTO> map =  customerOrderApi.findSkuShowDtoBySkuIds(skuIds);
        	
        	Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
        	BigDecimal allPrice = BigDecimal.ZERO;
        	for (String tstr : thirdChannelIdList) {
        		ThirdParyDTO dto = ThirdParyDTOMap.get(tstr);
        		List<ThirdParyItemDTO> thirdParyItemDTOs = dto.getThirdParyItemDTOs();
        		BigDecimal totalPrice = BigDecimal.ZERO;
        		for(ThirdParyItemDTO thirdParyItemDTO : thirdParyItemDTOs) {
        			B2cProductOrderShowDTO bposDTO = map.get(thirdParyItemDTO.getSkuId());
                    if(bposDTO==null){
                    	continue;
                    }
                    
                    BigDecimal subTotalPrice = bposDTO.getPrice().multiply(BigDecimal.valueOf(thirdParyItemDTO.getNum()));
                    totalPrice = totalPrice.add(subTotalPrice);
        		}
        		
        		priceMap.put(tstr, totalPrice);
        		allPrice = allPrice.add(totalPrice);
        	}
        	
        	for (String tstr : thirdChannelIdList) {
        		ThirdParyDTO dto = ThirdParyDTOMap.get(tstr);
        		dto.setPrice(BigDecimal.valueOf(dto.getPrice()).multiply(priceMap.get(tstr)).divide(allPrice, 2, RoundingMode.HALF_UP).setScale(2).doubleValue());
        	}
        }
	}
    //校验excel的列是否有数据
    public Boolean checkCell(Row row,int num,String msg,List<ThirdPartyMessage> thirdMsglist){
    	Boolean flag=false;
    	if(row.getCell(num)==null ||"".equals(row.getCell(num).toString())||"null".equals(row.getCell(num).toString())){
    		flag=true;
    		ThirdPartyMessage m = new ThirdPartyMessage("fail","",msg);
        	thirdMsglist.add(m);
    	}
    	return flag;
    }
}
