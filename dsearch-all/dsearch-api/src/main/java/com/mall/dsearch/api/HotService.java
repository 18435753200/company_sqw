package com.mall.dsearch.api;

import java.util.List;

public interface HotService {
	/**
	 * 通过页面首页直接调用此接口，返回搜索度最高的前几个词
	 * @return
	 */
public List<String> hotSearch();
}
