<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>错误提示</title>
	<%
		String cp = request.getContextPath();
	
        String param = (String)request.getAttribute("errors");
		out.println("接收到:"+param);
    %>
    <style>
    	. imgBox {
    		text-align:center;
    		width:50px;
    		border: green solid 1px;
            margin: 0 auto;
            height: 40px;
        }
    </style>
   
</head>
<body>
	
	<p>不能跳转请
	<a  href="<%=cp %>/login.html" title="跳转">点击这儿</a>
	</p>
	
	<div id="msg"></div>
    <script>
         var times=10;
         function test(){         
        	document.getElementById("msg").innerHTML="页面将在"+times+"后跳转到登录页面";
            times-=1;
           	if(times<=0){
                window.clearInterval(doTEST);
                window.location.href="<%=cp %>/login.html";
            }
         }
        var doTEST;
        window.clearInterval(doTEST);
        doTest=window.setInterval("test()",1000);
    </script>
    <div>
    <img src="<%=cp %>/image/2024117.jpg" style="width:100%;height: 100%;"/>
    </div>

</body>
</html>