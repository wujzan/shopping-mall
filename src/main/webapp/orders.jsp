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
	    if(auth != null && auth.getAdmin() == 1){ //已登入 且 具有管理權限
	    	request.setAttribute("auth", auth);	    	
	    	orders = new OrderDao(DbCon.getConnection()).allOrders(); //查詢所有訂單列表
	    }else{
	    	response.sendRedirect("login.jsp"); //跳回登入頁面
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>訂單管理</title>
	<%@include file="includes/head.jsp" %>
	<link href="admin.css" rel="stylesheet">
</head>
<body>
	<%@include file="includes/navbarAdmin.jsp" %>
	
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
	<div class="container">
		<!-- 管理員後台管理的 訂單管理列表 -->
		<div class="card-header my-3">全部訂單列表</div>
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
					<th scope="col">取消訂單</th>
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
							
							<!-- 互動視窗(Modal) - 此為Bootstrap -->
							<!-- Button trigger modal -->
							<button type="button" class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#changeStatusModal<%= o.getOrderId() %>">
								<i class="fa-solid fa-pen-to-square"></i>
						  	</button>						  
						  
							<!-- Modal -->
							<div class="modal fade" id="changeStatusModal<%= o.getOrderId() %>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog">
								  <div class="modal-content">        
									<div class="modal-header">
									  <h5 class="modal-title" id="exampleModalLabel">訂單狀態</h5>
									  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									</div>						
							
									<!-- 修改訂單處理狀態 -->
									<form action="change-order-status" method="post" name="form">
										<div class="modal-body">						
											<div class="form-group">
												<input type="hidden" class="form-control" name="o_id" value="<%= o.getOrderId() %>">
							
												<!-- 下拉選單選擇訂單狀態 -->
												<label>請選擇</label>												
												<select name="order-status" class="form-select">
										            <option value="0">處理中</option>
										            <option value="1">配送中</option>
										            <option value="2">已完成</option>
										        </select>
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
						
						<!-- 按下"取消"按鈕後,刪除訂單 -->
						<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%= o.getOrderId() %>"> 取消 </a></td>
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