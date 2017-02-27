package cn.mylogger.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Admin implements Serializable {

	private String id;

	private String loginname;

	private String loginpwd;

	public Admin() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpwd() {
		return this.loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

}
