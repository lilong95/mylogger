package cn.mylogger.po;

import cn.mylogger.utils.CommonUtils;

public class BasePo {
	private String id;
	//private int status;// 0为正常数据，1为被删除了的数据

	public BasePo() {
		this.id = CommonUtils.uuid();
		//this.status = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}

}
