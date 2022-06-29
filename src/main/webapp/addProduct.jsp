<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	User auth = (User) request.getSession().getAttribute("auth");
	    if(auth == null || auth.getAdmin() == 0){ //沒登入or不具管理權限
	    	response.sendRedirect("index.jsp"); //跳轉回首頁
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>新增商品</title>
	<%@include file="includes/head.jsp" %>
	<link href="admin.css" rel="stylesheet">
</head>
<body>
	<%@include file="includes/navbarAdmin.jsp" %>
	
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">新增商品</div>
			
			<!-- 新增商品的表單 -->
			<div class="card-body">
				<form action="add-product" method="post" enctype="multipart/form-data" name="form">
					<div class="form-group">
					<label>商品名稱</label>
					<input type="text" class="form-control" name="product-name" placeholder="請輸入 商品名稱" required>
					</div>
				
					<div class="form-group">
					<label>目錄</label>
					<input type="text" class="form-control" name="product-category" placeholder="請輸入 目錄" required>
					</div>
					
					<div class="form-group">
					<label>價格</label>
					<input type="number" class="form-control" name="product-price" placeholder="請輸入 價格" required>
					</div>				
					
					<!-- 選擇上傳圖片.jpg or .jpeg -->
					<div class="form-group">
					<label>圖片</label>
					<input type="file" class="form-control" name="file" accept=".jpg,.jpeg" value="select images..."/>
					</div>					
					<br>
					
					<!-- 按下"新增商品"按鈕後,提交表單 -->
					<div class="text-center">
					<button type="submit" class="btn btn-primary">新增商品</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</main>
	
	<%@include file="includes/footer.jsp" %>	
</body>
</html>