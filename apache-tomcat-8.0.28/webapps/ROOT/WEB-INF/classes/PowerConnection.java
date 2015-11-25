import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PowerConnection extends HttpServlet{
//	private Connection con = null;
//	private Statement stmt = null;
//	private ResultSet rs = null;
//	private PreparedStatement pst = null;

	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		System.out.println ("PowerConnection init");
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	
		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		String[] mip=null;
		String[] nCount=null;
		String[] pState=null;

		Connection con=null;
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypower","root","1234567");
			mip = req.getParameterValues("mIP");
			nCount = req.getParameterValues("nCount");

//			pState = req.getParameterValues("pState");

			String sql = "select * from admin where name='"+mip[0]+"' order by mtime desc limit 1";
			System.out.println ("sql: "+sql);

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			String pStatex = null;

			if (rs.next()) {
				pStatex = rs.getString("pState");
				pState = new String[1];
				pState[0] = pStatex;
			} else {
				pState = new String[1];
				pState[0] = "x";
			}

//			rs.close();
//			stmt.close();

//			sql = "insert into mpower(mip,mtime,nCount,pState)" + "values(?,now(),?,?)";
			sql = "insert into mpower(mip,mtime,nCount,pState)" + "values('"+mip[0]+"',now(),'"+nCount[0]+"','"+pState[0]+"')";
			System.out.println ("sql:"+sql);

/*
			pst = con.prepareStatement(sql);
			pst.setString(1,mip[0]);

			pst.setString(2,nCount[0]);
			pst.setString(3,pState[0]);

			pst.executeUpdate();
			*/
			Statement stmt1 = con.createStatement();
			stmt1.executeUpdate(sql);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		try{
//			Statment stmt = con.createStatement();
			String sql = "select * from mpower where mip='"+mip[0]+"' order by mtime desc limit 1";
			System.out.println ("PowerConnection sql:\t"+sql);
//			rs = stmt.executeQuery(sql);

			HtmlSQLResult result = new HtmlSQLResult(sql,con);
			String pStateStr = result.toString();

			out.println(pStateStr);
			//out.println("192.168.43.86/?1");

			System.out.println("CilentIP:\t" + req.getRemoteAddr());
			System.out.println("CilentHost:\t" + req.getRemoteHost());
			System.out.println("CilentPort:\t" + req.getRemotePort());

			out.println ("mIP:\t"+req.getRemoteAddr());
			out.println ("nCount:\t"+nCount[0]);
			out.println ("pState:\t"+pStateStr);

/*			if (pState==null) {
				pState = new String[1];

				pState[0]="1,0,0,0,1,1,0,1,0";

			}

			out.println ("pState:"+pState[0]);

			

			if(req.getParameter("mip")!=null){

				out.println("Your IPaddress is current.");

			}*/

			
			out.println("Y");
			out.flush();
			out.close();
			
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void Post(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		doGet(req,res);
	}

/*
	public void destroy(){
		try{
			if(con != null) con.close();
		} catch(Exception ignored) {
			ignored.printStackTrace();
		}
	}
	*/
}