package com.mall.supplier.service;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierRegionSettings;

import java.util.List;

/**
 * Created by Zhutaoshen on 2016/12/30 0030.
 */
public interface SupplierRegionService {

    /**
     * 获得所有区域设置
     * @return
     */
    public List<SupplierRegionSettings> getAllRegionSettings();

    /**
     * 获得所有区域设置-条件查询带分页
     * @return
     */
    public List<SupplierRegionSettings> getRegionSettings(PageBean<SupplierRegionSettings> regionSettingsPageBean);
    public List<SupplierRegionSettings> getRegionSettingsxfg(PageBean<SupplierRegionSettings> regionSettingsPageBean);
    
    /**
     * @Author Zhutaoshen
     * @Date 2017/1/12 0012 20:10
     * 根据企业ID获得区域设置
     * @param supplierID
     * @return
     */
    public SupplierRegionSettings getRegionSettingsBySupplierID(Long supplierID);
    
    public SupplierRegionSettings getRegionSettingsBySupplierIDxfg(Long supplierID);
    
    /**
     * 根据区域ID得到区域设置
     * @param regionID
     * @return
     */
    public SupplierRegionSettings getRegionSettingsByID(int regionID);
    public SupplierRegionSettings getRegionSettingsByIDxfg(int regionID);

    /**
     * 新增一个区域设置
     * @param regionSettings
     * @return
     */
    public int insertRegionSettings(SupplierRegionSettings regionSettings);
    public int insertRegionSettingsxfg(SupplierRegionSettings regionSettings);

    /**
     * 修改一个区域设置
     * @param regionSettings
     * @return
     */
    public int updateRegionSettings(SupplierRegionSettings regionSettings);

    /**
     * 删除一个区域设置
     * @param regionSettings
     * @return
     */
    public int deleteRegionSettings(SupplierRegionSettings regionSettings);
    public int deleteRegionSettingsxfg(SupplierRegionSettings regionSettings);

	public int updateRegionSettingsxfg(SupplierRegionSettings regionSettings);

}
