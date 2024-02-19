

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Approve
 */
@WebServlet("/Approve")
public class Approve extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter p = response.getWriter();
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());
		   if(name == null || !role.equals("admin")){
			   response.sendRedirect("index.jsp");
		   }
		
		int loanid = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("action");
		String coment="";
		if(action.equals("reject")) {
			coment = request.getParameter("coment");
		}
			
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			if(action.equals("approve")) {
				statement.execute("UPDATE loan_applications SET status=1 WHERE id="+loanid);
			}
			else if(action.equals("reject")) {
				statement.execute("UPDATE loan_applications SET status=2 WHERE id="+loanid);
				statement.execute("INSERT INTO comments values(null,"+userid+","+loanid+",'"+coment+"',null,null)");
				
			}
			response.sendRedirect("Viewloan");
		}
		catch(Exception e) {
			p.print(e.getMessage());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
