package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CreateImage;



/**
 * Servlet implementation class CreateVerifyImageController
 */

public class CreateVerifyImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		CreateImage createVCodeImage = new CreateImage();
		//产生4位随机字符串
		String vCode= createVCodeImage.createCode();
		//获取HttpSession
		HttpSession session = request.getSession();
		//将产生的4位随机字符串存放于session中（存放在session中的数据会存放在一个会话这种，多个程序全局共享），以便验证
		session.setAttribute("verityCode", vCode);
		//设置返回的内容
		response.setContentType("img/jpeg");
		//浏览器不响应返回内容--验证码图片，点一次刷新一次，所以不能有缓存出现
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//设置验证码失效时间
		response.setDateHeader("Expires", 0);
		//以字节流发过去，交给img的src属性去解析即可
		BufferedImage image=createVCodeImage.CreateImage2(vCode);
		ServletOutputStream out= response.getOutputStream();
		ImageIO.write(image,"JPEG",out);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
