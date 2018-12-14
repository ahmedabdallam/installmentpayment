package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.model.Product;

@Repository
public class ProductDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String addProduct = "INSERT INTO PRODUCT (ID,PRODUCT_NAME,PRODUCT_PRICE,STORE_ID) "
			+ "VALUES(PRODUCT_SEQ.NEXTVAL,?,?,?)";

	public void addProduct(Product product) {
		jdbcTemplate.update(addProduct, product.getName(), product.getProductPrice(), product.getStoreId());
	}

	public String getProductPrice(int productId) {
		String price = (String) jdbcTemplate.queryForObject("SELECT PRODUCT_PRICE FROM PRODUCT WHERE ID=?",
				new Object[] { productId }, String.class);
		System.out.println("Price is >>" + price);
		return price;
	}

	public int getProductByName(String productName) {
		int productId = (Integer) jdbcTemplate.queryForObject("SELECT ID FROM PRODUCT WHERE PRODUCT_NAME=?",
				new Object[] { productName }, Integer.class);
		return productId;
	}
}
