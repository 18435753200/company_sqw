/**
 * 
 */
package com.mall.controller.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.model.User;
import com.mall.customer.order.dto.CartActivityGroupVO;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartGroupVO;
import com.mall.customer.order.dto.CartSkuDTO;
import com.mall.customer.order.dto.CartSkuGroupDTO;
import com.mall.customer.service.UserService;
//import com.mall.promotion.dto.ruleSkuDto.RuleListDto;

/**
 * @author zhengqiang.shi
 * 2015年4月8日 下午5:30:23
 */
@Service
public class UserControllerImpl extends AbstractControllerImpl {

	@Autowired
	private UserService userService;
	
	/**
	 * 根据用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	public User findUserById(Long userId){
		// 打印请求参数
		map = new HashMap<Object, Object>();
		map.put("Method", "findUserById");
		map.put("userId", userId);
		print(map);
		
		// 获取用户
		return userService.findUserById(userId);
	}
	
	/**
	 * 检查用户是否已经实名认证
	 * @param userId
	 * @param cartDTO
	 * @return
	 */
	public boolean checkUserRealName(Long userId,CartDTO cartDTO){
		
		boolean check = false;
		
		List<CartGroupVO> CartGroupVO = cartDTO.getCartGroupVOList();
		for (int i = 0; i < CartGroupVO.size(); i++) {
			if(check){
				break;
			}
			//List<CartSkuDTO> skuList = CartGroupVO.get(i).getSkuList();
			List<CartActivityGroupVO> cartActivityGroupList = CartGroupVO.get(i).getActivityGroupList();
			for (CartActivityGroupVO cartActivityGroupVO : cartActivityGroupList) {
				List<CartSkuDTO> skuList = cartActivityGroupVO.getSkuList();
				for (int j = 0; j < skuList.size(); j++) {
					String productType = skuList.get(j).getProductType();
					
					// 商品类型为【11:保税区发货 或 12:海外直邮 或 21:韩国直邮 或 50：特卖 或51：POP】时 需要实名认证
					if(Arrays.asList("11","12","21","50","51").contains(productType)){
						check = true;
						break;
					}
				}
			}
		}
		
		if(check){
			// 打印请求参数
			map = new HashMap<Object, Object>();
			map.put("Method", "checkUserRealName");
			map.put("userId", userId);
			print(map);
			
			// 检查用户是否已经实名认证
			User user = findUserById(userId);
			return user == null ? false : user.isRealNameValidate();
		}
		
		return true;
	}
	
	/**
	 * 检查用户是否已经实名认证
	 * @param userId
	 * @return
	 */
	public boolean checkUserRealName(Long userId){
		
		// 打印请求参数
		map = new HashMap<Object, Object>();
		map.put("Method", "checkUserRealName");
		map.put("userId", userId);
		print(map);
		
		// 检查用户是否已经实名认证
		User user = findUserById(userId);
		return user == null ? false : user.isRealNameValidate();
		
	}
}
