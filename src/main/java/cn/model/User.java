//軟創三508170624吳倬安
package cn.model;

public class User {
	private int id; //使用者uid
	private String name; //姓名
	private String email;
	private String password;
	private int admin; //是否具有管理員權限 0:一般帳號 1:管理員
	
	public User() {
	}


	public User(int id, String name, String email, String password,int admin) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	//看使用者是否具有管理員權限
	public int getAdmin() {
		return admin;
	}
	
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
}