package com.xiaoshu.util;

import java.util.List;

/**
 * Created by Kun on 2017/12/01 0001.
 */
public class Pager<T> {

    private int fullListSize; // 总记录数
    private List<T> list; // 每页列表
    private int pageSize = 10;// 每页记录数 page size
    private int pageNumber = 1; // 当前页码
    private int pageCount =0;

    private String searchId;
    private String sortCriterion;

    private String orderBy;

    public Integer getPageNo() {
        if(pageNumber<1){
            pageNumber = 1;
        }
        return pageNumber;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNumber = (pageNo == null || pageNo == 0) ? 1 : pageNo;
    }

    //-- 构造函数 --//
    public Pager() {
    }

    public Pager(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNumber - 1) * pageSize) + 1;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (fullListSize < 0) {
            return -1;
        }

        long count = fullListSize / pageSize;
        if (fullListSize % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNumber + 1 <= getTotalPages());
    }

    public int getFullListSize() {
        return fullListSize;
    }

    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSortCriterion() {
        return sortCriterion;
    }

    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    public int getPageCount() {
        pageCount = fullListSize/pageSize;
        if (fullListSize % pageSize > 0) {
            pageCount++;
        }
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getStartRow() {
        return (pageNumber - 1) * getPageSize();
    }

    @Override
    public String toString() {
        return "Page{" +
                "fullListSize=" + fullListSize +
                ", list=" + list +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", pageCount=" + pageCount +
                ", searchId='" + searchId + '\'' +
                ", sortCriterion='" + sortCriterion + '\'' +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
