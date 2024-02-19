

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

import com.mysql.cj.protocol.Resultset;

/**
 * Servlet implementation class Viewloan
 */
@WebServlet("/Viewloan")
public class Viewloan extends HttpServlet {
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
		   if(name == null || !role.equals("admin")){
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
							+ "<th>Decision</th>"
							+ "</tr>";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs = statement.executeQuery("SELECT * FROM loan_applications INNER JOIN students ON loan_applications.st_id=students.id WHERE loan_applications.status=0");
			
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
						
						+"<td><a href=\"Approve?id="+rs.getString("loan_applications.id")+"&action=approve\" class=\"btn btn-success btn-sm\">Approve</a>"
						
						+"<div class=\"dropdown d-inline\">\r\n" + 
						"  <button class=\"btn btn-danger btn-sm dropdown-toggle\" type=\"button\" id=\"dropdownMenuButton1\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\r\n" + 
						"    Reject\r\n" + 
						"  </button>\r\n" + 
						"<div class=\"dropdown-menu w-20\">\r\n" + 
						"  <form class=\"form\" action='Approve'>\r\n" + 
						"    <div class=\"mb-3\">\r\n" + 
						"      <label for=\"coment\" class=\"form-label\">Coment</label>\r\n" + 
						"     <textarea name=coment class='form-control'></textarea>" + 
						"    </div>\r\n" + 
						"<input type='hidden' name='action' value='reject'>"+
						"<input type='hidden' name='id' value="+rs.getString("loan_applications.id")+">"+
						"    <button type=\"submit\" class=\"btn btn-sm btn-primary\">Send</button>\r\n" + 
						"  </form>\r\n" + 
						"</div>\r\n" + 
						"</td>"
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

	}

}
