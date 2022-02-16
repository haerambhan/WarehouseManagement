package com.models;

public class Warehouse {
	public String prodName;
	public String supName;
	public int qty;

	public Warehouse(String prodName, String supName, int qty) {
		this.prodName = prodName;
		this.supName = supName;
		this.qty = qty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prodName == null) ? 0 : prodName.hashCode());
		result = prime * result + ((supName == null) ? 0 : supName.hashCode());
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
		Warehouse other = (Warehouse) obj;
		if (prodName == null) {
			if (other.prodName != null)
				return false;
		} else if (!prodName.equals(other.prodName))
			return false;
		if (supName == null) {
			if (other.supName != null)
				return false;
		} else if (!supName.equals(other.supName))
			return false;
		return true;
	}
} 