//�n�ФT508170624�d�Ӧw
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
			
			//���
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date =new Date();
			
			User auth = (User) request.getSession().getAttribute("auth");
			if(auth != null) { //�w�n�J
				String productId = request.getParameter("id");
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				if(productQuantity <= 0) {
					productQuantity = 1;
				}
				
				Order orderModel = new Order();
				orderModel.setId(Integer.parseInt(productId));
				
				//���ͭq�渹�X8�X
				Random generator = new Random();
				String num1 = Integer.toString(generator.nextInt(90)+10); //����10~99�ü�(�Y)
				String num2 = Integer.toString(generator.nextInt(90)+10); //����10~99�ü�(��)
				String OrderIdStr=num1+1234+num2; //�q��ID=(�Y)+1234+(��)
				int OrderId=Integer.parseInt(OrderIdStr);
				orderModel.setOrderId(OrderId); //�q��ID
								
				orderModel.setUid(auth.getId()); //�ϥΪ�u_id
				orderModel.setQuantity(productQuantity); //�ӫ~���ʶR�ƶq
				
				//�d�߰ӫ~�����B,�íp�� ���B*�ƶq
				ProductDao pDao = new ProductDao(DbCon.getConnection());
				Product product = pDao.getSingleProduct(Integer.parseInt(productId));
				orderModel.setPrice(product.getPrice()*productQuantity);
				
				orderModel.setDate(formatter.format(date));  //�q��إ߮ɶ�
				
				OrderDao orderDao = new OrderDao(DbCon.getConnection());
				boolean result = orderDao.insertOrder(orderModel); //�q��g�J��Ʈw,�æ^�Ǿާ@���G
				
				if(result) { //��q��إߦ��\��
					ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if(cart_list != null) {
						for(Cart c:cart_list) {
							if(c.getId()==Integer.parseInt(productId)) {
								cart_list.remove(cart_list.indexOf(c)); //�����ʪ����̪��ӫ~����
								break;
							}
						}					
					}
					
					response.sendRedirect("myOrders.jsp"); //�����ڪ����v�q��C��
				}else {
					out.print("�q��إߥ���order failed");
				}
			}else { //�Y���n�J,����n�J����
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
