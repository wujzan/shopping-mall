//軟創三508170624吳倬安
package cn.model;

public class Cart extends Product{ //繼承Product.java
	private int quantity; //數量
	
	public Cart() {}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}