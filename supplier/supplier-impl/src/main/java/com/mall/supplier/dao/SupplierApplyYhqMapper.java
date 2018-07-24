package com.mall.supplier.dao;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierApplyYhq;
import com.mall.supplier.model.SupplierApplyYhqExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierApplyYhqMapper {
    int countByExample(SupplierApplyYhqExample example);

    int deleteByExample(SupplierApplyYhqExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierApplyYhq record);

    int insertSelective(SupplierApplyYhq record);

    List<SupplierApplyYhq> selectByExample(SupplierApplyYhqExample example);

    SupplierApplyYhq selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierApplyYhq record, @Param("example") SupplierApplyYhqExample example);

    int updateByExample(@Param("record") SupplierApplyYhq record, @Param("example") SupplierApplyYhqExample example);

    int updateByPrimaryKeySelective(SupplierApplyYhq record);

    int updateByPrimaryKey(SupplierApplyYhq record);
    
    List<SupplierApplyYhq> getYhqPageList(PageBean<SupplierApplyYhq> pageBean);
    
    
}