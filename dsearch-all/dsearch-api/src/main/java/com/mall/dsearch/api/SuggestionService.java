package com.mall.dsearch.api;

import java.util.List;

import com.mall.dsearch.vo.Suggestion;
import com.mall.dsearch.vo.SuggestionRequest;

public interface SuggestionService {

	/**
	 * @param suggestion 查询的关键词
	 * @return 检索后的数据
	 * 
	 * */
	public List<Suggestion> suggestionSearch(SuggestionRequest suggestion);
}
