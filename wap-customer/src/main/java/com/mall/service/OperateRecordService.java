package com.mall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.dto.UserOperationRecordDto;
import com.mall.customer.model.User;
import com.mall.customer.service.UserOperationRecordService;
import com.mall.mybatis.utility.PageBean;

/**
 * 用户操作
 * 
 * @author cyy
 * 
 */
@Service
public class OperateRecordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperateRecordService.class);

	@Autowired
	private UserOperationRecordService userOperationRecordService;


	/**
	 * 获取用户操作记录
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public PageBean<UserOperationRecordDto> operate(User userInfo, String type,Integer page, Integer pageSize){
		try {
			UserOperationRecordDto userOperationRecordDto = new UserOperationRecordDto();
			userOperationRecordDto.setUserId(userInfo.getUserId().intValue());
			userOperationRecordDto.setDelFlag("0");
			userOperationRecordDto.setOperationType(type);
			PageBean<UserOperationRecordDto> pageBean = userOperationRecordService.findUserOperationRecordByListPage(
					userOperationRecordDto, page, pageSize);
			return pageBean;
		} catch (Exception exception) {
			LOGGER.error("获取用户操作记录失败", exception);
			throw new RuntimeException(exception);
		}
	}
}
