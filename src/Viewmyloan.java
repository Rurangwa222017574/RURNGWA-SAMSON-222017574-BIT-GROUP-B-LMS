

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
 * Servlet implementation class Viewmyloan
 */
@WebServlet("/Viewmyloan")
public class Viewmyloan extends HttpServlet {
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
		   if(name == null || !role.equals("student")){
			   response.sendRedirect("index.jsp");
		   }
		   
		try {
			
			String data = 	 "<div class='card m-2'>"
							+ "<div class='card-body'>"
							+"<h3 class='text-center'>Loan requests</h3>"
							+"<table class=\"table table-striped table-bordered\">" +
							"<tr>"
							+ "<th>Student</th>"
							+ "<th>Reg number</th>"
							+ "<th>Amount</th>"
							+ "<th>Description</th>"
							+ "<th>Date</th>"
							+ "<th>Status</th>"
							+ "</tr>";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs = statement.executeQuery("SELECT * FROM loan_applications INNER JOIN students ON loan_applications.st_id=students.id WHERE loan_applications.status=0 AND loan_applications.st_id="+userid);
			
			while(rs.next()){
				String statusText;
				if (rs.getString("status").equals("0")) {
				  statusText = "Pending";
				} 
				else if(rs.getString("status").equals("1")) {
				  statusText = "Approved";
				}
				else if(rs.getString("status").equals("2")) {
					 statusText = "Rejected";
				}
				else if(rs.getString("status").equals("3")) {
					 statusText = "Paid";
				}
				else {
					 statusText = "no status";
				}
				
				data += "<tr>"
						+"<td>"+rs.getString("name")+"</td>"
						+"<td>"+rs.getString("reg")+"</td>"
						+"<td>"+rs.getString("amount")+" rwf</td>"
						+"<td>"+rs.getString("description")+"</td>"
						+"<td>"+rs.getString("date")+"</td>"
						+"<td>"+statusText+"</td>"
						+"</tr>";
			}
			data += "</table>"
					+"</div>"
					+"</div>";
			
			request.setAttribute("data", data);
			request.setAttribute("location", "Loan requests");
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
