/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.mall.check.SuggestChecks.addSuggestChecks;

/**
 * @author zhengqiang.shi 2015年5月12日 下午12:05:59
 */
public class CustomerSuggestVO implements Serializable {

	private static final long serialVersionUID = 2167467984987824790L;

	@Range(min = 1, max = 7, message = "{CCIGMALL-100003.filed}", groups = { addSuggestChecks.class })
	private int suggestType; // 反馈类型

	@NotNull(message = "{CCIGMALL-100000.filed}", groups = { addSuggestChecks.class })
	@Length(min = 1, max = 500, message = "{CCIGMALL-100004.filed}", groups = { addSuggestChecks.class })
	private String suggestContent; // 反馈内容

	@NotNull(message = "{CCIGMALL-100000.filed}", groups = { addSuggestChecks.class })
	@Pattern(regexp = "(^1[0-9]{10}$)|(^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)", message = "{CCIGMALL-100005.filed}", groups = {
			addSuggestChecks.class })
	private String suggestContactWay;// 反馈人联系方式，手机号或邮箱
	private String suggestTime;// 反馈时间

	public int getSuggestType() {
		return suggestType;
	}

	public void setSuggestType(int suggestType) {
		this.suggestType = suggestType;
	}

	public String getSuggestContent() {
		return suggestContent;
	}

	public void setSuggestContent(String suggestContent) {
		this.suggestContent = suggestContent;
	}

	public String getSuggestContactWay() {
		return suggestContactWay;
	}

	public void setSuggestContactWay(String suggestContactWay) {
		this.suggestContactWay = suggestContactWay;
	}

	public String getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(String suggestTime) {
		this.suggestTime = suggestTime;
	}

	@Override
	public String toString() {
		return "[suggestType=" + suggestType + ", suggestContent=" + suggestContent + ", suggestContactWay="
				+ suggestContactWay + "]";
	}

}
