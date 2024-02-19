

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
 * Servlet implementation class AddStudent
 */
@WebServlet("/AddStudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());
		   if(name == null || !role.equals("super")){
			   response.sendRedirect("index.jsp");
		   }
		String form = "        <form method=\"POST\" action=\"\" >" + 
				"            <div class=\"row\">" + 
				"                <div class=\"col-md-2\"></div>" + 
				"                <div class=\"col-md-5\">" + 
				"<h3 class='text-center'>Student registration</h3>"+
				"		<div class=\"card\">" + 
				"		<div class=\"card-body p-4\">" +
				    
				"                <div class=\"form-group\">" + 
				"                <label class=\"required\" for=\"regno\">Reg number:</label>" + 
				"                <input class=\"form-control\" type=\"text\" name=\"regno\" id=\"regno\" required>" + 
				 
				"            	</div>" + 

				"                <div class=\"form-group\">" + 
				"                <label class=\"required\" for=\"name\">Names:</label>" + 
				"                <input class=\"form-control\" type=\"text\" name=\"name\" id=\"name\" required>" + 
				 
				"            	</div>" + 

				"                <div class=\"form-group\">" + 
				"                <label class=\"required\" for=\"email\">Email:</label>" + 
				"                <input class=\"form-control\" type=\"email\" name=\"email\" id=\"email\" required>" + 
				 
				"            	</div>" + 

				"                <div class=\"form-group\">" + 
				"                <label class=\"required\" for=\"class\">Class:</label>" + 
				"                <select class=\"form-control\" name=\"class\" id=\"class\" required>"
				+ "<option value=\"BTECH IT\">BTECH IT</option>"
				+ "<option value=\"BTECH CONS. T\">CONS. T</option>"
				+ "<option value=\"BTECH MECH\">BTECH MECH</option>"
				+ "<option value=\"BTECH EEE\">BTECH EEE</option>"
				+ "<option value=\"L7 DRAINAGE\">L7 DRAINAGE</option>"
				+ "<option value=\"L3 BIT\">L3 BIT</option>"
				+ "<option value=\"L4 CROP\">L4 CROP</option>"
				
				+ "				</select>" + 
				 
				"            	</div>" + 
				"            <div class=\"form-group\">" + 
				"                <input class=\"btn btn-success\" type=\"submit\" value='Register'>" + 
				 
				"                <button class=\"btn btn-danger\" type=\"reset\">Clear</button>" + 
				"            </div>" + 
				"        </form>" + 
				"    </div>" + 
				"</div>" + 
				"" + 
				"" + 
				"";
		

		request.setAttribute("location", "student registration");
		request.setAttribute("data", form);
		RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int user = Integer.parseInt(session.getAttribute("id").toString());


		String regno = request.getParameter("regno");
		String names = request.getParameter("name");
		String email = request.getParameter("email");
		String cls = request.getParameter("class");
		
		PrintWriter out = response.getWriter();
		
		LocalDate date = LocalDate.now();
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) as count FROM students WHERE reg="+regno);
			rs.next();
			
			if(rs.getInt("count")==0) {
				statement.execute("INSERT INTO students VALUES(null,'"+names+"','"+regno+"','"+email+"','"+cls+"')");
			}
				response.sendRedirect("Superlandingpage");
			
			
			
		}
		catch(Exception e) {
			out.print(e.getMessage());
		}
	}

}
