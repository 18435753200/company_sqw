package com.mall.vo;

import java.math.BigDecimal;

public class WealthVO {
	private BigDecimal arrivedFlag;      //可用的红旗券
	private BigDecimal unArrivedFlag;    //未到账的红旗券
	private BigDecimal moneyReward;      //可用分红额度
	private BigDecimal consumptionAmount;//我的消费金额
	private BigDecimal consumptionFlag;  //消费红旗券
	private BigDecimal transferReward;   // 可转账额度

	public BigDecimal getArrivedFlag() {
		return arrivedFlag;
	}

	public void setArrivedFlag(BigDecimal arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}

	public BigDecimal getUnArrivedFlag() {
		return unArrivedFlag;
	}

	public void setUnArrivedFlag(BigDecimal unArrivedFlag) {
		this.unArrivedFlag = unArrivedFlag;
	}

	public BigDecimal getMoneyReward() {
		return moneyReward;
	}

	public void setMoneyReward(BigDecimal moneyReward) {
		this.moneyReward = moneyReward;
	}

	public BigDecimal getConsumptionAmount() {
		return consumptionAmount;
	}

	public void setConsumptionAmount(BigDecimal consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}

	public BigDecimal getConsumptionFlag() {
		return consumptionFlag;
	}

	public void setConsumptionFlag(BigDecimal consumptionFlag) {
		this.consumptionFlag = consumptionFlag;
	}

	public BigDecimal getTransferReward() {
		return transferReward;
	}

	public void setTransferReward(BigDecimal transferReward) {
		this.transferReward = transferReward;
	}

}
