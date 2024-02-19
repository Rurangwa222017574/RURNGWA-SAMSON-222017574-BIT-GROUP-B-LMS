<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
    

    <title>Login - LMS</title>
   
</head>

<body class="header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden login-page font-montserrat">
    <div class="c-app flex-row auth">
        <div class="container bg-cool-gray-100 mt-1 ">
          <div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card mx-4 mt-4 bg-light">
            <div class="card-body p-4">
                <h1>LMS</h1>

                <p class="text-muted mt-5">Login</p>

               
                    
                       <%
    						if(request.getAttribute("error")!=null){
    					%>
    						
    					<p><%= 	request.getAttribute("error") %></p>
    					<% 
    						}  
    					%>
                   

                <form method="POST" action="checkLogin">
                    

                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-user fa-fw"></i>
                            </span>
                        </div>

                        <input id="email" name="username" type="text" class="form-control" required autocomplete="email" autofocus placeholder="Email">

                        
                    </div>

                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-lock"></i></span>
                        </div>

                        <input id="password" name="password" type="password" class="form-control" required placeholder="password">

                       
                    </div>

                    <div class="input-group mb-4">
                        <div class="form-check checkbox">
                            <input class="form-check-input" name="remember" type="checkbox" id="remember" style="vertical-align: middle;" />
                            <label class="form-check-label" for="remember" style="vertical-align: middle;">
                               Remember Me?
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
                            <button type="submit" class="btn btn-primary px-4">
                                Login
                            </button>
                        </div>
                        <div class="col-6 text-right">

                            <a class="btn btn-link px-4" href="register.jsp">
                               Register
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
        </div>
    </div>
   
</body>

</html>
    