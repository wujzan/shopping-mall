//�n�ФT508170624�d�Ӧw
package cn.model;

public class User {
	private int id; //�ϥΪ�uid
	private String name; //�m�W
	private String email;
	private String password;
	private int admin; //�O�_�㦳�޲z���v�� 0:�@��b�� 1:�޲z��
	
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
	
	//�ݨϥΪ̬O�_�㦳�޲z���v��
	public int getAdmin() {
		return admin;
	}
	
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
}