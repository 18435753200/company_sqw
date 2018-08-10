/**
 * 
 */
package com.mall.controller.impl;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.constant.CommonConstant;
import com.mall.customer.model.User;
import com.mall.platform.model.PlatformComplaint;
import com.mall.platform.model.findPlatformManageDTO;
import com.mall.platform.service.PlatformComplaintService;
import com.mall.vo.CustomerSuggestVO;


/**
 * @author zhengqiang.shi
 * 2015年5月12日 上午11:51:41
 */
@Service
public class CustomerSuggestControllerImpl extends AbstractControllerImpl {

	private static final Logger log = LoggerFactory.getLogger(CustomerSuggestControllerImpl.class);

	@Autowired
	private PlatformComplaintService platformComplaintService;
	
	/**
	 * 保存反馈信息
	 * @param user
	 * @param customerSuggestVO
	 * @return
	 */
	public int addSuggest(User user,CustomerSuggestVO customerSuggestVO){
		// 打印请求参数
		map = new HashMap<Object, Object>();
		map.put("Method", "addSuggest");
		map.put("CustomerSuggestVO", customerSuggestVO.toString());
		print(map);
		
		// 保存反馈信息
		try {
			platformComplaintService.savePlatformComplaint(setSuggestInfo(user, customerSuggestVO));
		} catch (Exception e) {
			log.info("call service savePlatformComplaint failed!"+e.getMessage(),e);
			return 0;
		}
		return 1;
	}
	/**
	 * 查询反馈信息
	 * @param user
	 * @param customerSuggestVO
	 * @return
	 */
	public findPlatformManageDTO findSuggest(Integer userid,Integer states,Integer page){
		
		
		// 查询反馈信息 id 状态0  1 不传页数
		findPlatformManageDTO findPlatformManage;
		try {
			findPlatformManage = platformComplaintService.findPlatformManage(userid,states, page);
			return	findPlatformManage ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		return null;
			
			
		
	}
	/**
	 * 根据id查询反馈信息
	 * @param user
	 * @param customerSuggestVO
	 * @return
	 */
	public PlatformComplaint findSuggestById(Long id){
		
		
		// 查询反馈信息 id 状态0  1 不传页数
		PlatformComplaint findPlatformManage;
		try {
			findPlatformManage = platformComplaintService.findByComplaintId(id);
			return	findPlatformManage ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		return null;
		
		
		
	}
	
	private PlatformComplaint setSuggestInfo(User user,CustomerSuggestVO customerSuggestVO){
		PlatformComplaint platformComplaint = new PlatformComplaint();
		
		platformComplaint.setComplaintSource(CommonConstant.NUMBER_3);
		platformComplaint.setReferenceId(user.getUserId());
		platformComplaint.setComplaintBy(user.getUserName());
		platformComplaint.setComplaintContent(customerSuggestVO.getSuggestContent());
		platformComplaint.setContactWay(customerSuggestVO.getSuggestContactWay());
		platformComplaint.setComplaintType(customerSuggestVO.getSuggestType());
		platformComplaint.setComplaintTime(new Date());
//		platformComplaint.setUserComplaintTime(customerSuggestVO.getSuggestTime());
		
		return platformComplaint;
	}
	
}
