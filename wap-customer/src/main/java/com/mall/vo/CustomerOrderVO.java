/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.mall.check.OrderChecks.submitOrderChecks;

/**
 * @author zhengqiang.shi
 * 2015年4月1日 上午10:23:33
 */
public class CustomerOrderVO implements Serializable{
	
	private static final long serialVersionUID = -4866360753002655423L;
	
	private List<Long> skuIdList;	// 商品规格ID集合
	
	private Map<Long,String> orderPromoteRelationsMap = new HashMap<Long, String>();	// 优惠券map
	
	private Long addressId;	// 收货地址ID
	
	private Short needInvoice;// 是否需要发票 0:不需要 1:需要
	
	 //发票抬头
    private String invoiceTitle;
    
    //发票明细
    private String invoiceDetail;
	
	private String message;	//订单备注
	
	private Boolean isCheck=true;	// 是否校验赠品库存
	
	private String orderFrom;	// 订单来源 cookie中获取 翼支付：bestpay cps：LTINFO 领克特 YIQIFAWAP 亿起发 
	
    private String cpsMemId; //cps联盟会员id 领克特 对应a_id 亿起发对应wi

	private String cpsChannel; //渠道 亿起发对应 wap 或 其他渠道，wap端为：wap

    private String mType;  //广告投放方式 领克特：1cpa 2 cps 本次为 2  亿起发：默认为2 cps

    private String lType; //广告类型  领克特对应：l_type1，亿起发对应：cid

    private String siteType;//推广站点类型  b2b b2c
    
    private String cpsCookie;//cps Cookie

	public Map<Long, String> getOrderPromoteRelationsMap() {
		return orderPromoteRelationsMap;
	}

	public void setOrderPromoteRelationsMap(
			Map<Long, String> orderPromoteRelationsMap) {
		this.orderPromoteRelationsMap = orderPromoteRelationsMap;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	@Range(min=0,max=1,message="{CCIGMALL-100002.filed}",groups={submitOrderChecks.class})
	public Short getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Short needInvoice) {
		this.needInvoice = needInvoice;
	}
	
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={submitOrderChecks.class})
	@Size(min=1,message="{CCIGMALL-100001.filed}",groups={submitOrderChecks.class})
	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={submitOrderChecks.class})
	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	 public String getCpsMemId() {
			return cpsMemId;
		}

		public void setCpsMemId(String cpsMemId) {
			this.cpsMemId = cpsMemId;
		}

		public String getCpsChannel() {
			return cpsChannel;
		}

		public void setCpsChannel(String cpsChannel) {
			this.cpsChannel = cpsChannel;
		}

		public String getmType() {
			return mType;
		}

		public void setmType(String mType) {
			this.mType = mType;
		}

		public String getlType() {
			return lType;
		}

		public void setlType(String lType) {
			this.lType = lType;
		}

		public String getSiteType() {
			return siteType;
		}

		public void setSiteType(String siteType) {
			this.siteType = siteType;
		}
		
		public String getCpsCookie() {
			return cpsCookie;
		}

		public void setCpsCookie(String cpsCookie) {
			this.cpsCookie = cpsCookie;
		}
}