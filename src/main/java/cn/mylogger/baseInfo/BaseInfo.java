package cn.mylogger.baseInfo;

import javax.servlet.http.HttpServletRequest;

import cn.mylogger.po.User;

public class BaseInfo {
	public static void setUser(User user, HttpServletRequest request) {
		request.getSession().setAttribute(WebConst.SESSION_USER, user);
	}

	public static User getUser(HttpServletRequest request) {
		if (request.getSession().getAttribute(WebConst.SESSION_USER) != null) {
			User user = (User) request.getSession().getAttribute(WebConst.SESSION_USER);
			return user;
		} else {
			return null;
		}
	}
}
