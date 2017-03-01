<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Show Picture</title>
</head>
<body>
	<%
		String fileDir = "";
		String perfectName = request.getParameter("url");
		FileInputStream is = null;
		ServletOutputStream sos = null;
		try {
			is = new FileInputStream(perfectName);
			response.reset();
			response.setContentType("image/jpeg");
			sos = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				sos.write(buffer, 0, len);
			}
			sos.flush();
			out.clear();
			out = pageContext.pushBody();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sos.close();
			is.close();
		}
	%>
</body>
</html>