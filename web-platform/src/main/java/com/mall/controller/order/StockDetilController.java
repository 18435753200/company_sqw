package com.mall.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.mall.supplier.order.api.rpc.StockDetilService;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value="/Stock")
public class StockDetilController {
	/*@RequestMapping(value = "/getDetil")
	@ResponseBody
	public String getDetil(Model model){
		String result="";
		try {
			StockDetilService stockDetilService=(StockDetilService)RemoteServiceSingleton.getInstance().getAppService("stockDetilService");
			stockDetilService.insertStockInDetil();
			stockDetilService.insertStockOutDetil();
			result="调用成功！";
		} catch (Exception e) {
			e.printStackTrace();
			result="调用失败";
		}
		
		return result;
	}*/
}
