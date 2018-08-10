/**
 * 
 */
package com.mall.controller.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.annotation.AuthPassport;
import com.mall.comment.api.rpc.CommentService;
import com.mall.comment.dto.response.ResponseDTO;
import com.mall.comment.po.Comment;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
//import com.mall.kefu.util.ValidateUtil;
import com.mall.service.CusCommentService;
import com.mall.service.CusOrderService;
import com.mall.utils.ValidateUtil;
import com.mall.vo.CommentForm;
import com.mall.vo.CommentVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * @author jianping.gao
 *
 */
@Controller
@RequestMapping(RequestContant.COMMENT)
public class OrderCommentController extends BaseController {

	/**
	 * 日志打印
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderCommentController.class);
	/**
	 * 评论服务
	 */
	@Autowired
	private CusCommentService cusCommentService;

	/**
	 * 订单服务
	 */
	@Autowired
	private CusOrderService cusOrderService;

	/**
	 * 提交评论
	 * 
	 * @param comments
	 * @param isAnon
	 * @param orderId
	 * @param no
	 *            0:添加评论 1：追加评论
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.COMMIT
			+ "/{orderId:[\\d]+}-{no:[0|1]}", method = { RequestMethod.POST })
	@ResponseBody
	public String commitComments(HttpServletRequest request,
			CommentForm comment, @PathVariable("orderId") Long orderId,
			@PathVariable("no") int no) {
		comment.setOrderId(String.valueOf(orderId));
		if (comment.getIsAnon() == null) {
			comment.setIsAnon("1");
		}
		ResponseDTO response = cusCommentService.saveComments(comment,
				this.getCurrentUser(request), no);
		return response.getCode();
	}

	/**
	 * 添加评论列表
	 * 
	 * @param request
	 * @param orderId
	 * @param model
	 * @param no
	 *            0:添加评论 1：追加评论
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.GO
			+ "/{orderId:[\\d]+}-{no:[0|1|2]}", method = { RequestMethod.GET })
	public String toComments(HttpServletRequest request,
			@PathVariable("orderId") Long orderId, Model model,
			@PathVariable("no") Long no) {

		User currentUser = getCurrentUser(request);
		// 获取订单详情
		OrderDTO order = cusOrderService.findOrderByOrderId(orderId,
				currentUser);
		if (null == order) {
			LOGGER.info("订单详情为空    [{orderId}]", orderId);
			return ResponseContant.ERROR_500;
		}
		List<OrderItemDTO> orderItemDTOs = order.getOrderItemDTOs();
		if (null == orderItemDTOs || orderItemDTOs.size() == 0) {
			LOGGER.info("订单中商品数量为0  [{orderId}]", orderId);
			return ResponseContant.ERROR_500;
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("no", no);
		model.addAttribute("imageBaseURL", CommonConstant.FILESERVER5);
		List<CommentVO> commentVOs = new ArrayList<CommentVO>();
		// 去评论
		if (no == 0) {
			for (OrderItemDTO orderItem : orderItemDTOs) {
				// 剔除赠品
				if (orderItem.getProductType().equals(new Integer(1))) {
					continue;
				}
				CommentVO commentVO = new CommentVO();
				commentVO.setOrderItem(orderItem);
				commentVOs.add(commentVO);
			}
		} else {
			// 获取评价内容
			List<Comment> comments = RemoteServiceSingleton.getInstance().getCommentApi().findCommentListByOrderId(orderId);
			if(ValidateUtil.isEmpty(comments)){
				return ResponseContant.ERROR_500;
			}
			// 算法优化
			Map<Long, Comment> map = new HashMap<Long, Comment>();
			for (Comment comment : comments) {
				map.put(comment.getOrderInfo().getSkuId(), comment);
			}

			for (OrderItemDTO orderItem : orderItemDTOs) {
				// 剔除赠品
				if (orderItem.getProductType().equals(new Integer(1))) {
					continue;
				}
				CommentVO commentVO = new CommentVO();
				commentVO.setOrderItem(orderItem);
				commentVO.setComment(map.get(orderItem
						.getSkuId()));
				commentVOs.add(commentVO);
			}

		}
		model.addAttribute("commentVOs", commentVOs);
		return ResponseContant.COMMENT + ResponseContant.GO_COMMENT;
	}
}
