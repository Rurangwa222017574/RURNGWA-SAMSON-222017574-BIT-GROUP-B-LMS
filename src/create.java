

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class create
 */
@WebServlet("/create")
public class create extends HttpServlet {
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
		   if(name == null || !role.equals("student")){
			   response.sendRedirect("index.jsp");
		   }
		String form = "        <form method=\"POST\" action=\"\" >" + 
				"            <div class=\"row\">" + 
				"                <div class=\"col-md-2\"></div>" + 
				"                <div class=\"col-md-5\">" + 
				"		<div class=\"card m-3\">" +
				"<h3 class='text-center'>Loan application</h3>" +
				"		<div class=\"card-body\">" +
				    
				"                <div class=\"form-group\">" + 
				"                <label class=\"required\" for=\"loan_amount\">Amount</label>" + 
				"                <input class=\"form-control loan_amount\" type=\"number\" name=\"amount\" id=\"loan_amount\" value=\"{{ old('loan_amount', '') }}\" step=\"0.01\">" + 
				 
				"            	</div>" +  
				"                <div class=\"form-group\">" + 
				"                <label for=\"description\">Description</label>" + 
				"                <textarea class=\"form-control description\" name=\"description\" id=\"description\"></textarea>" + 
				                

				"            </div>" + 
				"            <div class=\"form-group\">" + 
				"                <input class=\"btn btn-success\" type=\"submit\">" + 
				"" + 
				"" + 
				"                <button class=\"btn btn-danger\" type=\"reset\">Clear</button>" + 
				"            </div>" + 
				"        </form>" + 
				"    </div>" + 
				"</div>" + 
				"" + 
				"" + 
				"";
		

		request.setAttribute("location", "New loan");
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
		int amount = Integer.parseInt(request.getParameter("amount"));
		String desc = request.getParameter("description");
		PrintWriter out = response.getWriter();
		
		LocalDate date = LocalDate.now();
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			statement.execute("INSERT INTO loan_applications (amount,description, st_id, date) values("+amount+",'"+desc+"',"+user+",'"+date+"')");
				
				response.sendRedirect("Viewmyloan");
			
			
			
		}
		catch(Exception e) {
			out.print(e.getMessage());
		}
	}

}
