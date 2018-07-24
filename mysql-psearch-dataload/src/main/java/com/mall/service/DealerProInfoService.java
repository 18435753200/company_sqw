package com.mall.service;

import java.util.List;

import com.mall.po.DealerProInfo;

/**
 *  经销商商品服务类
 * 
 */
public interface DealerProInfoService {

	/**
	 * 获取产品信息
	 * 
	 * @param pid
	 * @param l2sid
	 */
	public List<DealerProInfo> getDealerProInfo(String pid);

}
