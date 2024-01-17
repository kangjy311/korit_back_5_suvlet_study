package com.study.servlet_study.repository;

import java.util.ArrayList;
import java.util.List;

import com.study.servlet_study.entity.Product;

public class ProductRepository {
	private static ProductRepository instance;
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepository();
		}
		return instance;
	}
	
	private List<Product> productList;
	
	private ProductRepository() {
		productList = new ArrayList<>();
		
	}
	
	public int saveProduct(Product product) {
//		for(Product product2 : productList) {
//			if(product.getProductName().equals(product.getProductName()));
//			return 0;
//		} 
//		클린코드
		
		productList.add(product);
		return 1;
	}
	
	public Product findProductByproductName(String productName) {
		Product findProduct = null;
		for(Product product : productList) {
			if(product.getProductName().equals(productName)) {
				findProduct = product;
				break;
			}
		}
		return findProduct;
	}
}
