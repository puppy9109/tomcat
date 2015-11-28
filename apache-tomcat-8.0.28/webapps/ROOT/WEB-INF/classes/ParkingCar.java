import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ParkingCar extends HttpServlet{
  public void init(ServletConfig config)throws ServletException{
    super.init(config);
    try{
     Class.forName("com.mysql.jdbc.Driver");
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public String getRandomCarNumber(Connection con) throws Exception {
    Statement stmt = con.createStatement();
    String sql = "select distinct number from car";
    ResultSet rs = stmt.executeQuery(sql);
    ArrayList carNumberList = new ArrayList();
    while (rs.next()) {
     String number = rs.getString("number");
     carNumberList.add (number);
    }
    rs.close();
    
    int ranSelect = (int)(Math.random()*carNumberList.size());
    return (String)carNumberList.get(ranSelect);
    
  }
  
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
    res.setContentType("text/html");
//    res.setIntHeader("refresh",10);
    PrintWriter out = res.getWriter();
    Connection con = null;
/*    
    Random rnd = new Random();
    
    try{
      Statement stmt = con.createStatement();
      if(rnd.nextInt(2) == 0){
        String sql = "insert into car (number,space,startDate,overDate)"+" values('"+number+"','"+space+"',now(),now())";
        ResultSet rs = stmt.executeUpdate(sql);
        rs = stmt.executeUpdate("update query");
      }else{                        
        String sql = "update car set overDate=now() where space='"+space+"'";
        ResultSet rs = stmt.executeUpdate(sql);
      }
      con.close();
    }catch(Exception e){
        e.printStackTrace();
    }
  */  
    try{
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","12345");
        String space = (String)req.getParameter("space");
	Statement stmt = con.createStatement();
	
	String number = getRandomCarNumber(con);


         ResultSet rs1 = stmt.executeQuery("select * from parkquery where space='"+space+"'");
         if (rs1.next()) {
           // if state='1'
           String state = (String)rs1.getString("state");
           if (state.equals("1")) {
              Statement stmt0 = con.createStatement();
              String sql_0 = "select sn from car where space='"+space+"' order by startDate desc limit 1";
              System.out.println ("ParkingCar sql_0:"+sql_0);
              ResultSet rs = stmt0.executeQuery(sql_0);
              
              String sn = null;
              if (rs.next()) {
               sn = rs.getString("sn");
               System.out.println ("sn:"+sn);
            
              }

              
//select sn from car where space='28' order by startDate desc limit 1;
//update car set startDate=startDate, overDate=now() where sn='102';

              Statement stmt1 = con.createStatement();
              String sql_1 = "update car set startDate=startDate, overDate=now() where sn='"+sn+"'";
              System.out.println ("ParkingCar sql_1.1:"+sql_1);
              stmt1.executeUpdate(sql_1);
              
              Statement stmt2 = con.createStatement();
              String sql_2 = "update parkquery set state='0' where space='"+space+"'";
              System.out.println ("ParkingCar sql_1.2:"+sql_2);
              stmt2.executeUpdate(sql_2);
           }
           
           if (state.equals("0")) {
             Statement stmt1 = con.createStatement();
             String sql_1 = "insert into car (number,space,startDate,overDate) values('"+number+"','"+space+"',now(),now())";
             
             System.out.println ("ParkingCar sql_1.3:"+sql_1);
             stmt1.executeUpdate(sql_1);

             Statement stmt2 = con.createStatement();
             String sql_2 = "update parkquery set state='1' where space='"+space+"'";
             System.out.println ("ParkingCar sql_2.4:"+sql_2);
             stmt2.executeUpdate(sql_2);
           }
        } else {
          // initial state , append the new data into the parkquery
          Statement stmt1 = con.createStatement();
          String sql_1 = "insert into parkquery values ('"+space+"','1')";
          System.out.println ("ParkingCar sql_2.5:"+sql_1);
          stmt1.executeUpdate(sql_1);

          Statement stmt2 = con.createStatement();
          String sql_2 = "insert into car (number,space,startDate,overDate) values('"+number+"','"+space+"',now(),now())";
          System.out.println ("ParkingCar sql_2.3:"+sql_2);
          stmt1.executeUpdate(sql_2);

          Statement stmt3 = con.createStatement();
          String sql_3 = "update parkquery set state='1' where space='"+space+"'";
          System.out.println ("ParkingCar sql_3.4:"+sql_3);
          stmt2.executeUpdate(sql_3);
        }
//        ResultSet rs = stmt.executeQuery("select * from parkquery where space='"+space+"'");  
//        rs = stmt.executeUpdate("update car set overDate=now() where space='"+space+"'");
        
/*
	String json = "";
	while(rs.next()){
          String space_1 = rs.getString("space");
          json = json + "[";
          json = json + "{";
          json = json + "\"space\":\""+space_1+"\"";
	}
	json = json + "}]";
	System.out.println("json:"+json);
	out.println(json);
	out.close();
	*/
	con.close();	
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
    doGet(req,res);
  }
}