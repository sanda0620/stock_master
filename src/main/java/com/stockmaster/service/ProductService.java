package com.stockmaster.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.stockmaster.model.Product;

public class ProductService {

	private static String url = "jdbc:mysql://localhost:3306/stockmanagement";
	private static String userName = "root";
	private static String password = "25&heW03";
	
	private static final String Insert_Product_Sql = "insert into product" + "(product_Id, productName, category, price, stockQuantity, supplier_Id) values"
	                                                +"(?, ?, ?, ?, ?, ?);";
	private static final String Select_Product_By_Id = "select product_Id, productName, category, price, stockQuantity, supplier_Id from product where product_Id=?";
	private static final String Select_All_Product = "select * from product";
	private static final String Update_Product_Sql = "update product set productName=?, category=?, price=?, stockQuantity=?, supplier_Id=? where product_Id=?;";
	private static final String Delete_Product_Sql = "delete from product where product_Id=?;";
	
	private static Connection con;
	protected static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, password);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	  }

	//create or insert a product
	public void insertProduct(Product product) throws SQLException {
		try(Connection con = getConnection();
		   PreparedStatement preparedStatement = con.prepareStatement(Insert_Product_Sql);){
		   preparedStatement.setString(1, product.getProduct_Id());
		   preparedStatement.setString(2, product.getProductName());
		   preparedStatement.setString(3, product.getCategory());
		   preparedStatement.setFloat(4, product.getPrice());
		   preparedStatement.setInt(5, product.getStockQuantity());
		   preparedStatement.setString(6, product.getSupplier_Id()); 
		   preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 //update a product
	public boolean updateProduct(Product product) throws SQLException {
		boolean rowUpdated;
		try(Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement(Update_Product_Sql);){
			statement.setString(1, product.getProductName());
			statement.setString(2, product.getCategory());
			statement.setFloat(3, product.getPrice());
			statement.setInt(4, product.getStockQuantity());
			statement.setString(5, product.getSupplier_Id()); 
			statement.setString(6, product.getProduct_Id());
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	
	///select supplier by ID
   public Product selectProduct (String product_Id) {
	   Product product = null;
	   try(Connection con = getConnection();
			   PreparedStatement preparedstatement = con.prepareStatement(Select_Product_By_Id);){
		       preparedstatement.setString(1, product_Id);
		       System.out.println(preparedstatement);
		       ResultSet rs = preparedstatement.executeQuery();
		       
		       while(rs.next()) {
		    	   String productName = rs.getString("productName");
		    	   String category = rs.getString("category");
		    	   float price = rs.getFloat("price");
		    	   int stockQuantity = rs.getInt("stockQuantity");
		    	   String supplier_Id = rs.getString("supplier_Id");
		    	   product = new Product(product_Id, productName, category, price, stockQuantity, supplier_Id);
		    	   
		       }
	     }
	    catch (SQLException e) {
	    	e.printStackTrace();
	    }
	   return product;
   }
   
   
   //select product
   public List<Product> selectAllProduct(){
	   List<Product> products = new ArrayList<>();
	   try(Connection con = getConnection();
		  PreparedStatement preparedstatement = con.prepareStatement(Select_All_Product);){
		   System.out.println(preparedstatement);
		   ResultSet rs = preparedstatement.executeQuery();
		   
		   while(rs.next()) {
			   String product_Id = rs.getString("product_Id");
			   String productName = rs.getString("productName");
			   String category = rs.getString("category");
			   float price = rs.getFloat("price");
	    	   int stockQuantity = rs.getInt("stockQuantity");
	    	   String supplier_Id = rs.getString("supplier_Id");
	    	   products.add(new Product(product_Id, productName, category, price, stockQuantity, supplier_Id));
	    	   
		   }
	   }
	   
	   catch (SQLException e) {
		   e.printStackTrace();
	   }
	   return products;
   }
   
   
   //delete product
   public boolean deleteProduct(String product_Id) throws SQLException {
	   boolean rowDeleted;
	   try(Connection con = getConnection();
		   PreparedStatement statement = con.prepareStatement(Delete_Product_Sql);){
		      statement.setString(1, product_Id);
		      rowDeleted = statement.executeUpdate() > 0;
		   }
	   return rowDeleted;
   }
   
   
	
}









