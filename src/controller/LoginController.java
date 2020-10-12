package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.JdbcUtil;
import vo.Download;
import vo.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns="/servlet/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int t1=0;
		int t2=0;
		User user1=new User();
		request.setCharacterEncoding("utf-8");
		JdbcUtil jdbc=new JdbcUtil();
		HttpSession session = request.getSession();
		String vCode=session.getAttribute("verityCode").toString();
		
		String userName=request.getParameter("user_name");
		String password=request.getParameter("password");
		String userCode=request.getParameter("userCode");
		
		String check=request.getParameter("box");
		System.out.println(check);
		boolean bool=Boolean.valueOf(check);
		
		
	    if(userCode.equals(vCode)) {
	    	t1=1;
	    }
	    		
	    try {
	    	if(jdbc.select(userName, password)!=null) {
	    		user1=jdbc.select(userName, password);
	    		t2=1;
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    //System.out.println(t2);
	    if(t1==0) {
	    	String errors="抱歉，您输入的验证码不正确";
	    			
	    	request.setAttribute("errors", errors);
	    	request.getRequestDispatcher("/error.jsp").forward(request, response);
	    			
	    }
	    if(t2==0) {
	    	String errors="用户名或密码错误!";
	    	//System.out.println("用户名或密码错误!");
	    	request.setAttribute("errors", errors);
	    	request.getRequestDispatcher("/error.jsp").forward(request, response);
	    }
	    
	    if(bool == true){//勾选
	    	Cookie cookie =new Cookie("userName", userName);
		    //cookie.setMaxAge(60*60*24*7);
	    	 cookie.setMaxAge(-1);
		    cookie.setPath("/");
		    response.addCookie(cookie);
		    
		    Cookie cookie2 =new Cookie("password", password);
		   // cookie2.setMaxAge(60*60*24*7);
		    cookie2.setMaxAge(-1);
		    cookie2.setPath("/");
		    response.addCookie(cookie2);
		    
	    }
	    session.setAttribute("user1", user1);
	    session.setAttribute("userName", userName);
	    session.setAttribute("password", password);
	    session.setAttribute("userCode", userCode);
	    				    		    			
	    request.getRequestDispatcher("/main.jsp").forward(request, response);	    		          	        				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
