package com.mall.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class StartupDto implements Serializable {

	private Long id;
	
	private String title;
	
	private String imgUrl;
	
	private String type;
	
	private String jumpLink;
	
	private Long displayDuration;
	
	private String startTime;
	
	private String endTime;
	
	private String platform;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJumpLink() {
		return jumpLink;
	}

	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}

	public Long getDisplayDuration() {
		return displayDuration;
	}

	public void setDisplayDuration(Long displayDuration) {
		this.displayDuration = displayDuration;
	}

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
