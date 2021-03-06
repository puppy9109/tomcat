import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ParkQuery extends HttpServlet{
	
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
/*		
		String number = (String)req.getParameter("number");
//		String nCount[]=req.getParameterValues("nCount");
		String space=(String)req.getParameter("space");
		
		System.out.println ("number:"+number);
		System.out.println ("space:"+space);
*/
		String json = "";
				
		Connection con = null;
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","12345");
//			name = req.getParameterValues("name");
//			nCount = req.getParameterValues("nCount");
//			String[] pstate = req.getParameterValues("pState");
			
//			String sql = "insert into admin (name,mtime,pState)" + "values(?,now(),?)";
/*
			String query_cond_1 = "";
			String query_cond_2 = "";
			String query_cond = "";
			
			if (number != null) query_cond_1 = "number like '%"+number+"%'";
			if (space !=null) query_cond_2 = "space like '%"+space+"%'";
			if (number!=null) { query_cond = query_cond_1; }
			if (space !=null) { query_cond = query_cond_2; }
			if (number!=null && space!=null) {
				query_cond = query_cond_1 + " and "+query_cond_2;
			}
*/			
			String sql = "select * from parkquery";
			System.out.println ("ParkQuery sql:"+sql);
			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			System.out.println(rs);
			json = "{ \"CarInfo\":[ ";
			while (rs.next()) {
			
//				String number_1 = rs.getString("number");
				String space_1 = rs.getString("space");
				String state   = rs.getString("state");
//				String startDate = rs.getString("startDate");
//				String overDate  = rs.getString("overDate");
//				String money  = rs.getString("money");
//				String allSpace=rs.getString("allSpace");
//				String remainSpace = rs.getString("remainSpace");
				
				if (state.equals("1")) {
					json = json + "{";
					json = json + "\"space\":\""+space_1+"\",";
					json = json + "\"state\":\""+state+"\"";
					json = json + "}";
	
					json = json+",";
					
					
					System.out.println ("space:"+space_1);
					System.out.println ("state:"+state);
				}
				
//				sn  | number   | space | startDate           | overDate            | money         | allSpace | remainSpace 
			}
			json = json.substring(0,json.length()-1);
			json = json + "]}";
			System.out.println ("json:"+json);
			out.println (json);
			out.flush();
			out.close();
			
			con.close();
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
		
/*		try {
			String status = (String)req.getParameter("status");
			String query_cond_3 = null;
			if (status != null) query_cond_3 = "status like '%"+status+"%'";
			
			System.out.println("Status: " + status);
			String sql = "select * from light where "+query_cond_3;			
			System.out.println ("sql:"+sql);
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("rs: "+rs);
		
			json = "{\"CarInfo\":[";
			while(rs.next()){
				String sn = rs.getString("sn");
				String status_1 = rs.getString("status");
				String people = rs.getString("people");
				String startTime = rs.getString("startTime");
				String useTime = rs.getString("useTime");
				String overTime = rs.getString("overTime");
				
				json = json + "{";
				json = json + "\"sn\":\"" + "\",";
				json = json + "\"status\":\"" + "\",";
				json = json + "\"people\":\"" + "\",";
				json = json + "\"startTime\":\"" + "\",";
				json = json + "\"usetime\":\"" + "\",";
				json = json + "\"overtime\":\"" + "\"";
				json = json + "}";
				
				System.out.println("sn:"+sn);
				System.out.println("status_1:"+status);
				System.out.println("people:"+people);
				System.out.println("startTime:"+startTime);
				System.out.println("useTime:"+useTime);
				System.out.println("overTime:"+overTime);
				
				json = json + ",";
				
			}
			json = json.substring(0,json.length()-1);
			json = json + "}]"; 
			System.out.println("json: "+json);
			out.println("");
			out.close();
			
			con.close();
		} catch(Exception ei) {
			ei.printStackTrace();
		}
		*/
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