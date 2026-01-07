package com.stockmaster.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stockmaster.model.Product;
import com.stockmaster.service.ProductService;

public class ProductServlet extends HttpServlet {
       private static final long serialVersionUID = 1L;
       private ProductService productService;
       
       
       public ProductServlet() {
    	   this.productService = new ProductService();
       }
       
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   this.doGet(request,response);
       }
       
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   String action = request.getParameter("action");
    	   
    	   
    	   switch (action) {
   		case "/new":
   			showNewForm(request,response);
   			break;
   		case "/add":
   			try {
				insertProduct(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   			break;
   		case "/delete":
   			try {
   				deleteProduct(request,response);
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			} catch (IOException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			break;
   		case "/edit":
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
   		case "/update":
   			try {
   				updateProduct(request,response);
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
   				listProduct(request,response);
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
   		RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
   		dispatcher.forward(request,response);
   	}
   	
       private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	    String product_Id = request.getParameter("product_Id");
    	    String productName = request.getParameter("productName");
    	    String category = request.getParameter("category");

    	    String priceParam = request.getParameter("price");
    	    float price = (priceParam != null && !priceParam.isEmpty()) ? Float.parseFloat(priceParam) : 0.0f;

    	    String stockQuantityParam = request.getParameter("stockQuantity");
    	    int stockQuantity = (stockQuantityParam != null && !stockQuantityParam.isEmpty()) ? Integer.parseInt(stockQuantityParam) : 0;

    	    String supplier_Id = request.getParameter("supplier_Id");

    	    Product newProduct = new Product(product_Id, productName, category, price, stockQuantity, supplier_Id);
    	    productService.insertProduct(newProduct);
    	    response.sendRedirect("product?action=list");
    	}

   	
   	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
   		String product_Id = request.getParameter("product_Id");
   		productService.deleteProduct(product_Id);
   		response.sendRedirect("product?action=list");
   	}
   	
   	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
   		String product_Id = request.getParameter("product_Id");
   		Product existingProduct = productService.selectProduct(product_Id);
   		RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
   		request.setAttribute("product", existingProduct);
   		dispatcher.forward(request,response);
   	}
   	
   	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
   		String product_Id = request.getParameter("product_Id");
   		String productName = request.getParameter("productName");
   		String category = request.getParameter("category");
   		float price = Float.parseFloat(request.getParameter("price"));
   		int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
   		String supplier_Id = request.getParameter("supplier_Id");
   		Product product = new Product(product_Id, productName, category, price, stockQuantity, supplier_Id);
   		productService.updateProduct(product);
   		response.sendRedirect("product?action=list");
   	}
   	
   	private void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
   		List<Product> listProduct = productService.selectAllProduct();
   		request.setAttribute("listProduct", listProduct);
   		RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
   		dispatcher.forward(request,response);
   	}

	
}
