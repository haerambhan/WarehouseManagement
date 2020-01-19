class Result {
	public static void printProducts(Product p) {
		System.out.println("---------------------");
		System.out.print("Product ID: " + p.prodId);
		System.out.print("\tProduct Name: " + p.prodName);
		System.out.print("\tProduct Category: " + p.catName);
		System.out.print("\tProduct Price:" + p.price);
		System.out.println();
	}

	public static void printUsers(User s) {
		System.out.println("---------------------");
		System.out.print("User ID: " + s.userId);
		System.out.print("\tUser Name: " + s.userName);
		System.out.println("\tUser type: " + s.userType);
		System.out.println();

	}

	public static void printCategories(Category c) {
		System.out.println("---------------------");
		System.out.print("Category ID: " + c.catId);
		System.out.print("\tCategory Name: " + c.catName);
		System.out.println();
	}

	public static void printWarehouse(Warehouse w) {
		System.out.println("---------------------");
		System.out.print("Product Name: " + w.prodName);
		System.out.print("\tSupplier Name: " + w.supName);
		System.out.print("\tQuantity: " + w.qty);
		System.out.println();
	}

	public static void printRecords(Record r) {
		System.out.println("---------------------");
		System.out.println("Transaction ID. " + r.transId + ": " + r.userName + " " + r.actionPerformed + " " + r.qty
				+ " " + r.prodName);
	}

	public static void printStocks(int prodId, String prodName, int qty) {
		System.out.println(prodId+") "+prodName+" qty: "+qty);
	}
}
