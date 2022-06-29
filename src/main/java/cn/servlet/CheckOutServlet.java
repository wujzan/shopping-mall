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
			
			//Ū���ʪ���
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			//���o�n�J���A
			User auth = (User) request.getSession().getAttribute("auth");			
			
			
			//�ˬd�ʪ����O�_������ �M �n�J���A
			if(cart_list != null && auth != null) {
				for(Cart c:cart_list) {
					Order order = new Order();
					order.setId(c.getId());	//�ʶR�ӫ~��ID
					
					//���ͭq�渹�X8�X
					Random generator = new Random();
					String num1 = Integer.toString(generator.nextInt(90)+10); //����10~99�ü�(�Y)
					String num2 = Integer.toString(generator.nextInt(90)+10); //����10~99�ü�(��)
					String OrderIdStr=num1+1234+num2; //�q��ID=(�Y)+1234+(��)
					int OrderId=Integer.parseInt(OrderIdStr);
					order.setOrderId(OrderId); //�q��ID
					
					order.setUid(auth.getId()); //�ϥΪ�u_id
					order.setQuantity(c.getQuantity()); //�ӫ~���ʶR�ƶq
					
					//�d�߰ӫ~�����B,�íp�� ���B*�ƶq
					ProductDao pDao = new ProductDao(DbCon.getConnection());
					Product product = pDao.getSingleProduct(c.getId());
					order.setPrice(product.getPrice()*c.getQuantity());
					
					order.setDate(formatter.format(date)); //�q��إ߮ɶ�
					
					OrderDao oDao = new OrderDao(DbCon.getConnection());
					boolean result = oDao.insertOrder(order);
					if(!result) break;
				}
				
				cart_list.clear(); //�ʶR��,�M���ʪ����{�����~
				response.sendRedirect("myOrders.jsp");
			}else {
				if(auth == null) {
					response.sendRedirect("login.jsp"); //�Y���n�J,���୺��
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
