<%@page import="vo.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.Download"%>
<%@page import="tools.JdbcUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>主页面</title>
	<%
		User user=(User)session.getAttribute("user1");
		String cp = request.getContextPath();
		
		String userName=(String)session.getAttribute("userName");
		if(userName==null){
			
			request.setAttribute("errors", "你必须登录后才能访问该资源");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		//out.println("欢迎你  "+ user.getChrName());
		
		ArrayList<Download> loadlist=new ArrayList<Download>();
		JdbcUtil jdbc=new JdbcUtil();
		try {
			loadlist=jdbc.selectfile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("list", loadlist);
	%>

</head>
<body>
	
	<%
	
	%>
	
	欢迎您：
	${user1.userName}
	${user1.password}
	
	<br/><br/>
	<strong>首页 |<a href="<%=cp%>/download.jsp">资源下载</a> | <a href="<%=cp%>/resourse/createRole.jsp">用户管理</a> | 
	<a href="<%=cp%>/resourse/freeze.jsp">资源管理</a> | <a href="<%=cp%>/resourse/checkInfo.jsp">个人中心</a> | <a href="<%=cp %>/LogoutController">安全退出</a> </strong>
	<div>
    <img src="<%=cp %>/image/2024248.jpg" style="width:100%;height: 100%;"/>
    </div>

</body>
</html>