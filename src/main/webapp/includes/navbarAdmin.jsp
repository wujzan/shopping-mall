<!-- 軟創三508170624吳倬安 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
  <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="index.jsp">小而巧購物商城</a>
  <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  
  <span class="w-100"></span>
  
  <div class="navbar-nav">
    <div class="nav-item text-nowrap">
      <a class="nav-link px-3" href="log-out">登出</a>
    </div>
  </div>
</header>


<div class="container-fluid">
  <div class="row">
  
	<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
      <div class="position-sticky pt-3">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link" href="dashboard.jsp">
              <span data-feather="home"></span>
              控制台
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="members.jsp">
              <span data-feather="users"></span>
              會員管理
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="orders.jsp">
              <span data-feather="file"></span>
              訂單管理
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="allProducts.jsp">
              <span data-feather="shopping-cart"></span>
              商品管理
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="addProduct.jsp">
              <span data-feather="shopping-cart"></span>
              新增商品
            </a>
          </li>          
        </ul>
      </div>
    </nav>
    
  </div>
</div>