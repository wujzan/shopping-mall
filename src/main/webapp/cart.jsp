<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.dao.ProductDao" %>
<%@page import="cn.connection.DbCon" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%    
	    User auth = (User) request.getSession().getAttribute("auth");
	    if(auth != null){
	    	request.setAttribute("auth", auth);
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
	    List<Cart> cartProduct = null;
	    int total=0;
	    if(cart_list != null){
	    	ProductDao pDao = new ProductDao(DbCon.getConnection());
	    	cartProduct = pDao.getCartProducts(cart_list); //傳入cart_list(僅商品ID&和數量),查詢MySQL,取得商品細節	    	
	    	request.setAttribute("cart_list",cart_list);
	    	
	    	total = pDao.getTotalCartPrice(cart_list); //購物車結算的總金額
	    	request.setAttribute("total",total);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>我的購物車 Cart</title>
	<%@include file="includes/head.jsp" %>
	
	<style type="text/css">
		.table tbody td{
			vartical-align:middle;
		}		
		.btn-incre, .btn-decre{
			box-shadow:name;
			font-size:25px;
		}
	</style>	
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<!-- 購物車 -->
		<div class="d-flex py-3">			
			<h3>總金額: $<%= total %></h3>
			<a class="mx-3 btn btn-primary" href="cart-check-out">全部結帳</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">商品名稱</th>
					<th scope="col">目錄</th>
					<th scope="col">價格</th>
					<th scope="col">直接購買</th>
					<th scope="col">取消</th>
				</tr>
			</thead>
			<tbody>
				<%
				if(cart_list != null){
					for(Cart c:cartProduct){%>
						<tr>
							<td><%= c.getName() %></td>
							<td><%= c.getCategory() %></td>
							<td>$<%= c.getPrice() %></td>
							<td>
								<form action="order-now" method="post" class="form-inline" name="form">
									<input type="hidden" name="id" value="<%= c.getId() %>" class="form-input">
									<div class="form-group d-flex justify-content-between w-50">
										<!-- 減少商品數量 -->
										<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId() %>"><i class="fas fa-minus-square"></i></a>
										
										<!-- 顯示商品數量 -->
										<input type="text" name="quantity" value="<%= c.getQuantity() %>" class="form-control w-50" readonly>
										
										<!-- 增加商品數量 -->
										<a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId() %>"><i class="fas fa-plus-square"></i></a>
										
										<!-- 按下"購買"按鈕後,提交表單(單項目商品) -->
										<button type="submit" class="btn btn-primary btn-sm">購買</button>
									</div>									
								</form>
							</td>
							
							<!-- 按下"移除"按鈕後,從購物車移除此項商品 -->
							<td><a class="btn btn-sm btn-danger" href="remove-from-cart?id=<%= c.getId() %>">移除</a></td>
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