<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	    User auth = (User) request.getSession().getAttribute("auth");
	    if(auth != null){ //已經登入狀態,跳轉回首頁(以防重複登入)
	    	response.sendRedirect("index.jsp"); 
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>會員登入 Login</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
<%@include file="includes/navbar.jsp" %>
<div class="container">
	<div class="card w-50 mx-auto my-5">
		<div class="card-header text-center">使用者登入</div>
		
		<!-- 輸入帳號密碼的登入表單 -->
		<div class="card-body">
			<form action="user-login" method="post" name="form">
				<div class="form-group">
				<label>Email地址</label>
				<input type="email" class="form-control" name="login-email" placeholder="Enter Your Email" required>
				</div>
				
				<div class="form-group">
				<label>密碼</label>
				<input type="password" class="form-control" name="login-password" placeholder="******" required>
				</div>
				
				<div class="text-center">
				<button type="submit" class="btn btn-primary">登入</button>
				</div>
			</form>
		</div>
	</div>
</div>

<%@include file="includes/footer.jsp" %>
</body>
</html>