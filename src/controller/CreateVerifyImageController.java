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
		//����4λ����ַ���
		String vCode= createVCodeImage.createCode();
		//��ȡHttpSession
		HttpSession session = request.getSession();
		//��������4λ����ַ��������session�У������session�е����ݻ�����һ���Ự���֣��������ȫ�ֹ������Ա���֤
		session.setAttribute("verityCode", vCode);
		//���÷��ص�����
		response.setContentType("img/jpeg");
		//���������Ӧ��������--��֤��ͼƬ����һ��ˢ��һ�Σ����Բ����л������
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//������֤��ʧЧʱ��
		response.setDateHeader("Expires", 0);
		//���ֽ�������ȥ������img��src����ȥ��������
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
