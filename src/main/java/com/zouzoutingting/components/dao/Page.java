package com.zouzoutingting.components.dao;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月3日
 */
public class Page  {

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	/**
	 * 当前页数
	 */
	private int pageNo = 1;
	
	/**
	 * 每页大小
	 */
	private int pageSize = 15;
	
	/**
	 * 排序字段，如：id
	 */
	private String orderFields = null;
	
	/**
	 * 排序类型，如：desc，asc
	 */
	private String order = null;
	
	/**
	 * 排序名称，如：id dsc，id asc
	 */
	private String orderName = null;
	
	/**
	 * 查询条件
	 */
	private String condition = null;
	
	private boolean autoCount = true;
	
	private int start = 0;
	
	/**
	 * 返回结果集合
	 */
	private List<Object> resultList = new ArrayList<Object>();
	
	/**
	 * 总共条数
	 */
	private long totalCount = -1L;
	
	public Page() {
		super();
	}
	
	public Page(String condition, int pageNo, int pageSize, String orderName) {
		super();
		this.condition = condition;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orderName = orderName;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1)
			this.pageNo = 1;
	}
	public Page pageNo(int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1)
			this.pageSize = 1;
	}
	public Page pageSize(int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}
	
	
	public String getOrderFields() {
		return orderFields;
	}
	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}
	public Page orderFields(String theOrderFields) {
		setOrderFields(theOrderFields);
		return this;
	}
	
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Page order(String theOrder) {
		setOrder(theOrder);
		return this;
	}
	
	
	public String getOrderName() {
		String orderName = "";
		if(this.orderName != null && !"".equals(this.orderName)) {
			orderName = this.orderName;
		} else {
			String orderFields = this.getOrderFields();
			if(orderFields != null && !orderFields.equals("")) {
				orderName = orderFields + " ";
				String order = this.getOrder();
				if(order != null && (order.toLowerCase().equals(ASC) || order.toLowerCase().equals(DESC))) {
					orderName += order;
				}
			}
		}
		
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	public boolean isAutoCount() {
		return autoCount;
	}
	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
	public Page autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List<Object> getResultList() {
		return resultList;
	}
	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	
	public int getFirst() {
		if (this.start != 0)
			return this.start + 1;
		return (this.pageNo - 1) * this.pageSize + 1;
	}
	
	public long getTotalPages() {
		if (this.totalCount < 0L) {
			return -1L;
		}

		long l = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0L) {
			l += 1L;
		}
		return l;
	}
	
	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}
	
	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}
	
	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
	
	public static String getPageCacheKey(String key) {
		String str = "";
		if (key.startsWith("/")) {
			str = key.substring(1, key.length());
		}

		if (str.indexOf("/") != -1) {
			str = str.substring(0, str.indexOf("/"));
		}

		return str;
	}
	
}
