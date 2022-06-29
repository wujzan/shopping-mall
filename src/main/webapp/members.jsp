<!-- 軟創三508170624吳倬安 -->
<%@page import="java.util.*" %>
<%@page import="cn.dao.UserDao" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="cn.connection.DbCon" %>
<%@page import="cn.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	User auth = (User) request.getSession().getAttribute("auth");
    	List<User> users = null;
	    if(auth != null && auth.getAdmin() == 1){ //已登入 且 具管理權限
	    	request.setAttribute("auth", auth);	    	
	    	users = new UserDao(DbCon.getConnection()).allUsers(); //查詢所有使用者列表
	    }else{
	    	response.sendRedirect("login.jsp");
	    }
	    
	    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //cart_list讀取購物車
		if(cart_list != null){
	    	request.setAttribute("cart_list",cart_list);
	    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>會員管理</title>
	<%@include file="includes/head.jsp" %>
	<link href="admin.css" rel="stylesheet">
</head>
<body>
	<%@include file="includes/navbarAdmin.jsp" %>
	
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
	<div class="container">
		<!-- 管理員後台管理的 會員管理列表 -->
		<div class="card-header my-3">全部會員帳號列表</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Uid</th>
					<th scope="col">管理員(Admin)權限</th>				
					<th scope="col">姓名</th>
					<th scope="col">Email(登入ID)</th>
					<th scope="col">密碼</th>					
					<th scope="col">移除帳號</th>
				</tr>
			</thead>
			<tbody>
			<%
			if(users != null){
				for(User u:users){%>
					<tr>
						<td><%= u.getId() %></td>
						<td><%= u.getAdmin()==1?"是":"否" %></td>
						<td><%= u.getName() %></td>
						<td><%= u.getEmail() %></td>


						<td>
						  <%= u.getPassword() %>
							
						  <!-- 互動視窗(Modal) - 此為Bootstrap -->
						  <!-- Button trigger modal -->
						  <button type="button" class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#changePwdModal<%= u.getId() %>">
							<i class="fa-solid fa-pen-to-square"></i>
						  </button>
						  						  
						  <!-- Modal -->
						  <div class="modal fade" id="changePwdModal<%= u.getId() %>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
							  <div class="modal-content">        
								<div class="modal-header">
								  <h5 class="modal-title" id="exampleModalLabel">變更密碼</h5>
								  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>						
						
								<!-- 修改會員密碼 -->
								<form action="change-password" method="post" name="form">
									<div class="modal-body">						
										<div class="form-group">
											<input type="hidden" class="form-control" name="uid" value="<%= u.getId() %>">
						
											<!-- 輸入新的密碼 -->
											<label>新密碼</label>
											<input type="password" class="form-control" name="login-password" placeholder="******" required>
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
						
						<!-- 第一個帳號不允許刪除(鎖定按鈕) -->
						<td><a class="btn btn-sm btn-danger <%= u.getAdmin()==1?"disabled":"" %>" href="delete-user?id=<%= u.getId() %>"> 移除 </a></td>
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