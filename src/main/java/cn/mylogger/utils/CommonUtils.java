package cn.mylogger.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import cn.mylogger.baseInfo.WebConst;

/**
 * 通用工具
 */
public class CommonUtils {
	/**
	 * 返回一个不重复的字符串
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static String toNormalStrByArray(String[] strs) {
		if (strs == null || strs.length <= 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str + ",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		return builder.toString();
	}

	public static String toJsonStr(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static String getShowPICUrl(HttpServletRequest request, String path) {
		return request.getContextPath() + WebConst.SHOW_PIC_URL + "?url=" + path;
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param str
	 * @return 为空返回true，非空返回false
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 取得访问的url(为了分页条)
	 * 
	 * @param request
	 * @return
	 */
	public static String getPageUrl(HttpServletRequest request) {
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.lastIndexOf("&pageCode=");
		if (index > -1) {
			url = url.substring(0, index);
		}
		return url;
	}

	/**
	 * 验证码是否正确
	 * 
	 * @param session
	 * @param verifyCode
	 * @return 正确返回true，错误返回false
	 */
	public static boolean isVerifyCode(HttpSession session, String verifyCode) {
		String trueCode = (String) session.getAttribute("verifyCode");
		if (trueCode == null || !trueCode.equalsIgnoreCase(verifyCode)) {
			return false;
		}
		return true;
	}
}
