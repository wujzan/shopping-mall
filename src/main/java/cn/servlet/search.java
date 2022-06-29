//軟創三508170624吳倬安
package cn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.connection.DbCon;
import cn.dao.OrderDao;
import cn.dao.ProductDao;
import cn.model.Product;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()) {
			String keyword = request.getParameter("keyword").trim(); //keyword
			if(keyword!=null) {
				ProductDao productDao = new ProductDao(DbCon.getConnection());
				List<Product> products = new ArrayList<Product>();
				if(!keyword.equals(""))				
					products = productDao.searchProduct(keyword);
				
				request.setAttribute("products", products);
				request.setAttribute("keyword", keyword);
				
				request.getRequestDispatcher("searchResult.jsp").forward(request,response); //轉發,伺服器內部跳轉
			}
			else response.sendRedirect("index.jsp"); //跳轉回index
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
