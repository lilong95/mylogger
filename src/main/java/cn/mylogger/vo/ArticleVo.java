package cn.mylogger.vo;

import java.util.Date;

public class ArticleVo {
	private String id;
	private String userId;
	private String title;
	private String content;
	private Date lastRevertDt; // 最后回复时间
	private Date createDt; // 创建时间
	private int visitNum;
	private String theme; // 主题名称，暂只有“前端”、“后台”、“数据库”三个

	private String userName; // 发布人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastRevertDt() {
		return lastRevertDt;
	}

	public void setLastRevertDt(Date lastRevertDt) {
		this.lastRevertDt = lastRevertDt;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
