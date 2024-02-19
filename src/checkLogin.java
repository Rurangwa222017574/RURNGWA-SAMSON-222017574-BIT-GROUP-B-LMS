

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class checkLogin
 */
@WebServlet("/checkLogin")
public class checkLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs = statement.executeQuery("SELECT students.id as stud_id, users.id as u_id, name, email,role"
					+ ", password, users.st_id"
					+ " FROM users JOIN students ON users.st_id=students.id WHERE students.email='"+username+"' AND users.password='"+password+"'");
			
			if(rs.next()) {
				//SESSION 
				HttpSession session = request.getSession();
				session.setAttribute("name", rs.getString("name"));
				session.setAttribute("id", rs.getInt("st_id"));
				session.setAttribute("email", rs.getString("email"));
				session.setAttribute("role", rs.getString("role"));

				if(rs.getString("role").equals("admin")) {
					response.sendRedirect("Adminlandingpage"); //GO TO ANOTHER PAGE 
				}
				else if(rs.getString("role").equals("student")) {
					response.sendRedirect("Studentlandingpage"); //GO TO ANOTHER PAGE 
				}
				else if(rs.getString("role").equals("super")) {
					response.sendRedirect("Superlandingpage"); //GO TO ANOTHER PAGE 
				}
				
			}
			else {
				String div ="<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\r\n" + 
						"  <strong>Nooo!</strong> Incorrect username or passwords" + 
						"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
						"</div>";
				request.setAttribute("error", div);
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}
						
		
		}
		catch(Exception e) {
			
			String div = "<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\r\n" + 
					"  <strong>Oops!</strong>" +e.getMessage() + 
					"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
					"</div>";
			request.setAttribute("error", div);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
	}

}
