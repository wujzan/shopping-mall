//軟創三508170624吳倬安
package cn.dao;

import java.util.*;

import cn.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public ProductDao(Connection con) {
		this.con = con;
	}
	
	//取得全部商品列表
	public List<Product> getAllProduct(){
		List<Product> products = new ArrayList<Product>();
		
		try {
			query = "select * from products";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Product row = new Product();
				row.setId(rs.getInt("p_id"));
				row.setName(rs.getString("p_name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getInt("p_price"));
				row.setImage(rs.getString("image"));
				
				products.add(row);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return products;  //回傳資料庫查詢結果List
	}
	
	//關鍵字搜尋,取得商品列表
	public List<Product> searchProduct(String keyword){
		List<Product> products = new ArrayList<Product>();
		
		try {			
			query = "select * from products where p_name like '%" + keyword + "%'";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Product row = new Product();
				row.setId(rs.getInt("p_id"));
				row.setName(rs.getString("p_name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getInt("p_price"));
				row.setImage(rs.getString("image"));
				
				products.add(row);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return products;  //回傳資料庫查詢結果List
	}
	
	//利用 商品id 查詢單項商品的詳細內容
	public Product getSingleProduct(int id) {
		Product row = null;
		try {
			query = "select * from products where p_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				row = new Product();
				row.setId(rs.getInt("p_id"));
				row.setName(rs.getString("p_name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getInt("p_price"));
				row.setImage(rs.getString("image"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	//查詢購物車中所有商品的詳細內容
	public List<Cart> getCartProducts(ArrayList<Cart> cartList){
		List<Cart> products = new ArrayList<Cart>();
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query = "select * from products where p_id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId()); //設定查詢條件(商品ID)
					rs = pst.executeQuery(); //進行SQL查詢
					while(rs.next()) { //查詢資料庫結果是否還有下一條記錄"rs.next()"
						Cart row = new Cart();
						row.setId(rs.getInt("p_id"));
						row.setName(rs.getString("p_name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getInt("p_price")*item.getQuantity()); //金額*商品數量
						row.setQuantity(item.getQuantity());
						products.add(row); //存入List
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return products; //回傳
	}
		
	//購物車顯示結算的總金額
	public int getTotalCartPrice(ArrayList<Cart> cartList){
		int sum=0;
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query = "select * from products where p_id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId()); //設定查詢條件(商品ID)
					rs = pst.executeQuery(); //進行SQL查詢
					while(rs.next()) { //查詢資料庫結果是否還有下一條記錄"rs.next()"
						sum += rs.getInt("p_price")*item.getQuantity();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return sum; //結算的總金額
	}
		
	//檢查商品名稱有無重複
	public boolean productNameCheck(String name) {
		boolean result = false;
		try {
			query = "select * from products where p_name=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();
			
			if(rs.next()) { //商品名稱已經存在
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage()); //印出錯誤
		}
		return result; //true:重複, false:無重複
	}
	
	//新增商品
	public boolean addNewProduct(Product model) {
		boolean result = false;
		try {
			query = "insert into products (p_name,category,p_price,image) values(?,?,?,?)";
			
			pst = this.con.prepareStatement(query);

			pst.setString(1, model.getName());
			pst.setString(2, model.getCategory());
			pst.setInt(3, model.getPrice());
			pst.setString(4, model.getImage());
			pst.executeUpdate();
			result = true;  //新增成功
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return result;  //回傳操作資料庫是否成功新增商品
	}
	
	//刪除商品
	public void deleteProduct(int id) {
		try {
			query = "delete from products where p_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			pst.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//修改商品販賣金額
	public boolean changePrice(Product model) {
		boolean result = false;
		try {
			query = "UPDATE products SET p_price=? WHERE p_id=?";
			
			pst = this.con.prepareStatement(query);
			pst.setInt(1, model.getPrice());
			pst.setInt(2, model.getId());
			pst.executeUpdate();
			result = true;	//修改成功
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;  //回傳是否成功修改商品販賣金額
	}
}
