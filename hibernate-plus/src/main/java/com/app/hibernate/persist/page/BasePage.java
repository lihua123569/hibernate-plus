package com.app.hibernate.persist.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * BasePage 基本分页
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
public class BasePage<T> implements Serializable {
	private static final long serialVersionUID = -3879553591596789829L;

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private Integer pageNumber = 1;
	private Integer pageSize = 50;
	private Integer pageTotal = -1;
	private Integer first = 1;
	private Integer total = -1;

	private boolean autoCount = false;
	private String orderBy = null;

	private List<T> rows = new ArrayList<T>();

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageTotal() {
		pageTotal = (total + pageSize - 1) / pageSize;
		return pageTotal;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst() {
		this.first = first;
	}

	public Integer getTotal() {
		total = rows.size();
		return total;
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
