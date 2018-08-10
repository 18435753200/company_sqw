/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;
import java.util.List;

import com.mall.comment.dto.request.BaseComment;

/**
 * @author jianping.gao
 *
 */
public class CommentForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2397510121139391009L;

	/**
	 * 评论内容
	 */
	private List<BaseComment> comments;

	/**
	 * 0:匿名,1:不匿名
	 */
	private String isAnon;

	/**
	 * 
	 */
	private String orderId;

	/**
	 * @return the comments
	 */
	public List<BaseComment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(List<BaseComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the isAnon
	 */
	public String getIsAnon() {
		return isAnon;
	}

	/**
	 * @param isAnon
	 *            the isAnon to set
	 */
	public void setIsAnon(String isAnon) {
		this.isAnon = isAnon;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
