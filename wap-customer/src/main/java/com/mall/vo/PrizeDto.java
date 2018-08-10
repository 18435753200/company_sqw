package com.mall.vo;


import java.io.Serializable;

/**
 * 奖品表
 * Created by menghl on 2016/3/15.
 */
public class PrizeDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *主键 id
     */
    private String prizeId;
    /**
     * 奖品等级
     */
    private String level;
    /**
     * 奖品标题
     */
    private String title;
    /**
     * 奖品内容
     */
    private String content;
    /**
     * 奖品数量
     */
    private long number;
    /**
     * 状态 （1 启用 0 禁用）
     */
    private String status;
    /**
     * 奖品图片地址 （全路径）
     */
    private String prizeImg;
    /**
     * 类型  （1 实物，2 优惠券） type
     */
    private String type;

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg == null ? null : prizeImg.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}
