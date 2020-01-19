import java.util.*;

class User {
	int userId;
	String userName;
	String userType;

	public User(int userId, String userName, String userType) {
		this.userId = userId;
		this.userName = userName;
		this.userType = userType;
	}
}

class Product {
	int prodId;
	int price;
	String catName;
	String prodName;

	public Product(int prodId, int price, String catName, String prodName) {
		this.prodId = prodId;
		this.price = price;
		this.catName = catName;
		this.prodName = prodName;
	}
}

class Category {
	int catId;
	String catName;

	public Category(int catId, String catName) {
		this.catId = catId;
		this.catName = catName;
	}
}

class Warehouse {
	String prodName;
	String supName;
	int qty;

	public Warehouse(String prodName, String supName, int qty) {
		this.prodName = prodName;
		this.supName = supName;
		this.qty = qty;
	}
} 

class Record {
	int transId;
	String userName;
	String actionPerformed;
	int qty;
	String prodName;

	public Record(int transId, String userName, String prodName, int qty, String actionPerformed) {
		this.transId = transId;
		this.userName = userName;
		this.prodName = prodName;
		this.actionPerformed = actionPerformed;
		this.qty = qty;
	}
}

class NoRecordsFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public NoRecordsFoundException(String message) {
		super(message);
	}
}

public class MyClass2 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		int ch = 0;
		while (true) {
			System.out.println("---------------------");
			System.out.println("1) Admin");
			System.out.println("2) Customer");
			System.out.println("3) Supplier");
			System.out.println("4) Exit Application");
			try {
				ch = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Integer value required");
				sc.next();
			}
			switch (ch) {
			case 1: {
				adminDriver();
				break;
			}
			case 2: {
				customerDriver();
				break;
			}
			case 3: {
				supplierDriver();
				break;
			}
			case 4: {
				return;
			}
			default: {
				System.out.println("---------------------");
				System.out.println("Invalid choice");
				break;
			}
			}
		}
	}

	public static void supplierDriver() {
		SupplierService c = new SupplierService();
		int id;
		try {
			System.out.println("Enter your ID");
			id = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e1) {
			System.out.println("Integer value required");
			sc.next();
			return;
		}
		User u = validate(2, id);
		if (u == null)
			return;

		while (true) {
			System.out.println("---------------------");
			System.out.println("Welcome " + u.userName);
			System.out.println("---------------------");
			System.out.println("1) Add new product to warehouse");
			System.out.println("2) Remove product from warehouse");
			System.out.println("3) Logout");
			int ch = 0;
			try {
				ch = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Integer value required");
				sc.next();
				return;
			}
			switch (ch) {
			case 1: {
				c.addToWarehouse(u);
				break;
			}
			case 2: {
				c.removeFromWarehouse(u);
				break;
			}
			case 3: {
				return;
			}
			default: {
				System.out.println("---------------------");
				System.out.println("Invalid choice");
			}
			}
		}
	}

	public static void adminDriver() {
		AdminService m = new AdminService();
		int id;
		try {
			System.out.println("Enter your ID");
			id = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e1) {
			System.out.println("Integer value required");
			sc.next();
			return;
		}
		User u = validate(1, id);
		if (u == null)
			return;

		while (true) {
			System.out.println("---------------------");
			System.out.println("Welcome " + u.userName);
			System.out.println("---------------------");
			System.out.println("1) Add supplier");
			System.out.println("2) Add customer");
			System.out.println("3) Add product");
			System.out.println("4) View database");
			System.out.println("5) Logout");
			int ch = 0;
			try {
				ch = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Integer value required");
				sc.next();
			}
			switch (ch) {
			case 1: {
				m.addUser(2);
				break;
			}
			case 2: {
				m.addUser(3);
				break;
			}
			case 3: {
				m.addProduct();
				break;
			}
			case 4:
			{
				boolean valid = true;
				while(valid)
				{
					System.out.println("---------------------");
					System.out.println("1) Show users");
					System.out.println("2) Show products");
					System.out.println("3) Show categories");
					System.out.println("4) Show Transaction history");
					System.out.println("5) Show warehouse content");
					System.out.println("6) Go back to previous menu");
					int ch1 = 0;
					try {
						ch1 = sc.nextInt();
						sc.nextLine();
					} catch (InputMismatchException e) {
						System.out.println("Integer value required");
						sc.next();
					}
					switch (ch1) {
					case 1: {
						m.showUsers();
						break;
					}
					case 2: {
						m.showProducts();
						break;
					}
					case 3: {
						m.showCategories();
						break;
					}
					case 4: {
						m.showRecords();
						break;
					}
					case 5: {
						m.showWarehouse();
						break;
					}
					case 6: {
						valid = false;
						break;
					}
					default:
					{
						System.out.println("---------------------");
						System.out.println("Invalid choice");
					}
					}
				}
				break;
			}
			case 5: {
				return;
			}
			default: {
				System.out.println("---------------------");
				System.out.println("Invalid choice");
			}
			}
		}
	}

	public static void customerDriver() {
		CustomerService c = new CustomerService();
		int id;
		try {
			System.out.println("Enter your ID");
			id = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e1) {
			System.out.println("---------------------");
			System.out.println("Integer value required");
			sc.next();
			return;
		}
		User u = validate(3, id);
		if (u == null)
			return;
		while (true) {
			System.out.println("---------------------");
			System.out.println("Welcome " + u.userName);
			System.out.println("---------------------");
			System.out.println("1) Browse available products");
			System.out.println("2) Logout");
			int ch = 0;
			try {
				ch = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("---------------------");
				System.out.println("Integer value required");
				sc.next();
			}
			switch (ch) {
			case 1: {
				c.browseProducts(u);
				break;
			}
			case 2: {
				return;
			}
			default: {
				System.out.println("---------------------");
				System.out.println("Invalid choice");
			}
			}
		}
	}

	public static User validate(int type, int id) {
		User user = null;
		try {
			user = new DatabaseAccess().getUser(id, type);
		} catch (NoRecordsFoundException e) {
			System.out.println("---------------------");
			System.out.println("User does not exist");		
		}
		return user;
	}

}
