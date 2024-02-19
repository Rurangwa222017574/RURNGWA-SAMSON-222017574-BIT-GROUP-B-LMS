

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Pay
 */
@WebServlet("/Pay")
public class Pay extends HttpServlet {
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



		PrintWriter p = response.getWriter();
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());
		   if(name == null || !role.equals("admin")){
			   response.sendRedirect("index.jsp");
		   }

			int loanid = Integer.parseInt(request.getParameter("id"));

			int amount = Integer.parseInt(request.getParameter("amount").toString());
			
			LocalDate date = LocalDate.now();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 

			ResultSet rs = statement.executeQuery("SELECT amount, sum(payment_amount) as payment_amount FROM loan_applications JOIN loan_payments WHERE loan_applications.id="+loanid+" AND loan_payments.loan_id="+loanid);
			//ResultSet ps = statement.executeQuery("SELECT sum(payment_amount) FROM loan_payments WHERE loan_id="+loanid);
			rs.next();
			
//				p.print(rs.getInt("amount"));
//				ps.next();

				if(rs.getInt("amount") > rs.getInt("payment_amount")+amount) {
					statement.execute("INSERT INTO loan_payments values(null,"+loanid+",'"+date+"',"+amount+","+userid+")");
				}

				else if(rs.getInt("amount") == rs.getInt("payment_amount")+amount) {
					statement.execute("INSERT INTO loan_payments values(null,"+loanid+",'"+date+"',"+amount+","+userid+")");
					statement.execute("UPDATE loan_applications SET pay_status=1 WHERE id="+loanid);
				}
				else {
					response.sendRedirect("Loans");
				}
				con.close();
			response.sendRedirect("Loans");
			
		}
		catch(Exception e) {
			p.print(e.getMessage());
		}
	}

}
