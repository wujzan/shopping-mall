//�n�ФT508170624�d�Ӧw
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
import cn.dao.ProductDao;
import cn.model.Product;

/**
 * Servlet implementation class ChangeProductPriceServlet
 */
@WebServlet("/change-product-price")
public class ChangeProductPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			
			String pid = request.getParameter("p_id"); //�ӫ~��id
			String price = request.getParameter("product-price"); //�ӫ~�ק�᪺����
			
			try {
				ProductDao pdao = new ProductDao(DbCon.getConnection());
				
				Product product = new Product();
				product.setId(Integer.parseInt(pid));
				product.setPrice(Integer.parseInt(price));
									
				boolean result = pdao.changePrice(product);
				if(!result) out.print("�s����ק異��!");
				
				response.sendRedirect("allProducts.jsp");
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}			

		}
	}

}