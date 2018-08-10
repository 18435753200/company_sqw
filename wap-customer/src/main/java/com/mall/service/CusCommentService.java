package com.mall.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.comment.api.rpc.CommentService;
import com.mall.comment.dto.request.RequestDTO;
import com.mall.comment.dto.response.ResponseDTO;
import com.mall.comment.po.Comment;
import com.mall.comment.po.OrderInfo;
import com.mall.comment.po.UserInfo;
import com.mall.constant.CommonConstant;
import com.mall.customer.model.User;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.vo.CommentForm;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 评论有关的服务
 * 
 * @author ccigQA01
 * 
 */
@Service
public class CusCommentService {
	/**
	 * 日志打印
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CusCommentService.class);

	@Autowired
	private MemcachedClient memcachedClient;

	/*
	 * 评论服务
	 */
	@Resource
	private CommentService commentService;

	/**
	 * 提交评论
	 * 
	 * @param orderId
	 * @param dto
	 * @param pid
	 * @return
	 */
	public String saveComment(Long orderId, Integer isAnonymity,
			OrderItemDTO dto, Comment comment, User currentUser) {

		comment.setDate(new Date());
		comment.setId(dto.getId());
		// 0,pc.1,android.2,iphone.3,wap.4,零售商pad,5,零售商pc
		comment.setFromType("3");
		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBuyDate(dto.getCreateTime());
		orderInfo.setOrderId(dto.getOrderId());
		orderInfo.setPid(dto.getPid());
		orderInfo.setpName(dto.getpName());
		orderInfo.setSkuTotalNum(dto.getSkuQty());

		comment.setOrderInfo(orderInfo);
		// 设置评论人信息
		UserInfo userInfo = new UserInfo();
		userInfo.setIsComment(1);// 1评论 2 回复
		userInfo.setUserId(currentUser.getUserId());
		userInfo.setUserName(currentUser.getUserName());
		userInfo.setUserPic(currentUser.getIcon());
		userInfo.setIsAnonymity(isAnonymity);

		comment.setUserInfo(userInfo);

		String res = "ok";
		try {
			commentService.saveComment(comment);
			
		} catch (Exception e) {
			LOGGER.error("call commentService.saveCommen error");
			res = "error";
			e.printStackTrace();
		}
		return res;

	}

	/**
	 * 保存评论列表
	 * 
	 * @param comment
	 * @param orderId
	 * @param user
	 * @return
	 */
	public ResponseDTO saveComments(CommentForm comment, User user, int no) {
		RequestDTO request = new RequestDTO();
		request.setComments(comment.getComments());
		request.setIsAnon(Integer.parseInt(comment.getIsAnon()));
		// 3:wap
		request.setFromType(String.valueOf(CommonConstant.NUMBER_3));
		request.setOrderId(comment.getOrderId());
		// 0:B2B 1:B2C
		request.setPlatformType(String.valueOf(CommonConstant.NUMBER_1));
		request.setUserId(user.getUserId());
		request.setUserName(user.getUserName());
		ResponseDTO response = null;
		if (no == 0) {
			response = commentService.saveComments(request);
		}
		if (no == 1) {
			response = commentService.saveAdditionalComments(request);
		}

		LOGGER.info("评论提交结果 code: [{}] message:[{}]", response.getCode(),
				response.getMessage());
		return response;
	}

	/**
	 * 根据orderId 获取 这个单下所有子订单的 评论
	 * 
	 * @param orderId
	 * @param currentUser
	 * @return
	 */
	public List<Comment> findCommentByOrderId(Long orderId, User currentUser) {
		List<Comment> comments = null;
		try {
			comments = RemoteServiceSingleton.getInstance().getCommentApi().findCommentListByOrderId(orderId);
			for(Comment ct :comments){
				
			}
		} catch (Exception e) {
			LOGGER.error("call commentService.findCommentListByOrderId error");
			return comments;
		}

		return comments;
	}

}
