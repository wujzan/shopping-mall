//軟創三508170624吳倬安
package cn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.connection.DbCon;
import cn.dao.UserDao;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()) {
			String id = request.getParameter("id"); //使用者uid
			if(id!=null) {
				UserDao userDao = new UserDao(DbCon.getConnection());
				userDao.deleteUser(Integer.parseInt(id));
			}
			response.sendRedirect("members.jsp"); //跳轉
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
