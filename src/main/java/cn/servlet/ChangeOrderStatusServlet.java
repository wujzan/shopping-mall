//軟創三508170624吳倬安
package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import cn.connection.DbCon;
import cn.dao.OrderDao;
import cn.model.Order;

/**
 * Servlet implementation class ChangeProductPriceServlet
 */
@WebServlet("/change-order-status")
public class ChangeOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			
			String oid = request.getParameter("o_id"); //訂單的id
			String status = request.getParameter("order-status"); //訂單修改後的狀態
			
			try {
				OrderDao odao = new OrderDao(DbCon.getConnection());
								
				Order order = new Order();
				order.setOrderId(Integer.parseInt(oid));
				order.setStatus(Integer.parseInt(status));
									
				boolean result = odao.changeStatus(order);
				if(!result) out.print("訂單狀態修改失敗!");
				
				response.sendRedirect("orders.jsp");
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
	}

}