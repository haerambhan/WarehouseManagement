import java.util.Scanner;

class AdminService {
	Scanner sc;
	DatabaseAccess d;

	AdminService() {
		sc = new Scanner(System.in);
		d = new DatabaseAccess();
	}

	public void addUser(int type) {

		if (type == 3)
			System.out.println("Enter Customer Name");
		else
			System.out.println("Enter Supplier name");
		String name = sc.nextLine();
		int id = d.createUser(name, type);
		if(id!=0)
		{
			System.out.println("---------------------");
			System.out.println("New User added successfully...ID is " + id);			
		}
	}

	public void addProduct() {
		try {
			System.out.println("Enter Product name");
			String name = sc.nextLine();
			d.getCategories();
			System.out.println("Enter the category ID");
			int catId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter the price");
			int price = sc.nextInt();
			sc.nextLine();
			int id = d.createProduct(name, catId, price);
			if(id!=0)
			{
				System.out.println("---------------------");
				System.out.println("New Product added successfully...ID is " + id);				
			}
		} catch (NoRecordsFoundException e) {
			System.out.println("---------------------");
			System.out.println("Foreign key constraint fails...the selected category does not exist");
		}
	}

	public void showRecords() {
		try {
			d.getRecords();
		} catch (NoRecordsFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void showWarehouse() {
		try {
			d.getWarehouseContent();
		} catch (NoRecordsFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	public void showProducts()
	{
		try {
			d.getProducts();
		} catch (NoRecordsFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	public void showCategories()
	{
		try {
			d.getCategories();
		} catch (NoRecordsFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	public void showUsers()
	{
		try {
			d.getUser();
		} catch (NoRecordsFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}