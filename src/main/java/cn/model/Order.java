//�n�ФT508170624�d�Ӧw
package cn.model;

public class Order extends Product{
	private int orderId;//�q��id
	private int uid; //�ϥΪ�uid
	private int quantity; //�ƶq
	private String date; //�q��إߤ��
	private int status; //�q��B�z���A
		

	public Order() {}

	public Order(int orderId, int uid, int quantity, String date) {
		super();
		this.orderId = orderId;
		this.uid = uid;
		this.quantity = quantity;
		this.date = date;
	}

	public Order(int uid, int quantity, String date) {
		super();
		this.uid = uid;
		this.quantity = quantity;
		this.date = date;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
