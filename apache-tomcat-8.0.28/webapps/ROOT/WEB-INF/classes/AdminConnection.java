import java.io.*;

import java.sql.*;

import javax.servlet.*;

import javax.servlet.http.*;



public class AdminConnection extends HttpServlet{

//	private Connection con = null;

//	private Statement stmt = null;

//	private ResultSet rs = null;

//	private PreparedStatement pst = null;

	

	public void init(ServletConfig config)throws ServletException{

		super.init(config);
		System.out.println ("AdminConnection init");
		try{

			Class.forName("com.mysql.jdbc.Driver");


		}catch(Exception e){

			e.printStackTrace();

		}

	}

	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		System.out.println ("AdminConnection doGet");
		
		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		

		String name[] = req.getParameterValues("name");

//		String nCount[]=req.getParameterValues("nCount");

		String pState[]=req.getParameterValues("pState");
		System.out.println ("AdminConnection name[0]:"+name[0]);
		System.out.println ("AdminConnection pState[0]:"+pState[0]);
		

		Connection con=null;
		try{

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypower","root","1234567");

//			name = req.getParameterValues("name");

//			nCount = req.getParameterValues("nCount");

//			String[] pstate = req.getParameterValues("pState");

			

//			String sql = "insert into admin (name,mtime,pState)" + "values(?,now(),?)";
			String sql = "insert into admin (name,mtime,pState)" + "values('"+name[0]+"',now(),'"+pState[0]+"')";

			System.out.println ("AdminConnection sql:"+sql);
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
/*
			pst = con.prepareStatement(sql);

			pst.setString(1,name[0]);

//			pst.setString(2,nCount[0]);

			pst.setString(2,pState[0]);

			pst.executeUpdate();
*/
		}catch(Exception ex){

			ex.printStackTrace();

		}

		

		try {

			String sql = "update mpower,admin set mpower.pState=admin.pState" + 

				" where admin.name='"+name[0]+"' and mpower.mIP='"+name[0]+"'";

			

			System.out.println ("AdminConnection sql 1:"+sql);

			

			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);
			
			con.close();

		} catch(Exception ei) {

			ei.printStackTrace();

		}

		

		
/*
		try{

			String sql = "select * from admin where name order by mtime desc limit 1";

			System.out.println ("AdminConnection: "+sql);

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

//			HtmlSQLResult result = new HtmlSQLResult(sql,con);

//			out.println(result.toString());
			con.close();
		}catch(Exception e){

			e.printStackTrace();

		}
		*/

	}

	public void Post(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{

		doGet(req,res);

	}
	/*

	public void destroy(){

		try{

			if(con != null) con.close();

		}catch(Exception ignored){}

	}
	*/

}