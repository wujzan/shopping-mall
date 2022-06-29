//�n�ФT508170624�d�Ӧw
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
	
	//�ϥΪ̵n�J
	public User userLogin(String email,String password) {
		User user = null;
		try {
			query = "select * from users where email=? and password=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, email); //�n�J�b����email
			pst.setString(2, password); //�n�J�K�X
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
			System.out.print(e.getMessage()); //�L�X���~
		}
		
		return user;
	}
	
	//�d�߱b��(email)���L�s�b(�O�_�w�Q���U)
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
			System.out.print(e.getMessage()); //�L�X���~
		}
		
		return result;
	}
	
	//�ϥΪ̵��U�b��
	public boolean userSignUp(User model) {
		boolean result = false;
		try {
			query = "insert into users (name,email,password) values(?,?,?)";
			
			pst = this.con.prepareStatement(query);

			pst.setString(1, model.getName());
			pst.setString(2, model.getEmail());
			pst.setString(3, model.getPassword());
			pst.executeUpdate();
			result = true;	//�^��true���U���\
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
		
	////////////////////////////////////////////////
	//�d�ߥ�������Ʈw���Ҧ��|���b��
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
	
	//�R���b��
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
	
	//�ק�ϥΪ̱K�X
	public boolean userPWDchange(User model) {
		boolean result = false;
		try {
			query = "UPDATE users SET password=? WHERE u_id=?";
			
			pst = this.con.prepareStatement(query);
			pst.setString(1, model.getPassword());
			pst.setInt(2, model.getId());
			pst.executeUpdate();
			result = true;	//���\�ק�:true
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
