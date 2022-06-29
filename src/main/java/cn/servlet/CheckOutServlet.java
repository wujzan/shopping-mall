//軟創三508170624吳倬安
package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cn.connection.DbCon;
import cn.dao.OrderDao;
import cn.dao.ProductDao;
import cn.model.*;

/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date =new Date();
			
			//讀取購物車
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			//取得登入狀態
			User auth = (User) request.getSession().getAttribute("auth");			
			
			
			//檢查購物車是否不為空 和 登入狀態
			if(cart_list != null && auth != null) {
				for(Cart c:cart_list) {
					Order order = new Order();
					order.setId(c.getId());	//購買商品的ID
					
					//產生訂單號碼8碼
					Random generator = new Random();
					String num1 = Integer.toString(generator.nextInt(90)+10); //產生10~99亂數(頭)
					String num2 = Integer.toString(generator.nextInt(90)+10); //產生10~99亂數(尾)
					String OrderIdStr=num1+1234+num2; //訂單ID=(頭)+1234+(尾)
					int OrderId=Integer.parseInt(OrderIdStr);
					order.setOrderId(OrderId); //訂單ID
					
					order.setUid(auth.getId()); //使用者u_id
					order.setQuantity(c.getQuantity()); //商品的購買數量
					
					//查詢商品的金額,並計算 金額*數量
					ProductDao pDao = new ProductDao(DbCon.getConnection());
					Product product = pDao.getSingleProduct(c.getId());
					order.setPrice(product.getPrice()*c.getQuantity());
					
					order.setDate(formatter.format(date)); //訂單建立時間
					
					OrderDao oDao = new OrderDao(DbCon.getConnection());
					boolean result = oDao.insertOrder(order);
					if(!result) break;
				}
				
				cart_list.clear(); //購買後,清除購物車現有物品
				response.sendRedirect("myOrders.jsp");
			}else {
				if(auth == null) {
					response.sendRedirect("login.jsp"); //若未登入,跳轉首頁
				}else {
					response.sendRedirect("cart.jsp");
				}				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
