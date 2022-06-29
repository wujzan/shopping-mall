<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.model.*" %>
<%@page import="cn.dao.*" %>
<%@page import="cn.connection.DbCon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	    User auth = (User) request.getSession().getAttribute("auth");
	    if(auth == null || auth.getAdmin() == 0){
	    	response.sendRedirect("index.jsp"); //若未登入 或 不具管理員權限,跳回首頁
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
	    
		//////////查詢/////////
	    StatisticDao sDao = new StatisticDao(DbCon.getConnection());
	    int userCount = sDao.getUserStatistic(); //註冊會員數

	    int[] orderStatistic = sDao.getOrderStatistic();
	    int orderCount = orderStatistic[0]; //本站總訂單數
	    int orderTotal = orderStatistic[1]; //本站訂單總交易金額

	    int productsCount = sDao.getProductStatistic(); //上架總商品數
    %>
<!DOCTYPE html>
<html>
<head>
	<title>後台管理 控制台</title>
	<%@include file="includes/head.jsp" %>
	<link href="admin.css" rel="stylesheet">
</head>
<body>
	<%@include file="includes/navbarAdmin.jsp" %>
	
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
		<div class="container">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	        	<h1 class="h2">控制台</h1>	        
	    	</div>
	
			<div class="wellcome" style="text-align:center">
				<h1>歡迎使用後台管理系統!!~</h1>				
				<p class="text-secondary">請使用選單列來選擇管理項目!!~</p>
			</div>
			
			<br>
			
			<!-- 顯示本站各項統計數據 -->
			<div class="row row-cols-1 row-cols-md-2 g-4">
				<div class="col">
				  <div class="card">
					<div class="card-body" style="text-align:center;">					
						<h5 class="card-title">總交易金額</h5>					
						<p class="card-text">新台幣<%= orderTotal %>元</p>
					</div>
				  </div>
				</div>
					
				<div class="col">
				  <div class="card">
					<div class="card-body" style="text-align:center;">					
						<h5 class="card-title">總訂單數</h5>					
						<p class="card-text">共<%= orderCount %>筆訂單</p>
					</div>
				  </div>
				</div>
					  
				<div class="col">
				  <div class="card">
					<div class="card-body" style="text-align:center;">					
						<h5 class="card-title">上架總商品數</h5>					
						<p class="card-text"><%= productsCount %>筆商品</p>
					</div>
				  </div>
				</div>
				
				<div class="col">	
				  <div class="card">        
					<div class="card-body" style="text-align:center;">
						<h5 class="card-title">註冊會員數</h5>					
						<p class="card-text"><%= userCount %>人</p>		
						</div>
					  </div>
				</div>
			</div>							  	 
		</div>
	</main>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>