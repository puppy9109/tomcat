import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LightInfo extends HttpServlet{
	
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
//		String status_1 = (String)req.getParameter("status");
		
//		String nCount[]=req.getParameterValues("nCount");
//		String space=(String)req.getParameter("space");
		
//		System.out.println ("status:"+status_1);

		String status = (String)req.getParameter("status");
		String lightid= (String)req.getParameter("lightid");
		
		String json = "";
				
		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","12345");
			
			String query_cond_1="";
			String query_cond_2="";
			String query_cond = "";
		
			if (status != null) query_cond_1 = "status like '%"+status+"%'";
			if (lightid!=null) query_cond_2	 = "lightid='"+lightid+"'";
			
			if (status !=null) query_cond = query_cond_1;
			if (lightid!=null) query_cond = query_cond_2;
			if (status != null && lightid!=null) query_cond = query_cond_1+" and "+query_cond_2;
			
//			 System.out.println("light status: " + status_1);
			 String sql = "select * from light where "+query_cond;			
			 System.out.println ("sql:"+sql);
			
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(sql);
			 System.out.println("rs: "+rs);
			
			json = "{\"LightInfo\":[ ";
			while(rs.next()){
				System.out.println ("I am llll");
				String sn = rs.getString("sn");
				String status_2 = rs.getString("status");
				String lightid_1 = rs.getString("lightid");
				
				String startTime = null;
				String useTime = null;
				String overTime = null;
				
				try {
					startTime = rs.getString("startTime");
				} catch (Exception ex) {
				
					startTime = "2015-02-01";
				}
				try {
					useTime = rs.getString("useTime");
					if (useTime.trim().length()==0) useTime="2015-02-02";
				} catch (Exception ex) {
					useTime = "2015-02-01";
				}
				try {
					overTime = rs.getString("overTime");
				} catch (Exception ex) {
					overTime = "2015-02-01";
				}
				

				/*
				String startTime = "2015-02-01";
				//rs.getString("startTime");
				String useTime = "2015-01-01";
				//rs.getString("useTime");
				String overTime = "2015-01-02";
				//rs.getString("overTime");
				*/
				json = json + "{";
				json = json + "\"sn\":\"" +sn+ "\",";
				json = json + "\"status\":\"" +status_2+ "\",";
				json = json + "\"lightid\":\"" +lightid_1+ "\",";
				json = json + "\"startTime\":\"" +startTime+ "\",";
				json = json + "\"useTime\":\"" +useTime+ "\",";
				json = json + "\"overTime\":\"" +overTime+ "\"";
				json = json + "}";
				
				System.out.println("sn:"+sn);
				System.out.println("status:"+status_2);
				System.out.println("lightid:"+lightid);
				System.out.println("startTime:"+startTime);
				System.out.println("useTime:"+useTime);
				System.out.println("overTime:"+overTime);
				
				json = json + ",";
				
			}
			json = json.substring(0,json.length()-1);
			json = json + "]}"; 
			System.out.println("json: "+json);
			out.println(json);
			out.close();
			
			con.close();
		} catch(Exception ei) {
			ei.printStackTrace();
		}
		
		/*
		try{
			String sql = "select * from admin where name order by mtime desc limit 1";
			System.out.println ("AdminConnection: "+sql);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}
	public void Post(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		doGet(req,res);
	}
}