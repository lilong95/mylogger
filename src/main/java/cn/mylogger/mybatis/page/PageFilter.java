package cn.mylogger.mybatis.page;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(asyncSupported = true, value = { "*.do" })
public class PageFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			String path = ((HttpServletRequest) request).getServletPath()
					.substring(1);
			if (!path.contains("Page")) {
				chain.doFilter(request, response);
				return;
			}

			int pageSize = 10; // 每页大小,默认10
			int pageCode = 1;// 当前页码
			try {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			} catch (NumberFormatException e) {
				System.out.println("默认pageSize设置为10");
				pageSize = 10;
			}
			try {
				pageCode = Integer.parseInt(request.getParameter("pageCode"));
			} catch (NumberFormatException e) {
				System.out.println("默认pageCode设置为1");
				pageCode = 1;
			}
			PageContext.setPageCode(pageCode);
			PageContext.setPageSize(pageSize);
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PageContext.removeAll();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
