//�n�ФT508170624�d�Ӧw
package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.model.Cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			String id = request.getParameter("id");
			//out.println("Product Id: "+id);
			
			if(id!=null) {
				ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
				if(cart_list != null) { //�ʪ����٦��ӫ~��
					for(Cart c:cart_list) {
						if(c.getId()==Integer.parseInt(id)) {
							cart_list.remove(cart_list.indexOf(c)); //�����ʪ����̪��ӫ~����
							break;
						}
					}
					response.sendRedirect("cart.jsp");
				}
			}else {
				response.sendRedirect("cart.jsp");
			}
		}
	}

}
