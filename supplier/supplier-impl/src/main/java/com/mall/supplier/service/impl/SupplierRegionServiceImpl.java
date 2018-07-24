package com.mall.supplier.service.impl;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.annotation.ArgsLog;
import com.mall.supplier.dao.SupplierRegionSettingsMapper;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRegionService;

import org.apache.ibatis.annotations.Arg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zhutaoshen on 2016/12/30 0030.
 */
@Service
@Transactional
public class SupplierRegionServiceImpl implements SupplierRegionService {
    @Autowired
    SupplierRegionSettingsMapper regionSettingsMapper;

    @Autowired
    SupplierManagerService supplierManagerService;

    private final Logger logger = Logger.getLogger(SupplierRegionServiceImpl.class);

    @Override @ArgsLog
    public List<SupplierRegionSettings> getAllRegionSettings() {
        return regionSettingsMapper.selectAll();
    }

    @Override @ArgsLog
    public SupplierRegionSettings getRegionSettingsByID(int regionID) {
        return regionSettingsMapper.selectByPrimaryKey(regionID);
    }
    @Override @ArgsLog
    public SupplierRegionSettings getRegionSettingsByIDxfg(int regionID) {
    	return regionSettingsMapper.selectByPrimaryKeyxfg(regionID);
    }
    @Override @ArgsLog
    public int insertRegionSettings(SupplierRegionSettings regionSettings) {
        return regionSettingsMapper.insertSelective(regionSettings);
    }
    @Override @ArgsLog
    public int insertRegionSettingsxfg(SupplierRegionSettings regionSettings) {
    	return regionSettingsMapper.insertSelectivexfg(regionSettings);
    }

    @Override @ArgsLog
    public int updateRegionSettings(SupplierRegionSettings regionSettings) {
        return regionSettingsMapper.updateByPrimaryKeySelective(regionSettings);
    }
    @Override @ArgsLog
    public int updateRegionSettingsxfg(SupplierRegionSettings regionSettings) {
    	return regionSettingsMapper.updateByPrimaryKeySelectivexfg(regionSettings);
    }
 

    @Override @ArgsLog
    public int deleteRegionSettings(SupplierRegionSettings regionSettings) {
        // 检查是否允许删除if not return -1;
        List<Supplier>  supplierList = supplierManagerService.findAllSupplierByCommpanyQy(regionSettings.getRegionId().toString());
        if(supplierList.size()>0) return -1;
        return regionSettingsMapper.deleteByPrimaryKey(regionSettings.getRegionId());
    }
    @Override @ArgsLog
    public int deleteRegionSettingsxfg(SupplierRegionSettings regionSettings) {
    	// 检查是否允许删除if not return -1;
    	List<Supplier>  supplierList = supplierManagerService.findAllSupplierByCommpanyQy(regionSettings.getRegionId().toString());
    	if(supplierList.size()>0) return -1;
    	return regionSettingsMapper.deleteByPrimaryKeyxfg(regionSettings.getRegionId());
    }

    @Override @ArgsLog
    public List<SupplierRegionSettings> getRegionSettings(PageBean<SupplierRegionSettings> regionSettingsPageBean) {
        return regionSettingsMapper.selectByPageList(regionSettingsPageBean);
    }
    @Override @ArgsLog
    public List<SupplierRegionSettings> getRegionSettingsxfg(PageBean<SupplierRegionSettings> regionSettingsPageBean) {
    	return regionSettingsMapper.selectByPageListxfg(regionSettingsPageBean);
    }

    @Override @ArgsLog
    public SupplierRegionSettings getRegionSettingsBySupplierID(Long supplierID) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierID);
        supplier = supplierManagerService.getSupplier(supplier);
        if(supplier.getCompanyQy()==null){
            return null;
        }else{
            try {
                return this.getRegionSettingsByID(Integer.parseInt(supplier.getCompanyQy()));
            }catch (Exception e){
                logger.error("根据企业ID获取区域设置信息失败："+e.getMessage());
                return null;
            }
        }
    }
    @Override @ArgsLog
    public SupplierRegionSettings getRegionSettingsBySupplierIDxfg(Long supplierID) {
    	Supplier supplier = new Supplier();
    	supplier.setSupplierId(supplierID);
    	supplier = supplierManagerService.getSupplier(supplier);
    	if(supplier.getCompanyQy()==null){
    		return null;
    	}else{
    		try {
    			return this.getRegionSettingsByIDxfg(Integer.parseInt(supplier.getCompanyQy()));
    		}catch (Exception e){
    			logger.error("根据企业ID获取区域设置信息失败："+e.getMessage());
    			return null;
    		}
    	}
    }
}
