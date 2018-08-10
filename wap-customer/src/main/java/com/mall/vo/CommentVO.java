/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;

import com.mall.comment.po.Comment;
import com.mall.customer.order.dto.OrderItemDTO;

/**
 * @author jianping.gao
 *
 */
public class CommentVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689504065235556526L;

	/**
	 * 
	 */
	private OrderItemDTO orderItem;

	/**
	 * 
	 */
	private Comment comment;

	/**
	 * @return the orderItem
	 */
	public OrderItemDTO getOrderItem() {
		return orderItem;
	}

	/**
	 * @param orderItem
	 *            the orderItem to set
	 */
	public void setOrderItem(OrderItemDTO orderItem) {
		this.orderItem = orderItem;
	}

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
