package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tools.JdbcUtil;

/**
 * Servlet Filter implementation class Filter
 */

public class Filter implements javax.servlet.Filter {
	
	private String notCheckPath;

    /**
     * Default constructor. 
     */
    public Filter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		//chain.doFilter(request, response);
		
		HttpServletRequest requset = (HttpServletRequest) req;
		
		String path = requset.getServletPath();// 当前资源的urlPatterns
		
		HttpSession session = requset.getSession();
		String userName = session.getAttribute("userName").toString();
		String url="";
		try {
			JdbcUtil jdbc=new JdbcUtil();
			url=jdbc.selectURL(userName);
			System.out.println(url);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(path);
		
		String notFilterPath = notCheckPath+url;
		/*
		 * 给出过滤逻辑 判断当前请求的uriPatterns是否需要过滤
		 * 如果不需要，则直接放行 否则继续执行 判断用户是否登录
		 * 未登录，转发给error.jsp,并传递错误提示“您尚未登录，不能访问该资源”
		 * 已登录，向后传递
		 */
		if (notFilterPath.indexOf(path) == -1) {//需要过滤

			//HttpSession session = requset.getSession();
			/*
			if (session.getAttribute("user1") == null) {// 未登录
				requset.setAttribute("errors", "您尚未登录，不能访问该资源");
				requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
			}
			else {// 已登录,往后传递
				requset.setAttribute("errors", "您没有权限访问该资源!");
				requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
			}
			*/
			
			requset.setAttribute("errors", "您没有权限访问该资源!");
			requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
		} else {// 已登录,往后传递

			chain.doFilter(requset, resp);// 放行 到下一个过滤器
			
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		notCheckPath = fConfig.getInitParameter("notCheckPath");
	}

}
