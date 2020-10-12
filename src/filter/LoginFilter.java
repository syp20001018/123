package filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.JdbcUtil;
import vo.User;

/**
 * Servlet Filter implementation class LoginFilter
 */

public class LoginFilter implements Filter {
	
	private String notCheckPath;

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
		String userName="";
		String password="";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response =(HttpServletResponse) resp;
		
		//requset.setCharacterEncoding("utf-8");
        //response.setContentType("text/html;charset=utf-8");
    	
		String path = request.getServletPath();// 当前资源的urlPatterns
		HttpSession session = request.getSession();
		boolean flag = false;
       
               	 
        Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();

				if ("userName".equals(name)) { // 表明已经登陆过了，就直接跳转了
					if (cookie.getValue() != null) {
						userName = cookie.getValue();
						flag = true;
					}
				}
				if("password".equals(name)) {
					if (cookie.getValue() != null) {
						password=cookie.getValue();
					}
				}
			}
		}

		System.out.println("flag=" + flag);

		if (flag) {
			session.setAttribute("userName", userName);
			JdbcUtil jdbc=new JdbcUtil();
			User user=new User();
			try {
				user =jdbc.select(userName, password);
			}catch(Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("user1", user);
			request.getRequestDispatcher("/main.jsp").forward(request, response);
			// response.sendRedirect(request.getContextPath() + "/main.jsp");
		} 
		else {
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void updateHeader (HttpServletResponse response, HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //允许跨域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin")); 
        //允许携带带Cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //允许使用的请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST");
        //将请求中头部出现下列关键字的类型都予以支持
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		notCheckPath = fConfig.getInitParameter("notCheckPath");
	}

}
