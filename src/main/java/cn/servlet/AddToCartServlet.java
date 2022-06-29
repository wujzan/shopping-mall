//軟創三508170624吳倬安
package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			ArrayList<Cart> cartList = new ArrayList<>(); //這邊的cartList是用來新增.寫入商品的
			
			int id = Integer.parseInt(request.getParameter("id")); //讀取請求參數 ?id=XXX
			Cart cm = new Cart();
			cm.setId(id); //商品ID
			cm.setQuantity(1); //商品數量(首次商品放入購物車,所以數量為1)
			
			
			HttpSession session = request.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list"); //讀取購物車
			
			if(cart_list == null) { //尚無購物車
				cartList.add(cm);
				session.setAttribute("cart-list", cartList);
				response.sendRedirect("index.jsp");
			}else {
				cartList = cart_list;
				boolean exist = false;
				
				for(Cart c:cart_list) {
					if(c.getId()==id) {
						exist=true;
						out.println("<h2 style='color:crimson; text-align:center'>商品已經存在於購物車中!<br><a href='cart.jsp'>按我進入購物車</a></h2>");
					}					
				}
				if(!exist) {
					cartList.add(cm);
					response.sendRedirect("index.jsp");
				}
			}			

		}
	}

}
