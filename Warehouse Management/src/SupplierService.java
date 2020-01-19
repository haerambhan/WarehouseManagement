import java.util.Scanner;

class SupplierService {
	Scanner sc;
	DatabaseAccess d;

	SupplierService() {
		d = new DatabaseAccess();
		sc = new Scanner(System.in);
	}

	public void addToWarehouse(User u) {
		try {
			d.getProducts();
			System.out.println("---------------------");
			System.out.println("Enter product ID");
			int prodId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Quantity");
			int qty = sc.nextInt();
			sc.nextLine();
			d.addToWarehouse(prodId, u.userId, qty);
			System.out.println("---------------------");
			System.out.println("New Product added successfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void removeFromWarehouse(User u) {
		try {
			d.getStock(u.userId);
			System.out.println("---------------------");
			System.out.println("Enter product ID");
			int prodId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter quantity to be removed");
			int qty = sc.nextInt();
			sc.nextLine();
			d.removeFromWarehouse(prodId, u.userId, qty);
			System.out.println("---------------------");
			System.out.println("Product Removed from warehouse succesfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}