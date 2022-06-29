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
		//request周期中保存變量, 給予navbar使用
	}

	List<Product> products = (List<Product>) request.getAttribute("products"); //搜尋結果商品列表
	String keyword = (String) request.getAttribute("keyword"); //搜尋的關鍵字
	
	ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
	if(cart_list != null){
    	request.setAttribute("cart_list",cart_list);
    }
%>
<!DOCTYPE html>
<html>
<head>
	<title>小而巧購物商城</title>
	<%@include file="includes/head.jsp" %>
	
	<style>	 
	 .card:hover{
	   border-color:orange;
	   box-shadow: 0 10px 20px rgba(0,0,0,.12), 0 4px 8px rgba(0,0,0,.06);
	 }
	</style>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
		
	<div class="container">
		<div class="card-header my-3">'<%= keyword %>'搜尋結果</div>
		<div class="row">
			<%
			if(!products.isEmpty()){
				for(Product p:products){%>
					<!-- 販售的商品內容 -->
					<div class="col-md-3" style="margin-bottom:20px;">
						<div class="card w-100" style="width: 18rem;">
						  <!-- 商品圖片 -->
						  <img src="product-image/<%= p.getImage() %>" class="card-img-top" alt="...">
						  
						  <!-- 商品資訊 -->
						  <div class="card-body">
						    <h5 class="card-title"><%= p.getName() %></h5>
						    <h6 class="price">價格: $<%= p.getPrice() %></h6>
						    <h6 class="category">目錄: <%= p.getCategory() %></h6>
						    
						    <!-- 操作按鈕 -->
						    <div class="mt-3 d-flex justify-content-between">
						    	<a href="add-to-cart?id=<%= p.getId() %>" class="btn btn-primary">放入購物車</a>
						    	<a href="order-now?quantity=1&id=<%= p.getId() %>" class="btn btn-primary">直接購買</a>
						    </div>
						  </div>
						</div>
					</div>
				<%}
			}else{%>
				<h3 class="text-center">找不到結果<br>嘗試不同或更常見的關鍵字</h3>
			<%
			}
			%>			
		</div>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>