package com.mall.search.elasticsearch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.search.utils.WordsConstants;

/**
 * 对查询的词语进行转换
 * @author zcc
 *
 */
public class WorkTransform {
	
		private static final  Logger LOGGER=LoggerFactory.getLogger(WorkTransform.class);

		public static String [] strChangeToArray(String sectorCodeName) {
			//进行同义词判断!~
			LOGGER.info("进行同义词判断!~");
			if(sectorCodeName.equals("美食") || sectorCodeName.equals("餐饮")){
				return WordsConstants.STR_CATE;
			}
			if(sectorCodeName.equals("便利店")){
				return WordsConstants.STR_STORE;
			}
			if(sectorCodeName.equals("超市")){
				return WordsConstants.STR_SUPERMARKET;
			}
			if(sectorCodeName.equals("商场")){
				return WordsConstants.STR_BUY;
			}
			if(sectorCodeName.equals("时尚购物")){
				return WordsConstants.STR_BUY;
			}
			if(sectorCodeName.equals("电影院")){
				return WordsConstants.STR_HAPPY;
			}
			if(sectorCodeName.equals("酒店")){
				return WordsConstants.STR_HOTEL;
			}
			if(sectorCodeName.equals("休闲娱乐")){
				return WordsConstants.STR_HAPPY;
			}
			if(sectorCodeName.equals("美容美发")){
			return WordsConstants.STR_BEAUTIFUL;
			}
			if(sectorCodeName.equals("景点")){
				return WordsConstants.STR_SCENIC;
			}
			if(sectorCodeName.equals("教育培训")){
				return WordsConstants.STR_EDUCATION;
			}
			if(sectorCodeName.equals("医疗")){
				return WordsConstants.STR_MEDICAL;
			}
			//如何没有匹配到就返回原来的查询条件
			String [] arr={sectorCodeName};
			return arr;
		}
	

		
}
