//軟創三508170624吳倬安
package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import cn.model.*;

public class OrderDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public OrderDao(Connection con) {
		this.con = con;
	}
	
	//訂單寫入資料庫
	public boolean insertOrder(Order model) {
		boolean result = false;
		
		try {
			query = "insert into orders (o_id,p_id,u_id,o_quantity,o_price,o_date) values(?,?,?,?,?,?)";
			
			pst = this.con.prepareStatement(query);
			pst.setInt(1, model.getOrderId());
			pst.setInt(2, model.getId());
			pst.setInt(3, model.getUid());
			pst.setInt(4, model.getQuantity());
			pst.setInt(5, model.getPrice());
			pst.setString(6, model.getDate());
			pst.executeUpdate();
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result; //回傳操作資料庫是否成功
	}
	
	//查詢單一使用者自己所購買的訂單歷史紀錄
	public List<Order> userOrders(int id){
		List<Order> list = new ArrayList<>();
		try {
			//降序排序 order by 訂單日期 desc
			query = "select * from orders where u_id=? order by orders.o_date desc";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Order order = new Order();
				ProductDao productDao = new ProductDao(this.con);
				int pId = rs.getInt("p_id");
				
				Product product = productDao.getSingleProduct(pId);
				
				if(product!=null) {
					order.setOrderId(rs.getInt("o_id"));
					order.setId(pId);
					order.setName(product.getName());
					order.setCategory(product.getCategory());
					order.setPrice(rs.getInt("o_price"));
					order.setQuantity(rs.getInt("o_quantity"));
					order.setDate(rs.getString("o_date"));
					order.setStatus(rs.getInt("o_status"));
					list.add(order);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;  //回傳資料庫查詢結果
	}
	
	//取消訂單
	public void cancelOrder(int id) {
		try {
			query = "delete from orders where o_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			pst.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//查詢本網站資料庫中所有使用者的訂單
	public List<Order> allOrders(){
		List<Order> list = new ArrayList<>();
		try {
			//降序排序 order by 訂單日期 desc
			query = "select * from orders order by orders.o_date desc";
			pst = this.con.prepareStatement(query);			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Order order = new Order();
				
				ProductDao productDao = new ProductDao(this.con);
				int pId = rs.getInt("p_id");				
				Product product = productDao.getSingleProduct(pId);
				
				if(product!=null) {
					order.setOrderId(rs.getInt("o_id"));
					order.setId(pId);
					order.setName(product.getName());
					order.setCategory(product.getCategory());
					order.setPrice(rs.getInt("o_price"));
					order.setQuantity(rs.getInt("o_quantity"));
					order.setDate(rs.getString("o_date"));
					order.setStatus(rs.getInt("o_status"));
					list.add(order);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;  //回傳資料庫查詢結果
	}
		
	//修改訂單狀態
	public boolean changeStatus(Order model) {
		boolean result = false;
				
		try {
			//o_status為目前訂單處理狀態
			query = "UPDATE orders SET o_status=? WHERE o_id=?";
			
			pst = this.con.prepareStatement(query);
			pst.setInt(1, model.getStatus());
			pst.setInt(2, model.getOrderId());
			pst.executeUpdate();
			result = true;	//成功修改訂單狀態(設定為true)
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;  //回傳修改訂單狀態 是否成功
	}
}
