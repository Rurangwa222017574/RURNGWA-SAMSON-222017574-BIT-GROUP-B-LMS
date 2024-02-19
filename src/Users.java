

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Users
 */
@WebServlet("/Users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());
		   if(name == null || !role.equals("super")){
			   response.sendRedirect("index.jsp");
		   }
		String roles = request.getParameter("role");
		
		try {
			
			String data = 	 "<div class='card m-2'>"
							+ "<div class='card-body'>"
							+"<h3 class='text-center'>"+roles+" users</h3>"

							+"<table class=\"table table-striped\">" +
							"<tr>"
							+ "<th>#</th>"
							+ "<th>Reg number</th>"
							+ "<th>Names</th>"
							+ "<th>Email</th>"
							+ "<th>Class</th>"
							+ "</tr>";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs1 = statement.executeQuery("SELECT * FROM students JOIN users ON students.id=users.st_id WHERE users.role='"+roles+"'");
			
			int i = 0;
			while(rs1.next()){
				i+=1;
				data += "<tr>"
						+"<td>"+i+"</td>"
						+"<td>"+rs1.getString("reg")+"</td>"
						+"<td>"+rs1.getString("name")+"</td>"
						+"<td>"+rs1.getString("email")+"</td>"
						+"<td>"+rs1.getString("class")+"</td>";
			}
			data += "</table>"
					+"</div>"
					+"</div>";
			
			request.setAttribute("data", data);
			request.setAttribute("location", "View loans");
			RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
			dispatcher.forward(request, response);
			
				
		}
		catch(Exception e) {
			out.print(e.getMessage());
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
