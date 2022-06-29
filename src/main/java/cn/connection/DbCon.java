//軟創三508170624吳倬安
package cn.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCon { //資料庫連線設定
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
