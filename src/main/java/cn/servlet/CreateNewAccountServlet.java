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
 * Servlet implementation class CreateNewAccount
 */
@WebServlet("/create-new-account")
public class CreateNewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			
			String name = request.getParameter("login-name");
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			
			try {
				UserDao udao = new UserDao(DbCon.getConnection());
				boolean accountExists = udao.userIdCheck(email);
				
				if(accountExists) { //Email已經被註冊
					out.println("<script type=\"text/javascript\">");
				    out.println("alert('帳號已存在');");
				    out.println("location='signup.jsp';");
				    out.println("</script>");
				}else {					
					User newUser = new User();

					newUser.setName(name);
					newUser.setEmail(email);
					newUser.setPassword(password);
										
					boolean result = udao.userSignUp(newUser);
					if(result) {
						out.print("帳號已成功建立!<br>三秒後自動跳轉到登入網頁..");
						response.setHeader("refresh","3;URL=login.jsp");
					}
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}			

		}
	}

}
