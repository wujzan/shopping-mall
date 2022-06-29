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
import cn.model.Cart;
import cn.model.Order;
import cn.model.Product;
import cn.model.User;

/**
 * Servlet implementation class OrderNowServlet
 */
@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			//日期
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date =new Date();
			
			User auth = (User) request.getSession().getAttribute("auth");
			if(auth != null) { //已登入
				String productId = request.getParameter("id");
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				if(productQuantity <= 0) {
					productQuantity = 1;
				}
				
				Order orderModel = new Order();
				orderModel.setId(Integer.parseInt(productId));
				
				//產生訂單號碼8碼
				Random generator = new Random();
				String num1 = Integer.toString(generator.nextInt(90)+10); //產生10~99亂數(頭)
				String num2 = Integer.toString(generator.nextInt(90)+10); //產生10~99亂數(尾)
				String OrderIdStr=num1+1234+num2; //訂單ID=(頭)+1234+(尾)
				int OrderId=Integer.parseInt(OrderIdStr);
				orderModel.setOrderId(OrderId); //訂單ID
								
				orderModel.setUid(auth.getId()); //使用者u_id
				orderModel.setQuantity(productQuantity); //商品的購買數量
				
				//查詢商品的金額,並計算 金額*數量
				ProductDao pDao = new ProductDao(DbCon.getConnection());
				Product product = pDao.getSingleProduct(Integer.parseInt(productId));
				orderModel.setPrice(product.getPrice()*productQuantity);
				
				orderModel.setDate(formatter.format(date));  //訂單建立時間
				
				OrderDao orderDao = new OrderDao(DbCon.getConnection());
				boolean result = orderDao.insertOrder(orderModel); //訂單寫入資料庫,並回傳操作結果
				
				if(result) { //當訂單建立成功時
					ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if(cart_list != null) {
						for(Cart c:cart_list) {
							if(c.getId()==Integer.parseInt(productId)) {
								cart_list.remove(cart_list.indexOf(c)); //移除購物車裡的商品項目
								break;
							}
						}					
					}
					
					response.sendRedirect("myOrders.jsp"); //跳轉到我的歷史訂單列表
				}else {
					out.print("訂單建立失敗order failed");
				}
			}else { //若未登入,跳轉登入頁面
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
