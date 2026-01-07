package com.stockmaster.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stockmaster.model.Order;
import com.stockmaster.service.OrderService;


public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private OrderService orderService;
    
    public OrderServlet() {
    	this.orderService = new OrderService();
    }
        
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
  	   
  	   switch (action) {
 		case "new":
 			showNewForm(request,response);
 			break;
 		case "add":
 			try {
				insertOrder(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			break;
 		case "delete":
 			try {
 				deleteOrder(request,response);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			break;
 		case "edit":
 			try {
 				showEditForm(request,response);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (ServletException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			break;
 		case "update":
 			try {
 				updateOrder(request,response);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			break;
 		default:
 			try {
 				listOrder(request,response);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (ServletException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			break;
 		}
 		
	}
	
	 private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   		RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
	   		dispatcher.forward(request,response);
	   	}
	   	
	       private void insertOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	    	    String order_Id = request.getParameter("order_Id");
	    	    String product_Id = request.getParameter("product_Id");

	    	    String unitPriceParam = request.getParameter("unitPrice");
	    	    float unitPrice = (unitPriceParam != null && !unitPriceParam.isEmpty()) ? Float.parseFloat(unitPriceParam) : 0.0f;

	    	    String quantityParam = request.getParameter("quantity");
	    	    int quantity = (quantityParam != null && !quantityParam.isEmpty()) ? Integer.parseInt(quantityParam) : 0;

	    	    String totalPriceParam = request.getParameter("totalPrice");
	    	    float totalPrice = (totalPriceParam != null && !totalPriceParam.isEmpty()) ? Float.parseFloat(totalPriceParam) : 0.0f;

	    	    Order newOrder = new Order(order_Id, product_Id, unitPrice, quantity, totalPrice);
	    	    orderService.insertOrder(newOrder);
	    	    response.sendRedirect("order?action=list");
	    	}

	   	
	   	private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	   		String order_Id = request.getParameter("order_Id");
	   		orderService.deleteOrder(order_Id);
	   		response.sendRedirect("order?action=list");
	   	}
	   	
	   	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
	   		String order_Id = request.getParameter("order_Id");
	   		Order existingOrder = orderService.selectOrder(order_Id);
	   		RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
	   		request.setAttribute("order", existingOrder);
	   		dispatcher.forward(request,response);
	   	}
	   	
	   	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	   		String order_Id = request.getParameter("order_Id");
	   		String product_Id = request.getParameter("product_Id");
	   		float unitPrice = Float.parseFloat(request.getParameter("unitPrice"));
	   		int quantity = Integer.parseInt(request.getParameter("quantity"));
	   		float totalPrice = Float.parseFloat(request.getParameter("totalPrice"));
	   		Order order = new Order(order_Id, product_Id, unitPrice, quantity, totalPrice);
	   		orderService.updateOrder(order);
	   		response.sendRedirect("order?action=list");
	   	}
	   	
	   	private void listOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
	   		List<Order> listOrder = orderService.selectAllOrder();
	   		request.setAttribute("listOrder", listOrder);
	   		RequestDispatcher dispatcher = request.getRequestDispatcher("order-list.jsp");
	   		dispatcher.forward(request,response);
	   	}

}
