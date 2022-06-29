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
import cn.dao.UserDao;
import cn.model.User;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			
			String id = request.getParameter("uid"); //使用者帳號uid
			String password = request.getParameter("login-password");
			
			try {
				UserDao udao = new UserDao(DbCon.getConnection());
				
				User user = new User();
				user.setId(Integer.parseInt(id));
				user.setPassword(password);
									
				boolean result = udao.userPWDchange(user); //傳回來的修改結果
				if(!result) out.print("user密碼修改失敗!");
				
				response.sendRedirect("members.jsp"); //跳轉
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
