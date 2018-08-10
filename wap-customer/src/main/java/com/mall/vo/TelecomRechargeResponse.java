/**
 * 
 */
package com.mall.vo;

/**
 * @author zhengqiang.shi
 * 2016-4-26 下午6:07:45
 */
public class TelecomRechargeResponse implements java.io.Serializable{

	private static final long serialVersionUID = -529647433155431974L;

	private Head head;
	
	private Biz biz;
	
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Biz getBiz() {
		return biz;
	}

	public void setBiz(Biz biz) {
		this.biz = biz;
	}

	public class Head implements java.io.Serializable{

		private static final long serialVersionUID = -1089456788044308110L;

		// 报文流水号,生成规则：系统编码（6位）+应用编码（6位）+时间（YYMMDDHHMM，10位）+ GUID（32位），共54位。
		private String transactionId;
		
		// 请求发起时间，格式：yyyyMMDD HH24:MI:SS。
		private String reqTime;
		
		private String code;
		
		private String err;
		
		private String attach;

		public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

		public String getReqTime() {
			return reqTime;
		}

		public void setReqTime(String reqTime) {
			this.reqTime = reqTime;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getErr() {
			return err;
		}

		public void setErr(String err) {
			this.err = err;
		}

		public String getAttach() {
			return attach;
		}

		public void setAttach(String attach) {
			this.attach = attach;
		}
		
	}
	
	public class Biz implements java.io.Serializable{

		private static final long serialVersionUID = 3959642205969526262L;
		
	}
}
