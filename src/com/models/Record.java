package com.models;

public class Record {
	public int transId;
	public String userName;
	public String actionPerformed;
	public int qty;
	public String prodName;

	public Record(int transId, String userName, String prodName, int qty, String actionPerformed) {
		this.transId = transId;
		this.userName = userName;
		this.prodName = prodName;
		this.actionPerformed = actionPerformed;
		this.qty = qty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transId;
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
		Record other = (Record) obj;
		if (transId != other.transId)
			return false;
		return true;
	}
}