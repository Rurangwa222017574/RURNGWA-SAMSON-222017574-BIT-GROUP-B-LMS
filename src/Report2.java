

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

import com.mysql.cj.xdevapi.JsonArray;
import org.json.*;
/**
 * Servlet implementation class Report2
 */
@WebServlet("/Report2")
public class Report2 extends HttpServlet {
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
		   
		String data1 = request.getParameter("data1");
		String data2 = request.getParameter("data2");
		
		

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");
			
			Statement statement = con.createStatement(); 
			ResultSet rs1 = null;

			if(!data2.equals("3") && !data1.equals("3")) {
				rs1 = statement.executeQuery("SELECT la.id AS application_id, "
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
				+ "  WHERE la.status="+data1+" AND la.pay_status="+data2+" GROUP BY la.id, la.description, la.date, la.amount, la.status, la.pay_status, "
				+ "s.id, s.name, s.reg, s.email, s.class");
			}

			else if(data2.equals("3") && !data1.equals("3")) {
				rs1 = statement.executeQuery("SELECT la.id AS application_id, "
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
				+ "  WHERE la.status="+data1+" GROUP BY la.id, la.description, la.date, la.amount, la.status, la.pay_status, "
				+ "s.id, s.name, s.reg, s.email, s.class");
			}
			else if(!data2.equals("3") && data1.equals("3")) {
				rs1 = statement.executeQuery("SELECT la.id AS application_id, "
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
				+ "  WHERE la.status=1 AND la.pay_status="+data2+" GROUP BY la.id, la.description, la.date, la.amount, la.status, la.pay_status, "
				+ "s.id, s.name, s.reg, s.email, s.class");
			}
			else{
				rs1 = statement.executeQuery("SELECT la.id AS application_id, "
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
				+ "  WHERE la.status<>0 GROUP BY la.id, la.description, la.date, la.amount, la.status, la.pay_status, "
				+ "s.id, s.name, s.reg, s.email, s.class");
			}
				
			String data ="";
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
					 statusPay = "<i class='text-info'>N/A</i>";
					 paid="-";
					 unpaid="-";
				}
				
				else {
					 statusText = "N/A";
				}
				

				if(rs1.getString("pay_status").equals("0") && statusText != "Rejected") {
					 statusPay = "<i class='text-danger'>Unpaid</i>";
				}
				else if(rs1.getString("pay_status").equals("1") && statusText != "Rejected") {
					 statusPay = "<i class='text-success'>Paid</i>";
				}

				if(rs1.getString("payment_amount")==null && rs1.getString("status").equals("1")) {
					paid="0 rwf";
					unpaid = (rs1.getInt("amount") - rs1.getInt("payment_amount"))+"rwf";
				}

				else if(rs1.getString("payment_amount")!=null && rs1.getString("status").equals("1")) {
					paid=rs1.getString("payment_amount")+"rwf";
					unpaid = (rs1.getInt("amount") - rs1.getInt("payment_amount"))+"rwf";
				}

				data += "<tr>"
						+"<td>"+rs1.getString("name")+"</td>"
						+"<td>"+rs1.getString("reg")+"</td>"
						+"<td>"+rs1.getString("amount")+"rwf</td>"

						+"<td>"+paid+"</td>"
						
						+"<td>"+unpaid+"</td>"
						
						+"<td>"+rs1.getString("date")+"</td>"
						+"<td>"+statusText+"</td>"
						+"<td>"+statusPay+"</td>"
						+ "</tr>";
			}

			
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();

			obj.put("data", data);

			
			String jsonData = obj.toString();
			
			out.print(data);
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
