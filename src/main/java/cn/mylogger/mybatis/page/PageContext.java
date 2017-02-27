package cn.mylogger.mybatis.page;
/**
 * Page的相关参数,order和sort由sql直接指定
 * @author li-long
 *
 */
public class PageContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	/**
	 * 分页的起始页,当前页码
	 */
	private static ThreadLocal<Integer> pageCode = new ThreadLocal<Integer>();
	/**
	 * 总记录数
	 */
	private static ThreadLocal<Long> totalRecord = new ThreadLocal<Long>();
	/**
	 * 总页数
	 */
	private static ThreadLocal<Long> totalPage = new ThreadLocal<Long>();

	public static Integer getPageSize() {
		return pageSize.get();
	}

	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}

	public static Long getTotalRecord() {
		return totalRecord.get();
	}

	public static void setTotalRecord(Long _totalRecord) {
		totalRecord.set(_totalRecord);
	}

	public static Long getTotalPage() {
		return totalPage.get();
	}

	public static void setTotalPage(Long _totalPage) {
		totalPage.set(_totalPage);
	}

	public static Integer getPageCode() {
		return pageCode.get();
	}

	public static void setPageCode(Integer _pageCode) {
		pageCode.set(_pageCode);
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}

	public static void removePageCode() {
		pageCode.remove();
	}

	public static void removeTotalRecord() {
		totalRecord.remove();
	}

	public static void removeTotalPage() {
		totalPage.remove();
	}
	
	public static void removeAll() {
		pageSize.remove();
		pageCode.remove();
		totalRecord.remove();
		totalPage.remove();
	}

}
