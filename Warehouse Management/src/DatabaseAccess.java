import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DatabaseAccess {

	public static Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/WarehouseManagement?useSSL=false", "root",
					"root");
		} catch (ClassNotFoundException e) {
			System.out.println("Connection to Database failed");
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		}
		return c;
	}

	public void getSuppliers() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from User where userType = 2 order by userId");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No suppliers found");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 2; i++) {
					p[i] = rs.getString(i);
				}
				int userId = Integer.parseInt(p[1]);
				String userName = p[2];
				String userType = "Supplier";
				Result.printUsers(new User(userId, userName, userType));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getSuppliers(int prodId) throws NoRecordsFoundException, SQLException {

		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select u.userId, u.userName from User as u INNER JOIN Warehouse as w on w.userId = u.userId where w.prodId = ? AND u.userTypeId = 2 order by u.userId;");
			pt.setInt(1, prodId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No suppliers found");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 2; i++) {
					p[i] = rs.getString(i);
				}
				int userId = Integer.parseInt(p[1]);
				String userName = p[2];
				Result.printUsers(new User(userId, userName, "Supplier"));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getProducts() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select p.prodId, p.prodName, c.catName , p.price from Product as p INNER JOIN Category as c on p.catId = c.catId ORDER BY p.prodId");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No products found");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 4; i++) {
					p[i] = rs.getString(i);
				}
				int prodId = Integer.parseInt(p[1]);
				String prodName = p[2];
				String catName = p[3];
				int price = Integer.parseInt(p[4]);
				Result.printProducts(new Product(prodId, price, catName, prodName));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public void getStock(int userId) throws NoRecordsFoundException
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select p.prodId, p.prodName, w.qty from Product as p INNER JOIN Warehouse as w on p.prodId = w.prodId where w.userId = ?  ORDER BY p.prodId;");
			pt.setInt(1, userId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No products available");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					p[i] = rs.getString(i);
				}
				int prodId = Integer.parseInt(p[1]);
				String prodName = p[2];
				int qty = Integer.parseInt(p[3]);
				Result.printStocks(prodId, prodName, qty);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getProducts(int catId) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select p.prodId, p.prodName, c.catName , p.price from Product as p INNER JOIN Category as c on p.catId = c.catId where p.catId = ?  ORDER BY p.prodId;");
			pt.setInt(1, catId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No products available");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 4; i++) {
					p[i] = rs.getString(i);
				}
				int prodId = Integer.parseInt(p[1]);
				String prodName = p[2];
				String catName = p[3];
				int price = Integer.parseInt(p[4]);
				Result.printProducts(new Product(prodId, price, catName, prodName));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getCategories() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Category");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No categories found");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 2; i++) {
					p[i] = rs.getString(i);
				}
				int catId = Integer.parseInt(p[1]);
				String catName = p[2];
				Result.printCategories(new Category(catId, catName));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	

	public User getUser(int id, int type) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		User user = null;

		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select u.userId, u.userName, ut.userType from User AS u INNER JOIN UserType as ut ON u.userTypeId = ut.userTypeId where u.userId = ? AND u.userTypeId = ?");
			pt.setInt(1, id);
			pt.setInt(2, type);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No user found");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					p[i] = rs.getString(i);
				}
				int userId = Integer.parseInt(p[1]);
				String userName = p[2];
				String userType = p[3];
				user = new User(userId, userName, userType);
				return user;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}

	public int createUser(String name, int type) {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into User(userName, userTypeId) values (?,?)");
			pt.setString(1, name);
			pt.setInt(2, type);
			pt.executeUpdate();
			pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return id;
	}

	public void buyProduct(int custId, int prodId, int supId) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Warehouse where prodId = ? AND userId = ?");
			pt.setInt(1, prodId);
			pt.setInt(2, supId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No products available");
			}
			if (rs.getInt(3) == 1) {
				pt.close();
				pt = con.prepareStatement("Delete from Warehouse where prodId = ? AND userId = ?;");
				pt.executeUpdate();
			} else {
				pt.close();
				pt = con.prepareStatement("Update Warehouse Set qty = qty - 1 where prodId = ? AND userId = ?;");
				pt.setInt(1, prodId);
				pt.setInt(2, supId);
				pt.executeUpdate();
			}
			pt = con.prepareStatement(
					"Insert into History(userId, prodId, actionPerformed, qty) values(?,?,'Bought',1)");
			pt.setInt(1, custId);
			pt.setInt(2, prodId);
			pt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public int createProduct(String name, int catId, int price) {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		int prodId = 0;

		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into Product(prodName, catId, price) values (?,?,?);");
			pt.setString(1, name);
			pt.setInt(2, catId);
			pt.setInt(3, price);
			pt.executeUpdate();
			pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			prodId = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return prodId;
	}

	public void addToWarehouse(int prodId, int supId, int qty) throws SQLException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Warehouse where prodID = ? AND userId = ?");
			pt.setInt(1, prodId);
			pt.setInt(2, supId);
			rs = pt.executeQuery();
			if (rs.next() == true) {
				pt.close();
				pt = con.prepareStatement("Update Warehouse Set qty = qty + ? where prodId = ? AND userId = ?;");
				pt.setInt(1, qty);
				pt.setInt(2, prodId);
				pt.setInt(3, supId);
				pt.executeUpdate();
			} else {
				pt.close();
				pt = con.prepareStatement("Insert into Warehouse(prodId, userId, qty) values (?,?,?)");
				pt.setInt(1, prodId);
				pt.setInt(2, supId);
				pt.setInt(3, qty);
				pt.executeUpdate();
			}
			pt = con.prepareStatement("Insert into History(userId, prodId, actionPerformed, qty) values(?,?,'Stocked',?)");
			pt.setInt(1, supId);
			pt.setInt(2, prodId);
			pt.setInt(3, qty);
			pt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Foreign key constraint fails / Invalid ID");
			throw e;
		} 
		finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void removeFromWarehouse(int prodId, int userId, int qty) throws NoRecordsFoundException, SQLException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Warehouse where prodId = ? AND userId = ?");
			pt.setInt(1, prodId);
			pt.setInt(2, userId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				pt.close();
				throw new NoRecordsFoundException("No records found");
			}
			if (qty > rs.getInt(3)) {
				pt.close();
				throw new SQLException("Quantity to be removed is higher than available quantity");
			}
			if (rs.getInt(3) == 1) {
				pt.close();
				pt = con.prepareStatement("Delete from Warehouse where prodId = ? AND userId = ?;");
				pt.executeUpdate();
			} else {
				pt.close();
				pt = con.prepareStatement("Update Warehouse Set qty = qty - ? where prodId = ? AND userId = ?;");
				pt.setInt(1, qty);
				pt.setInt(2, prodId);
				pt.setInt(3, userId);
				pt.executeUpdate();
			}
			pt = con.prepareStatement(
					"Insert into History(userId, prodId, actionPerformed, qty) values(?,?,'Removed',?)");
			pt.setInt(1, userId);
			pt.setInt(2, prodId);
			pt.setInt(3, qty);
			pt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getWarehouseContent() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select p.prodName, u.userName, w.qty from Product as p INNER JOIN Warehouse as w ON p.prodId = w.prodId INNER JOIN User AS u ON w.userId = u.userId;");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("Warehouse is empty");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					p[i] = rs.getString(i);
				}
				String prodName = p[1];
				String supName = p[2];
				int qty = Integer.parseInt(p[3]);
				Result.printWarehouse(new Warehouse(prodName, supName, qty));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getRecords() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select r.transId , u.userName, p.prodName, r.actionPerformed, r.qty from History as r INNER JOIN User as u on r.userId = u.userId INNER JOIN Product as p ON r.prodId = p.prodId ORDER BY r.transId");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No records found");
			}
			rs.previous();
			String p[] = new String[6];
			while (rs.next()) {
				for (int i = 1; i <= 5; i++) {
					p[i] = rs.getString(i);
				}
				int transId = Integer.parseInt(p[1]);
				String userName = p[2];
				String prodName = p[3];
				String actionPerformed = p[4];
				int qty = Integer.parseInt(p[5]);
				Result.printRecords(new Record(transId, userName, prodName, qty, actionPerformed));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void getUser() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select u.userId , u.userName, ut.userType from User as u INNER JOIN UserType as ut on u.userTypeId = ut.userTypeId  ORDER BY u.userId");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			rs.previous();
			String p[] = new String[6];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					p[i] = rs.getString(i);
				}
				int userId = Integer.parseInt(p[1]);
				String userName = p[2];
				String userType = p[3];

				Result.printUsers(new User(userId, userName, userType));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}
}