package com.mall.supplier.enums;

/**
 * Created by Zhutaoshen on 2016/12/26 0026.
 */
public enum SupplierType {
    NonSelfOwendEnterprises("非自营企业",0),Subsidiary("子公司",1),ChainEnterprises("连锁企业",2),ChainSubsidiary("连锁子公司",3),SelfOwendEnterprises("自营企业",4),ProjectIndustry("项目产业",5),CudyomrtIndustry("会员企业",10);

    SupplierType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static SupplierType getSupplierType(int value){
        for (SupplierType st: SupplierType.values()) {
            if(st.getValue()==value){
                return st;
            }
        }
        return null;
    }

    public static String getSupplierTypeName(int value){
        for (SupplierType st: SupplierType.values()) {
            if(st.getValue()==value){
                return st.getName();
            }
        }
        return null;
    }
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}