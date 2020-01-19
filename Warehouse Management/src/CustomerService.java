import java.util.Scanner;
import java.sql.*;

class CustomerService {
	Scanner sc;
	DatabaseAccess d;

	CustomerService() {
		d = new DatabaseAccess();
		sc = new Scanner(System.in);
	}

	public void browseProducts(User u) {
		try {
			d.getCategories();
			System.out.println("---------------------");
			System.out.println("Enter the category ID");
			int catId = sc.nextInt();
			sc.nextLine();
			d.getProducts(catId);
			System.out.println("Enter the product ID");
			int prodId = sc.nextInt();
			sc.nextLine();
			d.getSuppliers(prodId);
			System.out.println("Enter the supplier ID");
			int supId = sc.nextInt();
			sc.nextLine();
			d.buyProduct(u.userId, prodId, supId);
			System.out.println("---------------------");
			System.out.println("Transaction Completed successfully");
		} 
		catch (NoRecordsFoundException e) {
			System.out.println("---------------------");
			System.out.println(e.getMessage());
		}
		catch (SQLException e)
		{
			System.out.println("----------------");
			System.out.println(e.getMessage());
		}
	}
}