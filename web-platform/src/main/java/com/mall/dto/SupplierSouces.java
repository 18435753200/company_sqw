package com.mall.dto;

public enum SupplierSouces {

	TRADE("一般贸易", 1),OVERSEAS("海外直邮",11),BONDED("保税区发货",12),KOREA("韩国直邮",21), SALE("特卖商品", 50), POP("POP商品", 51);
    // 成员变量
    private String name;
    private int index;
    
 // 构造方法
    private SupplierSouces(String name, int index) {
        this.name = name;
        this.index = index;
    }
    
 // 普通方法
    public static String getName(int index) {
        for (SupplierSouces c : SupplierSouces.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    
    
 // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
