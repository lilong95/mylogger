package cn.mylogger.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User extends BasePo implements Serializable {
	
	private String loginName;
	
	private String nickname; //昵称

	private String password;
	
	private int sex; //0为男，1为女

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
