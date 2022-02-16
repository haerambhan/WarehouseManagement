package com.database;
import com.models.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccess {

	public static Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/WarehouseManagement?useSSL=false", "root",
					"root");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw e;
		}
		return c;
	}
	
	public Set<User> getSuppliers(int prodId) throws NoRecordsFoundException {

		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<User> suppliers = null;

		try {
			suppliers = new LinkedHashSet<User>();
			con = getConnection();
			pt = con.prepareStatement(
					"Select u.userId, u.userName from User as u INNER JOIN Warehouse as w on w.userId = u.userId where w.prodId = ? AND u.userTypeId = 2 order by u.userId;");
			pt.setInt(1, prodId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				System.out.println("Hello");
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
				suppliers.add(new User(userId, userName, "Supplier"));
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
		return suppliers;
	}

	public Set<Product> getProducts() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Product> products = null;
		try {
			products = new LinkedHashSet<Product>();
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
				products.add(new Product(prodId, price, catName, prodName));
//				Result.printProducts(new Product(prodId, price, catName, prodName));
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
		return products;
	}
	
	public Set<Stock> getStock(int userId) throws NoRecordsFoundException
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Stock> stocks = null;
		try {
			stocks = new LinkedHashSet<Stock>();
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
				stocks.add(new Stock(prodId, prodName, qty));
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
		return stocks;
	}

	public Set<Product> getProducts(int catId) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Product> products = null;
		try {
			products = new LinkedHashSet<Product>();
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
				products.add(new Product(prodId, price, catName, prodName));
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
		return products;
	}
	
	public Product getProductById(int prodId) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Product product = null;
		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select p.prodId, p.prodName, c.catName , p.price from Product as p INNER JOIN Category as c on p.catId = c.catId where p.prodId = ?  ORDER BY p.prodId;");
			pt.setInt(1, prodId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No products available");
			}
			String p[] = new String[5];
			for (int i = 1; i <= 4; i++) {
				p[i] = rs.getString(i);
			}
			int prdId = Integer.parseInt(p[1]);
			String prodName = p[2];
			String catName = p[3];
			int price = Integer.parseInt(p[4]);
			product = new Product(prdId, price, catName, prodName);
		} catch (SQLException e) {
			e.printStackTrace();
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
		return product;
	}

	public Set<Category> getCategories() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Category> categories = null;

		try {
			categories = new LinkedHashSet<Category>();
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
				categories.add(new Category(catId, catName));
			}
		} catch (Exception e) {
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
		return categories;
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
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception has occured");
		}
		catch (NoRecordsFoundException e) {
			throw e;
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
	
	public User getUser(int id) throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		User user = null;

		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select u.userId, u.userName, ut.userType from User AS u INNER JOIN UserType as ut ON u.userTypeId = ut.userTypeId where u.userId = ?");
			pt.setInt(1, id);
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
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception has occured");
		}
		catch (NoRecordsFoundException e) {
			throw e;
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

	public void addToWarehouse(int prodId, int supId, int qty) {
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
		} catch (SQLException e) {
			System.out.println("Foreign key constraint fails / Invalid ID");
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

	public void removeFromWarehouse(int prodId, int supId, int qty) throws SQLException {
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
				pt.setInt(1, prodId);
				pt.setInt(2, supId);
				pt.executeUpdate();
			} else {
				pt.close();
				pt = con.prepareStatement("Update Warehouse Set qty = qty - ? where prodId = ? AND userId = ?;");
				pt.setInt(1, qty);
				pt.setInt(2, prodId);
				pt.setInt(3, supId);
				pt.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;			
		}
		catch(NoRecordsFoundException e)
		{
			System.out.println(e.getMessage());
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
	
	public void addtoHistory(int userId, int prodId, String actionPerformed, int qty)
	{
		Connection con = null;
		PreparedStatement pt = null;	
		try
		{
			con = getConnection();
			pt = con.prepareStatement("Insert into History(userId, prodId, actionPerformed, qty) values(?,?,?,?)");
			pt.setInt(1, userId);
			pt.setInt(2, prodId);
			pt.setString(3, actionPerformed);
			pt.setInt(4, qty);
			pt.executeUpdate();
		}
		catch (SQLException e) 
		{
			System.out.println(e);
			e.printStackTrace();
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

	public Set<Warehouse> getWarehouseContent() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Warehouse> wh = null;
		try {
			wh = new LinkedHashSet<Warehouse>();
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
				wh.add(new Warehouse(prodName, supName, qty));

			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return wh;
	}

	public Set<Record> getRecords() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Record> records = null;
		try {
			records = new LinkedHashSet<Record>();
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
				records.add(new Record(transId, userName, prodName, qty, actionPerformed));			
				}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return records;
	}

	public Set<User> getUsers() throws NoRecordsFoundException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<User> users = null;
		try {
			users = new LinkedHashSet<User>();
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
				users.add(new User(userId, userName, userType));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return users;
	}
	
	public Set<Image> getImages() throws NoRecordsFoundException
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Image> images = null;
		try {
			images = new LinkedHashSet<Image>();
			con = getConnection();
			pt = con.prepareStatement("Select * from Image");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			rs.previous();
			String p[] = new String[3];
			while (rs.next()) {
				for (int i = 1; i <= 2; i++) {
					p[i] = rs.getString(i);
				}
				int imageId = Integer.parseInt(p[1]);
				String imageUrl = p[2];
				images.add(new Image(imageId, imageUrl));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return images;
		
	}
	
	public Set<Image> getImages(int prodId) throws NoRecordsFoundException
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Image> images = null;
		try {
			images = new LinkedHashSet<Image>();
			con = getConnection();
			pt = con.prepareStatement("Select i.imageId, i.imageUrl from Image as i INNER JOIN ProductImage as pi ON pi.imageId = i.imageId WHERE pi.prodId = ?");
			pt.setInt(1, prodId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			rs.previous();
			String p[] = new String[3];
			while (rs.next()) {
				for (int i = 1; i <= 2; i++) {
					p[i] = rs.getString(i);
				}
				int imageId = Integer.parseInt(p[1]);
				String imageUrl = p[2];
				images.add(new Image(imageId, imageUrl));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return images;
		
	}

	public Image getImage(int id) throws NoRecordsFoundException 
	{	
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Image image = null;
		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Image where imageId = ?");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			String p[] = new String[3];
			for (int i = 1; i <= 2; i++) 
			{
				p[i] = rs.getString(i);
			}
			int imageId = Integer.parseInt(p[1]);
			String imageUrl = p[2];
			image = new Image(imageId, imageUrl);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return image;
	}
	public int createImage(String url) {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into Image(imageUrl) values (?)");
			pt.setString(1, url);
			pt.executeUpdate();
			pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
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

	public int mapImageToProduct(int imgId, int prodId) {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into ProductImage(imageId, prodId) values (?,?)");
			pt.setInt(1, imgId);
			pt.setInt(2, prodId);
			pt.executeUpdate();
			pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
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
	
	public Set<Rating> getRatings(int prodId) throws NoRecordsFoundException 
	{	
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Rating> r = null;
		try {
			r = new LinkedHashSet<Rating>();
			con = getConnection();
			pt = con.prepareStatement("SELECT r.rating, u.userName, r.prodId FROM Rating as r INNER JOIN User as u ON r.userId = u.userid where prodId = ?");
			pt.setInt(1, prodId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			rs.previous();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) 
				{
					p[i] = rs.getString(i);
				}
				int rating = Integer.parseInt(p[1]);
				String userName = p[2];
				int prId = Integer.parseInt(p[3]);
				r.add(new Rating(rating,userName,prId));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return r;
	}
	
	public Set<Rating> getRatings() throws NoRecordsFoundException 
	{	
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Rating> r = null;
		try {
			r = new LinkedHashSet<Rating>();
			con = getConnection();
			pt = con.prepareStatement("SELECT r.rating, u.userName, r.prodId FROM Rating as r INNER JOIN User as u ON r.userId = u.userid;");
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			rs.previous();
			String p[] = new String[4];
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) 
				{
					p[i] = rs.getString(i);
				}
				int rating = Integer.parseInt(p[1]);
				String userName = p[2];
				int prId = Integer.parseInt(p[3]);
				r.add(new Rating(rating,userName,prId));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return r;
	}
	
	public Rating getRatings(int prodId, int userId) throws NoRecordsFoundException 
	{	
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Rating r = null;
		try {
			con = getConnection();
			pt = con.prepareStatement("SELECT r.rating, u.userName, r.prodId FROM Rating as r INNER JOIN User as u ON r.userId = u.userId where r.prodId = ? AND r.userId = ?");
			pt.setInt(1, prodId);
			pt.setInt(2, userId);
			rs = pt.executeQuery();
			if (!rs.next()) {
				throw new NoRecordsFoundException("No users registered");
			}
			String p[] = new String[5];
			for (int i = 1; i <= 3; i++) 
			{
				p[i] = rs.getString(i);
			}
			int rating = Integer.parseInt(p[1]);
			String userName = p[2];
			int prId = Integer.parseInt(p[3]);
			r = new Rating(rating,userName,prId);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return r;
	}
	
	
	public void createRating(int userId, int prodId, int rating) {
		Connection con = null;
		PreparedStatement pt = null;
		try {
			System.out.println(userId+""+prodId+""+rating);
			con = getConnection();
			pt = con.prepareStatement("Insert into Rating(rating, userId, prodId) values (?,?,?)");
			pt.setInt(1,rating);
			pt.setInt(2,userId);
			pt.setInt(3,prodId);
			pt.executeUpdate();
			pt.close();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return;
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



