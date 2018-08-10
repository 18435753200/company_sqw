package com.mall.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class prizeDataVO implements Serializable {

	private String startTime;
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
