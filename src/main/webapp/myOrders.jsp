<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.dao.OrderDao" %>
<%@page import="cn.connection.DbCon" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%	    
    	User auth = (User) request.getSession().getAttribute("auth");
    	List<Order> orders = null;
	    if(auth != null){ //若已登入,取得自己的歷史訂單列表
	    	request.setAttribute("auth", auth);
	    	orders = new OrderDao(DbCon.getConnection()).userOrders(auth.getId());
	    }else{
	    	response.sendRedirect("login.jsp"); //若未登入,跳回首頁
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>我的訂單 Orders</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<!-- 會員查看自己的歷史訂單列表 -->
		<div class="card-header my-3">我的訂單</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">日期</th>
					<th scope="col">訂單ID</th>
					<th scope="col">商品名稱</th>
					<th scope="col">目錄</th>
					<th scope="col">數量</th>
					<th scope="col">價格</th>
					<th scope="col">狀態</th>
				</tr>
			</thead>
			<tbody>
			<%
			if(orders != null){
				for(Order o:orders){%>
					<tr>
						<td><%= o.getDate() %></td>
						<td><%= o.getOrderId() %></td>
						<td><%= o.getName() %></td>
						<td><%= o.getCategory() %></td>
						<td><%= o.getQuantity() %></td>
						<td><%= o.getPrice() %></td>
						<td>
							<% if(o.getStatus()==0){ %>
									處理中
							<% }else if(o.getStatus()==1){ %>
									配送中
							<% }else{ %>
									已完成
							<% } %>
						</td>
					</tr>					
				<%}
			}
			%>
			</tbody>
		</table>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>