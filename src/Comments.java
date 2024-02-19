

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
 * Servlet implementation class Comments
 */
@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());
		   if(name == null || !role.equals("admin")){
			   response.sendRedirect("index.jsp");
		   }
		String data = 	 "<div class='card m-2'>"
				+ "<div class='card-body'>"
				+"<h3 class='text-center'>Comments</h3>"

				+"<table class=\"table table-striped table-bordered table-hoverable\">" +
				"<tr>"
				+ "<th>Student</th>"
				+ "<th>Reg number</th>"
				+ "<th>Amount</th>"
				+ "<th>Date</th>"
				+ "<th>Comment</th>"
				+ "</tr>";

			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement stm = con.createStatement();
			ResultSet rs1 = stm.executeQuery("SELECT * FROM comments cm JOIN loan_applications la ON cm.loan_id = la.id "
					+ "JOIN students st ON st.id = la.st_id");
			
			while(rs1.next()) {

				data += "<tr>"
						+"<td>"+rs1.getString("name")+"</td>"
						+"<td>"+rs1.getString("reg")+"</td>"
						+"<td>"+rs1.getString("amount")+"rwf</td>"

						+"<td>"+rs1.getString("date")+"</td>"
						+"<td>"+rs1.getString("comment_text")+"</td>"
						+ "</tr>";
			}
			
			data += "</table>"
					+"</div>"
					+"</div>";
			request.setAttribute("data", data);
			request.setAttribute("location", "<a href='Comments'>Comments</a>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
			dispatcher.forward(request, response);
		}
		catch(Exception e) {
			out.println(e.getMessage());
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
