package com.models;

public class Product {
	public int prodId;
	public int price;
	public String catName;
	public String prodName;

	public Product(int prodId, int price, String catName, String prodName) {
		this.prodId = prodId;
		this.price = price;
		this.catName = catName;
		this.prodName = prodName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prodId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (prodId != other.prodId)
			return false;
		return true;
	}
}