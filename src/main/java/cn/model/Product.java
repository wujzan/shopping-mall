//�n�ФT508170624�d�Ӧw
package cn.model;

public class Product {
	private int id; //�ӫ~pid
	private String name; //�ӫ~�W��
	private String category; //�ӫ~���ݥؿ�
	private int price; //�ӫ~���B
	private String image; //�ӫ~�Ϥ��ɦW
	
	
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