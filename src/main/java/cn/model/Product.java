//軟創三508170624吳倬安
package cn.model;

public class Product {
	private int id; //商品pid
	private String name; //商品名稱
	private String category; //商品所屬目錄
	private int price; //商品金額
	private String image; //商品圖片檔名
	
	
	public Product() {
	}


	public Product(int id, String name, String category, int price, String image) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.image = image;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
}