//�n�ФT508170624�d�Ӧw
package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatisticDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public StatisticDao(Connection con) {
		this.con = con;
	}
	
	//���U�|���ƶq�έp
	public int getUserStatistic() {
		int userCount = 0;
		
		try {
			query = "select count(*) from users";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();		
						
			while(rs.next()) {
		    	userCount = rs.getInt(1);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return userCount;
	}
	
	//�W�[�ӫ~�ƶq�έp
	public int getProductStatistic() {
		int productsCount = 0;
		
		try {
			query = "select count(*) from products";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();		
						
			while(rs.next()) {
		    	productsCount = rs.getInt(1);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return productsCount;
	}
	
	//�֭p�q���`�q.����B
	public int[] getOrderStatistic() {
		int[] orderStatistic=new int[2]; //�Q�ΰ}�C���x�s�d�ߵ��G
		int orderCount = 0;
	    int orderTotal = 0;
		
		try {
			query = "select count(*),SUM(o_price) from orders";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();		
						
			while(rs.next()) {
		    	orderCount = rs.getInt(1);
				orderTotal = rs.getInt(2);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		orderStatistic[0] = orderCount;
		orderStatistic[1] = orderTotal;
		return orderStatistic;
	}

}
