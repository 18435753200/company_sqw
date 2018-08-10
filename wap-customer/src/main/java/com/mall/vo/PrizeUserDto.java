package com.mall.vo;


import java.io.Serializable;

/**
 * 抽奖用户
 * Created by menghl on 2016/3/15.
 */
public class PrizeUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String prizeUserId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户手机号（完整手机号，前端抽奖记录隐藏四位）
     */
    private String phone;

    /**
     * 抽奖次数 （每次递减）
     */
    private Integer number;

    /**
     * 抽奖标志位 0,1
     */
    private String status;

    public String getPrizeUserId() {
        return prizeUserId;
    }

    public void setPrizeUserId(String prizeUserId) {
        this.prizeUserId = prizeUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}
