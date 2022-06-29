<!-- 軟創三508170624吳倬安 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">小而巧購物商城</a>    
    <!-- Search form -->        
    <form class="d-flex" action="search" method="get" name="form">
   	  <div class="input-group">	  
	    <input type="search" class="form-control" name="keyword" placeholder="搜尋商品" required/>
	    <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i></button>
	  </div>
	</form>
    
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
	
    <!-- Menu -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ms-auto mb-2 mb-lg-0">        
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="index.jsp">首頁</a>
        </li>        
        <li class="nav-item">
          <a class="nav-link" href="cart.jsp">購物車<span class="badge bg-danger">${ cart_list.size() }</span></a>
        </li>
        
        <%@page import="cn.model.*" %>
        <% if(request.getAttribute("auth") != null){ %>
	        <li class="nav-item"><a class="nav-link" href="myOrders.jsp">我的訂單</a></li>
	        
	        <% if(((User) request.getSession().getAttribute("auth")).getAdmin() == 1){ %>
	        	<li class="nav-item fw-bold"><a class="nav-link" href="dashboard.jsp">後台管理(AdminArea)</a></li>
	        <% } %>
	        
	        <li class="nav-item"><a class="nav-link" href="log-out">登出</a></li>
        <% }else{ %>
        	<li class="nav-item"><a class="nav-link" href="signup.jsp">註冊</a></li>
	        <li class="nav-item"><a class="nav-link" href="login.jsp">登入</a></li>
        <% } %>
        
      </ul>
    </div>
  </div>
</nav>