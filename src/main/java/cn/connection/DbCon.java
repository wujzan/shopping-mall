//�n�ФT508170624�d�Ӧw
package cn.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCon { //��Ʈw�s�u�]�w
	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(connection==null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping_db","root","root123");
			System.out.print("connected");
		}
		return connection;
	}

}
