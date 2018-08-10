package com.mall.utils;

import java.io.Serializable;
import java.util.List;

public final class Page<T> implements Serializable{
	
		private static final long serialVersionUID = -1081725270632079438L;
		private int pageSize = 10;
		private long totalCount;
		private int totalPage;
		private int page = 1;
		private List<T> result;
		
		public Page(){
			
		}
		
		public Page(int pageSize)
		{
		  if (pageSize < 1)
		    this.pageSize = 10;
		  else {
		    this.pageSize = pageSize;
		  }
		  this.page = 1;
		}
		
		public int getPageSize() {
		  return this.pageSize;
		}
		
		public int getTotalPage() {
		  return this.totalPage;
		}
		
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
			
		}

		public long getTotalCount()
		{
		  return this.totalCount;
		}
		
		public void setTotalCount(long totalCount)
		{
		  this.totalCount = totalCount;
		  this.totalPage = (int)(this.totalCount / this.pageSize + (this.totalCount % this.pageSize == 0L ? 0 : 1));
		}
		
		public int getPage()
		{
		  return this.page <= 0 ? 1 : this.page;
		}
		
		public void setPage(int page)
		{
		  if (page < 1) {
		    this.page = 1;
		    return;
		  }
		  this.page = page;
		}
		
		public List<T> getResult()
		{
		  return this.result;
		}
		
		public void setResult(List<T> result)
		{
		  this.result = result;
		}
		
		public String toString() {
		  return "pageSize:" + this.pageSize + ",totalCount:" + this.totalCount + ",totalPage:" + this.totalPage + ",page:" + this.page;
		}
		
		public void setPageSize(int pageSize) {
		  this.pageSize = pageSize;
		}
	}
