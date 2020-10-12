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
		
		String path = requset.getServletPath();// ��ǰ��Դ��urlPatterns
		
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
		 * ���������߼� �жϵ�ǰ�����uriPatterns�Ƿ���Ҫ����
		 * �������Ҫ����ֱ�ӷ��� �������ִ�� �ж��û��Ƿ��¼
		 * δ��¼��ת����error.jsp,�����ݴ�����ʾ������δ��¼�����ܷ��ʸ���Դ��
		 * �ѵ�¼����󴫵�
		 */
		if (notFilterPath.indexOf(path) == -1) {//��Ҫ����

			//HttpSession session = requset.getSession();
			/*
			if (session.getAttribute("user1") == null) {// δ��¼
				requset.setAttribute("errors", "����δ��¼�����ܷ��ʸ���Դ");
				requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
			}
			else {// �ѵ�¼,���󴫵�
				requset.setAttribute("errors", "��û��Ȩ�޷��ʸ���Դ!");
				requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
			}
			*/
			
			requset.setAttribute("errors", "��û��Ȩ�޷��ʸ���Դ!");
			requset.getRequestDispatcher("/error.jsp").forward(requset, resp);
		} else {// �ѵ�¼,���󴫵�

			chain.doFilter(requset, resp);// ���� ����һ��������
			
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
