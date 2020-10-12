package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.JdbcUtil;
import vo.Download;

/**
 * Servlet implementation class GetDownloadListController
 */
@WebServlet(urlPatterns="/servlet/download.do")
public class GetDownloadListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		//1,��ȡҪ���ص��ļ��ľ���·��
		String filepath=request.getParameter("path");
		
		String path =request.getServletContext().getRealPath(filepath);
		//2.��ȡҪ���ص��ļ���
		String fileName = path.substring(path.lastIndexOf("\\")+1);
		//3.����content-disposition��Ӧͷ��������������ص���ʽ���ļ�
		//����context-disposition��Ӧͷ�������������������ʽ�򿪣�����ע���ļ��ַ��������ʽ������utf-8����Ȼ���������
		response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		//4.��ȡҪ���ص��ļ�������
		//�ַ�������FileReader in= new FileReader(path);
		InputStream in=new FileInputStream(path);
		int len=0;
		//5.�������ݻ�����
		byte[] buffer = new byte[1024];
		//6.ͨ��response�����ȡOutputStream��
		ServletOutputStream out=response.getOutputStream();
		//7.��FileInputStream��д�뵽buffer������
		while((len = in.read(buffer))!=-1) {
			//8.ʹ��OutputStream����������������������ͻ��������
			out.write(buffer, 0, len);
		}
		in.close();
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
