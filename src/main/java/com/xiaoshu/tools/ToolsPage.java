package com.xiaoshu.tools;

/**
 * 分页bean
 * @author XGB
 *
 */
public class ToolsPage {
	 private int pageSize;//每页记录最大显示的数
	 private int totalCurrent;//总的记录数
	 
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCurrent() {
		return totalCurrent;
	}
	public void setTotalCurrent(int totalCurrent) {
		this.totalCurrent = totalCurrent;
	}

	/**
	 * 分页
	 * @param totalCurrent 传入总的记录数
	 * @param pageSize 传入的每页记录条数
	 * @return 返回 总页数
	 */
	public static int totalPage(int totalCurrent,int pageSize){
		int totalPage = 1;//
        if(totalCurrent%pageSize == 0)
        {
        	totalPage = totalCurrent / pageSize;
        }
        else
        {
        	totalPage = totalCurrent / pageSize + 1;
        }
		return totalPage;
		
	} 
}
