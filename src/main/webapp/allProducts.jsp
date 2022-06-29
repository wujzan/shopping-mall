<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.dao.ProductDao" %>
<%@page import="cn.connection.DbCon" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%
		User auth = (User) request.getSession().getAttribute("auth");
		if(auth == null || auth.getAdmin() == 0){  //沒登入or不具管理權限
	    	response.sendRedirect("index.jsp");  //跳轉回首頁
	    }
	
		ProductDao pd = new ProductDao(DbCon.getConnection());
		List<Product> products = pd.getAllProduct(); //請求查看販售商品列表
		
		ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
	%>
<!DOCTYPE html>
<html>
<head>
	<title>商品管理</title>
	<%@include file="includes/head.jsp" %>
	<link href="admin.css" rel="stylesheet">
</head>
<body>
	<%@include file="includes/navbarAdmin.jsp" %>
	
	<!-- <% out.print(DbCon.getConnection()); %> -->
	
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
	<div class="container">		
		<!-- 管理員後台管理的 (販售商品)商品管理列表 -->
		<div class="card-header my-3">全部商品列表</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">商品ID</th>
					<th scope="col">商品名稱</th>
					<th scope="col">目錄</th>
					<th scope="col">價格</th>
					<th scope="col">刪除商品</th>
				</tr>
			</thead>
			<tbody>
			<%
			if(!products.isEmpty()){
				for(Product p:products){%>
					<tr>
						<td><%= p.getId() %></td>
						<td><%= p.getName() %></td>
						<td><%= p.getCategory() %></td>
												
						<td>
						  <%= p.getPrice() %>
							
						  <!-- 互動視窗(Modal) - 此為Bootstrap -->
						  <!-- Button trigger modal -->
						  <button type="button" class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#changePriceModal<%= p.getId() %>">
							<i class="fa-solid fa-pen-to-square"></i>
						  </button>
						  
						  
						  <!-- Modal -->
						  <div class="modal fade" id="changePriceModal<%= p.getId() %>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
							  <div class="modal-content">        
								<div class="modal-header">
								  <h5 class="modal-title" id="exampleModalLabel">修改金額</h5>
								  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>						
						
								<!-- 修改販售金額 -->
								<form action="change-product-price" method="post" name="form">
									<div class="modal-body">						
										<div class="form-group">
											<input type="hidden" class="form-control" name="p_id" value="<%= p.getId() %>">
						
											<!-- 輸入新的金額 -->
											<label>新價格</label>											
											<input type="number" class="form-control" name="product-price" required>
										</div>						
									</div>
						
									<div class="modal-footer">
										<!-- 按下"取消"按鈕後,關閉 互動視窗(Modal) -->
										<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
										<!-- 按下"保存修改"按鈕後,提交表單,並關閉 互動視窗(Modal) -->
										<button type="submit" class="btn btn-primary">保存修改</button>
									</div>
								</form>								
						
							  </div>
							</div>
						  </div>
						</td>						
						
						<!-- 按下"刪除"按鈕,刪除商品 -->
						<td><a class="btn btn-sm btn-danger" href="delete-product?id=<%= p.getId() %>"> 刪除 </a></td>
					</tr>					
				<%}
			}
			%>
			</tbody>
		</table>
	</div>
	</main>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>