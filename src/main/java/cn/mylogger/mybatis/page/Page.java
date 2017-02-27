package cn.mylogger.mybatis.page;

import java.util.List;

/**
 * 分页某页的数据
 * 
 * @author li-long
 *
 * @param <T>
 */
public class Page<T> {
	private long totalRecord; // 总记录数 select count(*) from T;
	private long totalPage;// 总页数 totalRecord/pageSize
	private int pageSize; // 每页大小,一般固定
	private int pageCode;// 当前页码
	private List<T> data; // select * from T limit (current-1)*pageSize,pageSize

	public Page() {
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	

}