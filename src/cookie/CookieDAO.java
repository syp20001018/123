package cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieDAO
 */
@WebServlet("/CookieDAO")
public class CookieDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        boolean flag = false;
        PrintWriter out = response.getWriter();
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name=cookie.getName();
                if ("userName".equals(name)) { // 表明已经登陆过了，就直接跳转了
                	if(cookie.getValue()!=null) {
                		flag = true;
                	}
                }
            }
        }

        if(flag) {
        	request.getRequestDispatcher("/main.jsp").forward(request, response);
            //response.sendRedirect(request.getContextPath() + "/main.jsp");

        }else {
        	request.setAttribute("errors", "请先登录!");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
          
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
