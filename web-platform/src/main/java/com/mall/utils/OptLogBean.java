/**   
* FileName :OptLogBean.java
* @Author :zhanglk
* @version :1.0  
* Date     :2015-3-18
*/
package com.mall.utils;

import java.io.Serializable;
import java.util.Date;

/** 
 * Title:用户操作日志bean
 * Description: 用户操作日志bean
 * @Author :zhanglk
 * @Date   :2015-3-18
 * @Version:1.0
 */
public class OptLogBean implements Serializable {

	/**   
	* serialVersionUID
	*   
	* @since Ver 1.0  
	*/
	private static final long serialVersionUID = 3573380315208359434L;
	//功能名称
	private String funtName;
	//操作用户编码
	private String optUserCode;
	//用户名
	private String userName;
	//部门ID
	private Integer deptId;
	//部门名称
	private String deptName;
	//lj拼音码
	private String admCode;
	//lj名称
	private String admName;
	//操作IP
	private String optIp;
	//操作时间
	private Date optDate;
	//操作功能url
	private String optUrl;
	//用户级别
	private String userLevel;
	//功能类别
	private String funtType;
	//是否记录功能日志
	private String isLog;
	
	
	/**   
	 * funtName   
	 *   
	 * @return the funtName
	 */
	public String getFuntName() {
		return funtName;
	}
	/**
	 * @param funtName 要设置的 funtName
	 */
	public void setFuntName(String funtName) {
		this.funtName = funtName;
	}
	/**   
	 * optUserCode   
	 *   
	 * @return the optUserCode
	 */
	public String getOptUserCode() {
		return optUserCode;
	}
	/**
	 * @param optUserCode 要设置的 optUserCode
	 */
	public void setOptUserCode(String optUserCode) {
		this.optUserCode = optUserCode;
	}
	/**   
	 * userName   
	 *   
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName 要设置的 userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**   
	 * deptId   
	 *   
	 * @return the deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId 要设置的 deptId
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**   
	 * deptName   
	 *   
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName 要设置的 deptName
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**   
	 * admCode   
	 *   
	 * @return the admCode
	 */
	public String getAdmCode() {
		return admCode;
	}
	/**
	 * @param admCode 要设置的 admCode
	 */
	public void setAdmCode(String admCode) {
		this.admCode = admCode;
	}
	/**   
	 * admName   
	 *   
	 * @return the admName
	 */
	public String getAdmName() {
		return admName;
	}
	/**
	 * @param admName 要设置的 admName
	 */
	public void setAdmName(String admName) {
		this.admName = admName;
	}
	/**   
	 * optIp   
	 *   
	 * @return the optIp
	 */
	public String getOptIp() {
		return optIp;
	}
	/**
	 * @param optIp 要设置的 optIp
	 */
	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}
	/**   
	 * optDate   
	 *   
	 * @return the optDate
	 */
	public Date getOptDate() {
		return optDate;
	}
	/**
	 * @param optDate 要设置的 optDate
	 */
	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}
	/**   
	 * optUrl   
	 *   
	 * @return the optUrl
	 */
	public String getOptUrl() {
		return optUrl;
	}
	/**
	 * @param optUrl 要设置的 optUrl
	 */
	public void setOptUrl(String optUrl) {
		this.optUrl = optUrl;
	}
	/**   
	 * userLevel   
	 *   
	 * @return the userLevel
	 */
	public String getUserLevel() {
		return userLevel;
	}
	/**
	 * @param userLevel 要设置的 userLevel
	 */
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	/**   
	 * funtType   
	 *   
	 * @return the funtType
	 */
	public String getFuntType() {
		return funtType;
	}
	/**
	 * @param funtType 要设置的 funtType
	 */
	public void setFuntType(String funtType) {
		this.funtType = funtType;
	}
	/**   
	 * isLog   
	 *   
	 * @return the isLog
	 */
	public String getIsLog() {
		return isLog;
	}
	/**
	 * @param isLog 要设置的 isLog
	 */
	public void setIsLog(String isLog) {
		this.isLog = isLog;
	}
}
