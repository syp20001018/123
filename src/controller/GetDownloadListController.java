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
		//1,获取要下载的文件的绝对路径
		String filepath=request.getParameter("path");
		
		String path =request.getServletContext().getRealPath(filepath);
		//2.获取要下载的文件名
		String fileName = path.substring(path.lastIndexOf("\\")+1);
		//3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		//设置context-disposition响应头，控制浏览器以下载形式打开，这里注意文件字符集编码格式，设置utf-8，不然会出现乱码
		response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		//4.获取要下载的文件输入流
		//字符流输入FileReader in= new FileReader(path);
		InputStream in=new FileInputStream(path);
		int len=0;
		//5.创建数据缓冲区
		byte[] buffer = new byte[1024];
		//6.通过response对象获取OutputStream流
		ServletOutputStream out=response.getOutputStream();
		//7.将FileInputStream流写入到buffer缓冲区
		while((len = in.read(buffer))!=-1) {
			//8.使用OutputStream流将缓冲区的数据输出到客户端浏览器
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
