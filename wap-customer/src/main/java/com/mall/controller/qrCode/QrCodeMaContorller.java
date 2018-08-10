/*package com.mall.controller.qrCode;

public class QrCodeMaContorller {

}*/
package com.mall.controller.qrCode;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mall.annotation.AuthPassport;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.CartControllerImpl;
import com.mall.controller.myccigmall.CustomerOrderPayController;
import com.mall.customer.model.BizRcode;
import com.mall.customer.model.User;
import com.mall.customer.model.WxUser;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.po.CreateFalseOrder;
import com.mall.customer.order.utils.PKUtils;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductService;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.vo.CustomerCartVO;
@Controller
@RequestMapping(value="/QrCodeMaContorller")
public class QrCodeMaContorller extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private CustomerOrderService customerOrderService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;
	@Autowired
	private SupplierDiscountService supplierDiscountService;
	@Autowired
	private CartControllerImpl cartControllerImpl;
	@Autowired
	private DealerProductService dealerProductService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	/**
	 * 跳转付款二维码页面
	 * rcodeType字段 0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
	 * @return
	 */
	@AuthPassport
	@RequestMapping("/qrCode")
	public String qrCode(Model model,HttpServletRequest request,Integer rcodeType){
		User user = getCurrentUser(request);
		if(user==null||"".equals(user)){
			return ResponseContant.LOGIN;
		}else{
			if(user.getSupplierId()=="0"||"0".equals(user.getSupplierId())){
				return "/qrcode/notMall";
			}
		}
		model.addAttribute("rcodeType", rcodeType);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("sId", user.getSupplierId());
		return "/qrcode/qrCode";
	}
	/**
	 * 二维码页面统一回调
	 * @param bizid,rcodeType
	 * 用户Id,二维码类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createSelectQr")
	public BizRcode createSelectQr(String bizid,Integer rcodeType){
		BizRcode bizRcode = new BizRcode();
		if(!"".equals(rcodeType)&&null!=rcodeType){
			//0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
			if(rcodeType==0||rcodeType.equals(0)){
				bizRcode=userService.myRcodeByFortoUser(bizid);
			}else if(rcodeType==1||rcodeType.equals(1)){
				bizRcode=userService.myRcodeByFortoM(bizid);
			}else if(rcodeType==2||rcodeType.equals(2)){
				bizRcode=userService.myRcodeByFortoPay(bizid);
			}else if(rcodeType==3||rcodeType.equals(3)){
				bizRcode=userService.myRcodeByFortoPresent(bizid);
			}else if(rcodeType==4||rcodeType.equals(4)){
				bizRcode=userService.myRcodeByFortoDiscount(bizid);
			}else if(rcodeType==5||rcodeType.equals(5)){
				bizRcode=userService.myRcodeByFortoCash(bizid);
			}
		}
		return bizRcode;
	}
	/**
	 * 跳转二维码页面
	 * rcodeType字段 0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
	 * @return
	 */
	@AuthPassport
	@RequestMapping("/thqrCode")
	public String thqrCode(Model model,HttpServletRequest request,Integer rcodeType){
		User user = getCurrentUser(request);
		model.addAttribute("rcodeType", rcodeType);
		model.addAttribute("userId", user.getUserId());
		return "/myccig/wealth/qrcode";
	}
	
}

