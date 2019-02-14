package com.chen.infoapp.common.pager;

/**
 * 分页实体类
 */
public class PageModel {

    /** 分页中默认一个4条数据 */
    public static final int PAGE_DEFAULT_SIZE = 4;

    /** 分页总数据条数  */
    private long recordCount;
    /** 当前页面 */
    private int pageIndex ;
    /** 每页分多少条数据   */
    private int pageSize = PAGE_DEFAULT_SIZE;

    /** 总页数  */
    private int totalSize;

    public long getRecordCount() {
        this.recordCount = this.recordCount <= 0 ? 0:this.recordCount;
        return recordCount;
    }
    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
    public int getPageIndex() {
//		this.pageIndex = this.pageIndex>=this.getTotalSize()?this.getTotalSize():this.pageIndex;
        this.pageIndex = this.pageIndex <= 0?1:this.pageIndex;
//		/** 判断当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页  */
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public int getPageSize() {
        this.pageSize = this.pageSize <= PAGE_DEFAULT_SIZE?PAGE_DEFAULT_SIZE:this.pageSize;
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        if(this.getRecordCount() <=0){
            totalSize = 0 ;
        }else{
            totalSize = (int) ((this.getRecordCount() -1)/this.getPageSize() + 1);
        }
        return totalSize;
    }

    public int getFirstLimitParam(){
        return (this.getPageIndex()-1)*this.getPageSize() ;
    }

}
