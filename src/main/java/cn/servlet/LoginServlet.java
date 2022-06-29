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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){			
			
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			
			try {
				UserDao udao = new UserDao(DbCon.getConnection());
				User user = udao.userLogin(email, password);
				
				if(user != null) {
					//使用者登入
					request.getSession().setAttribute("auth",user);
					response.sendRedirect("index.jsp"); //登入後跳轉回首頁
				}else {
					out.print("登入失敗"); //登入失敗,2秒後跳轉回首頁
					response.setHeader("refresh","2;URL=login.jsp");
				}
			} catch (ClassNotFoundException | SQLException e) {				
				e.printStackTrace();
			}			

		}
	}

}
