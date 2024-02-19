 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
  <% 
	 String name = (String) session.getAttribute("name");
	 String role = (String) session.getAttribute("role");

	   if(name == null){
		   response.sendRedirect("index.jsp");
	   }
    %>

<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="js/app.js" defer></script>
    <script src="js/main.js " defer></script>
     <!-- Jquery JS-->
    <script src="vendor/jquery-3.2.1.min.js "  defer></script>
    <!-- Bootstrap JS-->
    <script src=" vendor/bootstrap-4.1/popper.min.js " defer></script>
    <script src=" vendor/bootstrap-4.1/bootstrap.min.js " defer></script>
    <!-- Vendor JS       -->
    <script src=" vendor/slick/slick.min.js " defer>
    </script>
    <script src=" vendor/wow/wow.min.js " defer></script>
    <script src=" vendor/animsition/animsition.min.js " defer></script>
    <script src=" vendor/bootstrap-progressbar/bootstrap-progressbar.min.js " defer>
    </script>
    <script src=" vendor/counter-up/jquery.waypoints.min.js " defer></script>
    <script src=" vendor/counter-up/jquery.counterup.min.js " defer>
    </script>
    <script src=" vendor/circle-progress/circle-progress.min.js " defer></script>
    <script src=" vendor/perfect-scrollbar/perfect-scrollbar.js " defer></script>
    <script src=" vendor/chartjs/Chart.bundle.min.js " defer></script>
    <script src=" vendor/select2/select2.min.js " defer>
    </script>


    <!-- Fonts -->
    <link rel="dns-prefetch" href="//fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!-- Styles -->
    <link href=" css/app.css " rel="stylesheet">
      <link href=" css/font-awesome.css " rel="stylesheet"/>
    <link href=" css/font-awesome-all.min.css " rel="stylesheet"/>
    <link href=" css/theme.css " rel="stylesheet" media="all">
    <link href=" vendor/font-awesome-5/css/fontawesome-all.min.css " rel="stylesheet" media="all">
    <link href=" vendor/mdi-font/css/material-design-iconic-font.min.css " rel="stylesheet" media="all">

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>




    </style>
    </head>
	<body class="" data-spy="scroll">
     <div id="app " >
         <div class="page-wrapper">
        <!-- MENU SIDEBAR-->
        <aside class="menu-sidebar2">
            <div class="logo">
                <h5><a href="">LMS  </a></h5>
            </div>
			<%
				if(role.equals("super")){
			%>
                <nav class="navbar-sidebar2">
                    <ul class="list-unstyled navbar__list">
                        <li class="active has-sub">
                            <a class="js-arrow" href="Superlandingpage">
                                <i class="fas fa-tachometer-alt"></i>Dashboard

                            </a>


                        <li>
                            <a href="AddStudent">
                                <i class="fa fa-users"></i>Add student</a>
                        </li>
                        <li>
                            <a href="AllStudents">
                                <i class="fas fa-briefcase"></i>Add admins</a>
                        </li>
                       
                        
                        <li>
                            <a href="logout">
							<i class="fas  fa-lock"></i>Log Out</a>
                        </li>

                            </ul>
                        </li>
                    </ul>
                </nav>
                <% } 
			
				else if(role.equals("admin")){
			%>
            <nav class="navbar-sidebar2">
                <ul class="list-unstyled navbar__list">
                    <li class="active has-sub">
                        <a class="js-arrow" href="Adminlandingpage">
                            <i class="fas fa-tachometer-alt"></i>Dashboard

                        </a>


                    <li>
                        <a href="Viewloan">
                            <i class="fa fa-credit-card"></i>Loan requests</a>
                    </li>
                    <li>
                        <a href="Loans">
                            <i class="fas fa-dollar-sign"></i>Loan applications</a>
                    </li>
                    
                    <li>
                        <a href="Comments">
                            <i class="fas  fa-comment"></i>Comments</a>
                    </li>
                    
                    <li>
                        <a href="Report">
                            <i class="fas  fa-book"></i>Reports</a>
                    </li>
                    <li>
                        <a href="logout">
						<i class="fas  fa-lock"></i>Log Out</a>
                    </li>

                        </ul>
                    </li>
                </ul>
            </nav>
            <% } 
				else if(role.equals("student")){
                %>
                 <nav class="navbar-sidebar2">
                    <ul class="list-unstyled navbar__list">
                        <li class="active has-sub">
                            <a class="js-arrow" href="Studentlandingpage">
                                <i class="fas fa-tachometer-alt"></i>Dashboard

                            </a>


                        <li>
                            <a href="Viewmyloan">
                                <i class="fa fa-credit-card"></i>Loan requests</a>
                        </li>
                        <li>
                            <a href="MyLoans">
                                <i class="fas fa-dollar-sign"></i>Loan applications</a>
                        </li>
                        
                        
                        <li>
                            <a href="logout">
							<i class="fas  fa-lock"></i>Log Out</a>
                        </li>

                            </ul>
                        </li>
                    </ul>
                </nav>
                <%} %>
            </div>
        </aside>
        <!-- END MENU SIDEBAR-->

        <!-- PAGE CONTAINER-->
        <div class="page-container2">
            <!-- HEADER DESKTOP-->
            <header class="header-desktop2">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="header-wrap2">
                            
                            <div class="header-button2 text-light"><b>
                              <%= session.getAttribute("name") %>
	                        </b></div>
	                    </div>
	                </div>
	            </header>

            <!-- END HEADER DESKTOP-->

            <!-- BREADCRUMB-->
            <section class="au-breadcrumb m-t-75">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="au-breadcrumb-content">
                                    <div class="au-breadcrumb-left">
                                        
                                        <ul class="list-unstyled list-inline au-breadcrumb__list">
                                            <li class="list-inline-item active">
                                                Home
                                            </li>
                                            <li class="list-inline-item seprate">
                                                <span>/</span>
                                            </li>
                                            <li class="list-inline-item"><%= request.getAttribute("location") %></li>
                                        </ul>
										
                            </div>
                             	<% if(role.equals("student")){  %>
				                   <a href="create" class="btn btn-outline-primary flex-end" style="width:40px; height:40px; border-radius:20px; border: 2px solid skyblue" title="New loan"><b>+</b></a>
				                <% } %>
                        </div>
                        
                    </div>
                </div>
            </section>
            <!-- END BREADCRUMB-->
        <main class="py-4">
            
            <%= 
            	request.getAttribute("data")
            %>
            
        </main>
    </div>


   
</body>
</html>
