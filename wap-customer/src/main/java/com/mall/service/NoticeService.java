package com.mall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.dto.CNoticePageDto;
import com.mall.customer.model.CNotice;
import com.mall.customer.service.UserService;

/**
 * 收货人地址
 * 
 * @author ccigQA01
 * 
 */
@Service
public class NoticeService {
	private static final Logger log = LoggerFactory.getLogger(NoticeService.class);

	@Autowired
	private UserService userService;

	/**
	 * 获取公告信息一条
	 * 
	 * 
	 * @return
	 */
	public CNotice info(Integer id) {
		try {
			return userService.getNotice(id);
		} catch (Exception e) {
			log.error("info error ....");
			throw new RuntimeException("call getNotice error", e);
		}
	}
	/**
	 * 获取公告信息多条
	 * 
	 * 
	 * @return
	 */
	public CNoticePageDto infolist(Integer page) {
		try {
			
			return userService.getlistCNotice(page);
		} catch (Exception e) {
			log.error("info error ....");
			throw new RuntimeException("call getNotice error", e);
		}
	}
	/**
	 * 获取公告信息加载更多
	 * 
	 * 
	 * @return
	 */
	public CNoticePageDto infolistmore() {
		try {
			Integer page = 1;
			return userService.getlistCNotice(page);
		} catch (Exception e) {
			log.error("info error ....");
			throw new RuntimeException("call getNotice error", e);
		}
	}

}
