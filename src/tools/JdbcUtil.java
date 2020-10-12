package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import vo.Download;
import vo.User;



public class JdbcUtil {
	
	private static String driverClass;
	private static String url;
	private static String user;
	private static String passwords;
	
	
	static{
		init();
	}
	
	
	public static void init(){//读取配置文件
		
		try {
			Properties prop = new Properties();
			//加载属性文件
			//this.getClass().getResource("/").getPath()+"tag.properties"
			//InputStream in=new BufferedInputStream(new FileInputStream(classPath+"/jdbc.properties"));
			//InputStream in=new BufferedInputStream(new FileInputStream(JdbcUtil.getClass().getResource("/").getPath()+"tag.properties"));
			//System.out.println();
			//prop.load(new InputStreamReader(in,"utf-8"));
			prop.load(JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			//in.close();
			//获取属性信息(连接数据库的相关字符串)
			driverClass = prop.getProperty("driver");
			url = prop.getProperty("jdbc");
			user = prop.getProperty("userName");
			passwords = prop.getProperty("password");
			
			//创建数据源并配置
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  User select(String uname,String password) throws Exception{
		
		//?useUnicode=true&characterEncoding=utf-8
		//Class.forName(driverClass);
		//Connection con=DriverManager.getConnection(url+"?useUnicode=true&characterEncoding=utf-8",user,passwords);
		Connection con=getConn();
		String sql2="select * from t_user";
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		
		ResultSet rs=ps2.executeQuery();
		//ps2.setString(1, s);
		while(rs.next()) {
			String name=rs.getString("userName");
			String word=rs.getString("password");
			if(name.equals(uname) && word.equals(password)) {
				String chrName=rs.getString("chrName");
				String role=rs.getString("role");
				User user1=new User(name,word,chrName,role);
				return user1;
			}
		}
		ps2.close();
		rs.close();
		con.close();
		return null;
	}
	
	public static Connection getConn(){//得到连接
		//System.out.println(System.getProperty("user.dir"));
		Connection con=null;
		try {
			Class.forName(driverClass);
			con=DriverManager.getConnection(url+"?useUnicode=true&characterEncoding=utf-8",user,passwords);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public  ArrayList<Download> selectfile() throws Exception{
		//?useUnicode=true&characterEncoding=utf-8
		ArrayList<Download> downlist=new ArrayList<Download>();
		//Class.forName("com.mysql.jdbc.Driver");
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/excise?useUnicode=true&characterEncoding=utf-8","root","123456");
		Connection con=getConn();
		String sql2="select * from t_downloadList";
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		
		ResultSet rs=ps2.executeQuery();
		//ps2.setString(1, s);
		while(rs.next()) {
			int id=rs.getInt("id");
			
			String name=rs.getString("name");
			String path=rs.getString("path");
			String description=rs.getString("description");
			long size=rs.getLong("size");
			int star=rs.getInt("star");
			String image=rs.getString("image");
			Download dload=new Download(id,name,path,description,size,star,image);
			downlist.add(dload);
		}
		ps2.close();
		rs.close();
		con.close();
		return downlist;
	}
	
	public  String selectURL(String userName) throws Exception{
		String Url="";
		//Class.forName("com.mysql.jdbc.Driver");
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/excise?useUnicode=true&characterEncoding=utf-8","root","123456");
		Connection con=getConn();
		String sql2="select * from t_resource where resourceId IN (select resourceId from t_role_resource where roleId IN "
				+ "(select roleId from t_user_role WHERE userName = ?))";
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		ps2.setString(1, userName);
		
		ResultSet rs=ps2.executeQuery();
		while(rs.next()) {
			Url += rs.getString("url")+",";
		}
		
		return Url;

	}


}
