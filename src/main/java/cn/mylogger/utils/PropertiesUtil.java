package cn.mylogger.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String filePath) {
		Properties prop = null;
		try {
			prop = new Properties();
			prop.load(PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(filePath));
		} catch (IOException e) {
			System.out.println("加载properties错误");
			e.printStackTrace();
		}
		return prop;
	}
}
