

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Superlandingpage
 */
@WebServlet("/Superlandingpage")
public class Superlandingpage extends HttpServlet {
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
		   if(name == null || !role.equals("super")){
			   response.sendRedirect("index.jsp");
		   }

		   PreparedStatement stm1 = null;
		   PreparedStatement stm2 = null;
		   PreparedStatement stm3 = null;
		   PreparedStatement stm4 = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS","root","");

			stm1 = con.prepareStatement("SELECT count(*) AS result1 FROM students");
			stm2 = con.prepareStatement("SELECT count(*) AS result2 FROM users WHERE role='student'");
			stm3 = con.prepareStatement("SELECT count(*) AS result3 FROM users WHERE role='admin'");


			ResultSet count1=stm1.executeQuery();
			ResultSet count2=stm2.executeQuery();
			ResultSet count3=stm3.executeQuery();
		
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
				"				<div class=\"col-md-4\">\r\n" +
			    "                        <div class=\"card bg-primary text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">All students</div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count1.next()) { 		
				data += count1.getInt("result1");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-graduation-cap\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"AllStudents\">View all students</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n"  +    
				"                <div class=\"col-md-4\">\r\n" +
				"            <div class=\"card bg-info text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Registered users</div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count2.next()) { 		
					data += count2.getInt("result2");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-users\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"Users?role=student\">View registered students</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n"+
				"				<div class=\"col-md-4\">\r\n" + 
				"                        <div class=\"card bg-success text-white mb-3\">\r\n" + 
				"                            <div class=\"card-body\">\r\n" + 
				"                                <div class=\"d-flex justify-content-between align-items-center\">\r\n" + 
				"                                    <div class=\"mr-3\">\r\n" + 
				"                                        <div class=\"text-white-75 small\">Admins </div>\r\n" + 
				"                                        <div class=\"text-lg font-weight-bold\">";
				while(count3.next()) { 		
					data += count3.getInt("result3");
				}
				data+="</div>\r\n" + 
				"                                    </div>\r\n" + 
				"                                    <i class=\"fas fa-briefcase\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div class=\"card-footer d-flex align-items-center justify-content-between\">\r\n" + 
				"                                <a class=\"small text-white stretched-link\" href=\"Users?role=admin\">View admins</a>\r\n" + 
				"                                <div class=\"small text-white\">\r\n" + 
				"                                	<i class=\"fas fa-angle-right\"></i>\r\n" + 
				"                                </div>\r\n" + 
				"                            </div>\r\n" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n" +
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
