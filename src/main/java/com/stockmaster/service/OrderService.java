package com.stockmaster.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.stockmaster.model.Order;

public class OrderService {
	
	private static String url = "jdbc:mysql://localhost:3306/stockmanagement";
	private static String userName = "root";
	private static String password = "25&heW03";
	
	private static final String Insert_Order_Sql = "insert into order_product" + "(order_Id, product_Id, unitprice, quantity, totalPrice) values"
	                                                +"(?, ?, ?, ?, ?);";
	private static final String Select_Order_By_Id = "select order_Id, product_Id, unitprice, quantity, totalPrice from order_product where order_Id=?";
	private static final String Select_All_Orders = "select * from order_product";
	private static final String Update_Order_Sql = "update order_product set product_Id=?, unitprice=?, quantity=?, totalPrice=? where order_Id=?;";
	private static final String Delete_Order_Sql = "delete from order_product where order_Id=?;";
	
	private static Connection con;
	protected static Connection getConnection() {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection(url, userName, password);
	    } catch (SQLException e) {
	        System.out.println("SQL Exception while getting connection: " + e.getMessage());
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        System.out.println("JDBC Driver not found: " + e.getMessage());
	        e.printStackTrace();
	    } 
	    return con;
	}

	//create or insert a order
	public void insertOrder(Order order) throws SQLException {
		try(Connection con = getConnection();
		   PreparedStatement preparedStatement = con.prepareStatement(Insert_Order_Sql);){
		   preparedStatement.setString(1, order.getOrder_Id());
		   preparedStatement.setString(2, order.getProduct_Id());
		   preparedStatement.setFloat(3, order.getUnitPrice());
		   preparedStatement.setInt(4, order.getQuantity());
		   preparedStatement.setFloat(5, order.getTotalPrice()); 
		   preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 //update a order
	public boolean updateOrder(Order order) throws SQLException {
		boolean rowUpdated;
		try(Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement(Update_Order_Sql);){
			statement.setString(1, order.getProduct_Id());
			statement.setFloat(2, order.getUnitPrice());
			statement.setInt(3, order.getQuantity());
			statement.setFloat(4, order.getTotalPrice()); 
			statement.setString(5, order.getOrder_Id());
			
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	
	///select order by ID
   public Order selectOrder (String order_Id) {
	   Order order = null;
	   try(Connection con = getConnection();
			   PreparedStatement preparedstatement = con.prepareStatement(Select_Order_By_Id);){
		       preparedstatement.setString(1, order_Id);
		       System.out.println(preparedstatement);
		       ResultSet rs = preparedstatement.executeQuery();
		       
		       while(rs.next()) {
		    	   String product_Id = rs.getString("product_Id");
		    	   float unitPrice = rs.getFloat("unitPrice");
		    	   int quantity = rs.getInt("quantity");
		    	   float totalPrice = rs.getFloat("totalPrice");
		    	   order = new Order(order_Id, product_Id, unitPrice, quantity, totalPrice);
		    	   
		       }
	     }
	    catch (SQLException e) {
	    	e.printStackTrace();
	    }
	   return order;
   }
   
   
   //select orders
   public List<Order> selectAllOrder(){
	   List<Order> orders = new ArrayList<>();
	   try(Connection con = getConnection();
		  PreparedStatement preparedstatement = con.prepareStatement(Select_All_Orders);){
		   System.out.println(preparedstatement);
		   ResultSet rs = preparedstatement.executeQuery();
		   
		   while(rs.next()) {
			   String order_Id = rs.getString("order_Id");
			   String product_Id = rs.getString("product_Id");
			   float unitprice = rs.getFloat("unitprice");
	    	   int quantity = rs.getInt("quantity");
	    	   Float totalPrice = rs.getFloat("totalPrice");
	    	   orders.add(new Order(order_Id, product_Id, unitprice, quantity, totalPrice));
	    	   
		   }
	   }
	   
	   catch (SQLException e) {
		   e.printStackTrace();
	   }
	   return orders;
   }
   
   
   //delete order
   public boolean deleteOrder(String order_Id) throws SQLException {
	   boolean rowDeleted;
	   try(Connection con = getConnection();
		   PreparedStatement statement = con.prepareStatement(Delete_Order_Sql);){
		      statement.setString(1, order_Id);
		      rowDeleted = statement.executeUpdate() > 0;
		   }
	   return rowDeleted;
   }

}
