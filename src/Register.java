

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		String regno = request.getParameter("regno");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");
		
		
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs = statement.executeQuery("SELECT count(*) AS stud FROM students WHERE reg='"+regno+"' AND email='"+email+"'");
			rs.next();
			
			if(rs.getInt("stud")==0) {
				
				String div = "<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\r\n" + 
						"  <strong>Wooo!</strong> Not matching with any student record!" + 
						"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
						"</div>";
				
				request.setAttribute("error", div);
				RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
				dispatcher.forward(request, response);
			}	
			else {
			if(!password.equals(confirm)){

				String div = "<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\r\n" + 
						"  <strong>Yallaaa!</strong> Confirmation password not matching" + 
						"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
						"</div>";
				request.setAttribute("error", div);
				RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
				dispatcher.forward(request, response);
			}
			else {
				ResultSet rs2 = statement.executeQuery("SELECT COUNT(*) AS available, students.id AS stud_id, role FROM users JOIN students ON students.id=users.st_id WHERE students.reg='"+regno+"'");
				rs2.next();
				if(rs2.getInt("available")>=1 && rs2.getString("role")=="student") {

					String div = "<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\r\n" + 
							"  <strong>Please!</strong> You can not have more than one accounts!" + 
							"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
							"</div>";
					request.setAttribute("error", div);
					RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
					dispatcher.forward(request, response);
					
				}
				else {
					LocalDate date = LocalDate.now();
					statement.execute("INSERT INTO users VALUES(null, 'student','"+password+"','"+date+"',"+rs2.getString("stud_id")+")");
					response.sendRedirect("index.jsp");
				}
			}
		}
						
		
		}
		catch(Exception e) {
			
			String div = "<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\r\n" + 
					"  <strong>Error! </strong> " +e.getMessage() + 
					"  <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n" + 
					"</div>";
			request.setAttribute("error", div);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
	}

}
