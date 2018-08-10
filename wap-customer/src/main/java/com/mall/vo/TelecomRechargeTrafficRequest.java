/**
 * 
 */
package com.mall.vo;

/**
 * @author zhengqiang.shi
 * 2016-4-26 下午6:07:45
 */
public class TelecomRechargeTrafficRequest implements java.io.Serializable{

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

	public class Head{
		// 系统编码
		private String sysCode;
		
		// 应用编码
		private String appCode;
		
		// 报文流水号,生成规则：系统编码（6位）+应用编码（6位）+时间（YYMMDDHHMM，10位）+ GUID（32位），共54位。
		private String transactionId;
		
		// 请求发起时间，格式：yyyyMMDD HH24:MI:SS。
		private String reqTime;
		
		// 接口类型编码
		private String method;
		
		// 接口版本号
		private Integer version;
		
		// 发起方自定义数据，服务侧在响应中原样返回
		private String attach;
		
		// 消息签名 格式：MD5 32位小写（报文流水号 + 由电渠平台分配的系统密钥）
		private String sign;

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

		public String getAppCode() {
			return appCode;
		}

		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}

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

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

		public String getAttach() {
			return attach;
		}

		public void setAttach(String attach) {
			this.attach = attach;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}
		
	}
	
	public class Biz{
		private String orderId;
		
		private String type;
		
		private String compCode;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getCompCode() {
			return compCode;
		}

		public void setCompCode(String compCode) {
			this.compCode = compCode;
		}

	}
}
