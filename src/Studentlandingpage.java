

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class Studentlandingpage
 */
@WebServlet("/Studentlandingpage")
public class Studentlandingpage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		HttpSession session = request.getSession(false);
		
		String name = (String) session.getAttribute("name");

		String role = (String) session.getAttribute("role");

		int userid = Integer.parseInt(session.getAttribute("id").toString());


		   if(name == null || !role.equals("student")){
			   response.sendRedirect("index.jsp");
		   }

		   PreparedStatement stm1 = null;
		   PreparedStatement stm2 = null;
		   PreparedStatement stm3 = null;
		   PreparedStatement stm4 = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");

			stm1 = con.prepareStatement("SELECT count(*) AS result1 FROM loan_applications WHERE  st_id="+userid);
			stm2 = con.prepareStatement("SELECT count(*) AS result2 FROM loan_applications WHERE status=0 AND st_id="+userid);
			stm3 = con.prepareStatement("SELECT count(*) AS result3 FROM loan_applications WHERE status=1 AND pay_status=1 AND st_id="+userid);
			stm4 = con.prepareStatement("SELECT count(*) AS result4 FROM loan_applications WHERE status=1 AND pay_status=0 AND st_id="+userid);

			ResultSet count1=stm1.executeQuery();
			ResultSet count2=stm2.executeQuery();
			ResultSet count3=stm3.executeQuery();
			ResultSet count4=stm4.executeQuery();
		
		String data = "<div class=\"containe-fluid\">\r\n" + 
				"\r\n" + 
				"	<div class=\"row\">\r\n" + 
				"		<div class=\"col-lg-12\">\r\n" + 
				"\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"\r\n" + 
				"	<div class=\"row mt-3 ml-3 mr-3\">\r\n" + 
				"			<div class=\"col-lg-12\">\r\n" + 
				"			<div class=\"card bg-gray-600 text-blue-200\" >\r\n" + 
				"				<div class=\"card-body\">\r\n" + 
				"						<h1>"+ name +" Dashboard</h1>\r\n" + 
				"				</div>\r\n" + 
				"				<hr>\r\n" + 
				"				<div class=\"row ml-2 mr-2\">\r\n" +
				"				<div class=\"col-md-3\">\r\n" +
			    "                        <div class=\"card bg-primary text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Loans Requested</div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count1.next()) { 		
				data += count1.getInt("result1");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-clipboard\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"MyDashboardCards?action=all\">View Loans Requested</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n"  +    
				"                <div class=\"col-md-3\">\r\n" +
				"            <div class=\"card bg-danger text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Pending Loans</div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count2.next()) { 		
					data += count2.getInt("result2");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-clock\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"Viewmyloan\">View Pending Loans</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n"+
				"				<div class=\"col-md-3\">\r\n" + 
				"                        <div class=\"card bg-success text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Paid Loans </div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count3.next()) { 		
					data += count3.getInt("result3");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-check-circle\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"MyDashboardCards?action=paid\">View paid Loans</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n" +
				"                     <div class=\"col-md-3\">\r\n" + 
				"                        <div class=\"card bg-warning text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Unpaid Loans</div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count4.next()) { 		
					data += count4.getInt("result4");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-exclamation-triangle\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"MyDashboardCards?action=unpaid\">View Unpaid Loans</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n" + 
				"\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"\r\n" + 
				"		</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"\r\n" + 
				"</div>"; 

		request.setAttribute("data", data);
		request.setAttribute("location", "Dashboard");
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
