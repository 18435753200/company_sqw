package com.mall.supplier.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierStore;

public interface SupplierStoreMapper {
	
    
    int insertSelective(SupplierStore supplierStore);

    
    SupplierStore findSupplierStoreBySupplierId(int spplierId);


    /**
     * 保存模版预览数据
     * @param spplierId
     * @param previewContent
     */
    public void updateSupplierStorePreview(@Param("supplierId") int spplierId,@Param("storeName") String storeName,@Param("previewContent") String previewContent);


    /**
     * 保存模版线上数据
     * @param spplierId
     * @param onlineContent
     */
    public void updateSupplierStoreOnline(@Param("supplierId") int spplierId,
    									  @Param("storeName") String storeName,
                                          @Param("onlineContent") String onlineContent,
                                          @Param("updateBy") int updateBy);

    /**
     * 设置商店的模版
     * @param supplierId
     * @param templateId
     */
    public void updateStoreTemplate(@Param("supplierId") int supplierId,@Param("templateId") int templateId);

}