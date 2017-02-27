package cn.mylogger.po;

public class Photo extends BasePo {
	private int type; // 图片类型
	private String url; // 图片路径

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
