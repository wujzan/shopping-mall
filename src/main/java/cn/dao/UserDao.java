//軟創三508170624吳倬安
package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.model.User;

public class UserDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public UserDao(Connection con) {
		this.con = con;
	}
	
	//使用者登入
	public User userLogin(String email,String password) {
		User user = null;
		try {
			query = "select * from users where email=? and password=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, email); //登入帳號為email
			pst.setString(2, password); //登入密碼
			rs = pst.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("u_id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setAdmin(rs.getInt("admin"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage()); //印出錯誤
		}
		
		return user;
	}
	
	//查詢帳號(email)有無存在(是否已被註冊)
	public boolean userIdCheck(String email) {
		boolean result = false;
		try {
			query = "select * from users where email=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, email);
			rs = pst.executeQuery();
			
			if(rs.next()) {							
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage()); //印出錯誤
		}
		
		return result;
	}
	
	//使用者註冊帳號
	public boolean userSignUp(User model) {
		boolean result = false;
		try {
			query = "insert into users (name,email,password) values(?,?,?)";
			
			pst = this.con.prepareStatement(query);

			pst.setString(1, model.getName());
			pst.setString(2, model.getEmail());
			pst.setString(3, model.getPassword());
			pst.executeUpdate();
			result = true;	//回傳true註冊成功
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
		
	////////////////////////////////////////////////
	//查詢本網站資料庫中所有會員帳號
	public List<User> allUsers(){
		List<User> list = new ArrayList<>();
		try {
			query = "select * from users";
			pst = this.con.prepareStatement(query);			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				
				user.setId(rs.getInt("u_id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAdmin(rs.getInt("admin"));
				list.add(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//刪除帳號
	public void deleteUser(int id) {
		try {
			query = "delete from users where u_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			pst.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//修改使用者密碼
	public boolean userPWDchange(User model) {
		boolean result = false;
		try {
			query = "UPDATE users SET password=? WHERE u_id=?";
			
			pst = this.con.prepareStatement(query);
			pst.setString(1, model.getPassword());
			pst.setInt(2, model.getId());
			pst.executeUpdate();
			result = true;	//成功修改:true
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
