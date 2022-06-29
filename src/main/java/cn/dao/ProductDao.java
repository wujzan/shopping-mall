//�n�ФT508170624�d�Ӧw
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
	
	//���o�����ӫ~�C��
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
		return products;  //�^�Ǹ�Ʈw�d�ߵ��GList
	}
	
	//����r�j�M,���o�ӫ~�C��
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
		return products;  //�^�Ǹ�Ʈw�d�ߵ��GList
	}
	
	//�Q�� �ӫ~id �d�߳涵�ӫ~���ԲӤ��e
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
	
	//�d���ʪ������Ҧ��ӫ~���ԲӤ��e
	public List<Cart> getCartProducts(ArrayList<Cart> cartList){
		List<Cart> products = new ArrayList<Cart>();
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query = "select * from products where p_id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId()); //�]�w�d�߱���(�ӫ~ID)
					rs = pst.executeQuery(); //�i��SQL�d��
					while(rs.next()) { //�d�߸�Ʈw���G�O�_�٦��U�@���O��"rs.next()"
						Cart row = new Cart();
						row.setId(rs.getInt("p_id"));
						row.setName(rs.getString("p_name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getInt("p_price")*item.getQuantity()); //���B*�ӫ~�ƶq
						row.setQuantity(item.getQuantity());
						products.add(row); //�s�JList
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return products; //�^��
	}
		
	//�ʪ�����ܵ��⪺�`���B
	public int getTotalCartPrice(ArrayList<Cart> cartList){
		int sum=0;
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query = "select * from products where p_id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId()); //�]�w�d�߱���(�ӫ~ID)
					rs = pst.executeQuery(); //�i��SQL�d��
					while(rs.next()) { //�d�߸�Ʈw���G�O�_�٦��U�@���O��"rs.next()"
						sum += rs.getInt("p_price")*item.getQuantity();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return sum; //���⪺�`���B
	}
		
	//�ˬd�ӫ~�W�٦��L����
	public boolean productNameCheck(String name) {
		boolean result = false;
		try {
			query = "select * from products where p_name=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();
			
			if(rs.next()) { //�ӫ~�W�٤w�g�s�b
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage()); //�L�X���~
		}
		return result; //true:����, false:�L����
	}
	
	//�s�W�ӫ~
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
			result = true;  //�s�W���\
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return result;  //�^�Ǿާ@��Ʈw�O�_���\�s�W�ӫ~
	}
	
	//�R���ӫ~
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
	
	//�ק�ӫ~�c����B
	public boolean changePrice(Product model) {
		boolean result = false;
		try {
			query = "UPDATE products SET p_price=? WHERE p_id=?";
			
			pst = this.con.prepareStatement(query);
			pst.setInt(1, model.getPrice());
			pst.setInt(2, model.getId());
			pst.executeUpdate();
			result = true;	//�ק令�\
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;  //�^�ǬO�_���\�ק�ӫ~�c����B
	}
}
