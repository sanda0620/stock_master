package com.stockmaster.model;

public class Product {
	
		private String product_Id;
	    private String productName;
	    private String category;
	    private float price;
	    private int stockQuantity;
	    private String supplier_Id;
	    
	    
	    
		public Product(String product_Id, String productName, String category, float price, int stockQuantity,String supplier_Id) {
			this.product_Id = product_Id;
			this.productName = productName;
			this.category = category;
			this.price = price;
			this.stockQuantity = stockQuantity;
			this.supplier_Id = supplier_Id;
		}
		
		
		
		public String getProduct_Id() {
			return product_Id;
		}
		public void setProduct_Id(String product_Id) {
			this.product_Id = product_Id;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		public int getStockQuantity() {
			return stockQuantity;
		}
		public void setStockQuantity(int stockQuantity) {
			this.stockQuantity = stockQuantity;
		}
		public String getSupplier_Id() {
			return supplier_Id;
		}
		public void setSupplier_Id(String supplier_Id) {
			this.supplier_Id = supplier_Id;
		}
	    
}
