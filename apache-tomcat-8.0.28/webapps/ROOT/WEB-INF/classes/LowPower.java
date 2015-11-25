import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LowPower extends HttpServlet{
  public void init(ServletConfig config)throws ServletException{
    super.init(config);
  }
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
  }
}