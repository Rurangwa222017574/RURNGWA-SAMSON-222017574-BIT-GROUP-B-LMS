

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
 * Servlet implementation class MyLoans
 */
@WebServlet("/MyLoans")
public class MyLoans extends HttpServlet {
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
							+"<h3 class='text-center'>Loans</h3>"

							+"<table class=\"table table-striped table-bordered table-hoverable\">" +
							"<tr>"
							+ "<th>Student</th>"
							+ "<th>Reg number</th>"
							+ "<th>Amount</th>"
							+ "<th>Paid</th>"
							+ "<th>Unpaid</th>"
							+ "<th>Date</th>"
							+ "<th>Status</th>"
							+ "<th>Payment status</th>"
							+ "</tr>";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			
			ResultSet rs1 = statement.executeQuery("SELECT la.id AS application_id, "
					+ "la.description, "
					+ "la.amount, "
					+ "la.date, "
					+ "la.status,"
					+ "la.pay_status,"
					+ "s.id AS student_id,"
					+ "s.name,"
					+ "s.reg,"
					+ "s.email,"
					+ "s.class,"
					+ "lp.p_id AS payment_id,"
					+ "lp.payment_date,"
					+ "SUM(lp.payment_amount) AS payment_amount "
					+ "FROM loan_applications la JOIN students s ON la.st_id=s.id LEFT JOIN loan_payments lp ON la.id=lp.loan_id"
					+ "  WHERE la.status<>0 AND la.st_id="+userid+" GROUP BY la.id, la.description, la.date, la.amount, la.status, la.pay_status, "
					+ "s.id, s.name, s.reg, s.email, s.class");
			
			
			while(rs1.next()){
				String statusText;
				String statusPay="";
				String paid=null;
				String unpaid=null;
				
				if (rs1.getString("status").equals("0")) {
				  statusText = "Pending";
				} 
				else if(rs1.getString("status").equals("1")) {
				  statusText = "Approved";
				}
				else if(rs1.getString("status").equals("2")) {
					 statusText = "Rejected";
					 statusPay = "<i class='badge badge-info'>N/A</i>";
					 paid="-";
					 unpaid="-";
				}
				
				else {
					 statusText = "N/A";
				}
				

				if(rs1.getString("pay_status").equals("0") && statusText != "Rejected") {
					 statusPay = "<i class='badge badge-danger'>Unpaid</i>";
				}
				else if(rs1.getString("pay_status").equals("1") && statusText != "Rejected") {
					 statusPay = "<i class='badge badge-success'>Paid</i>";
				}

				if(rs1.getString("payment_amount")==null && rs1.getString("status").equals("1")) {
					paid="0 rwf";
					unpaid = (rs1.getInt("amount") - rs1.getInt("payment_amount"))+" rwf";
				}

				else if(rs1.getString("payment_amount")!=null && rs1.getString("status").equals("1")) {
					paid=rs1.getString("payment_amount")+" rwf";
					unpaid = (rs1.getInt("amount") - rs1.getInt("payment_amount"))+"rwf";
				}

				data += "<tr>"
						+"<td>"+rs1.getString("name")+"</td>"
						+"<td>"+rs1.getString("reg")+"</td>"
						+"<td>"+rs1.getString("amount")+" rwf</td>"

						+"<td>"+paid+"</td>"
						
						+"<td>"+unpaid+"</td>"
						
						+"<td>"+rs1.getString("date")+"</td>"
						+"<td>"+statusText+"</td>"
						+"<td>"+statusPay+"</td>"
						+"</tr>";
			}
			data += "</table>"
					+"</div>"
					+"</div>";
			
			request.setAttribute("data", data);
			request.setAttribute("location", "My loans");
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
