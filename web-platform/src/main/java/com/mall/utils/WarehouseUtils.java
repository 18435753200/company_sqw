package com.mall.utils;

import com.mall.dto.WofeWarehouseType;

public class WarehouseUtils {

	public static String getWofeWarehouseTypeValue(String id){
		if(WofeWarehouseType.beijing.getId().equals(id)){
			return WofeWarehouseType.beijing.getName();
		}
		if(WofeWarehouseType.milan.getId().equals(id)){
			return WofeWarehouseType.milan.getName();
		}
		return null;
	}
	
	public static String getWofeWarehouseTypeValueByName(String name){
		if(WofeWarehouseType.beijing.getName().equals(name)){
			return WofeWarehouseType.beijing.getId();
		}
		if(WofeWarehouseType.milan.getName().equals(name)){
			return WofeWarehouseType.milan.getId();
		}
		return null;
	}
}
