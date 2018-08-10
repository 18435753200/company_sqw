package com.mall.vo;


import java.io.Serializable;
import java.util.Date;

/**
 * 抽奖记录主表
 * Created by menghl on 2016/3/15.
 */
public class PrizeRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String recordId;
    /**
     *  用户id
     */
    private String userId;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 奖品id
     */
    private String prizeId;
    /**
     * 奖品标题
     */
    private String prizeTitle;
    /**
     * 是否中奖 (1 中奖 0 未中奖)
     */
    private String isPrize;
    /**
     * 奖品状态 0 抽中 1 领取 2 发放 （优惠券自动发放）
     */
    private String status;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 详细地址 带省市区拼接，返回移动截取)
     */
    private String aderess;
    /**
     * 领奖人姓名
     */
    private String ownName;
    /**
     * 领奖人电话
     */
    private String ownPhone;
    /**
     * 奖品图片地址 （全路径）
     */
    private String prizeImg;
    /**
     * 领奖时间
     */
    private Date createDate;
    /**
     * 奖品类型 （1 实物，2 优惠券）
     */
    private String type;
    /**
     * 获奖时间
     */
    private Date prizeDate;
    /**
     * 奖品发放时间
     */
    private Date grantPrizeDate;

    /**
     * 获奖来源 0:wap,1:app
     * @return
     */
    private String source;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId == null ? null : prizeId.trim();
    }

    public String getPrizeTitle() {
        return prizeTitle;
    }

    public void setPrizeTitle(String prizeTitle) {
        this.prizeTitle = prizeTitle == null ? null : prizeTitle.trim();
    }

    public String getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(String isPrize) {
        this.isPrize = isPrize == null ? null : isPrize.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAderess() {
        return aderess;
    }

    public void setAderess(String aderess) {
        this.aderess = aderess == null ? null : aderess.trim();
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName == null ? null : ownName.trim();
    }

    public String getOwnPhone() {
        return ownPhone;
    }

    public void setOwnPhone(String ownPhone) {
        this.ownPhone = ownPhone == null ? null : ownPhone.trim();
    }

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg == null ? null : prizeImg.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getPrizeDate() {
        return prizeDate;
    }

    public void setPrizeDate(Date prizeDate) {
        this.prizeDate = prizeDate;
    }

    public Date getGrantPrizeDate() {
        return grantPrizeDate;
    }

    public void setGrantPrizeDate(Date grantPrizeDate) {
        this.grantPrizeDate = grantPrizeDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}
