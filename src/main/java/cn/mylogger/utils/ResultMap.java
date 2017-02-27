package cn.mylogger.utils;

import java.util.HashMap;

import cn.mylogger.mybatis.page.Page;

@SuppressWarnings("serial")
public class ResultMap extends HashMap<String, Object> {
	
	public ResultMap() {
		this.success();
	}

	public ResultMap success() {
		this.put("success", true);
		return this;
	}
	public ResultMap success(String info) {
		this.put("success", true);
		this.put("info", info);
		return this;
	}

	public ResultMap fail() {
		this.put("success", false);
		return this;
	}
	public ResultMap fail(String info) {
		this.put("success", false);
		this.put("info", info);
		return this;
	}

	public ResultMap info(String info) {
		this.put("info", info);
		return this;
	}

	public ResultMap data(Object obj) {
		this.put("data", obj);
		return this;
	}

	public ResultMap page(Page<?> page) {
		this.put("totalRecord", page.getTotalRecord());
		this.put("totalPage", page.getTotalPage());
		this.put("rows", page.getData());
		return this;
	}
}
